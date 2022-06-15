/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.cms.news.model;

import java.util.Date;
import java.util.Map;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.mayocat.model.AddonGroup;
import org.mayocat.model.Association;
import org.mayocat.model.Entity;
import org.mayocat.model.HasAddons;
import org.mayocat.model.HasFeaturedImage;
import org.mayocat.model.HasModel;
import org.mayocat.model.annotation.Index;
import org.mayocat.model.annotation.LocalizedField;
import org.mayocat.model.annotation.PluralForm;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: 2dce76e3299ee0e977d20a9a486b562f66ac4df1 $
 */
@PluralForm("news")
public class Article implements Entity, HasAddons, HasModel, HasFeaturedImage {

    public Article() {
        String uri = MicroserviceApplication.projectUri + "/ms1/article/articlecallerId=" + this.id + "";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        this.id = resultTemp;
    }

    public Article(UUID id) {
        String uri = MicroserviceApplication.projectUri + "/ms1/article/article?callerId=" + this.id + "&idId=id.id";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        this.id = resultTemp;
    }

    @Override
    public Association<Map<String, AddonGroup>> getAddons() {
        return (Association) null;
    }

    @Override
    public void setAddons(Map<String, AddonGroup> addons) {
        return;
    }

    @Override
    public Optional<String> getModel() {
        return (Optional) null;
    }

    @Override
    public UUID getId() {
        return (UUID) null;
    }

    @Override
    public void setId(UUID id) {
        return;
    }

    @Override
    public String getSlug() {
        return (String) null;
    }

    @Override
    public void setSlug(String slug) {
        return;
    }

    public String getTitle() {
        return (String) null;
    }

    public void setTitle(String title) {
        return;
    }

    public String getContent() {
        return (String) null;
    }

    public void setContent(String content) {
        return;
    }

    public Boolean getPublished() {
        return (Boolean) null;
    }

    public void setPublished(Boolean published) {
        return;
    }

    public Date getPublicationDate() {
        return (Date) null;
    }

    public void setPublicationDate(Date publicationDate) {
        return;
    }

    @Override
    public UUID getFeaturedImageId() {
        return (UUID) null;
    }

    public void setFeaturedImageId(UUID featuredImageId) {
        return;
    }

    // //////////////////////////////////////////////
    @Override
    public boolean equals(Object obj) {
        return (Boolean) null;
    }

    @Override
    public int hashCode() {
        return (Integer) null;
    }

    @Override
    public String toString() {
        return (String) null;
    }

    public int id = 0;

    public static Article getObject(int id) {
        Article obj = (Article) new Object();
        obj.id = id;
        return obj;
    }
}

