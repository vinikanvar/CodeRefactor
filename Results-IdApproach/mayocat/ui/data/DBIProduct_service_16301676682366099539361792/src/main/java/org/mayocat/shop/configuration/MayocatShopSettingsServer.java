/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.shop.configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.mayocat.cms.news.NewsSettings;
import org.mayocat.configuration.AbstractSettings;
import org.mayocat.configuration.DevelopmentEnvironmentSettings;
import org.mayocat.configuration.general.GeneralSettings;
import org.mayocat.configuration.theme.ThemeSettings;
import org.mayocat.shop.catalog.configuration.shop.CatalogSettings;
import org.mayocat.shop.checkout.CheckoutSettings;
import org.mayocat.shop.invoicing.InvoicingSettings;
import org.mayocat.shop.payment.PaymentsSettings;
import org.mayocat.shop.shipping.configuration.ShippingSettings;
import org.mayocat.shop.taxes.configuration.TaxesSettings;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.db.DataSourceFactory;

/**
 * @version $Id: e00109c906b83f9367c899ef113328eb5844badd $
 */
public class MayocatShopSettingsServer extends AbstractSettings {

    @Valid
    @JsonProperty
    private DevelopmentEnvironmentSettings developmentEnvironment = new DevelopmentEnvironmentSettings();

    @Valid
    @NotNull
    @JsonProperty
    private DataSourceFactory database = new DataSourceFactory();

    @Valid
    @NotNull
    @JsonProperty
    private GeneralSettings general = new GeneralSettings();

    @Valid
    @NotNull
    @JsonProperty
    private ThemeSettings theme = new ThemeSettings();

    @Valid
    @NotNull
    @JsonProperty
    private CheckoutSettings checkout = new CheckoutSettings();

    @Valid
    @NotNull
    @JsonProperty
    private PaymentsSettings payments = new PaymentsSettings();

    @Valid
    @NotNull
    @JsonProperty
    private CatalogSettings catalog = new CatalogSettings();

    @Valid
    @NotNull
    @JsonProperty
    private ShippingSettings shipping = new ShippingSettings();

    @Valid
    @NotNull
    @JsonProperty
    private TaxesSettings taxes = new TaxesSettings();

    @Valid
    @NotNull
    @JsonProperty
    private InvoicingSettings invoicing = new InvoicingSettings();

    @Valid
    @NotNull
    @JsonProperty
    private NewsSettings news = new NewsSettings();

    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    public GeneralSettings getGeneralSettings() {
        return general;
    }

    public CatalogSettings getCatalogSettings() {
        return catalog;
    }

    public ThemeSettings getThemeSettings() {
        return theme;
    }

    public CheckoutSettings getCheckoutSettings() {
        return checkout;
    }

    public ShippingSettings getShippingSettings() {
        return shipping;
    }

    public TaxesSettings getTaxesSettings() {
        return taxes;
    }

    public InvoicingSettings getInvoicing() {
        return invoicing;
    }

    public NewsSettings getNewsSettings() {
        return news;
    }

    public DevelopmentEnvironmentSettings getDevelopmentEnvironment() {
        return developmentEnvironment;
    }
}

