// 
package com.ibm.research.cma.api;

// COPYRIGHT LICENSE: This information contains sample code provided in source code form. You may copy,
// modify, and distribute these sample programs in any form without payment to IBM for the purposes of
// developing, using, marketing or distributing application programs conforming to the application
// programming interface for the operating platform for which the sample code is written.
// Notwithstanding anything to the contrary, IBM PROVIDES THE SAMPLE SOURCE CODE ON AN "AS IS" BASIS
// AND IBM DISCLAIMS ALL WARRANTIES, EXPRESS OR IMPLIED, INCLUDING, BUT NOT LIMITED TO, ANY IMPLIED
// WARRANTIES OR CONDITIONS OF MERCHANTABILITY, SATISFACTORY QUALITY, FITNESS FOR A PARTICULAR PURPOSE,
// TITLE, AND ANY WARRANTY OR CONDITION OF NON-INFRINGEMENT. IBM SHALL NOT BE LIABLE FOR ANY DIRECT,
// INDIRECT, INCIDENTAL, SPECIAL OR CONSEQUENTIAL DAMAGES ARISING OUT OF THE USE OR OPERATION OF THE
// SAMPLE SOURCE CODE. IBM HAS NO OBLIGATION TO PROVIDE MAINTENANCE, SUPPORT, UPDATES, ENHANCEMENTS
// OR MODIFICATIONS TO THE SAMPLE SOURCE CODE.
// 
// (C) COPYRIGHT International Business Machines Corp., 2004,2011
// All Rights Reserved * Licensed Materials - Property of IBM
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Hashtable;
import java.util.Properties;
import java.util.StringTokenizer;
import java.util.Vector;
import com.ibm.websphere.samples.pbw.utils.*;
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
 * @author aamortim
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
/**
 *  Utility class.
 */
@Controller
@RequestMapping("/ms2/listproperties/")
public class ListPropertiesWrapper {

    /* Method load
     * @param inStream
     */
    @RequestMapping(method = RequestMethod.POST, value = "load")
    public @ResponseBody ResponseEntity<Void> load(@RequestParam int callerId, @RequestParam int inStreamId) {
        try {
            ListPropertiesServer callerObj = (ListPropertiesServer) idObjMap.get(callerId);
            callerObj.load(InputStream.getObject(inStreamId));
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Method getProperties.
     * @param name
     * @return values
     */
    @RequestMapping(method = RequestMethod.GET, value = "getproperties")
    public @ResponseBody Integer getProperties(@RequestParam int callerId, @RequestParam String name) {
        ListPropertiesServer callerObj = (ListPropertiesServer) idObjMap.get(callerId);
        String[] result = callerObj.getProperties(name);
        return new Integer(0);
    }

    private static int maxId = 0;

    public static HashMap<Integer, ListPropertiesServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "listproperties")
    public  @ResponseBody int ListProperties() {
        ListPropertiesServer newServerObj = new ListPropertiesServer();
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
}

