/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.shop.marketplace.store.jdbi;

import java.util.List;
import javax.inject.Inject;
import org.mayocat.accounts.model.Tenant;
import org.mayocat.addons.store.dbi.AddonsHelper;
import org.mayocat.context.WebContext;
import org.mayocat.model.AddonGroup;
import org.mayocat.shop.catalog.model.Product;
import org.mayocat.shop.marketplace.model.EntityAndTenant;
import org.mayocat.shop.marketplace.store.MarketplaceProductStore;
import org.mayocat.store.rdbms.dbi.DBIProvider;
import org.xwiki.component.annotation.Component;
import org.xwiki.component.phase.Initializable;
import org.xwiki.component.phase.InitializationException;
import mayoapp.dao.MarketplaceProductDAO;
import mayoapp.dao.TenantDAO;
import static org.mayocat.addons.util.AddonUtils.asMap;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: aa1e1a93d0b667d49f87d2c080948990c60d4e19 $
 */
@Component(hints = { "jdbi", "default" })
public class DBIMarketplaceProductStore implements MarketplaceProductStore, Initializable {

    @Override
    public Product findBySlugAndTenant(String slug, String tenantSlug) {
        return (Product) null;
    }

    @Override
    public List<Product> findAllNotVariants(Integer number, Integer offset) {
        return (List) null;
    }

    @Override
    public List<Product> findAllNotVariants(Integer number, Integer offset, Order order) {
        return (List) null;
    }

    @Override
    public Integer countAllNotVariants() {
        return (Integer) null;
    }

    @Override
    public List<Product> findAllWithTitleLike(String title, Integer number, Integer offset) {
        return (List) null;
    }

    @Override
    public Integer countAllWithTitleLike(String title) {
        return (Integer) null;
    }

    @Override
    public List<Product> findAllOnShelfWithTitleLike(String title, Integer number, Integer offset) {
        return (List) null;
    }

    @Override
    public Integer countAllOnShelfWithTitleLike(String title) {
        return (Integer) null;
    }

    @Override
    public List<Product> findAllForTenant(Tenant tenant, Integer number, Integer offset) {
        return (List) null;
    }

    @Override
    public List<Product> findAllForTenantOnShelf(Tenant tenant, Integer number, Integer offset) {
        return (List) null;
    }

    @Override
    public void initialize() throws InitializationException {
        return;
    }

    public int id = 0;

    public static DBIMarketplaceProductStore getObject(int id) {
        DBIMarketplaceProductStore obj = (DBIMarketplaceProductStore) new Object();
        obj.id = id;
        return obj;
    }
}

