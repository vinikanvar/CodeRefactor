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
import java.text.DecimalFormat;
import com.ibm.websphere.samples.daytrader.util.FinancialUtils;
import com.ibm.websphere.samples.daytrader.web.jsf.*;
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
@RequestMapping("/ms1/quotedata/")
public class QuoteDataWrapper {

    @RequestMapping(method = RequestMethod.GET, value = "quotedata_getsymbol")
    public @ResponseBody String getSymbol(@RequestParam int callerId) {
        QuoteDataServer callerObj = (QuoteDataServer) idObjMap.get(callerId);
        String result = callerObj.getSymbol();
        return result;
    }

    private static int maxId = 0;

    public static HashMap<Integer, QuoteDataServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "quotedata_bigdecimal_bigdecimal_string")
    public  @ResponseBody int QuoteData(@RequestParam int priceId, @RequestParam int openId, @RequestParam String symbol) {
        QuoteDataServer newServerObj = new QuoteDataServer(price, open, symbol);
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }

    @RequestMapping(method = RequestMethod.POST, value = "quotedata")
    public  @ResponseBody int QuoteData(@RequestParam int openId, @RequestParam int priceId, @RequestParam String symbol, @RequestParam int highId, @RequestParam int lowId, @RequestParam String companyName, @RequestParam Double volume, @RequestParam Double change) {
        QuoteDataServer newServerObj = new QuoteDataServer(open, price, symbol, high, low, companyName, volume, change);
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
}

