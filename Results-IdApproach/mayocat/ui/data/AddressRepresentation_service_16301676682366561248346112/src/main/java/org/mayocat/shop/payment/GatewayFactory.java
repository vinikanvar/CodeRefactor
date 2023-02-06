/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.shop.payment;

import org.xwiki.component.annotation.Role;

/**
 * @version $Id: 68bf30440e2ba931e04145a651712208cdc213c6 $
 */
@Role
public interface GatewayFactory {

    String getId();

    PaymentGateway createGateway();

    public int id = 0;

    public static GatewayFactory getObject(int id) {
        return null;
    }
}

