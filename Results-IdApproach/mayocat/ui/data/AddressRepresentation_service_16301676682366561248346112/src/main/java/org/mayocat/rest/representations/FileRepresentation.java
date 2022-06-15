/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.rest.representations;

import org.mayocat.attachment.model.Attachment;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: af8781414d115524b601315870e83b2063c4432c $
 */
public class FileRepresentation {

    public FileRepresentation() {
        String uri = MicroserviceApplication.projectUri + "/msnull/filerepresentation/filerepresentationcallerId=" + this.id + "";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        this.id = resultTemp;
    }

    public FileRepresentation(Attachment attachment, String href) {
        String uri = MicroserviceApplication.projectUri + "/msnull/filerepresentation/filerepresentation?callerId=" + this.id + "&attachmentId=attachment.id&href=href";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        this.id = resultTemp;
    }

    public FileRepresentation(String extension, String href) {
        String uri = MicroserviceApplication.projectUri + "/msnull/filerepresentation/filerepresentation?callerId=" + this.id + "&extension=extension&href=href";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        this.id = resultTemp;
    }

    public String getExtension() {
        return (String) null;
    }

    public void setExtension(String extension) {
        return;
    }

    public String getHref() {
        return (String) null;
    }

    public void setHref(String href) {
        return;
    }

    public int id = 0;

    public static FileRepresentation getObject(int id) {
        FileRepresentation obj = (FileRepresentation) new Object();
        obj.id = id;
        return obj;
    }
}

