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
import java.io.Serializable;
import java.util.Collection;
import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import com.ibm.websphere.samples.pbw.jpa.BackOrder;
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
 * The BackOrderMgr provides a transactional and secured facade to access back order information.
 * This bean no longer requires an interface as there is one and only one implementation.
 */
@Dependent
@RolesAllowed("SampAdmin")
@Controller
@RequestMapping("/ms2/backordermgr/")
public class BackOrderMgrWrapper {

    /**
     * Method findBackOrderItems.
     *
     * @return Collection
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(method = RequestMethod.POST, value = "findbackorders")
    public @ResponseBody Integer findBackOrders(@RequestParam int callerId) {
        BackOrderMgrServer callerObj = (BackOrderMgrServer) idObjMap.get(callerId);
        Collection<BackOrder> result = callerObj.findBackOrders();
        return new Integer(0);
    }

    /**
     * Method deleteBackOrder.
     *
     * @param backOrderID
     */
    @RequestMapping(method = RequestMethod.POST, value = "deletebackorder")
    public @ResponseBody ResponseEntity<Void> deleteBackOrder(@RequestParam int callerId, @RequestParam String backOrderID) {
        BackOrderMgrServer callerObj = (BackOrderMgrServer) idObjMap.get(callerId);
        callerObj.deleteBackOrder(backOrderID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Method receiveConfirmation.
     *
     * @param backOrderID
     *            / public int receiveConfirmation(String backOrderID) { int rc = 0; BackOrder
     *            backOrder; Util.debug(
     *            "BackOrderMgr.receiveConfirmation() - Finding Back Order for backOrderID=" +
     *            backOrderID); backOrder = em.find(BackOrder.class, backOrderID);
     *            backOrder.setStatus(Util.STATUS_RECEIVEDSTOCK); Util.debug(
     *            "BackOrderMgr.receiveConfirmation() - Updating status(" +
     *            Util.STATUS_RECEIVEDSTOCK + ") of backOrderID(" + backOrderID + ")"); return (rc);
     *            }
     */
    /**
     * Method orderStock.
     *
     * @param backOrderID
     * @param quantity
     *            / public void orderStock(String backOrderID, int quantity) {
     *            this.setBackOrderStatus(backOrderID, Util.STATUS_ORDEREDSTOCK);
     *            this.setBackOrderQuantity(backOrderID, quantity);
     *            this.setBackOrderOrderDate(backOrderID); }
     */
    /**
     * Method updateStock.
     *
     * @param backOrderID
     * @param quantity
     */
    @RequestMapping(method = RequestMethod.POST, value = "updatestock")
    public @ResponseBody ResponseEntity<Void> updateStock(@RequestParam int callerId, @RequestParam String backOrderID, @RequestParam int quantity) {
        BackOrderMgrServer callerObj = (BackOrderMgrServer) idObjMap.get(callerId);
        callerObj.updateStock(backOrderID, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * @param backOrderID
     *            / public void abortorderStock(String backOrderID) { Util.debug(
     *            "backOrderStockBean.abortorderStock() - Aborting orderStock transation for backorderID: "
     *            + backOrderID); // Reset the back order status since the order failed.
     *            this.setBackOrderStatus(backOrderID, Util.STATUS_ORDERSTOCK); }
     */
    /**
     * Method getBackOrderID.
     *
     * @param backOrderID
     * @return String / public String getBackOrderID(String backOrderID) { String retbackOrderID =
     *         ""; Util.debug( "BackOrderMgr.getBackOrderID() - Entered"); // BackOrderLocal
     *         backOrder = getBackOrderLocalHome().findByPrimaryKey(new BackOrderKey(backOrderID));
     *         BackOrder backOrder = em.find(BackOrder.class, backOrderID); retbackOrderID =
     *         backOrder.getBackOrderID(); return retbackOrderID; }
     */
    /**
     * Method getBackOrderInventoryID.
     *
     * @param backOrderID
     * @return String
     */
    @RequestMapping(method = RequestMethod.GET, value = "getbackorderinventoryid")
    public @ResponseBody String getBackOrderInventoryID(@RequestParam int callerId, @RequestParam String backOrderID) {
        BackOrderMgrServer callerObj = (BackOrderMgrServer) idObjMap.get(callerId);
        String result = callerObj.getBackOrderInventoryID(backOrderID);
        return result;
    }

    /**
     * Method getBackOrderQuantity.
     *
     * @param backOrderID
     * @return int
     */
    @RequestMapping(method = RequestMethod.GET, value = "getbackorderquantity")
    public  @ResponseBody int getBackOrderQuantity(@RequestParam int callerId, @RequestParam String backOrderID) {
        BackOrderMgrServer callerObj = (BackOrderMgrServer) idObjMap.get(callerId);
        int result = callerObj.getBackOrderQuantity(backOrderID);
        return result;
    }

    /**
     * Method setBackOrderQuantity.
     *
     * @param backOrderID
     * @param quantity
     */
    @RequestMapping(method = RequestMethod.POST, value = "setbackorderquantity")
    public @ResponseBody ResponseEntity<Void> setBackOrderQuantity(@RequestParam int callerId, @RequestParam String backOrderID, @RequestParam int quantity) {
        BackOrderMgrServer callerObj = (BackOrderMgrServer) idObjMap.get(callerId);
        callerObj.setBackOrderQuantity(backOrderID, quantity);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private static int maxId = 0;

    public static HashMap<Integer, BackOrderMgrServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "backordermgr")
    public  @ResponseBody int BackOrderMgr() {
        BackOrderMgrServer newServerObj = new BackOrderMgrServer();
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
}

