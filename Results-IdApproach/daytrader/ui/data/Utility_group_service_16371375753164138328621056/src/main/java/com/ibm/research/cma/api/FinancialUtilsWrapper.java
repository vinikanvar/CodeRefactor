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
import java.util.Collection;
import java.util.Iterator;
import com.ibm.websphere.samples.daytrader.entities.HoldingDataBean;
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

@Controller
@RequestMapping("/ms7/financialutils/")
public class FinancialUtilsWrapper {

    @RequestMapping(method = RequestMethod.POST, value = "computegain")
    public @ResponseBody BigDecimal computeGain(@RequestParam int callerId, @RequestParam int currentBalanceId, @RequestParam int openBalanceId) {
        FinancialUtilsServer callerObj = (FinancialUtilsServer) idObjMap.get(callerId);
        BigDecimal result = callerObj.computeGain(currentBalance, openBalance);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "computegainpercent")
    public @ResponseBody BigDecimal computeGainPercent(@RequestParam int callerId, @RequestParam int currentBalanceId, @RequestParam int openBalanceId) {
        FinancialUtilsServer callerObj = (FinancialUtilsServer) idObjMap.get(callerId);
        BigDecimal result = callerObj.computeGainPercent(currentBalance, openBalance);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "computeholdingstotal")
    public @ResponseBody BigDecimal computeHoldingsTotal(@RequestParam int callerId, @RequestParam int holdingDataBeansId) {
        FinancialUtilsServer callerObj = (FinancialUtilsServer) idObjMap.get(callerId);
        BigDecimal result = callerObj.computeHoldingsTotal((Collection<?>) new Object());
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "printgainhtml")
    public @ResponseBody String printGainHTML(@RequestParam int callerId, @RequestParam int gainId) {
        FinancialUtilsServer callerObj = (FinancialUtilsServer) idObjMap.get(callerId);
        String result = callerObj.printGainHTML(gain);
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "printgainpercenthtml")
    public @ResponseBody String printGainPercentHTML(@RequestParam int callerId, @RequestParam int gainId) {
        FinancialUtilsServer callerObj = (FinancialUtilsServer) idObjMap.get(callerId);
        String result = callerObj.printGainPercentHTML(gain);
        return result;
    }

    private static int maxId = 0;

    public static HashMap<Integer, FinancialUtilsServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "financialutils")
    public  @ResponseBody int FinancialUtils() {
        FinancialUtilsServer newServerObj = new FinancialUtilsServer();
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
}

