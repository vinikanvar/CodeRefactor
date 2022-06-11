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
import java.util.ArrayList;
import java.util.Collection;
import javax.enterprise.context.SessionScoped;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import com.ibm.websphere.samples.pbw.jpa.BackOrder;
import com.ibm.websphere.samples.pbw.jpa.Customer;
import com.ibm.websphere.samples.pbw.jpa.Inventory;
import com.ibm.websphere.samples.pbw.jpa.Order;
import com.ibm.websphere.samples.pbw.jpa.OrderItem;
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
 * ShopingCartBean provides a transactional facade for order collection and processing.
 */
@Transactional
@SessionScoped
@Controller
@RequestMapping("/ms0/shoppingcartbean/")
public class ShoppingCartBeanWrapper {

    /**
     * Add an item to the cart.
     *
     * @param new_item
     *            Item to add to the cart.
     */
    @RequestMapping(method = RequestMethod.POST, value = "additem")
    public @ResponseBody ResponseEntity<Void> addItem(@RequestParam int callerId, @RequestParam int new_itemId) {
        ShoppingCartBeanServer callerObj = (ShoppingCartBeanServer) idObjMap.get(callerId);
        callerObj.addItem(Inventory.getObject(new_itemId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Remove zero quantity items.
     */
    @RequestMapping(method = RequestMethod.POST, value = "removezeroquantityitems")
    public @ResponseBody ResponseEntity<Void> removeZeroQuantityItems(@RequestParam int callerId) {
        ShoppingCartBeanServer callerObj = (ShoppingCartBeanServer) idObjMap.get(callerId);
        callerObj.removeZeroQuantityItems();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Get the items in the shopping cart.
     *
     * @return A Collection of ShoppingCartItems.
     */
    @RequestMapping(method = RequestMethod.GET, value = "getitems")
    public @ResponseBody Integer getItems(@RequestParam int callerId) {
        ShoppingCartBeanServer callerObj = (ShoppingCartBeanServer) idObjMap.get(callerId);
        ArrayList<Inventory> result = callerObj.getItems();
        return new Integer(0);
    }

    /**
     * Get the contents of the shopping cart.
     *
     * @return The contents of the shopping cart. / public ShoppingCartContents getCartContents() {
     *         ShoppingCartContents cartContents = new ShoppingCartContents(); // Fill it with data.
     *         for (int i = 0; i < items.size(); i++) { cartContents.addItem((ShoppingCartItem)
     *         items.get(i)); } return cartContents; }
     */
    /**
     * Create a shopping cart.
     *
     * @param cartContents
     *            Contents to populate cart with. / public void setCartContents(ShoppingCartContents
     *            cartContents) { items = new ArrayList<ShoppingCartItem>(); int qty; String
     *            inventoryID; ShoppingCartItem si; Inventory inv; for (int i = 0; i <
     *            cartContents.size(); i++) { inventoryID = cartContents.getInventoryID(i); qty =
     *            cartContents.getQuantity(inventoryID); inv = em.find(Inventory.class,
     *            inventoryID); // clone so we can use Qty as qty to purchase, not inventory in
     *            stock si = new ShoppingCartItem(inv); si.setQuantity(qty); addItem(si); } }
     */
    /**
     * Get the cost of all items in the shopping cart.
     *
     * @return The total cost of all items in the shopping cart.
     */
    @RequestMapping(method = RequestMethod.GET, value = "getsubtotalcost")
    public  @ResponseBody float getSubtotalCost(@RequestParam int callerId) {
        ShoppingCartBeanServer callerObj = (ShoppingCartBeanServer) idObjMap.get(callerId);
        float result = callerObj.getSubtotalCost();
        return result;
    }

    /**
     * Create an order with contents of a shopping cart.
     *
     * @param customerID
     *            customer's ID
     * @param billName
     *            billing name
     * @param billAddr1
     *            billing address line 1
     * @param billAddr2
     *            billing address line 2
     * @param billCity
     *            billing address city
     * @param billState
     *            billing address state
     * @param billZip
     *            billing address zip code
     * @param billPhone
     *            billing phone
     * @param shipName
     *            shippng name
     * @param shipAddr1
     *            shippng address line 1
     * @param shipAddr2
     *            shippng address line 2
     * @param shipCity
     *            shippng address city
     * @param shipState
     *            shippng address state
     * @param shipZip
     *            shippng address zip code
     * @param shipPhone
     *            shippng phone
     * @param creditCard
     *            credit card
     * @param ccNum
     *            credit card number
     * @param ccExpireMonth
     *            credit card expiration month
     * @param ccExpireYear
     *            credit card expiration year
     * @param cardHolder
     *            credit card holder name
     * @param shippingMethod
     *            int of shipping method used
     * @param items
     *            vector of StoreItems ordered
     * @return OrderInfo
     */
    @RequestMapping(method = RequestMethod.POST, value = "createorder")
    public @ResponseBody Integer createOrder(@RequestParam int callerId, @RequestParam String customerID, @RequestParam String billName, @RequestParam String billAddr1, @RequestParam String billAddr2, @RequestParam String billCity, @RequestParam String billState, @RequestParam String billZip, @RequestParam String billPhone, @RequestParam String shipName, @RequestParam String shipAddr1, @RequestParam String shipAddr2, @RequestParam String shipCity, @RequestParam String shipState, @RequestParam String shipZip, @RequestParam String shipPhone, @RequestParam String creditCard, @RequestParam String ccNum, @RequestParam String ccExpireMonth, @RequestParam String ccExpireYear, @RequestParam String cardHolder, @RequestParam int shippingMethod, @RequestParam int itemsId) {
        ShoppingCartBeanServer callerObj = (ShoppingCartBeanServer) idObjMap.get(callerId);
        Order result = callerObj.createOrder(customerID, billName, billAddr1, billAddr2, billCity, billState, billZip, billPhone, shipName, shipAddr1, shipAddr2, shipCity, shipState, shipZip, shipPhone, creditCard, ccNum, ccExpireMonth, ccExpireYear, cardHolder, shippingMethod, (Collection<Inventory>) new Object());
        return result.id;
    }

    private static int maxId = 0;

    public static HashMap<Integer, ShoppingCartBeanServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "shoppingcartbean")
    public  @ResponseBody int ShoppingCartBean() {
        ShoppingCartBeanServer newServerObj = new ShoppingCartBeanServer();
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
}

