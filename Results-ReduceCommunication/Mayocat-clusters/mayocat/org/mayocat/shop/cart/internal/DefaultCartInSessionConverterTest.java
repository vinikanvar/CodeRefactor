/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.shop.cart.internal;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.UUID;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mayocat.localization.EntityLocalizationService;
import org.mayocat.model.Localized;
import org.mayocat.shop.cart.CartContents;
import org.mayocat.shop.cart.CartInSession;
import org.mayocat.shop.catalog.model.Product;
import org.mayocat.shop.catalog.store.ProductStore;
import org.mayocat.shop.shipping.ShippingOption;
import org.mayocat.shop.taxes.PriceWithTaxes;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.xwiki.component.manager.ComponentLookupException;
import org.xwiki.test.mockito.MockitoComponentMockingRule;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;

/**
 * @version $Id: f112698472dbce8c70ddb6cb3c3d7eb384557608 $
 */
public class DefaultCartInSessionConverterTest {

    @Rule
    public final MockitoComponentMockingRule<DefaultCartInSessionConverter> componentManager = new MockitoComponentMockingRule(DefaultCartInSessionConverter.class);

    private Product p1;

    private Product p2;

    @Before
    public void configure() throws Exception {
        final ProductStore productStore = this.componentManager.getInstance(ProductStore.class);
        final EntityLocalizationService localizationService = this.componentManager.getInstance(EntityLocalizationService.class);
        UUID id1 = UUID.randomUUID();
        p1 = new Product();
        p1.setId(id1);
        p1.setPrice(BigDecimal.ONE);
        UUID id2 = UUID.randomUUID();
        p2 = new Product();
        p2.setId(id2);
        p2.setPrice(BigDecimal.TEN);
        when(productStore.findById(id1)).thenReturn(p1);
        when(productStore.findById(id2)).thenReturn(p2);
        when(localizationService.localize(Matchers.<Localized>anyObject())).then(new Answer<Localized>() {

            @Override
            public Localized answer(InvocationOnMock invocationOnMock) throws Throwable {
                Object[] args = invocationOnMock.getArguments();
                return (Localized) args[0];
            }
        });
    }

    @Test
    public void testConversion() throws ComponentLookupException {
        CartContents cartContents = new CartContents(Currency.getInstance("EUR"));
        cartContents.addItem(p1, 4l);
        cartContents.addItem(p2, 3l);
        ShippingOption shippingOption = new ShippingOption(UUID.randomUUID(), "International Space Station", new PriceWithTaxes(BigDecimal.ONE, BigDecimal.ONE, BigDecimal.ZERO));
        cartContents.setSelectedShippingOption(shippingOption);
        // Do the Cart ))<>(( CartInSession back and forth
        CartInSession inSession = componentManager.getComponentUnderTest().convertToCartInSession(cartContents);
        Assert.assertEquals(2, inSession.getItems().size());
        CartContents retrieved = componentManager.getComponentUnderTest().loadFromCartInSession(inSession);
        Assert.assertEquals(BigDecimal.valueOf(34), retrieved.getItemsTotal());
        Assert.assertEquals(BigDecimal.valueOf(35), retrieved.getTotal());
        Assert.assertEquals(cartContents, retrieved);
    }
}
