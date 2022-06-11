/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.shop.invoicing;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.mayocat.configuration.Configurable;
import org.mayocat.configuration.ExposedSettings;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: a862cf46025a0b13f0934cc92227fa98f1e3e41f $
 */
public class InvoicingSettings implements ExposedSettings {

    public Configurable<Boolean> getEnabled() {
        return (Configurable) null;
    }

    public Configurable<String> getContentBefore() {
        return (Configurable) null;
    }

    public Configurable<String> getContentAfter() {
        return (Configurable) null;
    }

    public Configurable<String> getFooterLegalNotice() {
        return (Configurable) null;
    }

    @Override
    public String getKey() {
        return (String) null;
    }

    public int id = 0;

    public static InvoicingSettings getObject(int id) {
        InvoicingSettings obj = (InvoicingSettings) new Object();
        obj.id = id;
        return obj;
    }
}

