/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.shop.marketplace.search;

import java.util.List;
import java.util.Map;
import javax.inject.Inject;
import javax.inject.Provider;
import org.mayocat.accounts.model.Tenant;
import org.mayocat.search.EntityIndexDocumentPurveyor;
import org.mayocat.search.elasticsearch.AbstractGenericEntityIndexDocumentPurveyor;
import org.mayocat.shop.catalog.model.Collection;
import org.mayocat.shop.catalog.store.CollectionStore;
import org.mayocat.store.rdbms.dbi.DBIProvider;
import org.mayocat.url.URLHelper;
import org.slf4j.Logger;
import org.xwiki.component.annotation.Component;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import mayoapp.dao.CollectionDAO;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: bdfc0c42f8b6d98e8285b09c6e097d1ea0d3912b $
 */
@Component
public class ShopIndexDocumentPurveyor extends AbstractGenericEntityIndexDocumentPurveyor<Tenant> implements EntityIndexDocumentPurveyor<Tenant> {

    public Class forClass() {
        return (Class) null;
    }

    @Override
    public Map<String, Object> purveyDocument(Tenant entity, Tenant tenant) {
        return (Map) null;
    }

    public Map<String, Object> purveyDocument(Tenant tenant) {
        return (Map) null;
    }

    public int id = 0;

    public static ShopIndexDocumentPurveyor getObject(int id) {
        ShopIndexDocumentPurveyor obj = (ShopIndexDocumentPurveyor) new Object();
        obj.id = id;
        return obj;
    }
}

