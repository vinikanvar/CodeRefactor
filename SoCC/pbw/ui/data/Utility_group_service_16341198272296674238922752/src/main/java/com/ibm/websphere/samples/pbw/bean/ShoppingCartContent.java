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

import java.util.Enumeration;
import java.util.Hashtable;
import com.ibm.websphere.samples.pbw.jpa.Inventory;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * A class to hold a shopping cart's contents.
 */
public class ShoppingCartContent implements java.io.Serializable {

    public ShoppingCartContent() {
        String uri = MicroserviceApplication.projectUri + "/ms1/shoppingcartcontent/shoppingcartcontentcallerId=" + this.id + "";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        this.id = resultTemp;
    }

    /**
     * Add the item to the shopping cart.
     */
    public void addItem(Inventory si) {
        return;
    }

    /**
     * Update the item in the shopping cart.
     */
    public void updateItem(Inventory si) {
        return;
    }

    /**
     * Remove the item from the shopping cart.
     */
    public void removeItem(Inventory si) {
        return;
    }

    /**
     * Return the number of items in the cart.
     *
     * @return The number of items in the cart.
     */
    public int size() {
        return (Integer) null;
    }

    /**
     * Return the inventory ID at the index given. The first element is at index 0, the second at
     * index 1, and so on.
     *
     * @return The inventory ID at the index, or NULL if not present.
     */
    public String getInventoryID(int index) {
        return (String) null;
    }

    /**
     * Return the quantity for the inventory ID given.
     *
     * @return The quantity for the inventory ID given..
     */
    public int getQuantity(String inventoryID) {
        return (Integer) null;
    }

    public int id = 0;

    public static ShoppingCartContent getObject(int id) {
        ShoppingCartContent obj = (ShoppingCartContent) new Object();
        obj.id = id;
        return obj;
    }
}

