/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.store.rdbms.dbi.argument;

import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: 6ba3e5a3150e377a0f6a0fa103951168bc7df3ec $
 */
public class JsonArgument {

    public JsonArgument(Object wrapped) {
        String uri = MicroserviceApplication.projectUri + "/msnull/jsonargument/jsonargument?callerId=" + this.id + "&wrappedId=wrapped.id";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        this.id = resultTemp;
    }

    public Object getWrapped() {
        return (Object) null;
    }

    public int id = 0;

    public static JsonArgument getObject(int id) {
        JsonArgument obj = (JsonArgument) new Object();
        obj.id = id;
        return obj;
    }
}

