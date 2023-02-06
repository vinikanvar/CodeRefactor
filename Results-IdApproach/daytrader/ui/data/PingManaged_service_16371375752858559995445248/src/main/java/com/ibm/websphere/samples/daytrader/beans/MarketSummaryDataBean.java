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
package com.ibm.websphere.samples.daytrader.beans;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import com.ibm.websphere.samples.daytrader.entities.QuoteDataBean;
import com.ibm.websphere.samples.daytrader.util.FinancialUtils;
import com.ibm.websphere.samples.daytrader.util.Log;
import com.ibm.websphere.samples.daytrader.util.TradeConfig;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class MarketSummaryDataBean implements Serializable {

    /*
                                                  * Collection of top losing
                                                  * stocks
                                                  */
    // FUTURE private Collection topVolume; /* Collection of top stocks by
    /* Date this summary was taken */
    public MarketSummaryDataBean() {
        String uri = MicroserviceApplication.projectUri + "/ms8/marketsummarydatabean/marketsummarydatabean_callerId=" + this.id + "";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        this.id = resultTemp;
    }

    public MarketSummaryDataBean(// , Collection topVolume
    BigDecimal TSIA, // , Collection topVolume
    BigDecimal openTSIA, // , Collection topVolume
    double volume, // , Collection topVolume
    Collection<QuoteDataBean> topGainers, // , Collection topVolume
    Collection<QuoteDataBean> topLosers) {
        String uri = MicroserviceApplication.projectUri + "/ms8/marketsummarydatabean/marketsummarydatabean_bigdecimal_bigdecimal_double_collection<quotedatabean>_collection<quotedatabean>?callerId=" + this.id + "&TSIA=TSIA&openTSIA=openTSIA&volume=volume&topGainersId=topGainers.id&topLosersId=topLosers.id";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        this.id = resultTemp;
    }

    public static MarketSummaryDataBean getRandomInstance() {
        return (MarketSummaryDataBean) null;
    }

    @Override
    public String toString() {
        return (String) null;
    }

    public String toHTML() {
        return (String) null;
    }

    public JsonObject toJSON() {
        String uri = MicroserviceApplication.projectUri + "/ms8/marketsummarydatabean/tojsoncallerId=" + this.id + "";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        JsonObject result = JsonObject.getObject(resultTemp);
        return result;
    }

    public void print() {
        return;
    }

    public BigDecimal getGainPercent() {
        String uri = MicroserviceApplication.projectUri + "/ms8/marketsummarydatabean/getgainpercentcallerId=" + this.id + "";
        BigDecimal resultTemp = new RestTemplate().getForObject(uri, BigDecimal.class);
        BigDecimal result = resultTemp;
        return result;
    }

    /**
     * Gets the tSIA
     *
     * @return Returns a BigDecimal
     */
    public BigDecimal getTSIA() {
        String uri = MicroserviceApplication.projectUri + "/ms8/marketsummarydatabean/gettsiacallerId=" + this.id + "";
        BigDecimal resultTemp = new RestTemplate().getForObject(uri, BigDecimal.class);
        BigDecimal result = resultTemp;
        return result;
    }

    /**
     * Sets the tSIA
     *
     * @param tSIA
     *            The tSIA to set
     */
    public void setTSIA(BigDecimal tSIA) {
        return;
    }

    /**
     * Gets the openTSIA
     *
     * @return Returns a BigDecimal
     */
    public BigDecimal getOpenTSIA() {
        return (BigDecimal) null;
    }

    /**
     * Sets the openTSIA
     *
     * @param openTSIA
     *            The openTSIA to set
     */
    public void setOpenTSIA(BigDecimal openTSIA) {
        return;
    }

    /**
     * Gets the volume
     *
     * @return Returns a BigDecimal
     */
    public double getVolume() {
        String uri = MicroserviceApplication.projectUri + "/ms8/marketsummarydatabean/marketsummarydatabean_getvolumecallerId=" + this.id + "";
        double resultTemp = new RestTemplate().getForObject(uri, double.class);
        double result = resultTemp;
        return result;
    }

    /**
     * Sets the volume
     *
     * @param volume
     *            The volume to set
     */
    public void setVolume(double volume) {
        return;
    }

    /**
     * Gets the topGainers
     *
     * @return Returns a Collection
     */
    public Collection<QuoteDataBean> getTopGainers() {
        String uri = MicroserviceApplication.projectUri + "/ms8/marketsummarydatabean/gettopgainerscallerId=" + this.id + "";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        Collection<QuoteDataBean> result = (Collection<QuoteDataBean>) new Object();
        return result;
    }

    /**
     * Sets the topGainers
     *
     * @param topGainers
     *            The topGainers to set
     */
    public void setTopGainers(Collection<QuoteDataBean> topGainers) {
        return;
    }

    /**
     * Gets the topLosers
     *
     * @return Returns a Collection
     */
    public Collection<QuoteDataBean> getTopLosers() {
        String uri = MicroserviceApplication.projectUri + "/ms8/marketsummarydatabean/gettoploserscallerId=" + this.id + "";
        int resultTemp = new RestTemplate().getForObject(uri, int.class);
        Collection<QuoteDataBean> result = (Collection<QuoteDataBean>) new Object();
        return result;
    }

    /**
     * Sets the topLosers
     *
     * @param topLosers
     *            The topLosers to set
     */
    public void setTopLosers(Collection<QuoteDataBean> topLosers) {
        return;
    }

    /**
     * Gets the summaryDate
     *
     * @return Returns a Date
     */
    public Date getSummaryDate() {
        String uri = MicroserviceApplication.projectUri + "/ms8/marketsummarydatabean/getsummarydatecallerId=" + this.id + "";
        Date resultTemp = new RestTemplate().getForObject(uri, Date.class);
        Date result = resultTemp;
        return result;
    }

    /**
     * Sets the summaryDate
     *
     * @param summaryDate
     *            The summaryDate to set
     */
    public void setSummaryDate(Date summaryDate) {
        return;
    }

    public int id = 0;

    public static MarketSummaryDataBean getObject(int id) {
        MarketSummaryDataBean obj = (MarketSummaryDataBean) new Object();
        obj.id = id;
        return obj;
    }
}

