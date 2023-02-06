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

import java.io.IOException;
import java.math.BigDecimal;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ibm.websphere.samples.daytrader.TradeAction;
import com.ibm.websphere.samples.daytrader.util.Log;
import com.ibm.websphere.samples.daytrader.util.TradeConfig;
import com.ibm.websphere.samples.daytrader.web.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.HashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

@WebServlet(name = "TestServlet", urlPatterns = { "/TestServlet" })
@Controller
@RequestMapping("/ms1/testservlet/")
public class TestServletWrapper {

    @RequestMapping(method = RequestMethod.POST, value = "testservlet_init")
    public @ResponseBody ResponseEntity<Void> init(@RequestParam int callerId, @RequestParam int configId) {
        try {
            TestServletServer callerObj = (TestServletServer) idObjMap.get(callerId);
            callerObj.init(ServletConfig.getObject(configId));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Process incoming HTTP GET requests
     *
     * @param request
     *            Object that encapsulates the request to the servlet
     * @param response
     *            Object that encapsulates the response from the servlet
     */
    @RequestMapping(method = RequestMethod.GET, value = "testservlet_doget")
    public @ResponseBody ResponseEntity<Void> doGet(@RequestParam int callerId, @RequestParam int requestId, @RequestParam int responseId) {
        try {
            TestServletServer callerObj = (TestServletServer) idObjMap.get(callerId);
            callerObj.doGet(HttpServletRequest.getObject(requestId), HttpServletResponse.getObject(responseId));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Process incoming HTTP POST requests
     *
     * @param request
     *            Object that encapsulates the request to the servlet
     * @param response
     *            Object that encapsulates the response from the servlet
     */
    @RequestMapping(method = RequestMethod.POST, value = "testservlet_dopost")
    public @ResponseBody ResponseEntity<Void> doPost(@RequestParam int callerId, @RequestParam int requestId, @RequestParam int responseId) {
        try {
            TestServletServer callerObj = (TestServletServer) idObjMap.get(callerId);
            callerObj.doPost(HttpServletRequest.getObject(requestId), HttpServletResponse.getObject(responseId));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static int maxId = 0;

    public static HashMap<Integer, TestServletServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "testservlet")
    public  @ResponseBody int TestServlet() {
        TestServletServer newServerObj = new TestServletServer();
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
}

