package com.ibm.websphere.samples.daytrader.direct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import com.ibm.websphere.samples.daytrader.util.KeyBlock;
import com.ibm.websphere.samples.daytrader.util.Log;
import com.ibm.websphere.samples.daytrader.util.TradeConfig;

 class KeySequenceDirectMDBQueue_service 
 { 
 private static final String getKeyForUpdateSQL = "select * from keygenejb kg where kg.keyname = ?  for update";
private static final String createKeySQL = "insert into keygenejb " + "( keyname, keyval ) " + "VALUES (  ?  ,  ? )";
private static final String updateKeyValueSQL = "update keygenejb set keyval = ? " + "where keyname = ?";

private static Collection<?> allocNewBlock(Connection conn, String keyName, boolean inSession, boolean inGlobalTxn) throws Exception {
    try {
        if (inGlobalTxn == false && !inSession) {
            conn.commit();
        }
        PreparedStatement stmt = conn.prepareStatement(getKeyForUpdateSQL);
        stmt.setString(1, keyName);
        ResultSet rs = stmt.executeQuery();
        if (!rs.next()) {
            PreparedStatement stmt2 = conn.prepareStatement(createKeySQL);
            int keyVal = 0;
            stmt2.setString(1, keyName);
            stmt2.setInt(2, keyVal);
            stmt2.executeUpdate();
            stmt2.close();
            stmt.close();
            stmt = conn.prepareStatement(getKeyForUpdateSQL);
            stmt.setString(1, keyName);
            rs = stmt.executeQuery();
            rs.next();
        }
        int keyVal = rs.getInt("keyval");
        stmt.close();
        stmt = conn.prepareStatement(updateKeyValueSQL);
        stmt.setInt(1, keyVal + TradeConfig.KEYBLOCKSIZE);
        stmt.setString(2, keyName);
        stmt.executeUpdate();
        stmt.close();
        Collection<?> block = new KeyBlock(keyVal, keyVal + TradeConfig.KEYBLOCKSIZE - 1);
        keyMap.put(keyName, block);
        if (inGlobalTxn == false && !inSession) {
            conn.commit();
        }
        return block;
    } catch (Exception e) {
        String error = "KeySequenceDirect:allocNewBlock - failure to allocate new block of keys for Entity type: " + keyName;
        Log.error(e, error);
        throw new Exception(error + e.toString());
    }
}
public static synchronized Integer getNextID(Connection conn, String keyName, boolean inSession, boolean inGlobalTxn) throws Exception {
    Integer nextID = null;
    if (keyMap.containsKey(keyName) == false) {
        allocNewBlock(conn, keyName, inSession, inGlobalTxn);
    }
    Collection<?> block = keyMap.get(keyName);
    Iterator<?> ids = block.iterator();
    if (ids.hasNext() == false) {
        ids = allocNewBlock(conn, keyName, inSession, inGlobalTxn).iterator();
    }
    nextID = (Integer) ids.next();
    if (Log.doTrace()) {
        Log.trace("KeySequenceDirect:getNextID inSession(" + inSession + ") - return new PK ID for Entity type: " + keyName + " ID=" + nextID);
    }
    return nextID;
}
} 
