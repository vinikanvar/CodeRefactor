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

/**
 * @version $Id: bdfc0c42f8b6d98e8285b09c6e097d1ea0d3912b $
 */
@Component
public class ShopIndexDocumentPurveyor extends AbstractGenericEntityIndexDocumentPurveyor<Tenant> implements EntityIndexDocumentPurveyor<Tenant> {

    @Inject
    private Logger logger;

    @Inject
    private Provider<CollectionStore> collectionStore;

    @Inject
    private DBIProvider dbi;

    @Inject
    private URLHelper urlHelper;

    private CollectionDAO dao;

    public Class forClass() {
        return Tenant.class;
    }

    @Override
    public Map<String, Object> purveyDocument(Tenant entity, Tenant tenant) {
        return purveyDocument(entity);
    }

    public Map<String, Object> purveyDocument(Tenant tenant) {
        Preconditions.checkNotNull(tenant);
        this.dao = this.dbi.get().onDemand(CollectionDAO.class);
        Map<String, Object> extracted = extractSourceFromEntity(tenant, tenant);
        List<Collection> collections = dao.findAll("collection", tenant.getId());
        List<Map<String, Object>> collectionsSource = Lists.newArrayList();
        for (Collection collection : collections) {
            Map<String, Object> collectionSource = extractSourceFromEntity(collection, tenant);
            collectionSource.put("api_url", urlHelper.getContextPlatformURL("/marketplace/api/shop/" + tenant.getSlug() + "/collections/" + collection.getSlug()).toString());
            collectionsSource.add(collectionSource);
        }
        extracted.put("collections", collectionsSource);
        return extracted;
    }
}
