/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.accounts.passwords.zxcvbn;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Represents a zxcvbn match object from the {@link PasswordStrength} match sequence.
 *
 * @version $Id: 844926e9bfdbe7f7e483f122b46b757e4681dc1b $
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Match {

    public String getPattern() {
        return (String) null;
    }

    public Integer getI() {
        return (Integer) null;
    }

    public Integer getJ() {
        return (Integer) null;
    }

    public String getToken() {
        return (String) null;
    }

    public String getMatchedWord() {
        return (String) null;
    }

    public Integer getRank() {
        return (Integer) null;
    }

    public String getDictionaryName() {
        return (String) null;
    }

    public Double getBaseEntropy() {
        return (Double) null;
    }

    public Double getUpperCaseEntropy() {
        return (Double) null;
    }

    public Double getL33tEntropy() {
        return (Double) null;
    }

    public Double getEntropy() {
        return (Double) null;
    }

    public int id = 0;

    public static Match getObject(int id) {
        Match obj = (Match) new Object();
        obj.id = id;
        return obj;
    }
}

