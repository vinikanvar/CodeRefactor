/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.configuration.internal;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import javax.validation.constraints.NotNull;
import org.apache.commons.lang3.SerializationUtils;
import com.google.common.collect.Maps;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: 411d8b47947473211d55771eb76a054273161f44 $
 */
public class ConfigurationJsonMerger extends AbstractJsonConfigurationHandler {

    public ConfigurationJsonMerger(final Map<String, Serializable> platform, final Map<String, Serializable> tenant) {
        String uri = MicroserviceApplication.projectUri + "/msnull/configurationjsonmerger/configurationjsonmerger?callerId=" + this.id + "&platformId=platform.id&tenantId=tenant.id";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        this.id = resultTemp;
    }

    public Map<String, Serializable> merge() {
        return (Map) null;
    }

    private HashMap<String, Serializable> merge(@NotNull final Map<String, Serializable> global, @Nullable final Map<String, Serializable> local) {
        return (HashMap) null;
    }

    public int id = 0;

    public static ConfigurationJsonMerger getObject(int id) {
        ConfigurationJsonMerger obj = (ConfigurationJsonMerger) new Object();
        obj.id = id;
        return obj;
    }
}

