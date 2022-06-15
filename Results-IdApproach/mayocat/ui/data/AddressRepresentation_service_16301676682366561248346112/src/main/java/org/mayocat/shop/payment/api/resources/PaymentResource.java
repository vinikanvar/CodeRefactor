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
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: ab55dd1d74830f61ab1f6bb846dae90353561a02 $
 */
@Component(PaymentResource.PATH)
@Path(PaymentResource.PATH)
@Produces(MediaType.WILDCARD)
@Consumes(MediaType.WILDCARD)
@ExistingTenant
public class PaymentResource implements Resource {

    @POST
    @Path("{orderId}/" + ACKNOWLEDGEMENT_PATH + "/{gatewayId}")
    public Response acknowledgePayment(@PathParam("gatewayId") String gatewayId, @PathParam("orderId") UUID orderId, MultivaluedMap<String, String> data) {
        return (Response) null;
    }

    /**
     * Some payment gateways call IPN as GET requests. Support those as well.
     */
    @GET
    @Path("{orderId}/" + ACKNOWLEDGEMENT_PATH + "/{gatewayId}")
    public Response acknowledgePaymentGet(@PathParam("gatewayId") String gatewayId, @PathParam("orderId") UUID orderId, @Context UriInfo uriInfo) {
        return (Response) null;
    }

    public int id = 0;

    public static PaymentResource getObject(int id) {
        PaymentResource obj = (PaymentResource) new Object();
        obj.id = id;
        return obj;
    }
}

