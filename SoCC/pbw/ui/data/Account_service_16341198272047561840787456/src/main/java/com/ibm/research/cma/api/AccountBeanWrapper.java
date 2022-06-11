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
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.Application;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import com.ibm.websphere.samples.pbw.bean.CustomerMgr;
import com.ibm.websphere.samples.pbw.bean.MailerAppException;
import com.ibm.websphere.samples.pbw.bean.MailerBean;
import com.ibm.websphere.samples.pbw.bean.ShoppingCartBean;
import com.ibm.websphere.samples.pbw.jpa.Customer;
import com.ibm.websphere.samples.pbw.utils.Util;
import com.ibm.websphere.samples.pbw.war.*;
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
 * Provides a combination of JSF action and backing bean support for the account web page.
 */
@Named(value = "account")
@SessionScoped
@Controller
@RequestMapping("/ms0/accountbean/")
public class AccountBeanWrapper {

    @RequestMapping(method = RequestMethod.POST, value = "performaccount")
    public @ResponseBody String performAccount(@RequestParam int callerId) {
        AccountBeanServer callerObj = (AccountBeanServer) idObjMap.get(callerId);
        String result = callerObj.performAccount();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "performaccountupdate")
    public @ResponseBody String performAccountUpdate(@RequestParam int callerId) {
        AccountBeanServer callerObj = (AccountBeanServer) idObjMap.get(callerId);
        String result = callerObj.performAccountUpdate();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "performcheckoutfinal")
    public @ResponseBody String performCheckoutFinal(@RequestParam int callerId) {
        AccountBeanServer callerObj = (AccountBeanServer) idObjMap.get(callerId);
        String result = callerObj.performCheckoutFinal();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "performcompletecheckout")
    public @ResponseBody String performCompleteCheckout(@RequestParam int callerId) {
        AccountBeanServer callerObj = (AccountBeanServer) idObjMap.get(callerId);
        String result = callerObj.performCompleteCheckout();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "performlogin")
    public @ResponseBody String performLogin(@RequestParam int callerId) {
        AccountBeanServer callerObj = (AccountBeanServer) idObjMap.get(callerId);
        String result = callerObj.performLogin();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "performlogincomplete")
    public @ResponseBody String performLoginComplete(@RequestParam int callerId) {
        AccountBeanServer callerObj = (AccountBeanServer) idObjMap.get(callerId);
        String result = callerObj.performLoginComplete();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "performorderinfo")
    public @ResponseBody String performOrderInfo(@RequestParam int callerId) {
        AccountBeanServer callerObj = (AccountBeanServer) idObjMap.get(callerId);
        String result = callerObj.performOrderInfo();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "performregister")
    public @ResponseBody String performRegister(@RequestParam int callerId) {
        AccountBeanServer callerObj = (AccountBeanServer) idObjMap.get(callerId);
        String result = callerObj.performRegister();
        return result;
    }

    private static int maxId = 0;

    public static HashMap<Integer, AccountBeanServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "accountbean")
    public  @ResponseBody int AccountBean() {
        AccountBeanServer newServerObj = new AccountBeanServer();
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
}

