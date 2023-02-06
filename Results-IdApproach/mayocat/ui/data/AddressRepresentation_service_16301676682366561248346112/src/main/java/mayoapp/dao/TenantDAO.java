/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package mayoapp.dao;

import java.util.List;
import org.mayocat.accounts.model.Tenant;
import org.mayocat.accounts.store.jdbi.mapper.TenantMapper;
import org.mayocat.addons.store.dbi.AddonsHelper;
import org.mayocat.store.rdbms.dbi.argument.DateAsTimestampArgumentFactory;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.BindBean;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterArgumentFactory;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.sqlobject.mixins.Transactional;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@RegisterMapper(TenantMapper.class)
@RegisterArgumentFactory({ DateAsTimestampArgumentFactory.class })
@UseStringTemplate3StatementLocator
public abstract class TenantDAO implements EntityDAO<Tenant>, Transactional<TenantDAO>, AddonsDAO<Tenant> {

    @SqlUpdate
    public abstract void updateConfiguration(@BindBean("tenant") Tenant tenant, @Bind("version") Integer version, @Bind("data") String configuration) {
        return;
    }

    @SqlUpdate
    public abstract void create(@BindBean("tenant") Tenant tenant, @Bind("version") Integer version, @Bind("data") String configuration) {
        return;
    }

    @SqlUpdate
    public abstract Integer update(@BindBean("tenant") Tenant tenant) {
        return (Integer) null;
    }

    @SqlQuery
    public abstract Tenant findByDefaultHost(@Bind("host") String host) {
        return (Tenant) null;
    }

    @SqlQuery
    public abstract List<Tenant> findAll(@Bind("number") Integer number, @Bind("offset") Integer offset) {
        return (List) null;
    }

    @SqlQuery
    public abstract Integer countAllTenants() {
        return (Integer) null;
    }

    public void createOrUpdateAddons(Tenant entity) {
        return;
    }

    public int id = 0;

    public static TenantDAO getObject(int id) {
        TenantDAO obj = (TenantDAO) new Object();
        obj.id = id;
        return obj;
    }
}

