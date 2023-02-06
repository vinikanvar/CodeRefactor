/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.shop.shipping.store.memory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.Nullable;
import org.mayocat.shop.shipping.Strategy;
import org.mayocat.shop.shipping.model.Carrier;
import org.mayocat.shop.shipping.store.CarrierStore;
import org.mayocat.store.EntityAlreadyExistsException;
import org.mayocat.store.EntityDoesNotExistException;
import org.mayocat.store.InvalidEntityException;
import org.mayocat.store.memory.BaseEntityMemoryStore;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

/**
 * In-memory implementation of {@link CarrierStore}
 *
 * @version $Id: 1427f3b0be1cc22447b87fa2a33af13c71391d10 $
 */
public class MemoryCarrierStoreServer extends BaseEntityMemoryStore<Carrier> implements CarrierStore {

    private Predicate<Carrier> withStrategy(final Strategy strategy) {
        return new Predicate<Carrier>() {

            public boolean apply(@Nullable Carrier input) {
                return input != null && input.getStrategy().equals(strategy);
            }
        };
    }

    public Set<Carrier> findAll() {
        return new HashSet(all());
    }

    public Set<Carrier> findAll(Strategy strategy) {
        return new HashSet(FluentIterable.from(all()).filter(withStrategy(strategy)).toList());
    }

    public void createCarrier(Carrier carrier) {
        try {
            this.create(carrier);
        } catch (InvalidEntityException | EntityAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateCarrier(Carrier carrier) {
        try {
            this.update(carrier);
        } catch (InvalidEntityException | EntityDoesNotExistException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteCarrier(Carrier carrier) {
        try {
            this.delete(carrier);
        } catch (EntityDoesNotExistException e) {
            throw new RuntimeException(e);
        }
    }

    public void updatePositions(List<Carrier> carriers) {
        throw new UnsupportedOperationException("Not supported");
    }
}

