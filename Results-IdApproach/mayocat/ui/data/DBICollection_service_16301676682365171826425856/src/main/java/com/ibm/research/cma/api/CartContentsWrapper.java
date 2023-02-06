/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.ibm.research.cma.api;

import java.math.BigDecimal;
import java.util.Currency;
import java.util.LinkedHashMap;
import java.util.Map;
import org.mayocat.shop.shipping.ShippingOption;
import org.mayocat.shop.taxes.Taxable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;
import org.mayocat.shop.cart.*;
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
 * @version $Id: cca0e17b610621e0b02a2e2298165dc4ebb7d963 $
 */
@Controller
@RequestMapping("/ms0/cartcontents/")
public class CartContentsWrapper {

    private static int maxId = 0;

    public static HashMap<Integer, CartContentsServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "cartcontents")
    public  @ResponseBody int CartContents(@RequestParam int currencyId) {
        CartContentsServer newServerObj = new CartContentsServer(Currency.getObject(currencyId));
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
}

