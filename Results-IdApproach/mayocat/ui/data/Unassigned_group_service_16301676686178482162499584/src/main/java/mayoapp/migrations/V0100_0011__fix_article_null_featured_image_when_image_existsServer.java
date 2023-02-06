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
 * Fix NULL featured image id for articles
 *
 * @version $Id: 65949aa758ad2ff8f5801bd44aec05c5d0c4da99 $
 */
public class V0100_0011__fix_article_null_featured_image_when_image_existsServer extends AbstractFixNullFeaturedImageMigration implements JdbcMigration {

    @Override
    public String getTableName() {
        return "article";
    }
}

