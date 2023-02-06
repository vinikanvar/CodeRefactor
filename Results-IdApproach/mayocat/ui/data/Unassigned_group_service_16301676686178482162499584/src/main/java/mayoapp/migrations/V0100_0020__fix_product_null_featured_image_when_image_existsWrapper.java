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
 * Fix NULL featured image id for products
 *
 * @version $Id: 3d21a0d9ea77ba801df7a5a8242b53b7155b518a $
 */
@Controller
@RequestMapping("/ms5/v0100_0020__fix_product_null_featured_image_when_image_exists/")
public class V0100_0020__fix_product_null_featured_image_when_image_existsWrapper {

    private static int maxId = 0;

    public static HashMap<Integer, V0100_0020__fix_product_null_featured_image_when_image_existsServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.GET, value = "v0100_0020__fix_product_null_featured_image_when_image_exists")
    public  @ResponseBody int V0100_0020__fix_product_null_featured_image_when_image_exists() {
        V0100_0020__fix_product_null_featured_image_when_image_existsServer newServerObj = new V0100_0020__fix_product_null_featured_image_when_image_existsServer();
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
}

