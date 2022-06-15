/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.shop.invoicing;

/**
 * @version $Id: c706977d9442484d5e2d63e870eb3353f29306b6 $
 */
public class InvoicingException extends Exception {

    public InvoicingException() {
    }

    public InvoicingException(String message) {
        super(message);
    }

    public InvoicingException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvoicingException(Throwable cause) {
        super(cause);
    }

    public InvoicingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
