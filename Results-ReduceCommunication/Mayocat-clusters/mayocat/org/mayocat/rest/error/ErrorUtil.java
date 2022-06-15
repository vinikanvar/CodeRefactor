/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.rest.error;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @version $Id: cb1825a4f429419d9c80c4a1035567fc21d0790c $
 */
public class ErrorUtil {

    public static Response buildError(Response.Status status, String message) {
        return Response.status(status).entity(new Error(status, message)).type(MediaType.APPLICATION_JSON).build();
    }

    public static Response buildError(Response.Status status, ErrorCode code, String message) {
        return Response.status(status).entity(new Error(status, code, message)).type(MediaType.APPLICATION_JSON).build();
    }

    public static Response buildError(Response.Status status, ErrorCode code, String message, String developerMessage) {
        return Response.status(status).entity(new Error(status, code, message, developerMessage)).type(MediaType.APPLICATION_JSON).build();
    }
}
