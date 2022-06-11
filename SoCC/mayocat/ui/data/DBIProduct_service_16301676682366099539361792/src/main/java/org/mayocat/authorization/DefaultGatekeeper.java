/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.authorization;

import java.util.List;
import javax.inject.Inject;
import javax.inject.Provider;
import org.mayocat.accounts.model.Role;
import org.mayocat.accounts.model.User;
import org.mayocat.accounts.store.UserStore;
import org.slf4j.Logger;
import org.xwiki.component.annotation.Component;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Component
public class DefaultGatekeeper implements Gatekeeper {

    @Override
    public boolean userHasRole(User user, Role role) {
        return (Boolean) null;
    }

    public int id = 0;

    public static DefaultGatekeeper getObject(int id) {
        DefaultGatekeeper obj = (DefaultGatekeeper) new Object();
        obj.id = id;
        return obj;
    }
}

