/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.rest.representations;

import java.util.List;
import org.mayocat.image.model.Image;
import org.mayocat.image.model.Thumbnail;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;

/**
 * @version $Id: f9d701347211ff3a5d91501216d0aadfe367210b $
 */
public class ImageRepresentationServer extends AttachmentRepresentation {

    private List<ThumbnailRepresentation> thumbnails;

    private Boolean featured = null;

    public ImageRepresentationServer() {
        // No-arg constructor required for Jackson deserialization
        super();
    }

    public ImageRepresentationServer(Image image, Boolean featured) {
        super(image.getAttachment(), buildImageApiHref(image), buildFileRepresentation(image));
        this.thumbnails = buildThumbnailsRepresentation(image);
        this.featured = featured;
    }

    public ImageRepresentationServer(Image image) {
        this(image, null);
    }

    public List<ThumbnailRepresentation> getThumbnails() {
        return thumbnails;
    }

    @JsonIgnore
    public boolean isFeaturedImage() {
        return featured != null && featured;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    // /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private static String buildImageApiHref(Image image) {
        return "/api/images/" + image.getAttachment().getSlug();
    }

    private static String buildImageFileHref(Image image) {
        return "/images/" + image.getAttachment().getSlug() + "." + image.getAttachment().getExtension();
    }

    private static FileRepresentation buildFileRepresentation(Image image) {
        return new FileRepresentation(image.getAttachment(), buildImageFileHref(image));
    }

    private static List<ThumbnailRepresentation> buildThumbnailsRepresentation(Image image) {
        List<ThumbnailRepresentation> thumbnailRepresentations = Lists.newArrayList();
        for (Thumbnail thumb : image.getThumbnails()) {
            thumbnailRepresentations.add(new ThumbnailRepresentation(thumb));
        }
        return thumbnailRepresentations;
    }
}

