/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.shop.catalog.model;

import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.mayocat.model.AddonGroup;
import org.mayocat.model.Association;
import org.mayocat.model.Child;
import org.mayocat.model.Entity;
import org.mayocat.model.HasAddons;
import org.mayocat.model.HasFeaturedImage;
import org.mayocat.model.Localized;
import org.mayocat.model.annotation.Index;
import org.mayocat.model.annotation.LocalizedField;
import com.google.common.base.Objects;

public class CollectionServer implements Entity, HasFeaturedImage, Localized, Child, HasAddons {

    private UUID id;

    private UUID parentId;

    @Index
    @NotNull
    @Size(min = 1)
    private String slug;

    @LocalizedField
    @Index
    @NotNull
    private String title;

    @LocalizedField
    @Index
    private String description;

    private UUID featuredImageId;

    private Map<Locale, Map<String, Object>> localizedVersions;

    private Association<Map<String, AddonGroup>> addons = Association.notLoaded();

    public CollectionServer() {
        super();
    }

    public CollectionServer(UUID id) {
        super();
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public UUID getParentId() {
        return this.parentId;
    }

    @Override
    public void setParentId(UUID parentId) {
        this.parentId = parentId;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public UUID getFeaturedImageId() {
        return featuredImageId;
    }

    public void setFeaturedImageId(UUID featuredImageId) {
        this.featuredImageId = featuredImageId;
    }

    public void setLocalizedVersions(Map<Locale, Map<String, Object>> versions) {
        this.localizedVersions = versions;
    }

    @Override
    public Map<Locale, Map<String, Object>> getLocalizedVersions() {
        return localizedVersions;
    }

    @Override
    public Association<Map<String, AddonGroup>> getAddons() {
        return addons;
    }

    @Override
    public void setAddons(Map<String, AddonGroup> addons) {
        this.addons = new Association(addons);
    }

    // //////////////////////////////////////////////
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Collection other = (Collection) obj;
        return Objects.equal(this.id, other.id) && Objects.equal(this.parentId, other.parentId) && Objects.equal(this.title, other.title) && Objects.equal(this.slug, other.slug) && Objects.equal(this.description, other.description);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(this.id, this.parentId, this.slug, this.title, this.description);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this).addValue(this.title).addValue(this.slug).toString();
    }
}

