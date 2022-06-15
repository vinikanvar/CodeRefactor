/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.shop.payment;

/**
 * Tag interface that indicates a payment gateway processes credit card information
 *
 * @version $Id: d066e823e17e1ca06c4c2c388c88aa332bd8e80c $
 */
public interface CreditCardPaymentGateway extends PaymentGateway {

    public int id = 0;

    public static CreditCardPaymentGateway getObject(int id) {
        return null;
    }
}

