/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.store.rdbms.dbi.argument;

/**
 * @version $Id: 6ba3e5a3150e377a0f6a0fa103951168bc7df3ec $
 */
public class JsonArgument {

    private Object wrapped;

    public JsonArgument(Object wrapped) {
        this.wrapped = wrapped;
    }

    public Object getWrapped() {
        return wrapped;
    }
}
