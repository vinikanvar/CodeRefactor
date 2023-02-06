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

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.component.html.HtmlDataTable;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import com.ibm.websphere.samples.daytrader.TradeAction;
import com.ibm.websphere.samples.daytrader.entities.OrderDataBean;
import com.ibm.websphere.samples.daytrader.entities.QuoteDataBean;
import com.ibm.websphere.samples.daytrader.util.Log;
import com.ibm.websphere.samples.daytrader.util.TradeConfig;
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

@Named("quotedata")
@RequestScoped
@Controller
@RequestMapping("/ms4/quotejsf/")
public class QuoteJSFWrapper {

    @RequestMapping(method = RequestMethod.GET, value = "getquotesbysymbols")
    public @ResponseBody String getQuotesBySymbols(@RequestParam int callerId) {
        QuoteJSFServer callerObj = (QuoteJSFServer) idObjMap.get(callerId);
        String result = callerObj.getQuotesBySymbols();
        return result;
    }

    @RequestMapping(method = RequestMethod.POST, value = "quotejsf_buy")
    public @ResponseBody String buy(@RequestParam int callerId) {
        QuoteJSFServer callerObj = (QuoteJSFServer) idObjMap.get(callerId);
        String result = callerObj.buy();
        return result;
    }

    private static int maxId = 0;

    public static HashMap<Integer, QuoteJSFServer> idObjMap = new HashMap<>();

    @RequestMapping(method = RequestMethod.POST, value = "quotejsf")
    public  @ResponseBody int QuoteJSF() {
        QuoteJSFServer newServerObj = new QuoteJSFServer();
        idObjMap.put(++maxId, newServerObj);
        return maxId;
    }
}

