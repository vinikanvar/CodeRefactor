/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.shop.payment.api.resources;

import com.google.common.base.Strings;
import java.net.URI;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import org.mayocat.rest.Resource;
import org.mayocat.rest.annotation.ExistingTenant;
import org.mayocat.shop.payment.GatewayException;
import org.mayocat.shop.payment.GatewayFactory;
import org.mayocat.shop.payment.GatewayResponse;
import org.mayocat.shop.payment.PaymentGateway;
import org.mayocat.shop.payment.event.PaymentOperationEvent;
import org.mayocat.shop.payment.model.PaymentOperation;
import org.mayocat.shop.payment.store.PaymentOperationStore;
import org.mayocat.store.EntityAlreadyExistsException;
import org.mayocat.store.InvalidEntityException;
import org.slf4j.Logger;
import org.xwiki.component.annotation.Component;
import org.xwiki.observation.ObservationManager;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.UUID;

/**
 * @version $Id: ab55dd1d74830f61ab1f6bb846dae90353561a02 $
 */
@Component(PaymentResource.PATH)
@Path(PaymentResource.PATH)
@Produces(MediaType.WILDCARD)
@Consumes(MediaType.WILDCARD)
@ExistingTenant
public class PaymentResource implements Resource {

    public static final String PATH = "payment";

    public static final String ACKNOWLEDGEMENT_PATH = "acknowledgement";

    @Inject
    private Map<String, GatewayFactory> gatewayFactories;

    @Inject
    private Provider<PaymentOperationStore> paymentOperationStore;

    @Inject
    private ObservationManager observationManager;

    @Inject
    private Logger logger;

    @POST
    @Path("{orderId}/" + ACKNOWLEDGEMENT_PATH + "/{gatewayId}")
    public Response acknowledgePayment(@PathParam("gatewayId") String gatewayId, @PathParam("orderId") UUID orderId, MultivaluedMap<String, String> data) {
        this.logger.debug("Entered acknowledgePayment for gateway {} and order ID {}", gatewayId, orderId);
        GatewayFactory factory = gatewayFactories.get(gatewayId);
        PaymentGateway gateway = factory.createGateway();
        GatewayResponse response;
        try {
            response = gateway.acknowledge(orderId, data);
            if (response != null && response.getOperation() != null) {
                PaymentOperation op = response.getOperation();
                op.setOrderId(orderId);
                paymentOperationStore.get().create(op);
                observationManager.notify(new PaymentOperationEvent(), op);
            } else {
                this.logger.error("Payment gateway {} did not return a valid response !", gatewayId);
                throw new WebApplicationException();
            }
        } catch (GatewayException | InvalidEntityException | EntityAlreadyExistsException e) {
            this.logger.error("Failed to acknowledge payment", e);
            throw new WebApplicationException(e);
        }
        if (!Strings.isNullOrEmpty(response.getResponseText())) {
            return Response.ok(response.getResponseText()).build();
        }
        return Response.ok().build();
    }

    /**
     * Some payment gateways call IPN as GET requests. Support those as well.
     */
    @GET
    @Path("{orderId}/" + ACKNOWLEDGEMENT_PATH + "/{gatewayId}")
    public Response acknowledgePaymentGet(@PathParam("gatewayId") String gatewayId, @PathParam("orderId") UUID orderId, @Context UriInfo uriInfo) {
        return this.acknowledgePayment(gatewayId, orderId, uriInfo.getQueryParameters());
    }
}
