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
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.ibm.websphere.samples.pbw.jpa.Supplier;
import com.ibm.websphere.samples.pbw.utils.Util;
import com.ibm.websphere.samples.pbw.bean.*;
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
 * Bean implementation class for Enterprise Bean: Suppliers
 */
@Dependent
@Controller
@RequestMapping("/ms3/suppliersbean/")
public class SuppliersBeanWrapper {

    /**
     * @param supplierID
     * @param name
     * @param street
     * @param city
     * @param state
     * @param zip
     * @param phone
     * @param url
     */
    @RequestMapping(method = RequestMethod.POST, value = "createsupplier")
    public @ResponseBody ResponseEntity<Void> createSupplier(@RequestParam int callerId, @RequestParam String supplierID, @RequestParam String name, @RequestParam String street, @RequestParam String city, @RequestParam String state, @RequestParam String zip, @RequestParam String phone, @RequestParam String url) {
        SuppliersBeanServer callerObj = (SuppliersBeanServer) idObjMap.get(callerId);
        callerObj.createSupplier(supplierID, name, street, city, state, zip, phone, url);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * @return Supplier
     */
    @RequestMapping(method = RequestMethod.GET, value = "getsupplier")
    public @ResponseBody Integer getSupplier(@RequestParam int callerId) {
        SuppliersBeanServer callerObj = (SuppliersBeanServer) idObjMap.get(callerId);
        Supplier result = callerObj.getSupplier();
        return result.id;
    }

    /**
     * @param supplierID
     * @param name
     * @param street
     * @param city
     * @param state
     * @param zip
     * @param phone
     * @param url
     * @return supplierInfo
     */
    @RequestMapping(method = RequestMethod.POST, value = "updatesupplier")
    public @ResponseBody Integer updateSupplier(@RequestParam int callerId, @RequestParam String supplierID, @RequestParam String name, @RequestParam String street, @RequestParam String city, @RequestParam String state, @RequestParam String zip, @RequestParam String phone, @RequestParam String url) {
        SuppliersBeanServer callerObj = (SuppliersBeanServer) idObjMap.get(callerId);
        Supplier result = callerObj.updateSupplier(supplierID, name, street, city, state, zip, phone, url);
        return result.id;
    }

    private static int maxId = 0;

    public static HashMap<Integer, SuppliersBeanServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "suppliersbean")
    public  @ResponseBody int SuppliersBean() {
        SuppliersBeanServer newServerObj = new SuppliersBeanServer();
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
}

