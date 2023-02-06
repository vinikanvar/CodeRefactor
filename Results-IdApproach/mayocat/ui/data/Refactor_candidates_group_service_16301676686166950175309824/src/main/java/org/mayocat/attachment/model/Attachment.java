/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.attachment.model;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import org.mayocat.model.Child;
import org.mayocat.model.Entity;
import org.mayocat.model.Localized;
import org.mayocat.model.annotation.LocalizedField;
import com.google.common.base.Objects;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: 08724e69a979b7d256d45ee798f62c53f7af1467 $
 */
public class Attachment implements Entity, Child, Localized {

    public Attachment() {
        String uri = MicroserviceApplication.projectUri + "/ms1/attachment/attachmentcallerId=" + this.id + "";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        this.id = resultTemp;
    }

    @Override
    public String getSlug() {
        return (String) null;
    }

    @Override
    public void setSlug(String slug) {
        return;
    }

    @Override
    public UUID getId() {
        return (UUID) null;
    }

    @Override
    public void setId(UUID id) {
        return;
    }

    public String getTitle() {
        return (String) null;
    }

    public void setTitle(String title) {
        return;
    }

    public String getDescription() {
        return (String) null;
    }

    public void setDescription(String description) {
        return;
    }

    public String getExtension() {
        return (String) null;
    }

    public void setExtension(String extension) {
        return;
    }

    public Map<String, Map<String, Object>> getMetadata() {
        return (Map) null;
    }

    public void setMetadata(Map<String, Map<String, Object>> metadata) {
        return;
    }

    @Override
    public UUID getParentId() {
        return (UUID) null;
    }

    public void setParentId(UUID parentId) {
        return;
    }

    @Override
    public Map<Locale, Map<String, Object>> getLocalizedVersions() {
        return (Map) null;
    }

    public void setLocalizedVersions(Map<Locale, Map<String, Object>> versions) {
        return;
    }

    // //////////////////////////////////////////////
    public String getFilename() {
        return (String) null;
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

    public static Attachment getObject(int id) {
        Attachment obj = (Attachment) new Object();
        obj.id = id;
        return obj;
    }
}

