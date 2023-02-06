package com.ibm.websphere.samples.daytrader.web.jsf;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import com.ibm.websphere.samples.daytrader.util.FinancialUtils;

 class HoldingDataUnassigned_group_service 
 { 
 private double quantity;
private BigDecimal price;
private BigDecimal basis;
private BigDecimal gain;
private Integer holdingID;
private BigDecimal purchasePrice;
private String quoteID;
private BigDecimal marketValue;
private Date purchaseDate;

public String getQuoteID() {
    return quoteID;
}
public void setMarketValue(BigDecimal marketValue) {
    this.marketValue = marketValue;
}
public Date getPurchaseDate() {
    return purchaseDate;
}
public BigDecimal getMarketValue() {
    return marketValue;
}
public BigDecimal getPurchasePrice() {
    return purchasePrice;
}
public BigDecimal getBasis() {
    return basis;
}
public void setBasis(BigDecimal basis) {
    this.basis = basis;
}
public void setGain(BigDecimal gain) {
    this.gain = gain;
}
public void setPrice(BigDecimal price) {
    this.price = price;
}
public double getQuantity() {
    return quantity;
}
public void setQuoteID(String quoteID) {
    this.quoteID = quoteID;
}
public BigDecimal getPrice() {
    return price;
}
public void setPurchasePrice(BigDecimal purchasePrice) {
    this.purchasePrice = purchasePrice;
}
public void setPurchaseDate(Date purchaseDate) {
    this.purchaseDate = purchaseDate;
}
public void setHoldingID(Integer holdingID) {
    this.holdingID = holdingID;
}
public String getGainHTML() {
    return FinancialUtils.printGainHTML(gain);
}
public void setQuantity(double quantity) {
    this.quantity = quantity;
}
public Integer getHoldingID() {
    return holdingID;
}
public BigDecimal getGain() {
    return gain;
}
} 
