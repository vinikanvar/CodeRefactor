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
package com.ibm.websphere.samples.pbw.jpa;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import com.ibm.websphere.samples.pbw.utils.Util;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * Inventory is the bean mapping for the INVENTORY table. It provides information about products the
 * store has for sale.
 *
 * @see Inventory
 */
@Entity(name = "Inventory")
@Table(name = "INVENTORY", schema = "APP")
@NamedQueries({ @NamedQuery(name = "getItemsByCategory", query = "select i from Inventory i where i.category = :category ORDER BY i.inventoryId"), @NamedQuery(name = "getItemsLikeName", query = "select i from Inventory i where i.name like :name"), @NamedQuery(name = "removeAllInventory", query = "delete from Inventory") })
public class Inventory implements Cloneable, java.io.Serializable {

    public Inventory() {
        String uri = MicroserviceApplication.projectUri + "/ms1/inventory/inventory_callerId=" + this.id + "";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        this.id = resultTemp;
    }

    /**
     * Create a new Inventory.
     *
     * @param key
     *            Inventory Key
     * @param name
     *            Name of inventory item.
     * @param heading
     *            Description heading of inventory item.
     * @param desc
     *            Description of inventory item.
     * @param pkginfo
     *            Package info of inventory item.
     * @param image
     *            Image of inventory item.
     * @param price
     *            Price of inventory item.
     * @param cost
     *            Cost of inventory item.
     * @param quantity
     *            Quantity of inventory items in stock.
     * @param category
     *            Category of inventory item.
     * @param notes
     *            Notes of inventory item.
     * @param isPublic
     *            Access permission of inventory item.
     */
    public Inventory(String key, String name, String heading, String desc, String pkginfo, String image, float price, float cost, int quantity, int category, String notes, boolean isPublic) {
        String uri = MicroserviceApplication.projectUri + "/ms1/inventory/inventory_string_string_string_string_string_string_float_float_int_int_string_boolean?callerId=" + this.id + "&key=key&name=name&heading=heading&desc=desc&pkginfo=pkginfo&image=image&price=price&cost=cost&quantity=quantity&category=category&notes=notes&isPublic=isPublic";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        this.id = resultTemp;
    }

    /**
     * Create a new Inventory.
     *
     * @param item
     *            Inventory to use to make a new inventory item.
     */
    public Inventory(Inventory item) {
        String uri = MicroserviceApplication.projectUri + "/ms1/inventory/inventory?callerId=" + this.id + "&itemId=item.id";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        this.id = resultTemp;
    }

    /**
     * Increase the quantity of this inventory item.
     *
     * @param quantity
     *            The number to increase the inventory by.
     */
    public void increaseInventory(int quantity) {
        return;
    }

    public int getCategory() {
        return (Integer) null;
    }

    public void setCategory(int category) {
        return;
    }

    public float getCost() {
        return (Float) null;
    }

    public void setCost(float cost) {
        return;
    }

    public String getDescription() {
        return (String) null;
    }

    public void setDescription(String description) {
        return;
    }

    public String getHeading() {
        return (String) null;
    }

    public void setHeading(String heading) {
        return;
    }

    public String getImage() {
        return (String) null;
    }

    public void setImage(String image) {
        return;
    }

    public String getName() {
        String uri = MicroserviceApplication.projectUri + "/ms1/inventory/getnamecallerId=" + this.id + "";
        String resultTemp = new RestTemplate().getForObject(uri, String.class);
        String result = resultTemp;
        return result;
    }

    public void setName(String name) {
        return;
    }

    public String getNotes() {
        return (String) null;
    }

    public void setNotes(String notes) {
        return;
    }

    public String getPkginfo() {
        return (String) null;
    }

    public void setPkginfo(String pkginfo) {
        return;
    }

    public float getPrice() {
        String uri = MicroserviceApplication.projectUri + "/ms1/inventory/getpricecallerId=" + this.id + "";
        float resultTemp = new RestTemplate().getForObject(uri, float.class);
        float result = resultTemp;
        return result;
    }

    public void setPrice(float price) {
        return;
    }

    public int getQuantity() {
        String uri = MicroserviceApplication.projectUri + "/ms1/inventory/getquantitycallerId=" + this.id + "";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        int result = resultTemp;
        return result;
    }

    public void setQuantity(int quantity) {
        String uri = MicroserviceApplication.projectUri + "/ms1/inventory/setquantity?callerId=" + this.id + "&quantity=quantity";
        new RestTemplate().getForObject(uri, void.class);
    }

    public int getMaxThreshold() {
        return (Integer) null;
    }

    public void setMaxThreshold(int maxThreshold) {
        return;
    }

    public int getMinThreshold() {
        String uri = MicroserviceApplication.projectUri + "/ms1/inventory/getminthresholdcallerId=" + this.id + "";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        int result = resultTemp;
        return result;
    }

    public void setMinThreshold(int minThreshold) {
        return;
    }

    public String getInventoryId() {
        String uri = MicroserviceApplication.projectUri + "/ms1/inventory/getinventoryidcallerId=" + this.id + "";
        String resultTemp = new RestTemplate().getForObject(uri, String.class);
        String result = resultTemp;
        return result;
    }

    public void setInventoryId(String id) {
        return;
    }

    /**
     * Same as getInventoryId. Added for compatability with ShoppingCartItem when used by the Client
     * XJB sample
     *
     * @return String ID of the inventory item
     */
    public String getID() {
        String uri = MicroserviceApplication.projectUri + "/ms1/inventory/getidcallerId=" + this.id + "";
        String resultTemp = new RestTemplate().getForObject(uri, String.class);
        String result = resultTemp;
        return result;
    }

    /**
     * Same as setInventoryId. Added for compatability with ShoppingCartItem when used by the Client
     * XJB sample
     */
    public void setID(String id) {
        return;
    }

    public boolean isPublic() {
        return (Boolean) null;
    }

    public void setIsPublic(boolean isPublic) {
        return;
    }

    /**
     * Set the inventory item's public availability.
     */
    public void setPrivacy(boolean isPublic) {
        return;
    }

    public byte[] getImgbytes() {
        return (byte[]) null;
    }

    public void setImgbytes(byte[] imgbytes) {
        return;
    }

    public BackOrder getBackOrder() {
        return (BackOrder) null;
    }

    public void setBackOrder(BackOrder backOrder) {
        String uri = MicroserviceApplication.projectUri + "/ms1/inventory/setbackorder?callerId=" + this.id + "&backOrderId=backOrder.id";
        new RestTemplate().getForObject(uri, void.class);
    }

    @Override
    public String toString() {
        return (String) null;
    }

    public int id = 0;

    public static Inventory getObject(int id) {
        Inventory obj = (Inventory) new Object();
        obj.id = id;
        return obj;
    }
}

