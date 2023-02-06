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
// (C) COPYRIGHT International Business Machines Corp., 2003,2011
// All Rights Reserved * Licensed Materials - Property of IBM
import com.ibm.websphere.samples.pbw.jpa.BackOrder;
import com.ibm.websphere.samples.pbw.jpa.Inventory;
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
 * A class to hold a back order item's data.
 */
@Controller
@RequestMapping("/ms1/backorderitem/")
public class BackOrderItemWrapper {

    /**
     * @see java.lang.Object#Object()
     */
    /**
     * Method getBackOrderID.
     *
     * @return String
     */
    @RequestMapping(method = RequestMethod.GET, value = "getbackorderid")
    public @ResponseBody String getBackOrderID(@RequestParam int callerId) {
        BackOrderItemServer callerObj = (BackOrderItemServer) idObjMap.get(callerId);
        String result = callerObj.getBackOrderID();
        return result;
    }

    /**
     * Method getSupplierOrderID.
     *
     * @return String
     */
    @RequestMapping(method = RequestMethod.GET, value = "getsupplierorderid")
    public @ResponseBody String getSupplierOrderID(@RequestParam int callerId) {
        BackOrderItemServer callerObj = (BackOrderItemServer) idObjMap.get(callerId);
        String result = callerObj.getSupplierOrderID();
        return result;
    }

    /**
     * Method getInventoryID.
     *
     * @return String
     */
    @RequestMapping(method = RequestMethod.GET, value = "getinventory")
    public @ResponseBody Integer getInventory(@RequestParam int callerId) {
        BackOrderItemServer callerObj = (BackOrderItemServer) idObjMap.get(callerId);
        Inventory result = callerObj.getInventory();
        return result.id;
    }

    /**
     * Method setName.
     *
     * @param name
     */
    @RequestMapping(method = RequestMethod.POST, value = "setname")
    public @ResponseBody ResponseEntity<Void> setName(@RequestParam int callerId, @RequestParam String name) {
        BackOrderItemServer callerObj = (BackOrderItemServer) idObjMap.get(callerId);
        callerObj.setName(name);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Method setInventoryQuantity.
     *
     * @param quantity
     */
    @RequestMapping(method = RequestMethod.POST, value = "setinventoryquantity")
    public @ResponseBody ResponseEntity<Void> setInventoryQuantity(@RequestParam int callerId, @RequestParam int quantity) {
        BackOrderItemServer callerObj = (BackOrderItemServer) idObjMap.get(callerId);
        callerObj.setInventoryQuantity(quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Method getStatus.
     *
     * @return String
     */
    @RequestMapping(method = RequestMethod.GET, value = "getstatus")
    public @ResponseBody String getStatus(@RequestParam int callerId) {
        BackOrderItemServer callerObj = (BackOrderItemServer) idObjMap.get(callerId);
        String result = callerObj.getStatus();
        return result;
    }

    /**
     * Method getLowDate.
     *
     * @return long
     */
    @RequestMapping(method = RequestMethod.GET, value = "getlowdate")
    public  @ResponseBody long getLowDate(@RequestParam int callerId) {
        BackOrderItemServer callerObj = (BackOrderItemServer) idObjMap.get(callerId);
        long result = callerObj.getLowDate();
        return result;
    }

    /**
     * Method getOrderDate.
     *
     * @return long
     */
    @RequestMapping(method = RequestMethod.GET, value = "getorderdate")
    public  @ResponseBody long getOrderDate(@RequestParam int callerId) {
        BackOrderItemServer callerObj = (BackOrderItemServer) idObjMap.get(callerId);
        long result = callerObj.getOrderDate();
        return result;
    }

    private static int maxId = 0;

    public static HashMap<Integer, BackOrderItemServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "backorderitem_")
    public  @ResponseBody int BackOrderItem() {
        BackOrderItemServer newServerObj = new BackOrderItemServer();
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }

    @RequestMapping(method = RequestMethod.POST, value = "backorderitem_string_inventory_string_int_string")
    public  @ResponseBody int BackOrderItem(@RequestParam String backOrderID, @RequestParam int inventoryIDId, @RequestParam String name, @RequestParam int quantity, @RequestParam String status) {
        BackOrderItemServer newServerObj = new BackOrderItemServer(backOrderID, Inventory.getObject(inventoryIDId), name, quantity, status);
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }

    @RequestMapping(method = RequestMethod.POST, value = "backorderitem")
    public  @ResponseBody int BackOrderItem(@RequestParam int backOrderId) {
        BackOrderItemServer newServerObj = new BackOrderItemServer(BackOrder.getObject(backOrderId));
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
}

