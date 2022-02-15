/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.shop.cart.internal.support;

import java.math.BigDecimal;

/**
 * @version $Id: 11257ffcff815e93e1a2de0ccd31a44e49399d72 $
 */
public class InputItem {

    private BigDecimal unitPrice;

    private Long quantity;

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public Long getQuantity() {
        return quantity;
    }
}
