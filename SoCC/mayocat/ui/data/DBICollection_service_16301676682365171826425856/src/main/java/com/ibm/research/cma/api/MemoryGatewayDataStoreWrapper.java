/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.ibm.research.cma.api;

import java.util.Map;
import java.util.UUID;
import org.mayocat.accounts.model.Tenant;
import org.mayocat.shop.customer.model.Customer;
import org.mayocat.shop.payment.InvalidGatewayDataException;
import org.mayocat.shop.payment.model.GatewayCustomerData;
import org.mayocat.shop.payment.model.GatewayTenantData;
import org.mayocat.shop.payment.store.GatewayDataStore;
import org.xwiki.component.annotation.Component;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import org.mayocat.shop.payment.store.memory.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.HashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * In-memory implementation of {@link GatewayDataStore}
 *
 * @version $Id: 090c6060679143864b82b7d61bb4f286648ddfd5 $
 */
@Component("memory")
@Controller
@RequestMapping("/ms0/memorygatewaydatastore/")
public class MemoryGatewayDataStoreWrapper {

    static class Key {

        Key(UUID id, String gateway, String type) {
            this.id = id;
            this.gateway = gateway;
            this.type = type;
        }

        private UUID id;

        private String gateway;

        private String type;
    }

    private static int maxId = 0;

    public static HashMap<Integer, MemoryGatewayDataStoreServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "memorygatewaydatastore")
    public  @ResponseBody int MemoryGatewayDataStore() {
        MemoryGatewayDataStoreServer newServerObj = new MemoryGatewayDataStoreServer();
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
}

