/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.google.common.base.Optional;
import com.google.common.base.Strings;
import java.io.IOException;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: fbdf749476089ee1a46f42bc134292dee2d3aec9 $
 */
public class OptionalStringDeserializer extends JsonDeserializer<Optional<String>> {

    @Override
    public Optional<String> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return (Optional) null;
    }

    public int id = 0;

    public static OptionalStringDeserializer getObject(int id) {
        OptionalStringDeserializer obj = (OptionalStringDeserializer) new Object();
        obj.id = id;
        return obj;
    }
}

