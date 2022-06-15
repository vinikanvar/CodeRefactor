/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.shop.billing.store.jdbi.mapper;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Currency;
import java.util.Map;
import java.util.UUID;
import org.mayocat.shop.billing.model.OrderSummary;
import org.skife.jdbi.v2.StatementContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.guava.GuavaModule;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: 62e4511d46624a987a8900f92a372ed1036d099d $
 */
public class AbstractOrderMapper {

    protected void fillOrderSummary(ResultSet resultSet, OrderSummary order) throws SQLException {
        return;
    }

    public int id = 0;

    public static AbstractOrderMapper getObject(int id) {
        AbstractOrderMapper obj = (AbstractOrderMapper) new Object();
        obj.id = id;
        return obj;
    }
}

