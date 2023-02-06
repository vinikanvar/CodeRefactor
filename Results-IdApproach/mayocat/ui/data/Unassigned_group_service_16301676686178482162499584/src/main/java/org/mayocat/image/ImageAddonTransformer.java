/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.image;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import javax.inject.Inject;
import org.mayocat.addons.AddonFieldTransformer;
import org.mayocat.addons.model.AddonFieldDefinition;
import org.mayocat.addons.web.AddonFieldValueWebObject;
import org.mayocat.entity.EntityData;
import org.mayocat.image.model.Image;
import org.mayocat.url.EntityURLFactory;
import org.xwiki.component.annotation.Component;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Maps;
import groovy.text.SimpleTemplateEngine;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: 8b04dbe7ac7f6cf53c0c5ab263a6a84ccfa39909 $
 */
@Component("image")
public class ImageAddonTransformer implements AddonFieldTransformer {

    public Optional<AddonFieldValueWebObject> toWebView(EntityData<?> entityData, AddonFieldDefinition definition, Object storedValue) {
        return (Optional) null;
    }

    public int id = 0;

    public static ImageAddonTransformer getObject(int id) {
        ImageAddonTransformer obj = (ImageAddonTransformer) new Object();
        obj.id = id;
        return obj;
    }
}

