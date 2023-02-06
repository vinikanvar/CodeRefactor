/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.rest.resources;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.servlet.ServletContext;
import javax.validation.Valid;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import org.mayocat.attachment.model.Attachment;
import org.mayocat.attachment.store.AttachmentStore;
import org.mayocat.image.model.Thumbnail;
import org.mayocat.image.store.ThumbnailStore;
import org.mayocat.image.util.ImageUtils;
import org.mayocat.rest.Resource;
import org.mayocat.rest.annotation.ExistingTenant;
import org.mayocat.rest.parameters.ImageOptions;
import org.mayocat.rest.representations.ThumbnailRepresentation;
import org.xwiki.component.annotation.Component;
import com.google.common.base.Optional;
import com.google.common.base.Strings;

/**
 * @version $Id: b8d5cb42582c274909ee8abf01b7b61ca187e192 $
 */
@Component("/tenant/{tenant}/api/images/")
@Path("/tenant/{tenant}/api/images/")
@ExistingTenant
public class TenantImageApi extends AbstractImageResource implements Resource {

    @Inject
    private Provider<ThumbnailStore> thumbnailStore;

    @PUT
    @Path("{slug}/thumbnails/")
    public Response createThumbnail(@PathParam("slug") String slug, @Valid List<ThumbnailRepresentation> thumbnailRepresentations) {
        Attachment file = this.attachmentStore.get().findAndLoadBySlug(slug);
        if (file == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        for (ThumbnailRepresentation thumbnailRepresentation : thumbnailRepresentations) {
            Thumbnail thumbnail = new Thumbnail();
            thumbnail.setAttachmentId(file.getId());
            thumbnail.setSource(thumbnailRepresentation.getSource());
            thumbnail.setHint(thumbnailRepresentation.getHint());
            thumbnail.setX(thumbnailRepresentation.getX());
            thumbnail.setY(thumbnailRepresentation.getY());
            thumbnail.setWidth(thumbnailRepresentation.getWidth());
            thumbnail.setHeight(thumbnailRepresentation.getHeight());
            if (Strings.isNullOrEmpty(thumbnailRepresentation.getRatio())) {
                thumbnail.setRatio(ImageUtils.imageRatio(thumbnail.getWidth(), thumbnail.getHeight()));
            } else {
                thumbnail.setRatio(thumbnailRepresentation.getRatio());
            }
            this.thumbnailStore.get().createOrUpdateThumbnail(thumbnail);
        }
        return Response.ok().build();
    }

    @GET
    @Path("{slug}/file/{slug}.{ext}")
    public Response downloadImage(@PathParam("slug") String slug, @PathParam("ext") String extension, @Context ServletContext servletContext, @Context Optional<ImageOptions> imageOptions) {
        return super.downloadImage(slug, extension, servletContext, imageOptions);
    }
}
