package com.ibm.websphere.samples.daytrader.web;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.ibm.websphere.samples.daytrader.TradeAction;
import com.ibm.websphere.samples.daytrader.TradeServices;
import com.ibm.websphere.samples.daytrader.entities.AccountDataBean;
import com.ibm.websphere.samples.daytrader.entities.AccountProfileDataBean;
import com.ibm.websphere.samples.daytrader.entities.HoldingDataBean;
import com.ibm.websphere.samples.daytrader.entities.OrderDataBean;
import com.ibm.websphere.samples.daytrader.entities.QuoteDataBean;
import com.ibm.websphere.samples.daytrader.util.Log;
import com.ibm.websphere.samples.daytrader.util.TradeConfig;

 class TradeServletActionTradeServlet_service 
 { 
 package_private TradeServletActionTradeServlet_service(){ 
} 


private void requestDispatch(ServletContext ctx, HttpServletRequest req, HttpServletResponse resp, String userID, String page) throws ServletException, IOException {
    ctx.getRequestDispatcher(page).include(req, resp);
}
void doMarketSummary(ServletContext ctx, HttpServletRequest req, HttpServletResponse resp, String userID) throws ServletException, IOException {
    req.setAttribute("results", "test");
    requestDispatch(ctx, req, resp, userID, TradeConfig.getPage(TradeConfig.MARKET_SUMMARY_PAGE));
}
void doLogout(ServletContext ctx, HttpServletRequest req, HttpServletResponse resp, String userID) throws ServletException, IOException {
    String results = "";
    try {
        tAction.logout(userID);
    } catch (java.lang.IllegalArgumentException e) {
        req.setAttribute("results", results + "illegal argument:" + e.getMessage());
        Log.error(e, "TradeServletAction.doLogout(...)", "illegal argument, information should be in exception string", "treating this as a user error and forwarding on to a new page");
    } catch (Exception e) {
        Log.error(e, "TradeServletAction.doLogout(...):", "Error logging out" + userID, "fowarding to an error page");
        throw new ServletException("TradeServletAction.doLogout(...)" + "exception logging out user " + userID, e);
    }
    HttpSession session = req.getSession();
    if (session != null) {
        session.invalidate();
    }
    req.logout();
    Object o = req.getAttribute("TSS-RecreateSessionInLogout");
    if (o != null && ((Boolean) o).equals(Boolean.TRUE)) {
        session = req.getSession(true);
    }
    requestDispatch(ctx, req, resp, userID, TradeConfig.getPage(TradeConfig.WELCOME_PAGE));
}
void doWelcome(ServletContext ctx, HttpServletRequest req, HttpServletResponse resp, String status) throws ServletException, IOException {
    req.setAttribute("results", status);
    requestDispatch(ctx, req, resp, null, TradeConfig.getPage(TradeConfig.WELCOME_PAGE));
}
void doRegister(ServletContext ctx, HttpServletRequest req, HttpServletResponse resp, String userID, String passwd, String cpasswd, String fullname, String ccn, String openBalanceString, String email, String address) throws ServletException, IOException {
    String results = "";
    try {
        if ((passwd.equals(cpasswd)) && (passwd.length() >= 1)) {
            AccountDataBean accountData = tAction.register(userID, passwd, fullname, address, email, ccn, new BigDecimal(openBalanceString));
            if (accountData == null) {
                results = "Registration operation failed;";
                System.out.println(results);
                req.setAttribute("results", results);
                requestDispatch(ctx, req, resp, userID, TradeConfig.getPage(TradeConfig.REGISTER_PAGE));
            } else {
                doLogin(ctx, req, resp, userID, passwd);
                results = "Registration operation succeeded;  Account " + accountData.getAccountID() + " has been created.";
                req.setAttribute("results", results);
            }
        } else {
            results = "Registration operation failed, your passwords did not match";
            System.out.println(results);
            req.setAttribute("results", results);
            requestDispatch(ctx, req, resp, userID, TradeConfig.getPage(TradeConfig.REGISTER_PAGE));
        }
    } catch (Exception e) {
        throw new ServletException("TradeServletAction.doRegister(...)" + " exception user =" + userID, e);
    }
}
void doPortfolio(ServletContext ctx, HttpServletRequest req, HttpServletResponse resp, String userID, String results) throws ServletException, IOException {
    try {
        Collection<QuoteDataBean> quoteDataBeans = new ArrayList<QuoteDataBean>();
        Collection<?> holdingDataBeans = tAction.getHoldings(userID);
        if (holdingDataBeans.size() > 0) {
            Iterator<?> it = holdingDataBeans.iterator();
            while (it.hasNext()) {
                HoldingDataBean holdingData = (HoldingDataBean) it.next();
                QuoteDataBean quoteData = tAction.getQuote(holdingData.getQuoteID());
                quoteDataBeans.add(quoteData);
            }
        } else {
            results = results + ".  Your portfolio is empty.";
        }
        req.setAttribute("results", results);
        req.setAttribute("holdingDataBeans", holdingDataBeans);
        req.setAttribute("quoteDataBeans", quoteDataBeans);
        requestDispatch(ctx, req, resp, userID, TradeConfig.getPage(TradeConfig.PORTFOLIO_PAGE));
    } catch (java.lang.IllegalArgumentException e) {
        req.setAttribute("results", results + "illegal argument:" + e.getMessage());
        requestDispatch(ctx, req, resp, userID, TradeConfig.getPage(TradeConfig.PORTFOLIO_PAGE));
        Log.error(e, "TradeServletAction.doPortfolio(...)", "illegal argument, information should be in exception string", "user error");
    } catch (Exception e) {
        throw new ServletException("TradeServletAction.doPortfolio(...)" + " exception user =" + userID, e);
    }
}
void doAccountUpdate(ServletContext ctx, HttpServletRequest req, HttpServletResponse resp, String userID, String password, String cpassword, String fullName, String address, String creditcard, String email) throws javax.servlet.ServletException, java.io.IOException {
    String results = "";
    boolean doUpdate = true;
    if (password.equals(cpassword) == false) {
        results = "Update profile error: passwords do not match";
        doUpdate = false;
    } else if (password.length() <= 0 || fullName.length() <= 0 || address.length() <= 0 || creditcard.length() <= 0 || email.length() <= 0) {
        results = "Update profile error: please fill in all profile information fields";
        doUpdate = false;
    }
    AccountProfileDataBean accountProfileData = new AccountProfileDataBean(userID, password, fullName, address, email, creditcard);
    try {
        if (doUpdate) {
            accountProfileData = tAction.updateAccountProfile(accountProfileData);
            results = "Account profile update successful";
        }
    } catch (java.lang.IllegalArgumentException e) {
        req.setAttribute("results", results + "invalid argument, check userID is correct, and the database is populated" + userID);
        Log.error(e, "TradeServletAction.doAccount(...)", "illegal argument, information should be in exception string", "treating this as a user error and forwarding on to a new page");
    } catch (Exception e) {
        throw new ServletException("TradeServletAction.doAccountUpdate(...)" + " exception user =" + userID, e);
    }
    doAccount(ctx, req, resp, userID, results);
}
void doAccount(ServletContext ctx, HttpServletRequest req, HttpServletResponse resp, String userID, String results) throws javax.servlet.ServletException, java.io.IOException {
    try {
        AccountDataBean accountData = tAction.getAccountData(userID);
        AccountProfileDataBean accountProfileData = tAction.getAccountProfileData(userID);
        Collection<?> orderDataBeans = (TradeConfig.getLongRun() ? new ArrayList<Object>() : (Collection<?>) tAction.getOrders(userID));
        req.setAttribute("accountData", accountData);
        req.setAttribute("accountProfileData", accountProfileData);
        req.setAttribute("orderDataBeans", orderDataBeans);
        req.setAttribute("results", results);
        requestDispatch(ctx, req, resp, userID, TradeConfig.getPage(TradeConfig.ACCOUNT_PAGE));
    } catch (java.lang.IllegalArgumentException e) {
        req.setAttribute("results", results + "could not find account for userID = " + userID);
        requestDispatch(ctx, req, resp, userID, TradeConfig.getPage(TradeConfig.HOME_PAGE));
        Log.error("TradeServletAction.doAccount(...)", "illegal argument, information should be in exception string", e);
    } catch (Exception e) {
        throw new ServletException("TradeServletAction.doAccount(...)" + " exception user =" + userID, e);
    }
}
void doBuy(ServletContext ctx, HttpServletRequest req, HttpServletResponse resp, String userID, String symbol, String quantity) throws ServletException, IOException {
    String results = "";
    try {
        OrderDataBean orderData = tAction.buy(userID, symbol, new Double(quantity).doubleValue(), TradeConfig.orderProcessingMode);
        req.setAttribute("orderData", orderData);
        req.setAttribute("results", results);
    } catch (java.lang.IllegalArgumentException e) {
        req.setAttribute("results", results + "illegal argument:");
        requestDispatch(ctx, req, resp, userID, TradeConfig.getPage(TradeConfig.HOME_PAGE));
        Log.error(e, "TradeServletAction.doBuy(...)", "illegal argument. userID = " + userID, "symbol = " + symbol);
    } catch (Exception e) {
        throw new ServletException("TradeServletAction.buy(...)" + " exception buying stock " + symbol + " for user " + userID, e);
    }
    requestDispatch(ctx, req, resp, userID, TradeConfig.getPage(TradeConfig.ORDER_PAGE));
}
void doSell(ServletContext ctx, HttpServletRequest req, HttpServletResponse resp, String userID, Integer holdingID) throws ServletException, IOException {
    String results = "";
    try {
        OrderDataBean orderData = tAction.sell(userID, holdingID, TradeConfig.orderProcessingMode);
        req.setAttribute("orderData", orderData);
        req.setAttribute("results", results);
    } catch (java.lang.IllegalArgumentException e) {
        Log.error(e, "TradeServletAction.doSell(...)", "illegal argument, information should be in exception string", "user error");
    } catch (Exception e) {
        throw new ServletException("TradeServletAction.doSell(...)" + " exception selling holding " + holdingID + " for user =" + userID, e);
    }
    requestDispatch(ctx, req, resp, userID, TradeConfig.getPage(TradeConfig.ORDER_PAGE));
}
void doLogin(ServletContext ctx, HttpServletRequest req, HttpServletResponse resp, String userID, String passwd) throws javax.servlet.ServletException, java.io.IOException {
    String results = "";
    try {
        AccountDataBean accountData = tAction.login(userID, passwd);
        if (accountData != null) {
            HttpSession session = req.getSession(true);
            session.setAttribute("uidBean", userID);
            session.setAttribute("sessionCreationDate", new java.util.Date());
            results = "Ready to Trade";
            doHome(ctx, req, resp, userID, results);
            return;
        } else {
            req.setAttribute("results", results + "\nCould not find account for + " + userID);
            Log.log("TradeServletAction.doLogin(...)", "Error finding account for user " + userID + "", "user entered a bad username or the database is not populated");
        }
    } catch (java.lang.IllegalArgumentException e) {
        req.setAttribute("results", results + "illegal argument:" + e.getMessage());
        Log.error(e, "TradeServletAction.doLogin(...)", "illegal argument, information should be in exception string", "treating this as a user error and forwarding on to a new page");
    } catch (Exception e) {
        throw new ServletException("TradeServletAction.doLogin(...)" + "Exception logging in user " + userID + "with password" + passwd, e);
    }
    requestDispatch(ctx, req, resp, userID, TradeConfig.getPage(TradeConfig.WELCOME_PAGE));
}
void doHome(ServletContext ctx, HttpServletRequest req, HttpServletResponse resp, String userID, String results) throws javax.servlet.ServletException, java.io.IOException {
    try {
        AccountDataBean accountData = tAction.getAccountData(userID);
        Collection<?> holdingDataBeans = tAction.getHoldings(userID);
        req.setAttribute("accountData", accountData);
        req.setAttribute("holdingDataBeans", holdingDataBeans);
        req.setAttribute("results", results);
    } catch (java.lang.IllegalArgumentException e) {
        req.setAttribute("results", results + "check userID = " + userID + " and that the database is populated");
        requestDispatch(ctx, req, resp, userID, TradeConfig.getPage(TradeConfig.HOME_PAGE));
        Log.error("TradeServletAction.doHome(...)" + "illegal argument, information should be in exception string" + "treating this as a user error and forwarding on to a new page", e);
    } catch (javax.ejb.FinderException e) {
        req.setAttribute("results", results + "\nCould not find account for + " + userID);
        Log.error("TradeServletAction.doHome(...)" + "Error finding account for user " + userID + "treating this as a user error and forwarding on to a new page", e);
    } catch (Exception e) {
        throw new ServletException("TradeServletAction.doHome(...)" + " exception user =" + userID, e);
    }
    requestDispatch(ctx, req, resp, userID, TradeConfig.getPage(TradeConfig.HOME_PAGE));
}
void doQuotes(ServletContext ctx, HttpServletRequest req, HttpServletResponse resp, String userID, String symbols) throws ServletException, IOException {
    requestDispatch(ctx, req, resp, userID, TradeConfig.getPage(TradeConfig.QUOTE_PAGE));
}
} 
