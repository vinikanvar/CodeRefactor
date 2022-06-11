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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Maps;
import org.hibernate.validator.constraints.NotBlank;
import org.mayocat.addons.model.AddonGroupDefinition;
import org.mayocat.configuration.images.ImageFormatDefinition;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: 0cb9712b6d6f41aac2c870bbf63665a7a4cdf905 $
 */
public class ThemeDefinition {

    public String getName() {
        return (String) null;
    }

    public String getDescription() {
        return (String) null;
    }

    public Map<String, ImageFormatDefinition> getImageFormats() {
        return (Map) null;
    }

    public Map<String, Model> getModels() {
        return (Map) null;
    }

    public Map<String, AddonGroupDefinition> getAddons() {
        return (Map) null;
    }

    @JsonIgnore
    public Map<String, PaginationDefinition> getPaginationDefinitions() {
        return (Map) null;
    }

    public PaginationDefinition getPaginationDefinition(String key) {
        return (PaginationDefinition) null;
    }

    public Map<String, TypeDefinition> getProductTypes() {
        return (Map) null;
    }

    public int id = 0;

    public static ThemeDefinition getObject(int id) {
        ThemeDefinition obj = (ThemeDefinition) new Object();
        obj.id = id;
        return obj;
    }
}

