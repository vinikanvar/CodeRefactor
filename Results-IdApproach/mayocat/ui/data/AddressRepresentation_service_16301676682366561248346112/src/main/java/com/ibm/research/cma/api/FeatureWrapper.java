/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.ibm.research.cma.api;

import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotBlank;
import org.mayocat.model.AddonGroup;
import org.mayocat.model.Association;
import org.mayocat.model.Child;
import org.mayocat.model.Entity;
import org.mayocat.model.HasAddons;
import org.mayocat.model.Localized;
import org.mayocat.model.annotation.DoNotIndex;
import org.mayocat.model.annotation.LocalizedField;
import com.google.common.base.Objects;
import org.mayocat.shop.catalog.model.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.HashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Model for a product feature (for example: a size, a color, etc.)
 *
 * @version $Id: 4364e3313c4fc8cfc5e711150ba27c418b8cdf4e $
 */
@Controller
@RequestMapping("/ms2/feature/")
public class FeatureWrapper {

    // 
    private static int maxId = 0;

    public static HashMap<Integer, FeatureServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "feature")
    public  @ResponseBody int Feature() {
        FeatureServer newServerObj = new FeatureServer();
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
    // 
}

