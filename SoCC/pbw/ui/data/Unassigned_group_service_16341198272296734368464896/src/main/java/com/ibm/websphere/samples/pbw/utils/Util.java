// 
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
// 
package com.ibm.websphere.samples.pbw.utils;

import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.StringTokenizer;
import javax.faces.application.Application;
import javax.faces.application.ProjectStage;
import javax.faces.context.FacesContext;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 *  Utility class.
 */
public class Util {

    // Servlet action codes.
    /**
     * Return the cached Initial Context.
     *
     * @return InitialContext, or null if a naming exception.
     */
    static public InitialContext getInitialContext() {
        return (InitialContext) null;
    }

    /**
     * Get the displayable name of a category.
     * @param index The int representation of a category.
     * @return The category as a String (null, if an invalid index given).
     */
    static public String getCategoryString(int index) {
        String uri = MicroserviceApplication.projectUri + "/ms5/util/getcategorystring?index=index";
        String resultTemp = new RestTemplate().getForObject(uri, String.class);
        String result = resultTemp;
        return result;
    }

    /**
     * Get the category strings in an array.
     *
     * @return The category strings in an array.
     */
    static public String[] getCategoryStrings() {
        return (String[]) null;
    }

    /**
     * Get the shipping method.
     * @param index The int representation of a shipping method.
     * @return The shipping method (null, if an invalid index given).
     */
    static public String getShippingMethod(int index) {
        return (String) null;
    }

    /**
     * Get the shipping method price.
     * @param index The int representation of a shipping method.
     * @return The shipping method price (-1, if an invalid index given).
     */
    static public float getShippingMethodPrice(int index) {
        String uri = MicroserviceApplication.projectUri + "/ms5/util/getshippingmethodprice?index=index";
        float resultTemp = new RestTemplate().getForObject(uri, float.class);
        float result = resultTemp;
        return result;
    }

    /**
     * Get the shipping method price.
     * @param index The int representation of a shipping method.
     * @return The shipping method time (null, if an invalid index given).
     */
    static public String getShippingMethodTime(int index) {
        return (String) null;
    }

    /**
     * Get the shipping method strings in an array.
     * @return The shipping method strings in an array.
     */
    static public String[] getShippingMethodStrings() {
        String uri = MicroserviceApplication.projectUri + "/ms5/util/getshippingmethodstrings";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        String[] result = (String[]) new Object();
        return result;
    }

    /**
     * Get the shipping method strings, including prices and times, in an array.
     * @return The shipping method strings, including prices and times, in an array.
     */
    static public String[] getFullShippingMethodStrings() {
        String uri = MicroserviceApplication.projectUri + "/ms5/util/getfullshippingmethodstrings";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        String[] result = (String[]) new Object();
        return result;
    }

    /**
     * Method readProperties.
     */
    public static void readProperties() throws FileNotFoundException {
        return;
    }

    /**
     * Method getProperty.
     * @param name
     * @return value
     */
    public static String getProperty(String name) {
        return (String) null;
    }

    /**
     * Method readTokens.
     * @param text
     * @param token
     * @return list
     */
    public static String[] readTokens(String text, String token) {
        String uri = MicroserviceApplication.projectUri + "/ms5/util/readtokens?text=text&token=token";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        String[] result = (String[]) new Object();
        return result;
    }

    /**
     * Method getProperties.
     * @param name
     * @return values
     */
    public static String[] getProperties(String name) {
        String uri = MicroserviceApplication.projectUri + "/ms5/util/util_getproperties?name=name";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        String[] result = (String[]) new Object();
        return result;
    }

    /**
     * Set debug setting to on or off.
     * @param val True or false.
     */
    static final public void setDebug(boolean val) {
        String uri = MicroserviceApplication.projectUri + "/ms5/util/setdebug?val=val";
        new RestTemplate().getForObject(uri, void.class);
    }

    /**
     * Is debug turned on?
     */
    static final public boolean debugOn() {
        String uri = MicroserviceApplication.projectUri + "/ms5/util/debugon";
        boolean resultTemp = new RestTemplate().getForObject(uri, boolean.class);
        boolean result = resultTemp;
        return result;
    }

    /**
     * Output RAS message.
     * @param msg Message to be output.
     */
    static final public void debug(String msg) {
        String uri = MicroserviceApplication.projectUri + "/ms5/util/debug?msg=msg";
        new RestTemplate().getForObject(uri, void.class);
    }

    public static boolean validateString(String input) {
        String uri = MicroserviceApplication.projectUri + "/ms5/util/validatestring?input=input";
        boolean resultTemp = new RestTemplate().getForObject(uri, boolean.class);
        boolean result = resultTemp;
        return result;
    }

    public int id = 0;

    public static Util getObject(int id) {
        Util obj = (Util) new Object();
        obj.id = id;
        return obj;
    }
}

