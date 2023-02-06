/*
 * Copyright (c) 2012, Mayocat <hello@mayocat.org>
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package org.mayocat.shop.front.context;

import java.util.Date;
import java.util.Locale;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * @version $Id: c5c23850c8d8316e5bd71d6a1acd0dceff919a07 $
 */
public class DateContext {

    public DateContext(Date date, Locale locale) {
        String uri = MicroserviceApplication.projectUri + "/msnull/datecontext/datecontext?callerId=" + this.id + "&date=date&localeId=locale.id";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        this.id = resultTemp;
    }

    public String getShortDate() {
        return (String) null;
    }

    public String getLongDate() {
        return (String) null;
    }

    public Integer getDayOfMonth() {
        return (Integer) null;
    }

    public Integer getMonthOfYear() {
        return (Integer) null;
    }

    public Integer getYear() {
        return (Integer) null;
    }

    public Long getTime() {
        return (Long) null;
    }

    public String getDateTime() {
        return (String) null;
    }

    public int id = 0;

    public static DateContext getObject(int id) {
        DateContext obj = (DateContext) new Object();
        obj.id = id;
        return obj;
    }
}

