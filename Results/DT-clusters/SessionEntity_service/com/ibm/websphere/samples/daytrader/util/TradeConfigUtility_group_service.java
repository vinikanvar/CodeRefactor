package com.ibm.websphere.samples.daytrader.util;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

 class TradeConfigUtility_group_service 
 { 
 private static final BigDecimal orderFee = new BigDecimal("24.95");
private static final BigDecimal cashFee = new BigDecimal("0.0");
private static boolean updateQuotePrices = true;

public static boolean getUpdateQuotePrices() {
    return updateQuotePrices;
}
public static void setUpdateQuotePrices(boolean updateQuotePrices) {
    TradeConfig.updateQuotePrices = updateQuotePrices;
}
public static BigDecimal getOrderFee(String orderType) {
    if ((orderType.compareToIgnoreCase("BUY") == 0) || (orderType.compareToIgnoreCase("SELL") == 0)) {
        return orderFee;
    }
    return cashFee;
}
} 
