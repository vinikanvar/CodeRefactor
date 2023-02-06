/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.shop.front.resources;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Date;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.CacheControl;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.EntityTag;
import javax.ws.rs.core.Request;
import javax.ws.rs.core.Response;
import org.mayocat.context.WebContext;
import org.mayocat.rest.Resource;
import org.mayocat.theme.ThemeFileResolver;
import org.mayocat.theme.ThemeResource;
import org.slf4j.Logger;
import org.xwiki.component.annotation.Component;
import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.hash.Hashing;
import com.google.common.io.Files;
import com.google.common.io.Resources;
import io.dropwizard.servlets.assets.ResourceURL;

/**
 * @version $Id: 7d9ab5465990a316a4c5e14aad437af7c9e9c687 $
 */
@Component(ResourceResource.PATH)
@Path(ResourceResource.PATH)
public class ResourceResourceServer implements Resource {

    public static final String PATH = "/resources/";

    @Inject
    private ThemeFileResolver themeFileResolver;

    @Inject
    private Logger logger;

    @Inject
    private WebContext context;

    @GET
    @Path("{path:.+}")
    public Response getResource(@PathParam("path") String resource, @Context Request request) throws Exception {
        ThemeResource themeResource = themeFileResolver.getResource(resource, context.getRequest().getBreakpoint());
        if (themeResource == null) {
            logger.debug("Resource [{}] not found", resource);
            throw new WebApplicationException(404);
        }
        File file;
        switch(themeResource.getType()) {
            default:
            case FILE:
                file = themeResource.getPath().toFile();
                break;
            case CLASSPATH_RESOURCE:
                URI uri = Resources.getResource(themeResource.getPath().toString()).toURI();
                if (uri.getScheme().equals("jar")) {
                    // Not supported for now
                    return Response.status(Response.Status.NOT_FOUND).build();
                }
                file = new File(uri);
                break;
        }
        String tag = Files.hash(file, Hashing.murmur3_128()).toString();
        EntityTag eTag = new EntityTag(tag);
        URL url = file.toURI().toURL();
        long lastModified = ResourceURL.getLastModified(url);
        if (lastModified < 1) {
            // Something went wrong trying to get the last modified time: just use the current time
            lastModified = System.currentTimeMillis();
        }
        // zero out the millis since the date we get back from If-Modified-Since will not have them
        lastModified = (lastModified / 1000) * 1000;
        CacheControl cacheControl = new CacheControl();
        cacheControl.setMaxAge(24 * 3600);
        Response.ResponseBuilder builder = request.evaluatePreconditions(new Date(lastModified), eTag);
        String mimeType = guessMimeType(file).or("application/octet-stream");
        if (builder == null) {
            if (mimeType.equals("application/javascript")) {
                // Handle javascript files as a special case. Something (what ?) is serializing them as URIs instead
                // of file contents.
                // FIXME: find out what at in Jersey or Jackson or DW is doing that and disable that behavior
                builder = Response.ok(Files.toString(file, Charsets.UTF_8), mimeType);
            } else {
                builder = Response.ok(file, mimeType);
            }
        }
        return builder.cacheControl(cacheControl).lastModified(new Date(lastModified)).build();
    }

    private Optional<String> guessMimeType(File file) {
        try {
            return Optional.fromNullable(java.nio.file.Files.probeContentType(file.toPath()));
        } catch (IOException e) {
            this.logger.warn("Error while attempting to resource mime type", e);
            return Optional.absent();
        }
    }
}

