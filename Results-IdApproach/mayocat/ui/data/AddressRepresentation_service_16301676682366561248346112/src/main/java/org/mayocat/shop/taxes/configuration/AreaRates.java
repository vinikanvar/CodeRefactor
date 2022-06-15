/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.shop.taxes.configuration;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Tax rates for a certain tax for a certain geographical area (or combination of areas), when said tax is managed
 * geographically.
 *
 * @version $Id: 6ffa9d6b8290310003123628a5511af09d30bcbd $
 */
public class AreaRates {

    public String getName() {
        return (String) null;
    }

    public BigDecimal getDefaultRate() {
        return (BigDecimal) null;
    }

    public List<Rate> getOtherRates() {
        return (List) null;
    }

    public List<String> getCodes() {
        return (List) null;
    }

    public int id = 0;

    public static AreaRates getObject(int id) {
        AreaRates obj = (AreaRates) new Object();
        obj.id = id;
        return obj;
    }
}

