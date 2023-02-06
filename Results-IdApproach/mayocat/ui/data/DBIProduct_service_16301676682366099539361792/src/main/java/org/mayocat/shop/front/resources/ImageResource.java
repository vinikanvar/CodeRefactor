/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.shop.front.resources;

import com.google.common.base.Optional;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import org.mayocat.rest.Resource;
import org.mayocat.rest.parameters.ImageOptions;
import org.mayocat.rest.resources.AbstractImageResource;
import org.xwiki.component.annotation.Component;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: ed314c42063da968f0dc06a740ada2f1b7689562 $
 */
@Component("/images/")
@Path("/images/")
public class ImageResource extends AbstractImageResource implements Resource {

    @GET
    @Path("thumbnails/{slug}_{x: \\d+}_{y: \\d+}_{width: \\d+}_{height: \\d+}.{ext}")
    public Response downloadThumbnail(@PathParam("slug") String slug, @PathParam("ext") String extension, @PathParam("x") Integer x, @PathParam("y") Integer y, @PathParam("width") Integer width, @PathParam("height") Integer height, @Context ServletContext servletContext, @Context Optional<ImageOptions> imageOptions) {
        return (Response) null;
    }

    @GET
    @Path("{slug}.{ext}")
    public Response downloadImage(@PathParam("slug") String slug, @PathParam("ext") String extension, @Context ServletContext servletContext, @Context Optional<ImageOptions> imageOptions) {
        return (Response) null;
    }

    public int id = 0;

    public static ImageResource getObject(int id) {
        ImageResource obj = (ImageResource) new Object();
        obj.id = id;
        return obj;
    }
}

