/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.search.elasticsearch.internal.testsupport;

import java.util.UUID;
import org.mayocat.model.Entity;
import org.mayocat.model.annotation.Index;

/**
 * @version $Id: a9325e670b5b4c87cf7d64bee34e378307aeace6 $
 */
public class EntityWithFieldLevelIndexAnnotations implements Entity {

    private UUID id;

    private String slug;

    @Index
    private String myIndexedField;

    private String myNotIndexField;

    public EntityWithFieldLevelIndexAnnotations(UUID id, String slug) {
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
        return this.slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getMyIndexedField() {
        return myIndexedField;
    }

    public void setMyIndexedField(String myIndexedField) {
        this.myIndexedField = myIndexedField;
    }

    public String getMyNotIndexField() {
        return myNotIndexField;
    }

    public void setMyNotIndexField(String myNotIndexField) {
        this.myNotIndexField = myNotIndexField;
    }
}
