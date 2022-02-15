/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.search.elasticsearch.internal.testsupport;

import java.util.HashMap;
import java.util.Map;
import org.mayocat.accounts.model.Tenant;
import org.mayocat.model.Entity;
import org.mayocat.search.EntityIndexDocumentPurveyor;
import org.mayocat.search.elasticsearch.AbstractGenericEntityIndexDocumentPurveyor;

/**
 * @version $Id: 4e8e331757a0e0f3779744ae3be9b288ef71c2ed $
 */
public class CustomEntityIndexDocumentPurveyor extends AbstractGenericEntityIndexDocumentPurveyor<CustomEntity> implements EntityIndexDocumentPurveyor<CustomEntity> {

    @Override
    protected Map<String, Object> extractSourceFromEntity(Entity entity, Tenant tenant) {
        return new HashMap<String, Object>() {

            {
                put("hello", "world");
            }
        };
    }
}
