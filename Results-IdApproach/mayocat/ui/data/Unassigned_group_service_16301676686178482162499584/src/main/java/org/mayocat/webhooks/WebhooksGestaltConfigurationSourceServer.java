/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.webhooks;

import javax.inject.Inject;
import org.mayocat.configuration.GestaltConfigurationSource;
import org.xwiki.component.annotation.Component;

/**
 * @version $Id: d9c437db98145cc30ab8027acdd52866f84cd9c3 $
 */
@Component("webhooks")
public class WebhooksGestaltConfigurationSourceServer implements GestaltConfigurationSource {

    @Override
    public Object get() {
        return new WebhooksSettings();
    }
}

