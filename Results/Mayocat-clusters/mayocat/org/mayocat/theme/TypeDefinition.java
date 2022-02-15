/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.theme;

import java.util.Map;
import com.google.common.collect.Maps;

/**
 * Represents the definition of a product type.
 *
 * @version $Id: be576a5cc02f7b1475bd8e1c2faebeca2d049b25 $
 */
public class TypeDefinition {

    private String name;

    private Map<String, FeatureDefinition> features = Maps.newHashMap();

    private VariantsDefinition variants = new VariantsDefinition();

    public String getName() {
        return name;
    }

    public Map<String, FeatureDefinition> getFeatures() {
        return features;
    }

    public VariantsDefinition getVariants() {
        return variants;
    }
}
