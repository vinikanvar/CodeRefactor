/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.entity;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @version $Id: 8af9ab474ca8413c008b7a8279ed1ea6bc2b5605 $
 */
public class EntityUtilsServer {

    public static Set<LoadingOption> asSet(LoadingOption[] options) {
        return new HashSet(Arrays.asList(options));
    }
}

