/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.context.internal.request;

import java.net.URI;
import org.mayocat.context.request.WebRequest;
import org.mayocat.theme.Breakpoint;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: 24b45451ea46ca44231b45548c5f753a90da6122 $
 */
public class DefaultWebRequestBuilder {

    public DefaultWebRequestBuilder canonicalPath(String path) {
        return (DefaultWebRequestBuilder) null;
    }

    public DefaultWebRequestBuilder path(String path) {
        return (DefaultWebRequestBuilder) null;
    }

    public DefaultWebRequestBuilder baseURI(URI baseURI) {
        return (DefaultWebRequestBuilder) null;
    }

    public DefaultWebRequestBuilder apiRequest(boolean isApiRequest) {
        return (DefaultWebRequestBuilder) null;
    }

    public DefaultWebRequestBuilder tenantRequest(boolean isTenantRequest) {
        return (DefaultWebRequestBuilder) null;
    }

    public DefaultWebRequestBuilder tenantPrefix(String tenantPrefix) {
        return (DefaultWebRequestBuilder) null;
    }

    public DefaultWebRequestBuilder secure(boolean secure) {
        return (DefaultWebRequestBuilder) null;
    }

    public DefaultWebRequestBuilder breakpoint(Optional<Breakpoint> breakpoint) {
        return (DefaultWebRequestBuilder) null;
    }

    public WebRequest build() {
        return (WebRequest) null;
    }

    public int id = 0;

    public static DefaultWebRequestBuilder getObject(int id) {
        DefaultWebRequestBuilder obj = (DefaultWebRequestBuilder) new Object();
        obj.id = id;
        return obj;
    }
}

