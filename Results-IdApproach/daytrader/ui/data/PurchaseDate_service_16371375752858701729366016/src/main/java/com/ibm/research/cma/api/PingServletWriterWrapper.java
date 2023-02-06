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
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ibm.websphere.samples.daytrader.util.Log;
import com.ibm.websphere.samples.daytrader.web.prims.*;
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
 * PingServlet extends PingServlet by using a PrintWriter for formatted output
 * vs. the output stream used by {@link PingServlet}.
 */
@WebServlet(name = "PingServletWriter", urlPatterns = { "/servlet/PingServletWriter" })
@Controller
@RequestMapping("/ms4/pingservletwriter/")
public class PingServletWriterWrapper {

    /**
     * forwards post requests to the doGet method Creation date: (11/6/2000
     * 10:52:39 AM)
     *
     * @param res
     *            javax.servlet.http.HttpServletRequest
     * @param res2
     *            javax.servlet.http.HttpServletResponse
     */
    @RequestMapping(method = RequestMethod.POST, value = "pingservletwriter_dopost")
    public @ResponseBody ResponseEntity<Void> doPost(@RequestParam int callerId, @RequestParam int reqId, @RequestParam int resId) {
        try {
            PingServletWriterServer callerObj = (PingServletWriterServer) idObjMap.get(callerId);
            callerObj.doPost(HttpServletRequest.getObject(reqId), HttpServletResponse.getObject(resId));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * this is the main method of the servlet that will service all get
     * requests.
     *
     * @param request
     *            HttpServletRequest
     * @param responce
     *            HttpServletResponce
     */
    @RequestMapping(method = RequestMethod.GET, value = "pingservletwriter_doget")
    public @ResponseBody ResponseEntity<Void> doGet(@RequestParam int callerId, @RequestParam int reqId, @RequestParam int resId) {
        try {
            PingServletWriterServer callerObj = (PingServletWriterServer) idObjMap.get(callerId);
            callerObj.doGet(HttpServletRequest.getObject(reqId), HttpServletResponse.getObject(resId));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * returns a string of information about the servlet
     *
     * @return info String: contains info about the servlet
     */
    /**
     * called when the class is loaded to initialize the servlet
     *
     * @param config
     *            ServletConfig:
     */
    @RequestMapping(method = RequestMethod.POST, value = "pingservletwriter_init")
    public @ResponseBody ResponseEntity<Void> init(@RequestParam int callerId, @RequestParam int configId) {
        try {
            PingServletWriterServer callerObj = (PingServletWriterServer) idObjMap.get(callerId);
            callerObj.init(ServletConfig.getObject(configId));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static int maxId = 0;

    public static HashMap<Integer, PingServletWriterServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "pingservletwriter")
    public  @ResponseBody int PingServletWriter() {
        PingServletWriterServer newServerObj = new PingServletWriterServer();
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
}

