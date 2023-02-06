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
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import com.ibm.websphere.samples.pbw.utils.Util;
import com.ibm.websphere.samples.pbw.jpa.*;
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
 * Inventory is the bean mapping for the INVENTORY table. It provides information about products the
 * store has for sale.
 *
 * @see Inventory
 */
@Entity(name = "Inventory")
@Table(name = "INVENTORY", schema = "APP")
@NamedQueries({ @NamedQuery(name = "getItemsByCategory", query = "select i from Inventory i where i.category = :category ORDER BY i.inventoryId"), @NamedQuery(name = "getItemsLikeName", query = "select i from Inventory i where i.name like :name"), @NamedQuery(name = "removeAllInventory", query = "delete from Inventory") })
@Controller
@RequestMapping("/ms1/inventory/")
public class InventoryWrapper {

    @RequestMapping(method = RequestMethod.GET, value = "getname")
    public @ResponseBody String getName(@RequestParam int callerId) {
        InventoryServer callerObj = (InventoryServer) idObjMap.get(callerId);
        String result = callerObj.getName();
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "getprice")
    public  @ResponseBody float getPrice(@RequestParam int callerId) {
        InventoryServer callerObj = (InventoryServer) idObjMap.get(callerId);
        float result = callerObj.getPrice();
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "getquantity")
    public  @ResponseBody int getQuantity(@RequestParam int callerId) {
        InventoryServer callerObj = (InventoryServer) idObjMap.get(callerId);
        int result = callerObj.getQuantity();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "setquantity")
    public @ResponseBody ResponseEntity<Void> setQuantity(@RequestParam int callerId, @RequestParam int quantity) {
        InventoryServer callerObj = (InventoryServer) idObjMap.get(callerId);
        callerObj.setQuantity(quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getminthreshold")
    public  @ResponseBody int getMinThreshold(@RequestParam int callerId) {
        InventoryServer callerObj = (InventoryServer) idObjMap.get(callerId);
        int result = callerObj.getMinThreshold();
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "getinventoryid")
    public @ResponseBody String getInventoryId(@RequestParam int callerId) {
        InventoryServer callerObj = (InventoryServer) idObjMap.get(callerId);
        String result = callerObj.getInventoryId();
        return result;
    }

    /**
     * Same as getInventoryId. Added for compatability with ShoppingCartItem when used by the Client
     * XJB sample
     *
     * @return String ID of the inventory item
     */
    @RequestMapping(method = RequestMethod.GET, value = "getid")
    public @ResponseBody String getID(@RequestParam int callerId) {
        InventoryServer callerObj = (InventoryServer) idObjMap.get(callerId);
        String result = callerObj.getID();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "setbackorder")
    public @ResponseBody ResponseEntity<Void> setBackOrder(@RequestParam int callerId, @RequestParam int backOrderId) {
        InventoryServer callerObj = (InventoryServer) idObjMap.get(callerId);
        callerObj.setBackOrder(BackOrder.getObject(backOrderId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private static int maxId = 0;

    public static HashMap<Integer, InventoryServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "inventory_")
    public  @ResponseBody int Inventory() {
        InventoryServer newServerObj = new InventoryServer();
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }

    @RequestMapping(method = RequestMethod.POST, value = "inventory_string_string_string_string_string_string_float_float_int_int_string_boolean")
    public  @ResponseBody int Inventory(@RequestParam String key, @RequestParam String name, @RequestParam String heading, @RequestParam String desc, @RequestParam String pkginfo, @RequestParam String image, @RequestParam float price, @RequestParam float cost, @RequestParam int quantity, @RequestParam int category, @RequestParam String notes, @RequestParam boolean isPublic) {
        InventoryServer newServerObj = new InventoryServer(key, name, heading, desc, pkginfo, image, price, cost, quantity, category, notes, isPublic);
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }

    @RequestMapping(method = RequestMethod.POST, value = "inventory")
    public  @ResponseBody int Inventory(@RequestParam int itemId) {
        InventoryServer newServerObj = new InventoryServer(Inventory.getObject(itemId));
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
}

