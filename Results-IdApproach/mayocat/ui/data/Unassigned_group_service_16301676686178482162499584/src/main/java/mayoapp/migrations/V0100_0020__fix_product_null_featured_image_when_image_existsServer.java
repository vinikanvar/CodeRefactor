/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package mayoapp.migrations;

import org.mayocat.flyway.migrations.AbstractFixNullFeaturedImageMigration;
import com.googlecode.flyway.core.api.migration.jdbc.JdbcMigration;

/**
 * Fix NULL featured image id for products
 *
 * @version $Id: 3d21a0d9ea77ba801df7a5a8242b53b7155b518a $
 */
public class V0100_0020__fix_product_null_featured_image_when_image_existsServer extends AbstractFixNullFeaturedImageMigration implements JdbcMigration {

    @Override
    public String getTableName() {
        return "product";
    }
}

