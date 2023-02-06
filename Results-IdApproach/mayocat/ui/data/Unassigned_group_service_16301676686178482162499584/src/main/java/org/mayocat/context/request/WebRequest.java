/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.context.request;

import java.net.URI;
import org.mayocat.theme.Breakpoint;
import com.google.common.base.Optional;

/**
 * Represents information about the context's request that is relevant to Mayocat
 *
 * @version $Id: 78aa668f2421fe8fe715124ffb57ad83fd1fb253 $
 */
public interface WebRequest {

    boolean isApiRequest();

    boolean isTenantRequest();

    boolean isSecure();

    URI getBaseUri();

    String getTenantPrefix();

    String getPath();

    String getCanonicalPath();

    Optional<Breakpoint> getBreakpoint();

    public int id = 0;

    public static WebRequest getObject(int id) {
        return null;
    }
}

