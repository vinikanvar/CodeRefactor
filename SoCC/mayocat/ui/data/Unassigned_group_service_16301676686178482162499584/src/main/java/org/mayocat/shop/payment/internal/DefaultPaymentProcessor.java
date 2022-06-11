/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.shop.payment.internal;

import com.google.common.base.Strings;
import com.google.common.collect.ImmutableMap;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Provider;
import org.mayocat.configuration.ConfigurationService;
import org.mayocat.context.WebContext;
import org.mayocat.shop.billing.model.Order;
import org.mayocat.shop.billing.store.OrderStore;
import org.mayocat.shop.payment.BasePaymentData;
import org.mayocat.shop.payment.ConfigurationException;
import org.mayocat.shop.payment.CreditCardPaymentData;
import org.mayocat.shop.payment.CreditCardPaymentGateway;
import org.mayocat.shop.payment.GatewayException;
import org.mayocat.shop.payment.GatewayFactory;
import org.mayocat.shop.payment.GatewayResponse;
import org.mayocat.shop.payment.PaymentData;
import org.mayocat.shop.payment.PaymentException;
import org.mayocat.shop.payment.PaymentGateway;
import org.mayocat.shop.payment.PaymentProcessor;
import org.mayocat.shop.payment.PaymentRequest;
import org.mayocat.shop.payment.PaymentStatus;
import org.mayocat.shop.payment.PaymentsSettings;
import org.mayocat.shop.payment.RequiredAction;
import org.mayocat.shop.payment.event.ExternalPaymentInitialized;
import org.mayocat.shop.payment.event.PaymentOperationEvent;
import org.mayocat.shop.payment.model.PaymentOperation;
import org.mayocat.shop.payment.store.PaymentOperationStore;
import org.mayocat.store.EntityAlreadyExistsException;
import org.mayocat.store.EntityDoesNotExistException;
import org.mayocat.store.InvalidEntityException;
import org.mayocat.url.URLHelper;
import org.slf4j.Logger;
import org.xwiki.component.annotation.Component;
import org.xwiki.observation.ObservationManager;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: 48afcbb148468cd2729e12f53ca3a2d337141d4e $
 */
@Component
public class DefaultPaymentProcessor implements PaymentProcessor {

    @Override
    public PaymentRequest requestPayment(Order order, Map<PaymentData, Object> data) throws PaymentException {
        return (PaymentRequest) null;
    }

    private static boolean dataContainsCreditCard(Map<PaymentData, Object> data) {
        return (Boolean) null;
    }

    public int id = 0;

    public static DefaultPaymentProcessor getObject(int id) {
        DefaultPaymentProcessor obj = (DefaultPaymentProcessor) new Object();
        obj.id = id;
        return obj;
    }
}

