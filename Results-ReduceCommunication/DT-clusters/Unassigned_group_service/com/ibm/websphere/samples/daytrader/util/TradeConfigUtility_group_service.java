package com.ibm.websphere.samples.daytrader.util;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Random;

 class TradeConfigUtility_group_service 
 { 
 public static final String newUserPrefix = "ru:";
private static int count = 0;
private static String hostName = null;

public static String rndBalance() {
    return "1000000";
}
private static String getHostname() {
    try {
        if (hostName == null) {
            hostName = java.net.InetAddress.getLocalHost().getHostName();
            try {
                hostName = hostName.substring(0, hostName.indexOf('.'));
            } catch (Exception e) {
            }
        }
    } catch (Exception e) {
        Log.error("Exception getting local host name using 'localhost' - ", e);
        hostName = "localhost";
    }
    return hostName;
}
public static synchronized String rndNewUserID() {
    return newUserPrefix + getHostname() + System.currentTimeMillis() + count++;
}
} 
