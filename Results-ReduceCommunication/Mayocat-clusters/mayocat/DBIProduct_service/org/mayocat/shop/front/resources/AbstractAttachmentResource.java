/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.shop.front.resources;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Response;
import org.mayocat.attachment.model.LoadedAttachment;
import org.mayocat.attachment.store.AttachmentStore;

/**
 * @version $Id: 2815b8c5117f2d4f96da36961381757a8005e0bb $
 */
public class AbstractAttachmentResource {

    @Inject
    protected Provider<AttachmentStore> attachmentStore;

    public Response downloadFile(String slug, String extension, ServletContext servletContext) {
        String fileName = slug + "." + extension;
        LoadedAttachment file = this.attachmentStore.get().findAndLoadBySlugAndExtension(slug, extension);
        if (file == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(file.getData().getStream(), servletContext.getMimeType(fileName)).header("Content-disposition", "inline; filename*=utf-8''" + fileName).build();
    }

    protected AttachmentStore getAttachmentStore() {
        return this.attachmentStore.get();
    }
}
