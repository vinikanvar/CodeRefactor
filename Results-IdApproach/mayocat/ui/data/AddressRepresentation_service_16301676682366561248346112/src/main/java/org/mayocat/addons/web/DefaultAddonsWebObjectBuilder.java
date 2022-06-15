/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.addons.web;

import com.google.common.base.Strings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import org.mayocat.addons.AddonFieldTransformer;
import org.mayocat.addons.model.AddonFieldDefinition;
import org.mayocat.addons.model.AddonGroupDefinition;
import org.mayocat.addons.web.AddonFieldValueWebObject;
import org.mayocat.addons.web.AddonsWebObjectBuilder;
import org.mayocat.configuration.PlatformSettings;
import org.mayocat.context.WebContext;
import org.mayocat.entity.EntityData;
import org.mayocat.localization.EntityLocalizationService;
import org.mayocat.model.AddonGroup;
import org.mayocat.model.HasAddons;
import org.mayocat.model.Localized;
import org.xwiki.component.annotation.Component;
import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: 039c2210baa195d2359fa424563a95b283bef3a7 $
 */
@Component
public class DefaultAddonsWebObjectBuilder implements AddonsWebObjectBuilder {

    public Map<String, Object> build(EntityData<? extends HasAddons> entityData) {
        return (Map) null;
    }

    public Map<String, AddonFieldValueWebObject> getFieldsValue(EntityData entityData, AddonGroupDefinition groupDefinition, Map<String, Map<String, Object>> model, Map<String, Object> element) {
        return (Map) null;
    }

    private Optional<AddonGroupDefinition> findGroupDefinition(String groupName) {
        return (Optional) null;
    }

    public int id = 0;

    public static DefaultAddonsWebObjectBuilder getObject(int id) {
        DefaultAddonsWebObjectBuilder obj = (DefaultAddonsWebObjectBuilder) new Object();
        obj.id = id;
        return obj;
    }
}

