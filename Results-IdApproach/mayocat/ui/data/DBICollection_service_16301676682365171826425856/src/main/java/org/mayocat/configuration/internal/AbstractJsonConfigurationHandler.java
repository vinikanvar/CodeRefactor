/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.configuration.internal;

import java.io.Serializable;
import java.util.Map;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: fd3e0e039ba8d61835ac2d60b6250d8df523f437 $
 */
public class AbstractJsonConfigurationHandler {

    public AbstractJsonConfigurationHandler(final Map<String, Serializable> platform, final Map<String, Serializable> tenant) {
        String uri = MicroserviceApplication.projectUri + "/msnull/abstractjsonconfigurationhandler/abstractjsonconfigurationhandler?callerId=" + this.id + "&platformId=platform.id&tenantId=tenant.id";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        this.id = resultTemp;
    }

    protected boolean isConfigurableEntry(Map<String, Serializable> entry) {
        return (Boolean) null;
    }

    public int id = 0;

    public static AbstractJsonConfigurationHandler getObject(int id) {
        AbstractJsonConfigurationHandler obj = (AbstractJsonConfigurationHandler) new Object();
        obj.id = id;
        return obj;
    }
}

