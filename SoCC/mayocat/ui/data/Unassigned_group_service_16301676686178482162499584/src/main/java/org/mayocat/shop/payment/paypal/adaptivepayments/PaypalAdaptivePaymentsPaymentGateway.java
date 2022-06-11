/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.shop.payment.paypal.adaptivepayments;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.*;
import org.mayocat.shop.payment.BasePaymentData;
import org.mayocat.shop.payment.GatewayException;
import org.mayocat.shop.payment.GatewayResponse;
import org.mayocat.shop.payment.PaymentData;
import org.mayocat.shop.payment.PaymentGateway;
import org.mayocat.shop.payment.api.resources.PaymentResource;
import org.mayocat.shop.payment.model.PaymentOperation;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import com.paypal.core.ConfigManager;
import com.paypal.ipn.IPNMessage;
import com.paypal.svcs.services.AdaptivePaymentsService;
import com.paypal.svcs.types.ap.DisplayOptions;
import com.paypal.svcs.types.ap.PayRequest;
import com.paypal.svcs.types.ap.PayResponse;
import com.paypal.svcs.types.ap.Receiver;
import com.paypal.svcs.types.ap.ReceiverList;
import com.paypal.svcs.types.ap.SetPaymentOptionsRequest;
import com.paypal.svcs.types.ap.SetPaymentOptionsResponse;
import com.paypal.svcs.types.common.ClientDetailsType;
import com.paypal.svcs.types.common.RequestEnvelope;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: ae0772f38b5e30ea8beff6a1cea9c9cd37922d99 $
 */
public class PaypalAdaptivePaymentsPaymentGateway implements PaymentGateway {

    public PaypalAdaptivePaymentsPaymentGateway(InputStream configInputStream, String receiverEmail) {
        String uri = MicroserviceApplication.projectUri + "/msnull/paypaladaptivepaymentspaymentgateway/paypaladaptivepaymentspaymentgateway?callerId=" + this.id + "&configInputStreamId=configInputStream.id&receiverEmail=receiverEmail";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        this.id = resultTemp;
    }

    @Override
    public GatewayResponse purchase(BigDecimal amount, Map<PaymentData, Object> options) throws GatewayException {
        return (GatewayResponse) null;
    }

    public GatewayResponse acknowledge(UUID orderId, Map<String, List<String>> data) throws GatewayException {
        return (GatewayResponse) null;
    }

    public GatewayResponse acknowledge(Map<String, List<String>> data) throws GatewayException {
        return (GatewayResponse) null;
    }

    public GatewayResponse callback(Map<String, List<String>> data) throws GatewayException {
        return (GatewayResponse) null;
    }

    private Map<String, String[]> convertDataMap(Map<String, List<String>> data) {
        return (Map) null;
    }

    public int id = 0;

    public static PaypalAdaptivePaymentsPaymentGateway getObject(int id) {
        PaypalAdaptivePaymentsPaymentGateway obj = (PaypalAdaptivePaymentsPaymentGateway) new Object();
        obj.id = id;
        return obj;
    }
}

