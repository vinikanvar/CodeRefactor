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
import java.util.Vector;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.ibm.websphere.samples.pbw.jpa.Inventory;
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
 * The CatalogMgr provides transactional access to the catalog of items the store is willing to sell
 * to customers.
 *
 * @see com.ibm.websphere.samples.pbw.jpa.Inventory
 */
@Dependent
@SuppressWarnings("unchecked")
@Controller
@RequestMapping("/ms1/catalogmgr/")
public class CatalogMgrWrapper {

    /**
     * Get all inventory items.
     *
     * @return Vector of Inventorys. / public Vector<Inventory> getItems() { Vector<Inventory> items
     *         = new Vector<Inventory>(); int count = Util.getCategoryStrings().length; for (int i =
     *         0; i < count; i++) { items.addAll(getItemsByCategory(i)); } return items; }
     */
    /**
     * Get all inventory items for the given category.
     *
     * @param category
     *            of items desired.
     * @return Vector of Inventory.
     */
    @RequestMapping(method = RequestMethod.GET, value = "getitemsbycategory")
    public @ResponseBody Integer getItemsByCategory(@RequestParam int callerId, @RequestParam int category) {
        CatalogMgrServer callerObj = (CatalogMgrServer) idObjMap.get(callerId);
        Vector<Inventory> result = callerObj.getItemsByCategory(category);
        return new Integer(0);
    }

    /**
     * Get inventory items that contain a given String within their names.
     *
     * @param name
     *            String to search names for.
     * @return A Vector of Inventorys that match. / public Vector<Inventory> getItemsLikeName(String
     *         name) { Query q = em.createNamedQuery("getItemsLikeName"); q.setParameter("name", '%'
     *         + name + '%'); //The return type must be Vector because the PBW client ActiveX sample
     *         requires Vector return new Vector<Inventory>(q.getResultList()); }
     */
    /**
     * Get the StoreItem for the given ID.
     *
     * @param inventoryID
     *            - ID of the Inventory item desired.
     * @return StoreItem / public StoreItem getItem(String inventoryID) { return new
     *         StoreItem(getItemInventory(inventoryID)); }
     */
    /**
     * Get the Inventory item for the given ID.
     *
     * @param inventoryID
     *            - ID of the Inventory item desired.
     * @return Inventory
     */
    @RequestMapping(method = RequestMethod.GET, value = "getiteminventory")
    public @ResponseBody Integer getItemInventory(@RequestParam int callerId, @RequestParam String inventoryID) {
        CatalogMgrServer callerObj = (CatalogMgrServer) idObjMap.get(callerId);
        Inventory result = callerObj.getItemInventory(inventoryID);
        return result.id;
    }

    /**
     * Add an inventory item.
     *
     * @param item
     *            The Inventory to add.
     * @return True, if item added.
     */
    @RequestMapping(method = RequestMethod.POST, value = "catalogmgr_additem")
    public  @ResponseBody boolean addItem(@RequestParam int callerId, @RequestParam int itemId) {
        CatalogMgrServer callerObj = (CatalogMgrServer) idObjMap.get(callerId);
        boolean result = callerObj.addItem(Inventory.getObject(itemId));
        return result;
    }

    /**
     * Add an StoreItem item (same as Inventory item).
     *
     * @param item
     *            The StoreItem to add.
     * @return True, if item added. / public boolean addItem(StoreItem item) { return addItem(new
     *         Inventory(item)); }
     */
    /**
     * Delete an inventory item.
     *
     * @param inventoryID
     *            The ID of the inventory item to delete.
     * @return True, if item deleted. / public boolean deleteItem(String inventoryID) { boolean
     *         retval = true; em.remove(em.find(Inventory.class, inventoryID)); return retval; }
     */
    /**
     * Set the image for the inventory item.
     *
     * @param inventoryID
     *            The id of the inventory item wanted.
     * @param imgbytes
     *            Buffer containing the image.
     */
    @RequestMapping(method = RequestMethod.POST, value = "setitemimagebytes")
    public @ResponseBody ResponseEntity<Void> setItemImageBytes(@RequestParam int callerId, @RequestParam String inventoryID, @RequestParam int imgbytesId) {
        CatalogMgrServer callerObj = (CatalogMgrServer) idObjMap.get(callerId);
        callerObj.setItemImageBytes(inventoryID, (byte[]) new Object());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Set the inventory item's quantity.
     *
     * @param inventoryID
     *            The inventory item's ID.
     * @param quantity
     *            The inventory item's new quantity.
     */
    @RequestMapping(method = RequestMethod.POST, value = "setitemquantity")
    public @ResponseBody ResponseEntity<Void> setItemQuantity(@RequestParam int callerId, @RequestParam String inventoryID, @RequestParam int quantity) {
        CatalogMgrServer callerObj = (CatalogMgrServer) idObjMap.get(callerId);
        callerObj.setItemQuantity(inventoryID, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private static int maxId = 0;

    public static HashMap<Integer, CatalogMgrServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "catalogmgr")
    public  @ResponseBody int CatalogMgr() {
        CatalogMgrServer newServerObj = new CatalogMgrServer();
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
}

