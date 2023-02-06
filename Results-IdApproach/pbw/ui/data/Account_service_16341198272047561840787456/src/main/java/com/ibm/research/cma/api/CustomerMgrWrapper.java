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
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import com.ibm.websphere.samples.pbw.jpa.Customer;
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
 * The CustomerMgr provides a transactional facade for access to a user DB as well as simple
 * authentication support for those users.
 */
@Transactional
@Dependent
@Controller
@RequestMapping("/ms0/customermgr/")
public class CustomerMgrWrapper {

    /**
     * Create a new user.
     *
     * @param customerID
     *            The new customer ID.
     * @param password
     *            The password for the customer ID.
     * @param firstName
     *            First name.
     * @param lastName
     *            Last name.
     * @param addr1
     *            Address line 1.
     * @param addr2
     *            Address line 2.
     * @param addrCity
     *            City address information.
     * @param addrState
     *            State address information.
     * @param addrZip
     *            Zip code address information.
     * @param phone
     *            User's phone number.
     * @return Customer
     */
    @RequestMapping(method = RequestMethod.POST, value = "createcustomer")
    public @ResponseBody Integer createCustomer(@RequestParam int callerId, @RequestParam String customerID, @RequestParam String password, @RequestParam String firstName, @RequestParam String lastName, @RequestParam String addr1, @RequestParam String addr2, @RequestParam String addrCity, @RequestParam String addrState, @RequestParam String addrZip, @RequestParam String phone) {
        CustomerMgrServer callerObj = (CustomerMgrServer) idObjMap.get(callerId);
        Customer result = callerObj.createCustomer(customerID, password, firstName, lastName, addr1, addr2, addrCity, addrState, addrZip, phone);
        return result.id;
    }

    private static int maxId = 0;

    public static HashMap<Integer, CustomerMgrServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "customermgr")
    public  @ResponseBody int CustomerMgr() {
        CustomerMgrServer newServerObj = new CustomerMgrServer();
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
}

