/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.store.rdbms.dbi.argument;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import org.postgresql.util.PGobject;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;
import org.skife.jdbi.v2.tweak.ArgumentFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: 1efeac46b32dfdf7b3d1efa3dec5041efee44e62 $
 */
public class JsonArgumentAsJsonArgumentFactory implements ArgumentFactory<JsonArgument> {

    @Override
    public boolean accepts(Class<?> expectedType, Object value, StatementContext ctx) {
        return (Boolean) null;
    }

    @Override
    public Argument build(Class<?> expectedType, final JsonArgument value, StatementContext ctx) {
        return (Argument) null;
    }

    public int id = 0;

    public static JsonArgumentAsJsonArgumentFactory getObject(int id) {
        JsonArgumentAsJsonArgumentFactory obj = (JsonArgumentAsJsonArgumentFactory) new Object();
        obj.id = id;
        return obj;
    }
}

