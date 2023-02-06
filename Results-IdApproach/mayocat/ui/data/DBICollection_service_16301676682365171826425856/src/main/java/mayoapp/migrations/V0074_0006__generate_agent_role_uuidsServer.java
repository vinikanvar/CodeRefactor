/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package mayoapp.migrations;

import java.util.Arrays;
import java.util.List;
import org.mayocat.flyway.migrations.AbstractUUIDGenerationMigration;

/**
 * @version $Id: 63af1834f7d42d9ba9262f8306c5b688af368361 $
 */
public class V0074_0006__generate_agent_role_uuidsServer extends AbstractUUIDGenerationMigration {

    @Override
    public List<String> getTableNames() {
        return Arrays.asList("agent_role");
    }

    @Override
    public String getTransitionTableSuffix() {
        return "_with_uuid";
    }
}

