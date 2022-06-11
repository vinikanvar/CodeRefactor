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
package com.ibm.research.cma.api;

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
import com.ibm.websphere.samples.daytrader.beans.*;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.HashMap;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/ms8/marketsummarydatabean/")
public class MarketSummaryDataBeanWrapper {

    /*
                                                  * Collection of top losing
                                                  * stocks
                                                  */
    // FUTURE private Collection topVolume; /* Collection of top stocks by
    /* Date this summary was taken */
    @RequestMapping(method = RequestMethod.POST, value = "tojson")
    public @ResponseBody Integer toJSON(@RequestParam int callerId) {
        MarketSummaryDataBeanServer callerObj = (MarketSummaryDataBeanServer) idObjMap.get(callerId);
        JsonObject result = callerObj.toJSON();
        return result.id;
    }

    @RequestMapping(method = RequestMethod.GET, value = "getgainpercent")
    public @ResponseBody BigDecimal getGainPercent(@RequestParam int callerId) {
        MarketSummaryDataBeanServer callerObj = (MarketSummaryDataBeanServer) idObjMap.get(callerId);
        BigDecimal result = callerObj.getGainPercent();
        return result;
    }

    /**
     * Gets the tSIA
     *
     * @return Returns a BigDecimal
     */
    @RequestMapping(method = RequestMethod.GET, value = "gettsia")
    public @ResponseBody BigDecimal getTSIA(@RequestParam int callerId) {
        MarketSummaryDataBeanServer callerObj = (MarketSummaryDataBeanServer) idObjMap.get(callerId);
        BigDecimal result = callerObj.getTSIA();
        return result;
    }

    /**
     * Gets the volume
     *
     * @return Returns a BigDecimal
     */
    @RequestMapping(method = RequestMethod.GET, value = "marketsummarydatabean_getvolume")
    public  @ResponseBody double getVolume(@RequestParam int callerId) {
        MarketSummaryDataBeanServer callerObj = (MarketSummaryDataBeanServer) idObjMap.get(callerId);
        double result = callerObj.getVolume();
        return result;
    }

    /**
     * Gets the topGainers
     *
     * @return Returns a Collection
     */
    @RequestMapping(method = RequestMethod.GET, value = "gettopgainers")
    public @ResponseBody Integer getTopGainers(@RequestParam int callerId) {
        MarketSummaryDataBeanServer callerObj = (MarketSummaryDataBeanServer) idObjMap.get(callerId);
        Collection<QuoteDataBean> result = callerObj.getTopGainers();
        return new Integer(0);
    }

    /**
     * Gets the topLosers
     *
     * @return Returns a Collection
     */
    @RequestMapping(method = RequestMethod.GET, value = "gettoplosers")
    public @ResponseBody Integer getTopLosers(@RequestParam int callerId) {
        MarketSummaryDataBeanServer callerObj = (MarketSummaryDataBeanServer) idObjMap.get(callerId);
        Collection<QuoteDataBean> result = callerObj.getTopLosers();
        return new Integer(0);
    }

    /**
     * Gets the summaryDate
     *
     * @return Returns a Date
     */
    @RequestMapping(method = RequestMethod.GET, value = "getsummarydate")
    public @ResponseBody Date getSummaryDate(@RequestParam int callerId) {
        MarketSummaryDataBeanServer callerObj = (MarketSummaryDataBeanServer) idObjMap.get(callerId);
        Date result = callerObj.getSummaryDate();
        return result;
    }

    private static int maxId = 0;

    public static HashMap<Integer, MarketSummaryDataBeanServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "marketsummarydatabean_")
    public  @ResponseBody int MarketSummaryDataBean() {
        MarketSummaryDataBeanServer newServerObj = new MarketSummaryDataBeanServer();
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }

    @RequestMapping(method = RequestMethod.POST, value = "marketsummarydatabean_bigdecimal_bigdecimal_double_collection<quotedatabean>_collection<quotedatabean>")
    public  @ResponseBody int MarketSummaryDataBean(// , Collection topVolume
    @RequestParam int TSIAId, // , Collection topVolume
    @RequestParam int openTSIAId, // , Collection topVolume
    @RequestParam double volume, // , Collection topVolume
    @RequestParam int topGainersId, // , Collection topVolume
    @RequestParam int topLosersId) {
        MarketSummaryDataBeanServer newServerObj = new MarketSummaryDataBeanServer(TSIA, openTSIA, volume, (Collection<QuoteDataBean>) new Object(), (Collection<QuoteDataBean>) new Object());
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
}

