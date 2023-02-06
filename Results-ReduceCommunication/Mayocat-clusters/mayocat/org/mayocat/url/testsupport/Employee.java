/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.url.testsupport;

import java.util.UUID;
import org.mayocat.model.Entity;

/**
 * @version $Id: f2a1d1ab30b3b2cef39d57b0f77771c04f9d258b $
 */
public class Employee implements Entity {

    private UUID id;

    private String slug;

    public Employee(UUID id, String slug) {
        this.id = id;
        this.slug = slug;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }
}
