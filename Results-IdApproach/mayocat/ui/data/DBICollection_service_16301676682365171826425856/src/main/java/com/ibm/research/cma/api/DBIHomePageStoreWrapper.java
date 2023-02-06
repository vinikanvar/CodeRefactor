/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.ibm.research.cma.api;

import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.mayocat.cms.home.model.HomePage;
import org.mayocat.cms.home.store.HomePageStore;
import org.mayocat.model.AddonGroup;
import org.mayocat.model.event.EntityCreatedEvent;
import org.mayocat.model.event.EntityCreatingEvent;
import org.mayocat.model.event.EntityUpdatedEvent;
import org.mayocat.model.event.EntityUpdatingEvent;
import org.mayocat.store.EntityAlreadyExistsException;
import org.mayocat.store.EntityDoesNotExistException;
import org.mayocat.store.InvalidEntityException;
import org.mayocat.store.rdbms.dbi.DBIEntityStore;
import org.xwiki.component.annotation.Component;
import org.xwiki.component.phase.Initializable;
import org.xwiki.component.phase.InitializationException;
import mayoapp.dao.HomePageDAO;
import static org.mayocat.addons.util.AddonUtils.asMap;
import org.mayocat.cms.home.store.jdbi.*;
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
 * @version $Id: e4f887867e0430d28d126caef466e1227c85cef4 $
 */
@Component
@Controller
@RequestMapping("/ms0/dbihomepagestore/")
public class DBIHomePageStoreWrapper {

    private static int maxId = 0;

    public static HashMap<Integer, DBIHomePageStoreServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "dbihomepagestore")
    public  @ResponseBody int DBIHomePageStore() {
        DBIHomePageStoreServer newServerObj = new DBIHomePageStoreServer();
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
}

