/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.theme.internal;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;
import org.mayocat.accounts.model.Tenant;
import org.mayocat.configuration.ConfigurationService;
import org.mayocat.configuration.general.FilesSettings;
import org.mayocat.configuration.theme.ThemeSettings;
import org.mayocat.context.WebContext;
import org.mayocat.theme.Theme;
import org.mayocat.theme.ThemeDefinition;
import org.mayocat.theme.ThemeFileResolver;
import org.mayocat.theme.ThemeManager;
import org.slf4j.Logger;
import org.xwiki.component.annotation.Component;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.TreeTraversingParser;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.google.common.base.Charsets;
import com.google.common.base.Optional;
import com.google.common.io.Files;
import com.google.common.io.Resources;

/**
 * @version $Id: dfa6ed213d2db544b5357ad8bd1bf52de6e8785d $
 */
@Component
public class DefaultThemeManagerServer implements ThemeManager {

    /**
     * The "levels" at which themes can be defined.
     */
    private enum Level {

        /**
         * The tenant own permanent directory
         */
        TENANT_DIRECTORY,
        /**
         * The global themes directory
         */
        THEME_DIRECTORY,
        /**
         * The JVM classpath
         */
        CLASSPATH
    }

    private static final String THEME_YML = "theme.yml";

    private final static String THEMES_FOLDER_NAME = "themes";

    private final static String TENANTS_FOLDER_NAME = "tenants";

    @Inject
    private ObjectMapper mapper;

    @Inject
    private Logger logger;

    @Inject
    private ThemeFileResolver themeFileResolver;

    @Inject
    private ConfigurationService configurationService;

    @Inject
    private FilesSettings filesSettings;

    @Inject
    private ThemeSettings themeSettings;

    @Inject
    private WebContext context;

    public Theme getDefaultTheme() {
        return getTheme(themeSettings.getActive().getDefaultValue(), Optional.<Tenant>absent(), Collections.<Level>emptyList());
    }

    public Theme getTheme(String themeName) {
        return getTheme(themeName, Optional.<Tenant>absent(), Collections.<Level>emptyList());
    }

    public Theme getTheme() {
        return getTheme(this.context.getTenant());
    }

    public Theme getTheme(Tenant tenant) {
        String themeId = getActiveThemeId(tenant);
        return getTheme(themeId, Optional.of(tenant), Collections.<Level>emptyList());
    }

    private Theme getTheme(String themeId, Optional<Tenant> tenant, List<Level> ignore) {
        Level level = Level.TENANT_DIRECTORY;
        JsonNode node;
        Path themeDirectory = null;
        if (tenant.isPresent() && !ignore.contains(Level.TENANT_DIRECTORY)) {
            themeDirectory = getTenantThemeDirectory(tenant.get().getSlug(), themeId);
        }
        if ((themeDirectory == null || !themeDirectory.toFile().exists()) && !ignore.contains(Level.THEME_DIRECTORY)) {
            level = Level.THEME_DIRECTORY;
            themeDirectory = getGlobalThemeDirectory(themeId);
        }
        if (themeDirectory == null || !themeDirectory.toFile().exists()) {
            Optional<Path> path = getClasspathThemePath(themeId);
            if (path.isPresent() && !ignore.contains(Level.CLASSPATH)) {
                try {
                    node = mapper.readTree(Resources.getResource(path.get().resolve(THEME_YML).toString()));
                    ThemeDefinition definition = mapper.readValue(new TreeTraversingParser(node), ThemeDefinition.class);
                    return new Theme(path.get(), definition, null, Theme.Type.CLASSPATH);
                } catch (JsonProcessingException e) {
                    Theme theme = new Theme(path.get(), null, null, Theme.Type.CLASSPATH, false);
                } catch (IOException e) {
                    // Surrender
                    logger.error("Could not resolve theme", e);
                    return null;
                }
            } else {
                // Here there is nothing more we can do ; surrender
                return null;
            }
        }
        if (themeDirectory == null || !themeDirectory.resolve("theme.yml").toFile().isFile()) {
            // Theme not found
            return null;
        }
        ThemeDefinition definition = null;
        Theme parent = null;
        boolean definitionValid = true;
        logger.debug("Theme directory resolved to [{}]", themeDirectory.toString());
        try {
            node = mapper.readTree(themeDirectory.resolve("theme.yml").toFile());
            definition = mapper.readValue(new TreeTraversingParser(node), ThemeDefinition.class);
        } catch (JsonProcessingException e) {
            definition = null;
            definitionValid = false;
            logger.warn("Trying to load invalid theme. Error: {}", e.getMessage());
        } catch (IOException e) {
            logger.error("I/O exception parsing theme", e);
            // theme.yml file not found -> theme might have a parent
            if (tenant.isPresent()) {
                parent = getTheme(themeId, Optional.<Tenant>absent(), Arrays.asList(level));
            }
        }
        Theme theme = new Theme(themeDirectory, definition, parent, Theme.Type.FILE_SYSTEM, definitionValid);
        if (!level.equals(Level.THEME_DIRECTORY)) {
            // The theme lives in the tenant directory
            theme.setTenantOwnTheme(true);
        }
        return theme;
    }

    private Path getTenantThemeDirectory(String tenantSlug, String themeId) {
        return filesSettings.getPermanentDirectory().resolve(TENANTS_FOLDER_NAME).resolve(tenantSlug).resolve(THEMES_FOLDER_NAME).resolve(themeId);
    }

    private Path getGlobalThemeDirectory(String themeId) {
        return filesSettings.getPermanentDirectory().resolve(THEMES_FOLDER_NAME).resolve(themeId);
    }

    private Optional<Path> getClasspathThemePath(String themeId) {
        Path themePath = Paths.get(THEMES_FOLDER_NAME).resolve(themeId);
        try {
            Resources.getResource(themePath.toString());
            return Optional.of(themePath);
        } catch (IllegalArgumentException e) {
            return Optional.absent();
        }
    }

    private String getActiveThemeId(Tenant tenant) {
        ThemeSettings settings = configurationService.getSettings(ThemeSettings.class, tenant);
        logger.debug("Get active theme id, {}, {}", tenant, settings.getActive().getValue());
        return settings.getActive().getValue();
    }
}

