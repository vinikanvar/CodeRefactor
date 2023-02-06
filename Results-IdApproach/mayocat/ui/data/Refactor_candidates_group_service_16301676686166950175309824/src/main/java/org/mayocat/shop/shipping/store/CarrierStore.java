/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.shop.shipping.store;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.mayocat.shop.shipping.Strategy;
import org.mayocat.shop.shipping.model.Carrier;
import org.xwiki.component.annotation.Role;

/**
 * @version $Id: ffab2cdeca5c1b41b833f15a2ddd5685d00727b4 $
 */
@Role
public interface CarrierStore {

    Carrier findById(UUID id);

    Set<Carrier> findAll();

    Set<Carrier> findAll(Strategy strategy);

    void createCarrier(Carrier carrier);

    void updateCarrier(Carrier carrier);

    void deleteCarrier(Carrier carrier);

    void updatePositions(List<Carrier> carriers);

    public int id = 0;

    public static CarrierStore getObject(int id) {
        return null;
    }
}

