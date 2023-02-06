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
// 
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
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
 * Simple helper class for JSF validators to handle error messages.
 */
@Controller
@RequestMapping("/ms5/validatorutils/")
public class ValidatorUtilsWrapper {

    @RequestMapping(method = RequestMethod.POST, value = "adderrormessage_facescontext_string")
    protected @ResponseBody ResponseEntity<Void> addErrorMessage(@RequestParam int callerId, @RequestParam int contextId, @RequestParam String message) {
        ValidatorUtilsServer callerObj = (ValidatorUtilsServer) idObjMap.get(callerId);
        callerObj.addErrorMessage(FacesContext.getObject(contextId), message);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "adderrormessage_facescontext_uicomponent")
    protected @ResponseBody ResponseEntity<Void> addErrorMessage(@RequestParam int callerId, @RequestParam int contextId, @RequestParam int componentId) {
        ValidatorUtilsServer callerObj = (ValidatorUtilsServer) idObjMap.get(callerId);
        callerObj.addErrorMessage(FacesContext.getObject(contextId), UIComponent.getObject(componentId));
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private static int maxId = 0;

    public static HashMap<Integer, ValidatorUtilsServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "validatorutils")
    public  @ResponseBody int ValidatorUtils() {
        ValidatorUtilsServer newServerObj = new ValidatorUtilsServer();
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
}

