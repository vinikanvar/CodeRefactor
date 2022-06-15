package com.ibm.websphere.samples.daytrader.util;
import java.util.Collection;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;

 class LogUtility_group_service 
 { 
 
public static void setActionTrace(boolean traceValue) {
    TradeConfig.setActionTrace(traceValue);
}
public static void setTrace(boolean traceValue) {
    TradeConfig.setTrace(traceValue);
}
} 
