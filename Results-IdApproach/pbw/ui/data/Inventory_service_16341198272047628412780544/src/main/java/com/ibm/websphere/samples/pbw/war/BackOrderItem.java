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
// (C) COPYRIGHT International Business Machines Corp., 2003,2011
// All Rights Reserved * Licensed Materials - Property of IBM
// 
package com.ibm.websphere.samples.pbw.war;

import com.ibm.websphere.samples.pbw.jpa.BackOrder;
import com.ibm.websphere.samples.pbw.jpa.Inventory;
import com.ibm.websphere.samples.pbw.utils.Util;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * A class to hold a back order item's data.
 */
public class BackOrderItem implements java.io.Serializable {

    /**
     * @see java.lang.Object#Object()
     */
    /**
     * Default constructor.
     */
    public BackOrderItem() {
        String uri = MicroserviceApplication.projectUri + "/ms1/backorderitem/backorderitem_callerId=" + this.id + "";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        this.id = resultTemp;
    }

    /**
     * Method BackOrderItem.
     *
     * @param backOrderID
     * @param inventoryID
     * @param name
     * @param quantity
     * @param status
     */
    public BackOrderItem(String backOrderID, Inventory inventoryID, String name, int quantity, String status) {
        String uri = MicroserviceApplication.projectUri + "/ms1/backorderitem/backorderitem_string_inventory_string_int_string?callerId=" + this.id + "&backOrderID=backOrderID&inventoryIDId=inventoryID.id&name=name&quantity=quantity&status=status";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        this.id = resultTemp;
    }

    /**
     * Method BackOrderItem.
     *
     * @param backOrder
     */
    public BackOrderItem(BackOrder backOrder) {
        String uri = MicroserviceApplication.projectUri + "/ms1/backorderitem/backorderitem?callerId=" + this.id + "&backOrderId=backOrder.id";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        this.id = resultTemp;
    }

    /**
     * Method getBackOrderID.
     *
     * @return String
     */
    public String getBackOrderID() {
        String uri = MicroserviceApplication.projectUri + "/ms1/backorderitem/getbackorderidcallerId=" + this.id + "";
        String resultTemp = new RestTemplate().getForObject(uri, String.class);
        String result = resultTemp;
        return result;
    }

    /**
     * Method setBackOrderID.
     *
     * @param backOrderID
     */
    public void setBackOrderID(String backOrderID) {
        return;
    }

    /**
     * Method getSupplierOrderID.
     *
     * @return String
     */
    public String getSupplierOrderID() {
        String uri = MicroserviceApplication.projectUri + "/ms1/backorderitem/getsupplierorderidcallerId=" + this.id + "";
        String resultTemp = new RestTemplate().getForObject(uri, String.class);
        String result = resultTemp;
        return result;
    }

    /**
     * Method setSupplierOrderID.
     *
     * @param supplierOrderID
     */
    public void setSupplierOrderID(String supplierOrderID) {
        return;
    }

    /**
     * Method setQuantity.
     *
     * @param quantity
     */
    public void setQuantity(int quantity) {
        return;
    }

    /**
     * Method getInventoryID.
     *
     * @return String
     */
    public Inventory getInventory() {
        String uri = MicroserviceApplication.projectUri + "/ms1/backorderitem/getinventorycallerId=" + this.id + "";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        Inventory result = Inventory.getObject(resultTemp);
        return result;
    }

    /**
     * Method getName.
     *
     * @return String
     */
    public String getName() {
        return (String) null;
    }

    /**
     * Method setName.
     *
     * @param name
     */
    public void setName(String name) {
        String uri = MicroserviceApplication.projectUri + "/ms1/backorderitem/setname?callerId=" + this.id + "&name=name";
        new RestTemplate().getForObject(uri, void.class);
    }

    /**
     * Method getQuantity.
     *
     * @return int
     */
    public int getQuantity() {
        return (Integer) null;
    }

    /**
     * Method getInventoryQuantity.
     *
     * @return int
     */
    public int getInventoryQuantity() {
        return (Integer) null;
    }

    /**
     * Method setInventoryQuantity.
     *
     * @param quantity
     */
    public void setInventoryQuantity(int quantity) {
        String uri = MicroserviceApplication.projectUri + "/ms1/backorderitem/setinventoryquantity?callerId=" + this.id + "&quantity=quantity";
        new RestTemplate().getForObject(uri, void.class);
    }

    /**
     * Method getStatus.
     *
     * @return String
     */
    public String getStatus() {
        String uri = MicroserviceApplication.projectUri + "/ms1/backorderitem/getstatuscallerId=" + this.id + "";
        String resultTemp = new RestTemplate().getForObject(uri, String.class);
        String result = resultTemp;
        return result;
    }

    /**
     * Method getLowDate.
     *
     * @return long
     */
    public long getLowDate() {
        String uri = MicroserviceApplication.projectUri + "/ms1/backorderitem/getlowdatecallerId=" + this.id + "";
        long resultTemp = new RestTemplate().getForObject(uri, long.class);
        long result = resultTemp;
        return result;
    }

    /**
     * Method getOrderDate.
     *
     * @return long
     */
    public long getOrderDate() {
        String uri = MicroserviceApplication.projectUri + "/ms1/backorderitem/getorderdatecallerId=" + this.id + "";
        long resultTemp = new RestTemplate().getForObject(uri, long.class);
        long result = resultTemp;
        return result;
    }

    public int id = 0;

    public static BackOrderItem getObject(int id) {
        BackOrderItem obj = (BackOrderItem) new Object();
        obj.id = id;
        return obj;
    }
}

