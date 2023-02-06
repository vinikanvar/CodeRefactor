/**
 * (C) Copyright IBM Corporation 2015.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ibm.websphere.samples.daytrader.web.websocket;

import java.io.StringReader;
import javax.json.Json;
import javax.json.JsonObject;
import javax.websocket.DecodeException;
import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class JsonDecoder implements Decoder.Text<JsonMessage> {

    @Override
    public void destroy() {
        return;
    }

    @Override
    public void init(EndpointConfig ec) {
        return;
    }

    @Override
    public JsonMessage decode(String json) throws DecodeException {
        return (JsonMessage) null;
    }

    @Override
    public boolean willDecode(String json) {
        return (Boolean) null;
    }

    public int id = 0;

    public static JsonDecoder getObject(int id) {
        JsonDecoder obj = (JsonDecoder) new Object();
        obj.id = id;
        return obj;
    }
}

