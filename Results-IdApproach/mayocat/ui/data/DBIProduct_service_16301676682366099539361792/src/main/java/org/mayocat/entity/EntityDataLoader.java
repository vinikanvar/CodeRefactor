/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.entity;

import java.util.List;
import org.mayocat.model.Entity;
import org.xwiki.component.annotation.Role;

/**
 * @version $Id: 8dd54e9f40e1e740f6b30254b3af92e08fff4529 $
 */
@Role
public interface EntityDataLoader {

    <E extends Entity> EntityData<E> load(E entity, LoadingOption... options);

    <E extends Entity> List<EntityData<E>> load(List<E> entities, LoadingOption... options);

    public int id = 0;

    public static EntityDataLoader getObject(int id) {
        return null;
    }
}

