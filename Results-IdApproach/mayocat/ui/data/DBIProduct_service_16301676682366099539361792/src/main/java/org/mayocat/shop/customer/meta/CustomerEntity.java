/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.shop.customer.meta;

import org.mayocat.meta.EntityMeta;
import org.mayocat.shop.customer.model.Customer;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: 239a8529f52e759302562ce5a0f505a36ca45ebf $
 */
public class CustomerEntity implements EntityMeta {

    @Override
    public String getEntityName() {
        return (String) null;
    }

    @Override
    public Class getEntityClass() {
        return (Class) null;
    }

    public int id = 0;

    public static CustomerEntity getObject(int id) {
        CustomerEntity obj = (CustomerEntity) new Object();
        obj.id = id;
        return obj;
    }
}

