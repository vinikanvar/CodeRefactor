/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.shop.marketplace.store.jdbi;

import java.io.IOException;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import org.mayocat.accounts.model.Tenant;
import org.mayocat.accounts.model.TenantConfiguration;
import org.mayocat.accounts.store.jdbi.mapper.TenantMapper;
import org.mayocat.model.Entity;
import org.mayocat.shop.catalog.model.Product;
import org.mayocat.shop.marketplace.model.EntityAndTenant;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.google.common.base.Strings;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: 68dad93d0ea0269468d7a9f0254a9bcf7dbcfa0b $
 */
public abstract class AbstractEntityAndTenantMapper implements ResultSetMapper<EntityAndTenant<Product>> {

    @Override
    public EntityAndTenant<Product> map(int index, ResultSet result, StatementContext ctx) throws SQLException {
        return (EntityAndTenant) null;
    }

    public abstract <E extends Entity> E extractEntity(int index, ResultSet result, StatementContext ctx) throws SQLException {
        return (E) null;
    }

    public int id = 0;

    public static AbstractEntityAndTenantMapper getObject(int id) {
        AbstractEntityAndTenantMapper obj = (AbstractEntityAndTenantMapper) new Object();
        obj.id = id;
        return obj;
    }
}

