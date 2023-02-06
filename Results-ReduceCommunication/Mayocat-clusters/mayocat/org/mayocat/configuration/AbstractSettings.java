/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.mayocat.accounts.AccountsSettings;
import org.mayocat.configuration.general.FilesSettings;
import org.mayocat.mail.SmtpSettings;
import org.mayocat.rest.CorsSettings;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.client.JerseyClientConfiguration;
import org.mayocat.webhooks.WebhooksSettings;

/**
 * @version $Id: 26832a689c682f038fa51a64e788f67bc34f70c2 $
 */
public class AbstractSettings extends Configuration {

    @Valid
    @NotNull
    @JsonProperty
    private PlatformSettings platform = new PlatformSettings();

    @Valid
    @NotNull
    @JsonProperty
    private AccountsSettings accounts = new AccountsSettings();

    @Valid
    @NotNull
    @JsonProperty
    private MultitenancySettings multitenancy = new MultitenancySettings();

    @Valid
    @NotNull
    @JsonProperty
    private SecuritySettings security = new SecuritySettings();

    @Valid
    @NotNull
    @JsonProperty
    private FilesSettings files = new FilesSettings();

    @Valid
    @NotNull
    @JsonProperty
    private SiteSettings site = new SiteSettings();

    @Valid
    @NotNull
    @JsonProperty
    private SearchEngineSettings searchEngine = new SearchEngineSettings();

    @Valid
    @NotNull
    @JsonProperty
    private SmtpSettings smtp = new SmtpSettings();

    @Valid
    @JsonProperty
    private LocalizationFilterSettings localizationFilter = new LocalizationFilterSettings();

    @Valid
    @JsonProperty
    private CorsSettings cors = new CorsSettings();

    @Valid
    @NotNull
    @JsonProperty
    private JerseyClientConfiguration httpClient = new JerseyClientConfiguration();

    @Valid
    @NotNull
    @JsonProperty
    private WebhooksSettings webhooks = new WebhooksSettings();

    public MultitenancySettings getMultitenancySettings() {
        return multitenancy;
    }

    public SecuritySettings getAuthenticationSettings() {
        return security;
    }

    public SearchEngineSettings getSearchEngineSettings() {
        return searchEngine;
    }

    public PlatformSettings getPlatformSettings() {
        return platform;
    }

    public FilesSettings getFilesSettings() {
        return files;
    }

    public SiteSettings getSiteSettings() {
        return site;
    }

    public SmtpSettings getSmtpSettings() {
        return smtp;
    }

    public LocalizationFilterSettings getLocalizationFilterSettings() {
        return localizationFilter;
    }

    public CorsSettings getCorsSettings() {
        return cors;
    }

    public AccountsSettings getAccountsSettings() {
        return accounts;
    }

    public JerseyClientConfiguration getJerseyClientConfiguration() {
        return httpClient;
    }

    public PlatformSettings getPlatform() {
        return platform;
    }
}
