/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.shop.checkout.internal;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.inject.Inject;
import javax.inject.Provider;
import org.mayocat.accounts.model.Tenant;
import org.mayocat.accounts.store.TenantStore;
import org.mayocat.configuration.ConfigurationService;
import org.mayocat.configuration.PlatformSettings;
import org.mayocat.configuration.SiteSettings;
import org.mayocat.context.WebContext;
import org.mayocat.entity.EntityDataLoader;
import org.mayocat.shop.billing.model.Order;
import org.mayocat.shop.billing.model.OrderItem;
import org.mayocat.shop.billing.store.OrderStore;
import org.mayocat.shop.cart.Cart;
import org.mayocat.shop.cart.CartBuilder;
import org.mayocat.shop.cart.CartItemBuilder;
import org.mayocat.shop.cart.CartManager;
import org.mayocat.shop.catalog.configuration.shop.CatalogSettings;
import org.mayocat.shop.catalog.model.Purchasable;
import org.mayocat.shop.catalog.store.ProductStore;
import org.mayocat.shop.checkout.CheckoutException;
import org.mayocat.shop.checkout.CheckoutRegister;
import org.mayocat.shop.checkout.CheckoutRequest;
import org.mayocat.shop.checkout.CheckoutResponse;
import org.mayocat.shop.checkout.RegularCheckoutException;
import org.mayocat.shop.checkout.front.CheckoutResource;
import org.mayocat.shop.customer.model.Address;
import org.mayocat.shop.customer.model.Customer;
import org.mayocat.shop.customer.store.AddressStore;
import org.mayocat.shop.customer.store.CustomerStore;
import org.mayocat.shop.payment.BasePaymentData;
import org.mayocat.shop.payment.PaymentData;
import org.mayocat.shop.payment.PaymentException;
import org.mayocat.shop.payment.PaymentProcessor;
import org.mayocat.shop.payment.PaymentRequest;
import org.mayocat.shop.shipping.ShippingOption;
import org.mayocat.shop.shipping.ShippingService;
import org.mayocat.shop.shipping.model.Carrier;
import org.mayocat.shop.taxes.PriceWithTaxes;
import org.mayocat.shop.taxes.Taxable;
import org.mayocat.shop.taxes.TaxesService;
import org.mayocat.shop.taxes.configuration.TaxesSettings;
import org.mayocat.store.EntityAlreadyExistsException;
import org.mayocat.store.EntityDoesNotExistException;
import org.mayocat.store.InvalidEntityException;
import org.mayocat.url.URLHelper;
import org.slf4j.Logger;
import org.xwiki.component.annotation.Component;

/**
 * @version $Id: 75e7d99bf1706ba2ac9ac03b6a4810c214ecc964 $
 */
@Component
public class DefaultCheckoutRegisterServer implements CheckoutRegister {

    @Inject
    private Logger logger;

    @Inject
    private EntityDataLoader dataLoader;

    @Inject
    private PlatformSettings platformSettings;

    @Inject
    private TaxesSettings taxesSettings;

    @Inject
    private Provider<OrderStore> orderStore;

    @Inject
    private Provider<CustomerStore> customerStore;

    @Inject
    private Provider<AddressStore> addressStore;

    @Inject
    private Provider<ProductStore> productStore;

    @Inject
    private Provider<TenantStore> tenantStore;

    @Inject
    private ShippingService shippingService;

    @Inject
    private CartManager cartManager;

    @Inject
    private WebContext webContext;

    @Inject
    private SiteSettings siteSettings;

    @Inject
    private URLHelper urlHelper;

    @Inject
    private ConfigurationService configurationService;

    @Inject
    private TaxesService taxesService;

    @Inject
    private PaymentProcessor paymentProcessor;

    @Override
    public CheckoutResponse directCheckout(CheckoutRequest request, final Taxable taxable, final Long quantity) throws CheckoutException {
        List<ShippingOption> options = shippingService.getOptions(new HashMap<Purchasable, Long>() {

            {
                put(taxable, quantity);
            }
        });
        if (options.size() > 1) {
            throw new CheckoutException("Can't direct checkout when there is more than one shipping option");
        }
        CartBuilder builder = new CartBuilder();
        CartItemBuilder itemBuilder = new CartItemBuilder();
        PriceWithTaxes itemsTotal = new PriceWithTaxes(BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
        PriceWithTaxes itemUnit = taxesService.getPriceWithTaxes(taxable);
        itemsTotal = itemsTotal.add(itemUnit.multiply(quantity));
        CatalogSettings catalogSettings = configurationService.getSettings(CatalogSettings.class);
        builder.currency(catalogSettings.getCurrencies().getMainCurrency().getValue());
        itemBuilder.item(taxable).quantity(quantity).unitPrice(itemUnit);
        if (webContext.getTenant() != null) {
            itemBuilder.tenant(webContext.getTenant());
        } else {
            Tenant tenant = tenantStore.get().findById(taxable.getTenantId());
            if (tenant == null) {
                logger.error("Cannot load purchasable for which the tenant can't be found");
                throw new CheckoutException();
            }
            itemBuilder.tenant(tenant);
        }
        builder.addItem(itemBuilder.build());
        builder.itemsTotal(itemsTotal);
        if (options.size() > 0) {
            builder.setShipping(options.get(0).getPrice());
            builder.selectedShippingOption(options.get(0));
        }
        return this.doCheckoutInternal(request, builder.build());
    }

    @Override
    public CheckoutResponse checkoutCart(CheckoutRequest request) throws CheckoutException {
        return this.doCheckoutInternal(request, cartManager.getCart());
    }

    @Override
    public void dropOrder(UUID orderId) throws CheckoutException {
        Order order = orderStore.get().findById(orderId);
        if (order == null) {
            throw new RegularCheckoutException("Order with id [" + orderId.toString() + "] does not exist.");
        }
        try {
            order.setStatus(Order.Status.CANCELLED);
            orderStore.get().update(order);
        } catch (EntityDoesNotExistException | InvalidEntityException e) {
            throw new CheckoutException(e);
        }
    }

    // ---------------------------------------------------------------------------------------------
    private CheckoutResponse doCheckoutInternal(CheckoutRequest request, Cart cart) throws CheckoutException {
        Customer customer = request.getCustomer();
        Address deliveryAddress = request.getDeliveryAddress();
        Address billingAddress = request.getBillingAddress();
        Map<String, Object> extraOrderData = request.getOtherOrderData();
        Preconditions.checkNotNull(customer);
        Order order;
        Customer actualCustomer;
        try {
            UUID customerId;
            UUID deliveryAddressId = null;
            UUID billingAddressId = null;
            // Order JSON data
            Map<String, Object> data = Maps.newHashMap();
            if (customer.getId() == null) {
                // This is a new customer
                actualCustomer = createCustomer(customer);
                customerId = actualCustomer.getId();
                if (deliveryAddress != null) {
                    deliveryAddress = this.addressStore.get().create(deliveryAddress);
                    deliveryAddressId = deliveryAddress.getId();
                }
                if (billingAddress != null) {
                    billingAddress = this.addressStore.get().create(billingAddress);
                    billingAddressId = billingAddress.getId();
                }
            } else {
                // Customer already exists in database
                actualCustomer = customer;
                customerId = customer.getId();
                deliveryAddressId = deliveryAddress.getId();
                if (billingAddress != null) {
                    billingAddressId = billingAddress.getId();
                }
            }
            order = createOrder(extraOrderData, cart, customerId, deliveryAddressId, billingAddressId, data);
        } catch (EntityAlreadyExistsException | EntityDoesNotExistException | InvalidEntityException e) {
            throw new CheckoutException(e);
        }
        Map<PaymentData, Object> data = preparePaymentData(deliveryAddress, billingAddress, order, actualCustomer, cart.currency(), Optional.<String>absent());
        return doGatewayCheckout(order, data);
    }

    private CheckoutResponse doGatewayCheckout(Order order, Map<PaymentData, Object> data) throws CheckoutException {
        try {
            PaymentRequest request = paymentProcessor.requestPayment(order, data);
            return new CheckoutResponse(order, request);
        } catch (PaymentException e) {
            this.logger.error("Payment error while checking out cart", e);
            throw new CheckoutException(e);
        }
    }

    private Map<PaymentData, Object> preparePaymentData(Address deliveryAddress, Address billingAddress, Order order, Customer actualCustomer, Currency currency, Optional<String> gatewayId) {
        Map<PaymentData, Object> data = Maps.newHashMap();
        data.put(BasePaymentData.CURRENCY, currency);
        data.put(BasePaymentData.ORDER_ID, order.getId());
        data.put(BasePaymentData.ORDER, order);
        data.put(BasePaymentData.CUSTOMER, actualCustomer);
        data.put(BasePaymentData.DELIVERY_ADDRESS, deliveryAddress);
        if (billingAddress != null) {
            data.put(BasePaymentData.BILLING_ADDRESS, billingAddress);
        }
        // Sets a couple of URL that can be useful for payment gateways
        data.put(BasePaymentData.BASE_WEB_URL, urlHelper.getContextWebBaseURL());
        data.put(BasePaymentData.BASE_PLATFORM_URL, urlHelper.getContextPlatformBaseURL());
        // Cancel URL -> return for the customer from the gateway to the website when cancelling
        data.put(BasePaymentData.CANCEL_URL, urlHelper.getContextWebURL(CheckoutResource.PATH + "/" + CheckoutResource.PAYMENT_CANCEL_PATH + "/" + order.getId()).toString());
        // Return URL -> return for the customer from the gateway to the website after payment
        data.put(BasePaymentData.RETURN_URL, urlHelper.getContextWebURL(CheckoutResource.PATH + "/" + CheckoutResource.PAYMENT_RETURN_PATH + "/" + order.getId()).toString());
        if (gatewayId.isPresent()) {
            data.put(BasePaymentData.GATEWAY, gatewayId.get());
        }
        return data;
    }

    private Customer createCustomer(Customer customer) throws EntityAlreadyExistsException, InvalidEntityException, EntityDoesNotExistException {
        Customer actualCustomer;
        customer.setSlug(customer.getEmail());
        if (this.customerStore.get().findBySlug(customer.getEmail()) == null) {
            actualCustomer = this.customerStore.get().create(customer);
        } else {
            Customer existingCustomer = this.customerStore.get().findBySlug(customer.getEmail());
            boolean update = updateCustomerIfNecessary(existingCustomer, customer);
            if (update) {
                this.customerStore.get().update(existingCustomer);
            }
            actualCustomer = existingCustomer;
        }
        return actualCustomer;
    }

    private Order createOrder(Map<String, Object> extraOrderData, Cart cart, UUID customerId, UUID deliveryAddressId, UUID billingAddressId, Map<String, Object> data) throws EntityAlreadyExistsException, InvalidEntityException {
        Order order;
        order = new Order();
        order.setBillingAddressId(billingAddressId);
        order.setDeliveryAddressId(deliveryAddressId);
        order.setCustomerId(customerId);
        if (extraOrderData.containsKey("additionalInformation")) {
            order.setAdditionalInformation((String) extraOrderData.get("additionalInformation"));
        }
        if (extraOrderData.containsKey("extraData")) {
            data.put(Order.ORDER_DATA_EXTRA, extraOrderData.get("extraData"));
        }
        // Items
        List<OrderItem> items = FluentIterable.from(cart.items()).transform(new CartItemToOrderItemTransformer(dataLoader, platformSettings, productStore.get(), taxesSettings, webContext)).toList();
        order.setItemsTotal(cart.itemsTotal().incl());
        order.setItemsTotalExcl(cart.itemsTotal().excl());
        order.setOrderItems(items);
        order.setNumberOfItems(Long.valueOf(items.size()));
        // Shipping
        if (cart.selectedShippingOption().isPresent()) {
            final Carrier carrier = shippingService.getCarrier(cart.selectedShippingOption().get().getCarrierId());
            order.setShipping(cart.selectedShippingOption().get().getPrice().incl());
            order.setShippingExcl(cart.selectedShippingOption().get().getPrice().excl());
            data.put(Order.ORDER_DATA_SHIPPING, new HashMap<String, Object>() {

                {
                    put("carrierId", carrier.getId());
                    put("title", carrier.getTitle());
                    put("strategy", carrier.getStrategy());
                    put("vatRate", carrier.getVatRate().doubleValue());
                }
            });
        }
        // Dates, currency, status
        order.setCreationDate(new Date());
        order.setUpdateDate(order.getCreationDate());
        order.setCurrency(cart.currency());
        order.setStatus(Order.Status.NONE);
        // Grand total
        order.setGrandTotal(cart.total().incl());
        order.setGrandTotalExcl(cart.total().excl());
        // JSON data
        order.setOrderData(data);
        order = orderStore.get().create(order);
        return order;
    }

    private boolean updateCustomerIfNecessary(Customer existingCustomer, Customer customer) {
        boolean update = false;
        if (existingCustomer.getFirstName() == null || !existingCustomer.getFirstName().equals(customer.getFirstName())) {
            update = true;
            existingCustomer.setFirstName(customer.getFirstName());
        }
        if (existingCustomer.getLastName() == null || !existingCustomer.getLastName().equals(customer.getLastName())) {
            update = true;
            existingCustomer.setLastName(customer.getLastName());
        }
        if (existingCustomer.getPhoneNumber() == null || !existingCustomer.getPhoneNumber().equals(customer.getPhoneNumber())) {
            update = true;
            existingCustomer.setPhoneNumber(customer.getPhoneNumber());
        }
        if (existingCustomer.getCompany() == null || !existingCustomer.getCompany().equals(customer.getCompany())) {
            update = true;
            existingCustomer.setCompany(customer.getCompany());
        }
        return update;
    }
}

