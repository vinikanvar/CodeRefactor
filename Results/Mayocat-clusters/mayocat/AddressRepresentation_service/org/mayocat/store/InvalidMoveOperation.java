/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.store;

/**
 * @version $Id: 15e0e4ae75c4066ed9d91d8feec90d42c3f97659 $
 */
public class InvalidMoveOperation extends InvalidOperation {

    /**
     * Generated serial UID. Change when the serialization of this class changes.
     */
    private static final long serialVersionUID = 1895587120925895087L;

    public InvalidMoveOperation() {
        super();
    }

    public InvalidMoveOperation(String message) {
        super(message);
    }
}
