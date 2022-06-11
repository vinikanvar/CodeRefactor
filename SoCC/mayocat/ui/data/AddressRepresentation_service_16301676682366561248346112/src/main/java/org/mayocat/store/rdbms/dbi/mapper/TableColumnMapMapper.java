/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.store.rdbms.dbi.mapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.exceptions.ResultSetException;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Same as org.skife.jdbi.v2.DefaultMapper except the map has both the alias and the form <tableName>.<columnName> as keys
 */
public class TableColumnMapMapper implements ResultSetMapper<Map<String, Object>> {

    public Map<String, Object> map(int index, ResultSet r, StatementContext ctx) {
        return (Map) null;
    }

    private static class DefaultResultMap extends HashMap<String, Object> {

        public static final long serialVersionUID = 1L;

        @Override
        public Object get(Object o) {
            return (Object) null;
        }

        @Override
        public Object put(String key, Object value) {
            return (Object) null;
        }

        @Override
        public boolean containsKey(Object key) {
            return (Boolean) null;
        }
    }

    public int id = 0;

    public static TableColumnMapMapper getObject(int id) {
        TableColumnMapMapper obj = (TableColumnMapMapper) new Object();
        obj.id = id;
        return obj;
    }
}

