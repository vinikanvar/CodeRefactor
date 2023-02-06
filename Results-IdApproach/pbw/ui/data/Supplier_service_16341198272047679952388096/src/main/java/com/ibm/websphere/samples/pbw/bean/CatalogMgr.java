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
package com.ibm.websphere.samples.pbw.bean;

import java.io.Serializable;
import java.util.Vector;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.ibm.websphere.samples.pbw.jpa.Inventory;
import com.ibm.websphere.samples.pbw.utils.Util;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * The CatalogMgr provides transactional access to the catalog of items the store is willing to sell
 * to customers.
 *
 * @see com.ibm.websphere.samples.pbw.jpa.Inventory
 */
@Dependent
@SuppressWarnings("unchecked")
public class CatalogMgr implements Serializable {

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
    public Vector<Inventory> getItemsByCategory(int category) {
        String uri = MicroserviceApplication.projectUri + "/ms1/catalogmgr/getitemsbycategory?callerId=" + this.id + "&category=category";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        Vector<Inventory> result = (Vector<Inventory>) new Object();
        return result;
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
    public Inventory getItemInventory(String inventoryID) {
        String uri = MicroserviceApplication.projectUri + "/ms1/catalogmgr/getiteminventory?callerId=" + this.id + "&inventoryID=inventoryID";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        Inventory result = Inventory.getObject(resultTemp);
        return result;
    }

    /**
     * Add an inventory item.
     *
     * @param item
     *            The Inventory to add.
     * @return True, if item added.
     */
    public boolean addItem(Inventory item) {
        String uri = MicroserviceApplication.projectUri + "/ms1/catalogmgr/catalogmgr_additem?callerId=" + this.id + "&itemId=item.id";
        boolean resultTemp = new RestTemplate().getForObject(uri, boolean.class);
        boolean result = resultTemp;
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
     * Get the image for the inventory item.
     *
     * @param inventoryID
     *            The id of the inventory item wanted.
     * @return Buffer containing the image.
     */
    public byte[] getItemImageBytes(String inventoryID) {
        return (byte[]) null;
    }

    /**
     * Set the image for the inventory item.
     *
     * @param inventoryID
     *            The id of the inventory item wanted.
     * @param imgbytes
     *            Buffer containing the image.
     */
    public void setItemImageBytes(String inventoryID, byte[] imgbytes) {
        String uri = MicroserviceApplication.projectUri + "/ms1/catalogmgr/setitemimagebytes?callerId=" + this.id + "&inventoryID=inventoryID&imgbytesId=imgbytes.id";
        new RestTemplate().getForObject(uri, void.class);
    }

    /**
     * Set the inventory item's quantity.
     *
     * @param inventoryID
     *            The inventory item's ID.
     * @param quantity
     *            The inventory item's new quantity.
     */
    public void setItemQuantity(String inventoryID, int quantity) {
        String uri = MicroserviceApplication.projectUri + "/ms1/catalogmgr/setitemquantity?callerId=" + this.id + "&inventoryID=inventoryID&quantity=quantity";
        new RestTemplate().getForObject(uri, void.class);
    }

    /**
     * Get a remote Inventory object.
     *
     * @param inventoryID
     *            The id of the inventory item wanted.
     * @return Reference to the remote Inventory object.
     */
    private Inventory getInv(String inventoryID) {
        return (Inventory) null;
    }

    /**
     * Get a remote Inventory object to Update.
     *
     * @param inventoryID
     *            The id of the inventory item wanted.
     * @return Reference to the remote Inventory object.
     */
    private Inventory getInvUpdate(String inventoryID) {
        return (Inventory) null;
    }

    public int id = 0;

    public static CatalogMgr getObject(int id) {
        CatalogMgr obj = (CatalogMgr) new Object();
        obj.id = id;
        return obj;
    }
}

