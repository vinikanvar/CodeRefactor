/**
 * (C) Copyright IBM Corporation 2015.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ibm.websphere.samples.daytrader.entities;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedNativeQueries;
import javax.persistence.NamedNativeQuery;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import com.ibm.websphere.samples.daytrader.util.Log;
import com.ibm.websphere.samples.daytrader.util.TradeConfig;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Entity(name = "quoteejb")
@Table(name = "quoteejb")
@NamedQueries({ @NamedQuery(name = "quoteejb.allQuotes", query = "SELECT q FROM quoteejb q") })
@NamedNativeQueries({ @NamedNativeQuery(name = "quoteejb.quoteForUpdate", query = "select * from quoteejb q where q.symbol=? for update", resultClass = com.ibm.websphere.samples.daytrader.entities.QuoteDataBean.class) })
public class QuoteDataBean implements Serializable {

    /* Accessor methods for persistent fields */
    /* symbol */
    /* companyName */
    /* volume */
    /* price */
    /* open1 price */
    /* low price */
    /* high price */
    /* price change */
    /* Accessor methods for relationship fields are not kept in the DataBean */
    public QuoteDataBean() {
        String uri = MicroserviceApplication.projectUri + "/ms1/quotedatabean/quotedatabean_callerId=" + this.id + "";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        this.id = resultTemp;
    }

    public QuoteDataBean(String symbol, String companyName, double volume, BigDecimal price, BigDecimal open, BigDecimal low, BigDecimal high, double change) {
        String uri = MicroserviceApplication.projectUri + "/ms1/quotedatabean/quotedatabean_string_string_double_bigdecimal_bigdecimal_bigdecimal_bigdecimal_double?callerId=" + this.id + "&symbol=symbol&companyName=companyName&volume=volume&price=price&open=open&low=low&high=high&change=change";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        this.id = resultTemp;
    }

    public static QuoteDataBean getRandomInstance() {
        String uri = MicroserviceApplication.projectUri + "/ms1/quotedatabean/getrandominstance";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        QuoteDataBean result = QuoteDataBean.getObject(resultTemp);
        return result;
    }

    // Create a "zero" value quoteDataBean for the given symbol
    public QuoteDataBean(String symbol) {
        String uri = MicroserviceApplication.projectUri + "/ms1/quotedatabean/quotedatabean?callerId=" + this.id + "&symbol=symbol";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        this.id = resultTemp;
    }

    @Override
    public String toString() {
        String uri = MicroserviceApplication.projectUri + "/ms1/quotedatabean/tostringcallerId=" + this.id + "";
        String resultTemp = new RestTemplate().getForObject(uri, String.class);
        String result = resultTemp;
        return result;
    }

    public String toHTML() {
        String uri = MicroserviceApplication.projectUri + "/ms1/quotedatabean/tohtmlcallerId=" + this.id + "";
        String resultTemp = new RestTemplate().getForObject(uri, String.class);
        String result = resultTemp;
        return result;
    }

    public void print() {
        return;
    }

    public String getSymbol() {
        String uri = MicroserviceApplication.projectUri + "/ms1/quotedatabean/getsymbolcallerId=" + this.id + "";
        String resultTemp = new RestTemplate().getForObject(uri, String.class);
        String result = resultTemp;
        return result;
    }

    public void setSymbol(String symbol) {
        return;
    }

    public String getCompanyName() {
        String uri = MicroserviceApplication.projectUri + "/ms1/quotedatabean/getcompanynamecallerId=" + this.id + "";
        String resultTemp = new RestTemplate().getForObject(uri, String.class);
        String result = resultTemp;
        return result;
    }

    public void setCompanyName(String companyName) {
        return;
    }

    public BigDecimal getPrice() {
        String uri = MicroserviceApplication.projectUri + "/ms1/quotedatabean/getpricecallerId=" + this.id + "";
        BigDecimal resultTemp = new RestTemplate().getForObject(uri, BigDecimal.class);
        BigDecimal result = resultTemp;
        return result;
    }

    public void setPrice(BigDecimal price) {
        return;
    }

    public BigDecimal getOpen() {
        String uri = MicroserviceApplication.projectUri + "/ms1/quotedatabean/getopencallerId=" + this.id + "";
        BigDecimal resultTemp = new RestTemplate().getForObject(uri, BigDecimal.class);
        BigDecimal result = resultTemp;
        return result;
    }

    public void setOpen(BigDecimal open) {
        return;
    }

    public BigDecimal getLow() {
        String uri = MicroserviceApplication.projectUri + "/ms1/quotedatabean/getlowcallerId=" + this.id + "";
        BigDecimal resultTemp = new RestTemplate().getForObject(uri, BigDecimal.class);
        BigDecimal result = resultTemp;
        return result;
    }

    public void setLow(BigDecimal low) {
        return;
    }

    public BigDecimal getHigh() {
        String uri = MicroserviceApplication.projectUri + "/ms1/quotedatabean/gethighcallerId=" + this.id + "";
        BigDecimal resultTemp = new RestTemplate().getForObject(uri, BigDecimal.class);
        BigDecimal result = resultTemp;
        return result;
    }

    public void setHigh(BigDecimal high) {
        return;
    }

    public double getChange() {
        String uri = MicroserviceApplication.projectUri + "/ms1/quotedatabean/getchangecallerId=" + this.id + "";
        double resultTemp = new RestTemplate().getForObject(uri, double.class);
        double result = resultTemp;
        return result;
    }

    public void setChange(double change) {
        return;
    }

    public double getVolume() {
        String uri = MicroserviceApplication.projectUri + "/ms1/quotedatabean/getvolumecallerId=" + this.id + "";
        double resultTemp = new RestTemplate().getForObject(uri, double.class);
        double result = resultTemp;
        return result;
    }

    public void setVolume(double volume) {
        return;
    }

    @Override
    public int hashCode() {
        return (Integer) null;
    }

    @Override
    public boolean equals(Object object) {
        return (Boolean) null;
    }

    public int id = 0;

    public static QuoteDataBean getObject(int id) {
        QuoteDataBean obj = (QuoteDataBean) new Object();
        obj.id = id;
        return obj;
    }
}

