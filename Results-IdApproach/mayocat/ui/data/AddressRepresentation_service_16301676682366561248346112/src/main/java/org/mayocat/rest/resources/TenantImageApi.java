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
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: b8d5cb42582c274909ee8abf01b7b61ca187e192 $
 */
@Component("/tenant/{tenant}/api/images/")
@Path("/tenant/{tenant}/api/images/")
@ExistingTenant
public class TenantImageApi extends AbstractImageResource implements Resource {

    @PUT
    @Path("{slug}/thumbnails/")
    public Response createThumbnail(@PathParam("slug") String slug, @Valid List<ThumbnailRepresentation> thumbnailRepresentations) {
        return (Response) null;
    }

    @GET
    @Path("{slug}/file/{slug}.{ext}")
    public Response downloadImage(@PathParam("slug") String slug, @PathParam("ext") String extension, @Context ServletContext servletContext, @Context Optional<ImageOptions> imageOptions) {
        return (Response) null;
    }

    public int id = 0;

    public static TenantImageApi getObject(int id) {
        TenantImageApi obj = (TenantImageApi) new Object();
        obj.id = id;
        return obj;
    }
}

