package com.ibm.websphere.samples.daytrader.web.jsf;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import com.ibm.websphere.samples.daytrader.util.FinancialUtils;

 class QuoteDataUnassigned_group_service 
 { 
 private BigDecimal open;
private double volume;
private BigDecimal price;
private String companyName;
private String range;
private BigDecimal gain;
private BigDecimal low;
private String symbol;
private BigDecimal gainPercent;
private BigDecimal high;
private double change;
public QuoteDataUnassigned_group_service(BigDecimal price, String symbol, BigDecimal open){ 
this.open = open;
this.change = price.subtract(open).setScale(2).doubleValue();
this.symbol = symbol;
this.price = price;
} 

public QuoteDataUnassigned_group_service(BigDecimal price, Double change, String symbol, BigDecimal low, Double volume, String companyName, BigDecimal open, BigDecimal high){ 
this.open = open;
this.range = high.toString() + "-" + low.toString();
this.symbol = symbol;
this.high = high;
this.low = low;
this.price = price;
this.companyName = companyName;
this.gainPercent = FinancialUtils.computeGainPercent(price, open).setScale(2);
this.gain = FinancialUtils.computeGain(price, open).setScale(2);
this.change = change;
this.volume = volume;
} 


public String getGainPercentHTML() {
    return FinancialUtils.printGainPercentHTML(gainPercent);
}
public void setGainPercent(BigDecimal gainPercent) {
    this.gainPercent = gainPercent.setScale(2);
}
public void setCompanyName(String companyName) {
    this.companyName = companyName;
}
public void setRange(String range) {
    this.range = range;
}
public BigDecimal getHigh() {
    return high;
}
public void setGain(BigDecimal gain) {
    this.gain = gain;
}
public void setSymbol(String symbol) {
    this.symbol = symbol;
}
public void setPrice(BigDecimal price) {
    this.price = price;
}
public String getChangeHTML() {
    String htmlString, arrow;
    if (change < 0.0) {
        htmlString = "<FONT color=\"#cc0000\">";
        arrow = "arrowdown.gif";
    } else {
        htmlString = "<FONT color=\"#009900\">";
        arrow = "arrowup.gif";
    }
    DecimalFormat df = new DecimalFormat("####0.00");
    htmlString += df.format(change) + "</FONT><IMG src=\"images/" + arrow + "\" width=\"10\" height=\"10\" border=\"0\"></IMG>";
    return htmlString;
}
public BigDecimal getGainPercent() {
    return gainPercent;
}
public BigDecimal getPrice() {
    return price;
}
public double getChange() {
    return change;
}
public String getGainHTML() {
    return FinancialUtils.printGainHTML(gain);
}
public void setLow(BigDecimal low) {
    this.low = low;
}
public BigDecimal getGain() {
    return gain;
}
public String getCompanyName() {
    return companyName;
}
public String getSymbol() {
    return symbol;
}
public double getVolume() {
    return volume;
}
public void setHigh(BigDecimal high) {
    this.high = high;
}
public void setVolume(double volume) {
    this.volume = volume;
}
public BigDecimal getOpen() {
    return open;
}
public void setChange(double change) {
    this.change = change;
}
public String getRange() {
    return range;
}
public void setOpen(BigDecimal open) {
    this.open = open;
}
public BigDecimal getLow() {
    return low;
}
} 
