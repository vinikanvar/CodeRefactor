/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.ibm.research.cma.api;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import org.mayocat.model.Child;
import org.mayocat.model.Entity;
import org.mayocat.model.Localized;
import org.mayocat.model.annotation.LocalizedField;
import com.google.common.base.Objects;
import org.mayocat.attachment.model.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @version $Id: 08724e69a979b7d256d45ee798f62c53f7af1467 $
 */
@Controller
@RequestMapping("/ms1/attachment/")
public class AttachmentWrapper {

    // //////////////////////////////////////////////
    // //////////////////////////////////////////////
    private static int maxId = 0;

    public static HashMap<Integer, AttachmentServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "attachment")
    public  @ResponseBody int Attachment() {
        AttachmentServer newServerObj = new AttachmentServer();
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
    // //////////////////////////////////////////////
    // //////////////////////////////////////////////
}

