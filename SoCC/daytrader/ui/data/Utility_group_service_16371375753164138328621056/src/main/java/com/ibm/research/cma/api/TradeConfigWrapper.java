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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;
import com.ibm.websphere.samples.daytrader.util.*;
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
 * TradeConfig is a JavaBean holding all configuration and runtime parameters
 * for the Trade application TradeConfig sets runtime parameters such as the
 * RunTimeMode (EJB, JDBC, EJB_ALT)
 */
@Controller
@RequestMapping("/ms7/tradeconfig/")
public class TradeConfigWrapper {

    /* Trade Runtime Configuration Parameters */
    /* Trade Caching Type parameters 
    public static String[] cachingTypeNames = { "DistributedMap", "No Caching" };
    public static final int DISTRIBUTEDMAP = 0;
    public static final int NO_CACHING = 1;
    public static int cachingType = NO_CACHING;
    public static int distributedMapCacheSize = 100000;
    */
    /*
     * CJB (DAYTRADER-25) - Also need to impose a ceiling on the quote price to
     * ensure prevent account and holding balances from exceeding the databases
     * decimal precision. At some point, this maximum value can be used to
     * trigger a stock split.
     */
    /*
     * Trade Scenario actions mixes. Each of the array rows represents a
     * specific Trade Scenario Mix. The columns give the percentages for each
     * action in the column header. Note: "login" is always 0. logout represents
     * both login and logout (because each logout operation will cause a new
     * login when the user context attempts the next action.
     */
    // Tracks the number of buys over sell when a users portfolio is empty
    // Used to maintain the correct ratio of buys/sells
    /* JSP pages for all Trade Actions */
    // FUTURE:
    // If a "trade2.properties" property file is supplied, reset the default
    // values
    // to match those specified in the file. This provides a persistent runtime
    // property mechanism during server startup
    /**
     * Return the hostname for this system Creation date: (2/16/2000 9:02:25 PM)
     */
    /**
     * Return a Trade UI Web page based on the current configuration This may
     * return a JSP page or a Servlet page Creation date: (3/14/2000 9:08:34 PM)
     */
    @RequestMapping(method = RequestMethod.GET, value = "getpage")
    public @ResponseBody String getPage(@RequestParam int callerId, @RequestParam int pageNumber) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        String result = callerObj.getPage(pageNumber);
        return result;
    }

    /**
     * Return a Trade Scenario Operation based on the setting of the current mix
     * (TradeScenarioMix) Creation date: (2/10/2000 9:08:34 PM)
     */
    @RequestMapping(method = RequestMethod.GET, value = "getscenarioaction")
    public  @ResponseBody char getScenarioAction(@RequestParam int callerId, @RequestParam boolean newUser) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        char result = callerObj.getScenarioAction(newUser);
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "getuserid")
    public @ResponseBody String getUserID(@RequestParam int callerId) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        String result = callerObj.getUserID();
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "getorderfee")
    public @ResponseBody BigDecimal getOrderFee(@RequestParam int callerId, @RequestParam String orderType) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        BigDecimal result = callerObj.getOrderFee(orderType);
        return result;
    }

    /**
     * Increment the sell deficit counter Creation date: (6/21/2000 11:33:45 AM)
     */
    @RequestMapping(method = RequestMethod.POST, value = "incrementselldeficit")
    public synchronized @ResponseBody ResponseEntity<Void> incrementSellDeficit(@RequestParam int callerId) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        callerObj.incrementSellDeficit();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "rndaddress")
    public @ResponseBody String rndAddress(@RequestParam int callerId) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        String result = callerObj.rndAddress();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "rndbalance")
    public @ResponseBody String rndBalance(@RequestParam int callerId) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        String result = callerObj.rndBalance();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "rndcreditcard")
    public @ResponseBody String rndCreditCard(@RequestParam int callerId) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        String result = callerObj.rndCreditCard();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "rndemail")
    public @ResponseBody String rndEmail(@RequestParam int callerId, @RequestParam String userID) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        String result = callerObj.rndEmail(userID);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "rndfullname")
    public @ResponseBody String rndFullName(@RequestParam int callerId) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        String result = callerObj.rndFullName();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "rndint")
    public  @ResponseBody int rndInt(@RequestParam int callerId, @RequestParam int i) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        int result = callerObj.rndInt(i);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "rndfloat")
    public  @ResponseBody float rndFloat(@RequestParam int callerId, @RequestParam int i) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        float result = callerObj.rndFloat(i);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "rndbigdecimal")
    public @ResponseBody BigDecimal rndBigDecimal(@RequestParam int callerId, @RequestParam float f) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        BigDecimal result = callerObj.rndBigDecimal(f);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "rndboolean")
    public  @ResponseBody boolean rndBoolean(@RequestParam int callerId) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        boolean result = callerObj.rndBoolean();
        return result;
    }

    /**
     * Returns a new Trade user Creation date: (2/16/2000 8:50:35 PM)
     */
    @RequestMapping(method = RequestMethod.POST, value = "rndnewuserid")
    public synchronized @ResponseBody String rndNewUserID(@RequestParam int callerId) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        String result = callerObj.rndNewUserID();
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "getrandompricechangefactor")
    public @ResponseBody BigDecimal getRandomPriceChangeFactor(@RequestParam int callerId) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        BigDecimal result = callerObj.getRandomPriceChangeFactor();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "rndquantity")
    public  @ResponseBody float rndQuantity(@RequestParam int callerId) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        float result = callerObj.rndQuantity();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "rndsymbol")
    public @ResponseBody String rndSymbol(@RequestParam int callerId) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        String result = callerObj.rndSymbol();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "rndsymbols")
    public @ResponseBody String rndSymbols(@RequestParam int callerId) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        String result = callerObj.rndSymbols();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "rnduserid")
    public @ResponseBody String rndUserID(@RequestParam int callerId) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        String result = callerObj.rndUserID();
        return result;
    }

    // Trade implements a card deck approach to selecting
    /**
     * This is a convenience method for servlets to set Trade configuration
     * parameters from servlet initialization parameters. The servlet provides
     * the init param and its value as strings. This method then parses the
     * parameter, converts the value to the correct type and sets the
     * corresponding TradeConfig parameter to the converted value
     */
    @RequestMapping(method = RequestMethod.POST, value = "setconfigparam")
    public @ResponseBody ResponseEntity<Void> setConfigParam(@RequestParam int callerId, @RequestParam String parm, @RequestParam String value) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        callerObj.setConfigParam(parm, value);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Gets the webInterfaceNames
     *
     * @return Returns a String[]
     */
    /*public static String[] getCachingTypeNames() {
        return cachingTypeNames;
    }*/
    /**
     * Gets the trace
     *
     * @return Returns a boolean
     */
    @RequestMapping(method = RequestMethod.GET, value = "gettrace")
    public  @ResponseBody boolean getTrace(@RequestParam int callerId) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        boolean result = callerObj.getTrace();
        return result;
    }

    /**
     * Sets the trace
     *
     * @param trace
     *            The trace to set
     */
    @RequestMapping(method = RequestMethod.POST, value = "settrace")
    public @ResponseBody ResponseEntity<Void> setTrace(@RequestParam int callerId, @RequestParam boolean traceValue) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        callerObj.setTrace(traceValue);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Gets the mAX_USERS.
     *
     * @return Returns a int
     */
    @RequestMapping(method = RequestMethod.GET, value = "getmax_users")
    public  @ResponseBody int getMAX_USERS(@RequestParam int callerId) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        int result = callerObj.getMAX_USERS();
        return result;
    }

    /**
     * Sets the mAX_USERS.
     *
     * @param mAX_USERS
     *            The mAX_USERS to set
     */
    @RequestMapping(method = RequestMethod.POST, value = "setmax_users")
    public @ResponseBody ResponseEntity<Void> setMAX_USERS(@RequestParam int callerId, @RequestParam int mAX_USERS) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        callerObj.setMAX_USERS(mAX_USERS);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Gets the mAX_QUOTES.
     *
     * @return Returns a int
     */
    @RequestMapping(method = RequestMethod.GET, value = "getmax_quotes")
    public  @ResponseBody int getMAX_QUOTES(@RequestParam int callerId) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        int result = callerObj.getMAX_QUOTES();
        return result;
    }

    /**
     * Sets the mAX_QUOTES.
     *
     * @param mAX_QUOTES
     *            The mAX_QUOTES to set
     */
    @RequestMapping(method = RequestMethod.POST, value = "setmax_quotes")
    public @ResponseBody ResponseEntity<Void> setMAX_QUOTES(@RequestParam int callerId, @RequestParam int mAX_QUOTES) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        callerObj.setMAX_QUOTES(mAX_QUOTES);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Gets the actionTrace.
     *
     * @return Returns a boolean
     */
    @RequestMapping(method = RequestMethod.GET, value = "getactiontrace")
    public  @ResponseBody boolean getActionTrace(@RequestParam int callerId) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        boolean result = callerObj.getActionTrace();
        return result;
    }

    /**
     * Sets the actionTrace.
     *
     * @param actionTrace
     *            The actionTrace to set
     */
    @RequestMapping(method = RequestMethod.POST, value = "setactiontrace")
    public @ResponseBody ResponseEntity<Void> setActionTrace(@RequestParam int callerId, @RequestParam boolean actionTrace) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        callerObj.setActionTrace(actionTrace);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "incrementscenariocount")
    public synchronized @ResponseBody ResponseEntity<Void> incrementScenarioCount(@RequestParam int callerId) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        callerObj.incrementScenarioCount();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Gets the updateQuotePrices.
     *
     * @return Returns a boolean
     */
    @RequestMapping(method = RequestMethod.GET, value = "getupdatequoteprices")
    public  @ResponseBody boolean getUpdateQuotePrices(@RequestParam int callerId) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        boolean result = callerObj.getUpdateQuotePrices();
        return result;
    }

    @RequestMapping(method = RequestMethod.GET, value = "getprimiterations")
    public  @ResponseBody int getPrimIterations(@RequestParam int callerId) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        int result = callerObj.getPrimIterations();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "setprimiterations")
    public @ResponseBody ResponseEntity<Void> setPrimIterations(@RequestParam int callerId, @RequestParam int iter) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        callerObj.setPrimIterations(iter);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getlongrun")
    public  @ResponseBody boolean getLongRun(@RequestParam int callerId) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        boolean result = callerObj.getLongRun();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "setlongrun")
    public @ResponseBody ResponseEntity<Void> setLongRun(@RequestParam int callerId, @RequestParam boolean longRun) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        callerObj.setLongRun(longRun);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "setpublishquotepricechange")
    public @ResponseBody ResponseEntity<Void> setPublishQuotePriceChange(@RequestParam int callerId, @RequestParam boolean publishQuotePriceChange) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        callerObj.setPublishQuotePriceChange(publishQuotePriceChange);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getpublishquotepricechange")
    public  @ResponseBody boolean getPublishQuotePriceChange(@RequestParam int callerId) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        boolean result = callerObj.getPublishQuotePriceChange();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "setmarketsummaryinterval")
    public @ResponseBody ResponseEntity<Void> setMarketSummaryInterval(@RequestParam int callerId, @RequestParam int seconds) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        callerObj.setMarketSummaryInterval(seconds);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getmarketsummaryinterval")
    public  @ResponseBody int getMarketSummaryInterval(@RequestParam int callerId) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        int result = callerObj.getMarketSummaryInterval();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "setruntimemode")
    public @ResponseBody ResponseEntity<Void> setRunTimeMode(@RequestParam int callerId, @RequestParam int value) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        callerObj.setRunTimeMode(value);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getruntimemode")
    public  @ResponseBody int getRunTimeMode(@RequestParam int callerId) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        int result = callerObj.getRunTimeMode();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "setorderprocessingmode")
    public @ResponseBody ResponseEntity<Void> setOrderProcessingMode(@RequestParam int callerId, @RequestParam int value) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        callerObj.setOrderProcessingMode(value);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getorderprocessingmode")
    public  @ResponseBody int getOrderProcessingMode(@RequestParam int callerId) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        int result = callerObj.getOrderProcessingMode();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "setwebinterface")
    public @ResponseBody ResponseEntity<Void> setWebInterface(@RequestParam int callerId, @RequestParam int value) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        callerObj.setWebInterface(value);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /*public static void setCachingType(int value) {
        cachingType = value;
    }

    public static int getCachingType() {
        return cachingType;
    }
	*/
    @RequestMapping(method = RequestMethod.POST, value = "setdisplayorderalerts")
    public @ResponseBody ResponseEntity<Void> setDisplayOrderAlerts(@RequestParam int callerId, @RequestParam boolean value) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        callerObj.setDisplayOrderAlerts(value);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getdisplayorderalerts")
    public  @ResponseBody boolean getDisplayOrderAlerts(@RequestParam int callerId) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        boolean result = callerObj.getDisplayOrderAlerts();
        return result;
    }

    /*
    public static void setDistributedMapCacheSize(int value) {
        distributedMapCacheSize = value;
    }

    public static int getDistributedMapCacheSize() {
        return distributedMapCacheSize;
    }*/
    @RequestMapping(method = RequestMethod.POST, value = "setpercentsenttowebsocket")
    public @ResponseBody ResponseEntity<Void> setPercentSentToWebsocket(@RequestParam int callerId, @RequestParam int value) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        callerObj.setPercentSentToWebsocket(value);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, value = "getpercentsenttowebsocket")
    public  @ResponseBody int getPercentSentToWebsocket(@RequestParam int callerId) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        int result = callerObj.getPercentSentToWebsocket();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "setuseremoteejbinterface")
    public @ResponseBody ResponseEntity<Void> setUseRemoteEJBInterface(@RequestParam int callerId, @RequestParam boolean value) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        callerObj.setUseRemoteEJBInterface(value);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST, value = "useremoteejbinterface")
    public  @ResponseBody boolean useRemoteEJBInterface(@RequestParam int callerId) {
        TradeConfigServer callerObj = (TradeConfigServer) idObjMap.get(callerId);
        boolean result = callerObj.useRemoteEJBInterface();
        return result;
    }

    private static int maxId = 0;

    public static HashMap<Integer, TradeConfigServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "tradeconfig")
    public  @ResponseBody int TradeConfig() {
        TradeConfigServer newServerObj = new TradeConfigServer();
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
}

