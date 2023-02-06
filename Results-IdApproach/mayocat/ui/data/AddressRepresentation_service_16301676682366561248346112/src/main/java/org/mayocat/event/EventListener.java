/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.event;

import org.xwiki.component.annotation.Role;

@Role
public interface EventListener extends java.util.EventListener {

    public int id = 0;

    public static EventListener getObject(int id) {
        return null;
    }
}

