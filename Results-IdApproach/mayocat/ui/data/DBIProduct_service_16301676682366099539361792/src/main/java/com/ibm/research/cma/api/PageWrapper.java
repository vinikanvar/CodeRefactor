/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.ibm.research.cma.api;

import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.mayocat.model.AddonGroup;
import org.mayocat.model.Association;
import org.mayocat.model.Child;
import org.mayocat.model.Entity;
import org.mayocat.model.HasAddons;
import org.mayocat.model.HasFeaturedImage;
import org.mayocat.model.HasModel;
import org.mayocat.model.Localized;
import org.mayocat.model.annotation.Index;
import org.mayocat.model.annotation.LocalizedField;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import org.mayocat.cms.pages.model.*;
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
 * @version $Id: 1de4b0fbb8ae5066690048c187b4ab3c1dd7ad65 $
 */
@Controller
@RequestMapping("/ms1/page/")
public class PageWrapper {

    // //////////////////////////////////////////////
    // //////////////////////////////////////////////
    @RequestMapping(method = RequestMethod.POST, value = "equals")
    public  @ResponseBody boolean equals(@RequestParam int callerId, @RequestParam int objId) {
        PageServer callerObj = (PageServer) idObjMap.get(callerId);
        boolean result = callerObj.equals(Object.getObject(objId));
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "hashcode")
    public  @ResponseBody int hashCode(@RequestParam int callerId) {
        PageServer callerObj = (PageServer) idObjMap.get(callerId);
        int result = callerObj.hashCode();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "tostring")
    public @ResponseBody String toString(@RequestParam int callerId) {
        PageServer callerObj = (PageServer) idObjMap.get(callerId);
        String result = callerObj.toString();
        return result;
    }

    private static int maxId = 0;

    public static HashMap<Integer, PageServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "page")
    public  @ResponseBody int Page() {
        PageServer newServerObj = new PageServer();
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }

    @RequestMapping(method = RequestMethod.POST, value = "page")
    public  @ResponseBody int Page(@RequestParam int idId) {
        PageServer newServerObj = new PageServer(UUID.getObject(idId));
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
}

