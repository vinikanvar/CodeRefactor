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
import java.util.UUID;
import org.mayocat.store.rdbms.dbi.argument.pg.PG_UUID;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.Argument;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: 9bcebeb72ab4f417670766e645654149ac72b3a9 $
 */
public class PostgresUUIDArgument implements Argument {

    public PostgresUUIDArgument(UUID value) {
        String uri = MicroserviceApplication.projectUri + "/msnull/postgresuuidargument/postgresuuidargument?callerId=" + this.id + "&valueId=value.id";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        this.id = resultTemp;
    }

    @Override
    public void apply(int position, PreparedStatement statement, StatementContext ctx) throws SQLException {
        return;
    }

    public int id = 0;

    public static PostgresUUIDArgument getObject(int id) {
        PostgresUUIDArgument obj = (PostgresUUIDArgument) new Object();
        obj.id = id;
        return obj;
    }
}

