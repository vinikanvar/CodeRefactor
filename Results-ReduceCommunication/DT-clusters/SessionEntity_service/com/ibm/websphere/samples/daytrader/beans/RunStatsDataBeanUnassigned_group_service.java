package com.ibm.websphere.samples.daytrader.beans;
import java.io.Serializable;

 class RunStatsDataBeanUnassigned_group_service 
 { 
 private int holdingCount;
private int openOrderCount;
private int deletedOrderCount;
private int newUserCount;
private int tradeStockCount;
private int buyOrderCount;
private int orderCount;
private int cancelledOrderCount;
private int tradeUserCount;
private int sumLogoutCount;
private int sumLoginCount;
private int sellOrderCount;
public RunStatsDataBeanUnassigned_group_service(){ 
} 


public int getOrderCount() {
    return orderCount;
}
public void setOrderCount(int orderCount) {
    this.orderCount = orderCount;
}
public int getNewUserCount() {
    return newUserCount;
}
public int getHoldingCount() {
    return holdingCount;
}
public int getSumLogoutCount() {
    return sumLogoutCount;
}
public void setSumLoginCount(int sumLoginCount) {
    this.sumLoginCount = sumLoginCount;
}
public int getBuyOrderCount() {
    return buyOrderCount;
}
@Override
public String toString() {
    return "\n\tRunStatsData for reset at " + new java.util.Date() + "\n\t\t      tradeUserCount: " + getTradeUserCount() + "\n\t\t        newUserCount: " + getNewUserCount() + "\n\t\t       sumLoginCount: " + getSumLoginCount() + "\n\t\t      sumLogoutCount: " + getSumLogoutCount() + "\n\t\t        holdingCount: " + getHoldingCount() + "\n\t\t          orderCount: " + getOrderCount() + "\n\t\t       buyOrderCount: " + getBuyOrderCount() + "\n\t\t      sellOrderCount: " + getSellOrderCount() + "\n\t\t cancelledOrderCount: " + getCancelledOrderCount() + "\n\t\t      openOrderCount: " + getOpenOrderCount() + "\n\t\t   deletedOrderCount: " + getDeletedOrderCount();
}
public int getSumLoginCount() {
    return sumLoginCount;
}
public void setOpenOrderCount(int openOrderCount) {
    this.openOrderCount = openOrderCount;
}
public int getSellOrderCount() {
    return sellOrderCount;
}
public void setDeletedOrderCount(int deletedOrderCount) {
    this.deletedOrderCount = deletedOrderCount;
}
public int getOpenOrderCount() {
    return openOrderCount;
}
public void setSumLogoutCount(int sumLogoutCount) {
    this.sumLogoutCount = sumLogoutCount;
}
public void setBuyOrderCount(int buyOrderCount) {
    this.buyOrderCount = buyOrderCount;
}
public int getDeletedOrderCount() {
    return deletedOrderCount;
}
public void setCancelledOrderCount(int cancelledOrderCount) {
    this.cancelledOrderCount = cancelledOrderCount;
}
public void setNewUserCount(int newUserCount) {
    this.newUserCount = newUserCount;
}
public void setTradeUserCount(int tradeUserCount) {
    this.tradeUserCount = tradeUserCount;
}
public void setSellOrderCount(int sellOrderCount) {
    this.sellOrderCount = sellOrderCount;
}
public int getTradeStockCount() {
    return tradeStockCount;
}
public void setTradeStockCount(int tradeStockCount) {
    this.tradeStockCount = tradeStockCount;
}
public void setHoldingCount(int holdingCount) {
    this.holdingCount = holdingCount;
}
public int getTradeUserCount() {
    return tradeUserCount;
}
public int getCancelledOrderCount() {
    return cancelledOrderCount;
}
} 
