/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.rest.resources;

import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import org.mayocat.attachment.model.Attachment;
import org.mayocat.image.ImageService;
import org.mayocat.rest.Resource;
import org.mayocat.rest.annotation.ExistingTenant;
import org.slf4j.Logger;
import org.xwiki.component.annotation.Component;
import com.google.common.base.Optional;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: 09e00c503b681668f729e6c9ff7e89e2ea693622 $
 */
@Component("/tenant/{tenant}/api/attachments")
@Path("/tenant/{tenant}/api/attachments")
@ExistingTenant
public class AttachmentResource extends AbstractAttachmentResource implements Resource {

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response addAttachment(@FormDataParam("file") InputStream uploadedInputStream, @FormDataParam("file") FormDataContentDisposition fileDetail, @FormDataParam("title") String title, @FormDataParam("description") String description) {
        return (Response) null;
    }

    public int id = 0;

    public static AttachmentResource getObject(int id) {
        AttachmentResource obj = (AttachmentResource) new Object();
        obj.id = id;
        return obj;
    }
}

