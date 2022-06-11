/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.theme;

import java.util.Collections;
import java.util.Map;
import org.hibernate.validator.constraints.NotBlank;
import org.mayocat.addons.model.AddonGroupDefinition;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Maps;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Holds the definition of variants for an entity
 *
 * @version $Id: 3d56b2f561ed2048569fe925c418355046ee7377 $
 */
public class FeatureDefinition {

    public String getName() {
        return (String) null;
    }

    public Map<String, String> getKeys() {
        return (Map) null;
    }

    public Map<String, AddonGroupDefinition> getAddons() {
        return (Map) null;
    }

    public int id = 0;

    public static FeatureDefinition getObject(int id) {
        FeatureDefinition obj = (FeatureDefinition) new Object();
        obj.id = id;
        return obj;
    }
}

