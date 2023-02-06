/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.shop.catalog.store.memory;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.annotation.Nullable;
import org.mayocat.model.Entity;
import org.mayocat.model.EntityAndCount;
import org.mayocat.model.EntityAndParent;
import org.mayocat.model.PositionedEntity;
import org.mayocat.model.Slug;
import org.mayocat.shop.catalog.model.Collection;
import org.mayocat.shop.catalog.model.Product;
import org.mayocat.shop.catalog.model.ProductCollection;
import org.mayocat.shop.catalog.store.CollectionStore;
import org.mayocat.store.InvalidMoveOperation;
import org.mayocat.store.memory.AbstractPositionedEntityMemoryStore;
import org.xwiki.component.annotation.Component;
import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

/**
 * In-memory implementation of {@link org.mayocat.shop.catalog.store.CollectionStore}
 *
 * @version $Id: 0025a15726276c253b53e84ef6c1acac9cf99788 $
 */
@Component("memory")
public class MemoryCollectionStore extends AbstractPositionedEntityMemoryStore<Collection, CollectionPositionedEntity> implements CollectionStore {

    private static Predicate<CollectionPositionedEntity> hasProduct(final Product product) {
        return new Predicate<CollectionPositionedEntity>() {

            public boolean apply(CollectionPositionedEntity input) {
                return input.getProducts().contains(product);
            }
        };
    }

    private static Predicate<CollectionPositionedEntity> idIsPresent(final List<UUID> ids) {
        return new Predicate<CollectionPositionedEntity>() {

            public boolean apply(CollectionPositionedEntity input) {
                return FluentIterable.from(input.getProducts()).anyMatch(new Predicate<Product>() {

                    public boolean apply(Product input) {
                        return ids.contains(input.getId());
                    }
                });
            }
        };
    }

    private static Function<CollectionPositionedEntity, EntityAndCount<Collection>> entityAndCount = new Function<CollectionPositionedEntity, EntityAndCount<Collection>>() {

        public EntityAndCount<Collection> apply(CollectionPositionedEntity input) {
            return new EntityAndCount<Collection>(input.getEntity(), Long.valueOf(input.getProducts().size()));
        }
    };

    protected CollectionPositionedEntity createForEntity(Collection entity, Integer position) {
        return new CollectionPositionedEntity(entity, position, new ArrayList<Product>());
    }

    public void addProduct(Collection collection, Product product) {
        findPositionedById(collection.getId()).getProducts().add(product);
    }

    public void removeProduct(Collection collection, Product product) {
        findPositionedById(collection.getId()).getProducts().remove(product);
    }

    public void moveCollection(String collectionToMove, String collectionToMoveRelativeTo, RelativePosition relativePosition) throws InvalidMoveOperation {
        throw new UnsupportedOperationException("Not implemented");
    }

    public void moveProductInCollection(Collection collection, String productToMove, String productToMoveRelativeTo, RelativePosition relativePosition) throws InvalidMoveOperation {
        throw new UnsupportedOperationException("Not implemented");
    }

    public List<EntityAndCount<Collection>> findAllWithProductCount() {
        return FluentIterable.from(allPositioned()).transform(entityAndCount).toList();
    }

    public List<Collection> findAll() {
        return all();
    }

    public List<Collection> findAllForProduct(final Product product) {
        return FluentIterable.from(allPositioned()).filter(hasProduct(product)).transform(positionedToEntity).toList();
    }

    public List<Collection> findAllForProductIds(List<UUID> ids) {
        return FluentIterable.from(allPositioned()).filter(idIsPresent(ids)).transform(positionedToEntity).toList();
    }

    public List<ProductCollection> findAllProductsCollectionsForIds(List<UUID> ids) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public Collection findBySlug(final String slug, final UUID parentId) {
        CollectionPositionedEntity found = FluentIterable.from(allPositioned()).filter(new Predicate<CollectionPositionedEntity>() {

            public boolean apply(final CollectionPositionedEntity input) {
                return input.getEntity().getSlug().equals(slug) && input.getEntity().getParentId().equals(parentId);
            }
        }).first().orNull();
        if (found != null) {
            return found.getEntity();
        }
        return null;
    }

    @Override
    public List<Collection> findAllOrderedByParentAndPosition() {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void updateCollectionTree(List<PositionedEntity<Collection>> collections) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void addEntityToCollection(Collection collection, Entity entity) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public List<EntityAndParent<Collection>> findAllForEntity(Entity entity) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public void removeEntityFromCollection(Collection collection, Entity entity) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public List<EntityAndParent<Collection>> findAllChildrenOfCollection(Collection entity) {
        throw new RuntimeException("Not implemented");
    }

    @Override
    public EntityAndParent<Collection> findBySlugs(String... slugs) {
        throw new RuntimeException("Not implemented");
    }
}
