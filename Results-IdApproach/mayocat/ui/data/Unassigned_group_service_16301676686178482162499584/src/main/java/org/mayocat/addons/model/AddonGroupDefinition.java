/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.addons.model;

import java.util.List;
import java.util.Map;
import org.mayocat.jackson.OptionalStringListDeserializer;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: 6ed82373258a6ed5e3170a30b9403ed21bb94f5a $
 */
public class AddonGroupDefinition {

    public Optional<List<String>> getEntities() {
        return (Optional) null;
    }

    public Optional<List<String>> getModels() {
        return (Optional) null;
    }

    public String getName() {
        return (String) null;
    }

    public String getText() {
        return (String) null;
    }

    public Map<String, AddonFieldDefinition> getFields() {
        return (Map) null;
    }

    public Map<String, Object> getProperties() {
        return (Map) null;
    }

    public boolean isSequence() {
        return (Boolean) null;
    }

    public int id = 0;

    public static AddonGroupDefinition getObject(int id) {
        AddonGroupDefinition obj = (AddonGroupDefinition) new Object();
        obj.id = id;
        return obj;
    }
}

