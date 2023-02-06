/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.store.rdbms.dbi.mapper;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * Utility functions for mappers
 *
 * @version $Id: 0c9afbf1a10a42c11207d2b5377dc37e90d2686d $
 */
public class MapperUtils {

    public static boolean hasColumn(String columnName, ResultSet resultSet) {
        ResultSetMetaData meta = null;
        try {
            meta = resultSet.getMetaData();
            int columnCount = meta.getColumnCount();
            for (int i = 1; i < columnCount + 1; i++) {
                if (meta.getColumnName(i).equals(columnName)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            // Ignore
        }
        return false;
    }
}
