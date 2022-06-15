/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.rest.resources;

import com.google.common.base.Optional;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.ServletContext;
import javax.ws.rs.core.Response;
import org.mayocat.attachment.model.Attachment;
import org.mayocat.attachment.model.LoadedAttachment;
import org.mayocat.attachment.store.AttachmentStore;
import org.mayocat.image.ImageService;
import org.mayocat.rest.parameters.ImageOptions;
import org.slf4j.Logger;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: fdd665b717beb3d50d19336a71ea1216b826c7bd $
 */
public class AbstractImageResource {

    public Response downloadThumbnail(String slug, String extension, Integer x, Integer y, Integer width, Integer height, ServletContext servletContext, Optional<ImageOptions> imageOptions) {
        return (Response) null;
    }

    public Response downloadThumbnail(String tenantSlug, String slug, String extension, Integer x, Integer y, Integer width, Integer height, ServletContext servletContext, Optional<ImageOptions> imageOptions) {
        return (Response) null;
    }

    public Response downloadImage(String slug, String extension, ServletContext servletContext, Optional<ImageOptions> imageOptions) {
        return (Response) null;
    }

    public Response downloadImage(String tenantSlug, String slug, String extension, ServletContext servletContext, Optional<ImageOptions> imageOptions) {
        return (Response) null;
    }

    public Response downloadFile(String slug, String extension, ServletContext servletContext) {
        return (Response) null;
    }

    public Response downloadFile(String tenantSlug, String slug, String extension, ServletContext servletContext) {
        return (Response) null;
    }

    public int id = 0;

    public static AbstractImageResource getObject(int id) {
        AbstractImageResource obj = (AbstractImageResource) new Object();
        obj.id = id;
        return obj;
    }
}

