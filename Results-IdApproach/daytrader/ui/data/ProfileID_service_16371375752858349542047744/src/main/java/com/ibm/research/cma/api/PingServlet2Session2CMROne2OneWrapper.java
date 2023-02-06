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
import javax.ejb.EJB;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ibm.websphere.samples.daytrader.ejb3.TradeSLSBBean;
import com.ibm.websphere.samples.daytrader.entities.AccountProfileDataBean;
import com.ibm.websphere.samples.daytrader.util.Log;
import com.ibm.websphere.samples.daytrader.util.TradeConfig;
import com.ibm.websphere.samples.daytrader.web.prims.ejb3.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.HashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Primitive to test Entity Container Managed Relationshiop One to One Servlet
 * will generate a random userID and get the profile for that user using a
 * {@link trade.Account} Entity EJB This tests the common path of a Servlet
 * calling a Session to Entity EJB to get CMR One to One data
 */
@WebServlet(name = "ejb3.PingServlet2Session2CMR2One2One", urlPatterns = { "/ejb3/PingServlet2Session2CMR2One2One" })
@Controller
@RequestMapping("/ms1/pingservlet2session2cmrone2one/")
public class PingServlet2Session2CMROne2OneWrapper {

    @RequestMapping(method = RequestMethod.POST, value = "pingservlet2session2cmrone2one_dopost")
    public @ResponseBody ResponseEntity<Void> doPost(@RequestParam int callerId, @RequestParam int reqId, @RequestParam int resId) {
        try {
            PingServlet2Session2CMROne2OneServer callerObj = (PingServlet2Session2CMROne2OneServer) idObjMap.get(callerId);
            callerObj.doPost(HttpServletRequest.getObject(reqId), HttpServletResponse.getObject(resId));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "pingservlet2session2cmrone2one_doget")
    public @ResponseBody ResponseEntity<Void> doGet(@RequestParam int callerId, @RequestParam int reqId, @RequestParam int resId) {
        try {
            PingServlet2Session2CMROne2OneServer callerObj = (PingServlet2Session2CMROne2OneServer) idObjMap.get(callerId);
            callerObj.doGet(HttpServletRequest.getObject(reqId), HttpServletResponse.getObject(resId));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "pingservlet2session2cmrone2one_init")
    public @ResponseBody ResponseEntity<Void> init(@RequestParam int callerId, @RequestParam int configId) {
        try {
            PingServlet2Session2CMROne2OneServer callerObj = (PingServlet2Session2CMROne2OneServer) idObjMap.get(callerId);
            callerObj.init(ServletConfig.getObject(configId));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static int maxId = 0;

    public static HashMap<Integer, PingServlet2Session2CMROne2OneServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "pingservlet2session2cmrone2one")
    public  @ResponseBody int PingServlet2Session2CMROne2One() {
        PingServlet2Session2CMROne2OneServer newServerObj = new PingServlet2Session2CMROne2OneServer();
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
}

