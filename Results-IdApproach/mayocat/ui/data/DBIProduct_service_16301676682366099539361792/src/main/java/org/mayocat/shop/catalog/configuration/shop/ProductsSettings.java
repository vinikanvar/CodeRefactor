/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.shop.catalog.configuration.shop;

import java.util.Map;
import javax.validation.Valid;
import org.mayocat.configuration.Configurable;
import org.mayocat.shop.catalog.model.WeightUnit;
import org.mayocat.theme.TypeDefinition;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Maps;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: 61970dab9b39c25a7c40a5a8ecad03785367db7c $
 */
public class ProductsSettings {

    public Configurable<Boolean> getCollections() {
        return (Configurable) null;
    }

    public Configurable<Boolean> getStock() {
        return (Configurable) null;
    }

    public Configurable<Boolean> getWeight() {
        return (Configurable) null;
    }

    public Configurable<WeightUnit> getWeightUnit() {
        return (Configurable) null;
    }

    public Map<String, TypeDefinition> getTypes() {
        return (Map) null;
    }

    public int id = 0;

    public static ProductsSettings getObject(int id) {
        ProductsSettings obj = (ProductsSettings) new Object();
        obj.id = id;
        return obj;
    }
}

