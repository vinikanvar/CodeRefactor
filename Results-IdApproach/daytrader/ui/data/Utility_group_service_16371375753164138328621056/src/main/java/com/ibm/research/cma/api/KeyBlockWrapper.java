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
package com.ibm.research.cma.api;

import java.util.AbstractSequentialList;
import java.util.ListIterator;
import com.ibm.websphere.samples.daytrader.util.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.HashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ms7/keyblock/")
public class KeyBlockWrapper {

    class KeyBlockIterator implements ListIterator<Object> {
    }

    private static int maxId = 0;

    public static HashMap<Integer, KeyBlockServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "keyblock_")
    public  @ResponseBody int KeyBlock() {
        KeyBlockServer newServerObj = new KeyBlockServer();
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }

    @RequestMapping(method = RequestMethod.POST, value = "keyblock")
    public  @ResponseBody int KeyBlock(@RequestParam int min, @RequestParam int max) {
        KeyBlockServer newServerObj = new KeyBlockServer(min, max);
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
}

