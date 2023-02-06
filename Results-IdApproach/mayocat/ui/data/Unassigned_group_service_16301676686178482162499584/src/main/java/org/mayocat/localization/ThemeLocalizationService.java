/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.localization;

import org.xwiki.component.annotation.Role;
import java.util.Locale;
import java.util.Map;

/**
 * @version $Id: bb2e32ac542f0d0b43a6581f5aa9a62ad2bee043 $
 */
@Role
public interface ThemeLocalizationService {

    String getMessage(String key, Map<String, Object> namedArguments);

    String getMessage(String key, Locale locale, Map<String, Object> namedArguments);

    String getMessageTemplate(String key);

    String getMessageTemplate(String key, Locale locale);

    public int id = 0;

    public static ThemeLocalizationService getObject(int id) {
        return null;
    }
}

