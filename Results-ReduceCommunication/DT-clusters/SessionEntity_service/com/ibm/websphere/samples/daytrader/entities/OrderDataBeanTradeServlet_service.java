package com.ibm.websphere.samples.daytrader.entities;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import com.ibm.websphere.samples.daytrader.util.Log;
import com.ibm.websphere.samples.daytrader.util.TradeConfig;

 class OrderDataBeanTradeServlet_service 
 { 
 @Column(name = "ORDERTYPE")
private String orderType;
@Column(name = "ORDERSTATUS")
private String orderStatus;
@Column(name = "PRICE")
private BigDecimal price;
@Column(name = "OPENDATE")
@Temporal(TemporalType.TIMESTAMP)
private Date openDate;
@Transient
private String symbol;
@Column(name = "COMPLETIONDATE")
@Temporal(TemporalType.TIMESTAMP)
private Date completionDate;
@OneToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "HOLDING_HOLDINGID")
private HoldingDataBean holding;
@NotNull
@Column(name = "QUANTITY", nullable = false)
private double quantity;
@ManyToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "ACCOUNT_ACCOUNTID")
private AccountDataBean account;
@ManyToOne(fetch = FetchType.EAGER)
@JoinColumn(name = "QUOTE_SYMBOL")
private QuoteDataBean quote;
@Id
@TableGenerator(name = "orderIdGen", table = "KEYGENEJB", pkColumnName = "KEYNAME", valueColumnName = "KEYVAL", pkColumnValue = "order", allocationSize = 1000)
@GeneratedValue(strategy = GenerationType.TABLE, generator = "orderIdGen")
@Column(name = "ORDERID", nullable = false)
private Integer orderID;
@Column(name = "ORDERFEE")
private BigDecimal orderFee;
public OrderDataBeanTradeServlet_service(){ 
} 

public OrderDataBeanTradeServlet_service(Date openDate, Date completionDate, String orderType, BigDecimal price, BigDecimal orderFee, double quantity, String symbol, Integer orderID, String orderStatus){ 
setOrderID(orderID);
setCompletionDate(completionDate);
setOrderStatus(orderStatus);
setOrderType(orderType);
setOrderFee(orderFee);
setOpenDate(openDate);
setSymbol(symbol);
setPrice(price);
setQuantity(quantity);
} 

public OrderDataBeanTradeServlet_service(Date openDate, Date completionDate, String orderType, BigDecimal price, BigDecimal orderFee, double quantity, QuoteDataBean quote, HoldingDataBean holding, String orderStatus, AccountDataBean account){ 
setCompletionDate(completionDate);
setAccount(account);
setOrderStatus(orderStatus);
setOrderType(orderType);
setQuote(quote);
setOrderFee(orderFee);
setOpenDate(openDate);
setPrice(price);
setHolding(holding);
setQuantity(quantity);
} 


public void setOrderStatus(String orderStatus) {
    this.orderStatus = orderStatus;
}
public BigDecimal getPrice() {
    return price;
}
public String getSymbol() {
    if (quote != null) {
        return quote.getSymbol();
    }
    return symbol;
}
@Override
public int hashCode() {
    int hash = 0;
    hash += (this.orderID != null ? this.orderID.hashCode() : 0);
    return hash;
}
public boolean isOpen() {
    String orderStatus = getOrderStatus();
    if ((orderStatus.compareToIgnoreCase("open") == 0) || (orderStatus.compareToIgnoreCase("processing") == 0)) {
        return true;
    }
    return false;
}
@Override
public boolean equals(Object object) {
    if (!(object instanceof OrderDataBean)) {
        return false;
    }
    OrderDataBean other = (OrderDataBean) object;
    if (this.orderID != other.orderID && (this.orderID == null || !this.orderID.equals(other.orderID))) {
        return false;
    }
    return true;
}
public void setQuantity(double quantity) {
    this.quantity = quantity;
}
public String getOrderStatus() {
    return orderStatus;
}
public void setHolding(HoldingDataBean holding) {
    this.holding = holding;
}
public QuoteDataBean getQuote() {
    return quote;
}
public boolean isCompleted() {
    String orderStatus = getOrderStatus();
    if ((orderStatus.compareToIgnoreCase("completed") == 0) || (orderStatus.compareToIgnoreCase("alertcompleted") == 0) || (orderStatus.compareToIgnoreCase("cancelled") == 0)) {
        return true;
    }
    return false;
}
public boolean isSell() {
    String orderType = getOrderType();
    if (orderType.compareToIgnoreCase("sell") == 0) {
        return true;
    }
    return false;
}
public boolean isCancelled() {
    String orderStatus = getOrderStatus();
    if (orderStatus.compareToIgnoreCase("cancelled") == 0) {
        return true;
    }
    return false;
}
public void cancel() {
    setOrderStatus("cancelled");
}
public void print() {
    Log.log(this.toString());
}
public Integer getOrderID() {
    return orderID;
}
public void setOrderFee(BigDecimal orderFee) {
    this.orderFee = orderFee;
}
public double getQuantity() {
    return quantity;
}
public Date getCompletionDate() {
    return completionDate;
}
public void setOrderID(Integer orderID) {
    this.orderID = orderID;
}
public void setQuote(QuoteDataBean quote) {
    this.quote = quote;
}
public String toHTML() {
    return "<BR>Order <B>" + getOrderID() + "</B>" + "<LI>      orderType: " + getOrderType() + "</LI>" + "<LI>    orderStatus: " + getOrderStatus() + "</LI>" + "<LI>       openDate: " + getOpenDate() + "</LI>" + "<LI> completionDate: " + getCompletionDate() + "</LI>" + "<LI>       quantity: " + getQuantity() + "</LI>" + "<LI>          price: " + getPrice() + "</LI>" + "<LI>       orderFee: " + getOrderFee() + "</LI>" + "<LI>         symbol: " + getSymbol() + "</LI>";
}
public void setPrice(BigDecimal price) {
    this.price = price;
}
public String getOrderType() {
    return orderType;
}
public Date getOpenDate() {
    return openDate;
}
public void setSymbol(String symbol) {
    this.symbol = symbol;
}
public static OrderDataBean getRandomInstance() {
    return new OrderDataBean(new Integer(TradeConfig.rndInt(100000)), TradeConfig.rndBoolean() ? "buy" : "sell", "open", new java.util.Date(TradeConfig.rndInt(Integer.MAX_VALUE)), new java.util.Date(TradeConfig.rndInt(Integer.MAX_VALUE)), TradeConfig.rndQuantity(), TradeConfig.rndBigDecimal(1000.0f), TradeConfig.rndBigDecimal(1000.0f), TradeConfig.rndSymbol());
}
public BigDecimal getOrderFee() {
    return orderFee;
}
public AccountDataBean getAccount() {
    return account;
}
@Override
public String toString() {
    return "Order " + getOrderID() + "\n\t      orderType: " + getOrderType() + "\n\t    orderStatus: " + getOrderStatus() + "\n\t       openDate: " + getOpenDate() + "\n\t completionDate: " + getCompletionDate() + "\n\t       quantity: " + getQuantity() + "\n\t          price: " + getPrice() + "\n\t       orderFee: " + getOrderFee() + "\n\t         symbol: " + getSymbol();
}
public void setOrderType(String orderType) {
    this.orderType = orderType;
}
public boolean isBuy() {
    String orderType = getOrderType();
    if (orderType.compareToIgnoreCase("buy") == 0) {
        return true;
    }
    return false;
}
public HoldingDataBean getHolding() {
    return holding;
}
public void setCompletionDate(Date completionDate) {
    this.completionDate = completionDate;
}
public void setAccount(AccountDataBean account) {
    this.account = account;
}
public void setOpenDate(Date openDate) {
    this.openDate = openDate;
}
} 
