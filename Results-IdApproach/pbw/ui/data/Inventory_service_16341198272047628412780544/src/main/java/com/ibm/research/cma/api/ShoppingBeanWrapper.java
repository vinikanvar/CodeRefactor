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
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import com.ibm.websphere.samples.pbw.bean.CatalogMgr;
import com.ibm.websphere.samples.pbw.bean.ShoppingCartBean;
import com.ibm.websphere.samples.pbw.jpa.Inventory;
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
 * A combination JSF action bean and backing bean for the shopping web page.
 */
@Named(value = "shopping")
@SessionScoped
@Controller
@RequestMapping("/ms1/shoppingbean/")
public class ShoppingBeanWrapper {

    @RequestMapping(method = RequestMethod.POST, value = "performaddtocart")
    public @ResponseBody String performAddToCart(@RequestParam int callerId) {
        ShoppingBeanServer callerObj = (ShoppingBeanServer) idObjMap.get(callerId);
        String result = callerObj.performAddToCart();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "performcart")
    public @ResponseBody String performCart(@RequestParam int callerId) {
        ShoppingBeanServer callerObj = (ShoppingBeanServer) idObjMap.get(callerId);
        String result = callerObj.performCart();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "performproductdetail")
    public @ResponseBody String performProductDetail(@RequestParam int callerId) {
        ShoppingBeanServer callerObj = (ShoppingBeanServer) idObjMap.get(callerId);
        String result = callerObj.performProductDetail();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "performrecalculate")
    public @ResponseBody String performRecalculate(@RequestParam int callerId) {
        ShoppingBeanServer callerObj = (ShoppingBeanServer) idObjMap.get(callerId);
        String result = callerObj.performRecalculate();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "performshopping")
    public @ResponseBody String performShopping(@RequestParam int callerId) {
        ShoppingBeanServer callerObj = (ShoppingBeanServer) idObjMap.get(callerId);
        String result = callerObj.performShopping();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "setshippingcost")
    public @ResponseBody ResponseEntity<Void> setShippingCost(@RequestParam int callerId, @RequestParam float shippingCost) {
        ShoppingBeanServer callerObj = (ShoppingBeanServer) idObjMap.get(callerId);
        callerObj.setShippingCost(shippingCost);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private static int maxId = 0;

    public static HashMap<Integer, ShoppingBeanServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "shoppingbean")
    public  @ResponseBody int ShoppingBean() {
        ShoppingBeanServer newServerObj = new ShoppingBeanServer();
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
}

