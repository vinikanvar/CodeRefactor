/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.accounts;

import javax.validation.Valid;
import org.mayocat.configuration.Configurable;
import org.mayocat.configuration.ExposedSettings;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: b06a96ac27f04cd609776948fe0b32647b433907 $
 */
public class AccountsSettings implements ExposedSettings {

    public String getKey() {
        return (String) null;
    }

    public Configurable<Boolean> getUserValidation() {
        return (Configurable) null;
    }

    public Configurable<String> getUserValidationUriTemplate() {
        return (Configurable) null;
    }

    public Configurable<String> getUserPasswordResetUriTemplate() {
        return (Configurable) null;
    }

    public Configurable<Boolean> getUserValidationRequiredForLogin() {
        return (Configurable) null;
    }

    public Configurable<Integer> getWebSessionDuration() {
        return (Configurable) null;
    }

    public Configurable<Integer> getApiSessionDuration() {
        return (Configurable) null;
    }

    public Configurable<Boolean> isAutoLoginAfterSignup() {
        return (Configurable) null;
    }

    public PasswordRequirementsSettings getPasswordRequirements() {
        return (PasswordRequirementsSettings) null;
    }

    public int id = 0;

    public static AccountsSettings getObject(int id) {
        AccountsSettings obj = (AccountsSettings) new Object();
        obj.id = id;
        return obj;
    }
}

