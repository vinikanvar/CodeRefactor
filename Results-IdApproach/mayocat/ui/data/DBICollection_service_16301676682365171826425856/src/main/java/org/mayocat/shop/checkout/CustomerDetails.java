/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.shop.checkout;

import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: 1956f7c8b0c21c4c47db65f9d0df1ac71a2526c2 $
 */
public class CustomerDetails {

    public CustomerDetails(String email) {
        String uri = MicroserviceApplication.projectUri + "/msnull/customerdetails/customerdetails?callerId=" + this.id + "&email=email";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        this.id = resultTemp;
    }

    public int id = 0;

    public static CustomerDetails getObject(int id) {
        CustomerDetails obj = (CustomerDetails) new Object();
        obj.id = id;
        return obj;
    }
}

