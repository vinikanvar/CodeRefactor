/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.mail;

import java.util.Map;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.Optional;
import com.google.common.collect.Maps;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: 2cc5b723f1b8def7eb49a5da12dfc015044ec644 $
 */
public class SmtpSettings {

    public String getServer() {
        return (String) null;
    }

    public Integer getPort() {
        return (Integer) null;
    }

    public Optional<String> getUsername() {
        return (Optional) null;
    }

    public Optional<String> getPassword() {
        return (Optional) null;
    }

    public Map<String, String> getProperties() {
        return (Map) null;
    }

    public int id = 0;

    public static SmtpSettings getObject(int id) {
        SmtpSettings obj = (SmtpSettings) new Object();
        obj.id = id;
        return obj;
    }
}

