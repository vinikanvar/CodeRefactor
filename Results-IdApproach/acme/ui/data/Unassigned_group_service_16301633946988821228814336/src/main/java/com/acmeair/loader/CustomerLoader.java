/**
 * ****************************************************************************
 *  Copyright (c) 2013 IBM Corp.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 * *****************************************************************************
 */
package com.acmeair.loader;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import com.acmeair.service.CustomerService;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ApplicationScoped
public class CustomerLoader {

    public void dropCustomers() {
        return;
    }

    public void loadCustomers(long numCustomers) {
        return;
    }

    public int id = 0;

    public static CustomerLoader getObject(int id) {
        CustomerLoader obj = (CustomerLoader) new Object();
        obj.id = id;
        return obj;
    }
}

