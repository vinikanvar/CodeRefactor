/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.attachment.store;

import java.util.List;
import java.util.UUID;
import org.mayocat.attachment.model.Attachment;
import org.mayocat.attachment.model.LoadedAttachment;
import org.mayocat.model.Entity;
import org.mayocat.store.EntityDoesNotExistException;
import org.mayocat.store.EntityStore;
import org.mayocat.store.Store;
import org.xwiki.component.annotation.Role;

/**
 * @version $Id: fa22204b9799238718a64e6cbe5c71ae42da8d2e $
 */
@Role
public interface AttachmentStore extends Store<Attachment, UUID>, EntityStore {

    LoadedAttachment findAndLoadById(UUID id);

    LoadedAttachment findAndLoadBySlug(String slug);

    LoadedAttachment findAndLoadByTenantAndSlugAndExtension(String tenantSlug, String fileName, String extension);

    LoadedAttachment findAndLoadBySlugAndExtension(String fileName, String extension);

    Attachment findBySlugAndExtension(String fileName, String extension);

    Attachment findByTenantAndSlugAndExtension(String tenantSlug, String fileName, String extension);

    List<Attachment> findAllChildrenOf(Entity parent);

    List<Attachment> findAllChildrenOf(Entity parent, List<String> extensions);

    List<Attachment> findAllChildrenOfParentIds(List<UUID> parents);

    List<Attachment> findAllChildrenOfParentIds(List<UUID> parents, List<String> extensions);

    void detach(Attachment attachment) throws EntityDoesNotExistException;

    public int id = 0;

    public static AttachmentStore getObject(int id) {
        return null;
    }
}

