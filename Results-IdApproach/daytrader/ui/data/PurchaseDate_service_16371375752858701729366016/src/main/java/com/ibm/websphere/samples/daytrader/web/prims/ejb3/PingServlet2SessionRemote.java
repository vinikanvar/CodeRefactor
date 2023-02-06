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
package com.ibm.websphere.samples.daytrader.web.prims.ejb3;

import java.io.IOException;
import javax.ejb.EJB;
import javax.naming.InitialContext;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.ibm.websphere.samples.daytrader.ejb3.TradeSLSBRemote;
import com.ibm.websphere.samples.daytrader.util.Log;
import com.ibm.websphere.samples.daytrader.util.TradeConfig;
import com.ibm.research.cma.api.MicroserviceApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

/**
 * This primitive is designed to run inside the TradeApplication and relies upon
 * the {@link trade_client.TradeConfig} class to set configuration parameters.
 * PingServlet2SessionEJB tests key functionality of a servlet call to a
 * stateless SessionEJB. This servlet makes use of the Stateless Session EJB
 * {@link trade.Trade} by calling calculateInvestmentReturn with three random
 * numbers.
 */
@WebServlet(name = "ejb3.PingServlet2SessionRemote", urlPatterns = { "/ejb3/PingServlet2SessionRemote" })
public class PingServlet2SessionRemote extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) {
        String uri = MicroserviceApplication.projectUri + "/ms1/pingservlet2sessionremote/pingservlet2sessionremote_dopost?callerId=" + this.id + "&reqId=req.id&resId=res.id";
        ResponseEntity<Void> resultTemp = new RestTemplate().getForEntity(uri, void.class);
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) {
        String uri = MicroserviceApplication.projectUri + "/ms1/pingservlet2sessionremote/pingservlet2sessionremote_doget?callerId=" + this.id + "&reqId=req.id&resId=res.id";
        ResponseEntity<Void> resultTemp = new RestTemplate().getForEntity(uri, void.class);
    }

    @Override
    public String getServletInfo() {
        return (String) null;
    }

    @Override
    public void init(ServletConfig config) {
        String uri = MicroserviceApplication.projectUri + "/ms1/pingservlet2sessionremote/pingservlet2sessionremote_init?callerId=" + this.id + "&configId=config.id";
        ResponseEntity<Void> resultTemp = new RestTemplate().getForEntity(uri, void.class);
    }

    public int id = 0;

    public static PingServlet2SessionRemote getObject(int id) {
        PingServlet2SessionRemote obj = (PingServlet2SessionRemote) new Object();
        obj.id = id;
        return obj;
    }
}

