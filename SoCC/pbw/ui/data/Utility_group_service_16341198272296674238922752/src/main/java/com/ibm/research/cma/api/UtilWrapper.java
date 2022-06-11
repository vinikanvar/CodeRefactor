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
// (C) COPYRIGHT International Business Machines Corp., 2001,2011
// All Rights Reserved * Licensed Materials - Property of IBM
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.StringTokenizer;
import javax.faces.application.Application;
import javax.faces.application.ProjectStage;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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
 *  Utility class.
 */
@Controller
@RequestMapping("/ms5/util/")
public class UtilWrapper {

    // Servlet action codes.
    /**
     * Get the displayable name of a category.
     * @param index The int representation of a category.
     * @return The category as a String (null, if an invalid index given).
     */
    @RequestMapping(method = RequestMethod.GET, value = "getcategorystring")
    public @ResponseBody String getCategoryString(@RequestParam int callerId, @RequestParam int index) {
        UtilServer callerObj = (UtilServer) idObjMap.get(callerId);
        String result = callerObj.getCategoryString(index);
        return result;
    }

    /**
     * Get the shipping method price.
     * @param index The int representation of a shipping method.
     * @return The shipping method price (-1, if an invalid index given).
     */
    @RequestMapping(method = RequestMethod.GET, value = "getshippingmethodprice")
    public  @ResponseBody float getShippingMethodPrice(@RequestParam int callerId, @RequestParam int index) {
        UtilServer callerObj = (UtilServer) idObjMap.get(callerId);
        float result = callerObj.getShippingMethodPrice(index);
        return result;
    }

    /**
     * Get the shipping method strings in an array.
     * @return The shipping method strings in an array.
     */
    @RequestMapping(method = RequestMethod.GET, value = "getshippingmethodstrings")
    public @ResponseBody Integer getShippingMethodStrings(@RequestParam int callerId) {
        UtilServer callerObj = (UtilServer) idObjMap.get(callerId);
        String[] result = callerObj.getShippingMethodStrings();
        return new Integer(0);
    }

    /**
     * Get the shipping method strings, including prices and times, in an array.
     * @return The shipping method strings, including prices and times, in an array.
     */
    @RequestMapping(method = RequestMethod.GET, value = "getfullshippingmethodstrings")
    public @ResponseBody Integer getFullShippingMethodStrings(@RequestParam int callerId) {
        UtilServer callerObj = (UtilServer) idObjMap.get(callerId);
        String[] result = callerObj.getFullShippingMethodStrings();
        return new Integer(0);
    }

    /**
     * Method readTokens.
     * @param text
     * @param token
     * @return list
     */
    @RequestMapping(method = RequestMethod.POST, value = "readtokens")
    public @ResponseBody Integer readTokens(@RequestParam int callerId, @RequestParam String text, @RequestParam String token) {
        UtilServer callerObj = (UtilServer) idObjMap.get(callerId);
        String[] result = callerObj.readTokens(text, token);
        return new Integer(0);
    }

    /**
     * Method getProperties.
     * @param name
     * @return values
     */
    @RequestMapping(method = RequestMethod.GET, value = "util_getproperties")
    public @ResponseBody Integer getProperties(@RequestParam int callerId, @RequestParam String name) {
        UtilServer callerObj = (UtilServer) idObjMap.get(callerId);
        String[] result = callerObj.getProperties(name);
        return new Integer(0);
    }

    /**
     * Set debug setting to on or off.
     * @param val True or false.
     */
    @RequestMapping(method = RequestMethod.POST, value = "setdebug")
    final public @ResponseBody ResponseEntity<Void> setDebug(@RequestParam int callerId, @RequestParam boolean val) {
        UtilServer callerObj = (UtilServer) idObjMap.get(callerId);
        callerObj.setDebug(val);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Is debug turned on?
     */
    @RequestMapping(method = RequestMethod.POST, value = "debugon")
    final public  @ResponseBody boolean debugOn(@RequestParam int callerId) {
        UtilServer callerObj = (UtilServer) idObjMap.get(callerId);
        boolean result = callerObj.debugOn();
        return result;
    }

    /**
     * Output RAS message.
     * @param msg Message to be output.
     */
    @RequestMapping(method = RequestMethod.POST, value = "debug")
    final public @ResponseBody ResponseEntity<Void> debug(@RequestParam int callerId, @RequestParam String msg) {
        UtilServer callerObj = (UtilServer) idObjMap.get(callerId);
        callerObj.debug(msg);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "validatestring")
    public  @ResponseBody boolean validateString(@RequestParam int callerId, @RequestParam String input) {
        UtilServer callerObj = (UtilServer) idObjMap.get(callerId);
        boolean result = callerObj.validateString(input);
        return result;
    }

    private static int maxId = 0;

    public static HashMap<Integer, UtilServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "util")
    public  @ResponseBody int Util() {
        UtilServer newServerObj = new UtilServer();
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
}

