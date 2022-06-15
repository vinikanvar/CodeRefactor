/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.configuration.internal;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.google.common.collect.Maps;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: c0e914243eca523196398ba8aae35f4cb80285b7 $
 */
public class ValidConfigurationEnforcer extends AbstractJsonConfigurationHandler {

    public ValidConfigurationEnforcer(Map<String, Serializable> platform, Map<String, Serializable> tenant) {
        String uri = MicroserviceApplication.projectUri + "/msnull/validconfigurationenforcer/validconfigurationenforcer?callerId=" + this.id + "&platformId=platform.id&tenantId=tenant.id";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        this.id = resultTemp;
    }

    public class ValidationResult {

        private HashMap<String, Serializable> result;

        private boolean hasErrors = false;

        public HashMap<String, Serializable> getResult() {
            return (HashMap) null;
        }

        public void setResult(HashMap<String, Serializable> result) {
            return;
        }

        public boolean isHasErrors() {
            return (Boolean) null;
        }

        public void setHasErrors(boolean hasErrors) {
            return;
        }
    }

    public ValidationResult enforce() {
        return (ValidationResult) null;
    }

    private ValidationResult enforce(Map<String, Serializable> global, Map<String, Serializable> local) {
        return (ValidationResult) null;
    }

    public int id = 0;

    public static ValidConfigurationEnforcer getObject(int id) {
        ValidConfigurationEnforcer obj = (ValidConfigurationEnforcer) new Object();
        obj.id = id;
        return obj;
    }
}

