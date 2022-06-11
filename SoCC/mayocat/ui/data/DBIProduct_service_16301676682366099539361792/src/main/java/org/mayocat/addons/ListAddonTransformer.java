/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.addons;

import java.util.List;
import java.util.Map;
import org.mayocat.addons.model.AddonFieldDefinition;
import org.mayocat.addons.web.AddonFieldValueWebObject;
import org.mayocat.entity.EntityData;
import org.xwiki.component.annotation.Component;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;
import com.google.common.collect.Lists;
import static org.mayocat.addons.util.AddonUtils.emptyToNull;
import static org.mayocat.addons.util.AddonUtils.getListValues;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: 6877dcab57641376761bd0f1304c159142ef0e34 $
 */
@Component("list")
public class ListAddonTransformer implements AddonFieldTransformer {

    @Override
    public Optional<AddonFieldValueWebObject> toWebView(EntityData<?> entityData, AddonFieldDefinition addonField, Object fieldValue) {
        return (Optional) null;
    }

    public int id = 0;

    public static ListAddonTransformer getObject(int id) {
        ListAddonTransformer obj = (ListAddonTransformer) new Object();
        obj.id = id;
        return obj;
    }
}

