/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.theme;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.mayocat.addons.model.AddonGroupDefinition;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Definition of variants
 *
 * @version $Id: 09101e5a3645198cebe29661872751910c27ffb8 $
 */
public class VariantsDefinition {

    public Map<String, AddonGroupDefinition> getAddons() {
        return (Map) null;
    }

    public List<String> getProperties() {
        return (List) null;
    }

    public int id = 0;

    public static VariantsDefinition getObject(int id) {
        VariantsDefinition obj = (VariantsDefinition) new Object();
        obj.id = id;
        return obj;
    }
}

