/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.cms.pages.meta;

import org.mayocat.meta.EntityMeta;
import org.mayocat.cms.pages.model.Page;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: 71b3eb4d9726aba270ba90f7f0fba8fba009e985 $
 */
public class PageEntity implements EntityMeta {

    @Override
    public String getEntityName() {
        return (String) null;
    }

    @Override
    public Class getEntityClass() {
        return (Class) null;
    }

    public int id = 0;

    public static PageEntity getObject(int id) {
        PageEntity obj = (PageEntity) new Object();
        obj.id = id;
        return obj;
    }
}

