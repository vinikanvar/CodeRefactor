package com.ibm.websphere.samples.daytrader.util;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

 class TradeConfigUtility_group_service 
 { 
 private static int primIterations = 1;
public static int runTimeMode = EJB3;
private static boolean useRemoteEJBInterface = false;
private static int marketSummaryInterval = 20;

public static void setPrimIterations(int iter) {
    primIterations = iter;
}
public static boolean useRemoteEJBInterface() {
    return useRemoteEJBInterface;
}
public static void setMarketSummaryInterval(int seconds) {
    TradeConfig.marketSummaryInterval = seconds;
}
public static void setUseRemoteEJBInterface(boolean value) {
    useRemoteEJBInterface = value;
}
public static int getMarketSummaryInterval() {
    return TradeConfig.marketSummaryInterval;
}
public static int getPrimIterations() {
    return primIterations;
}
public static void setRunTimeMode(int value) {
    runTimeMode = value;
}
} 
