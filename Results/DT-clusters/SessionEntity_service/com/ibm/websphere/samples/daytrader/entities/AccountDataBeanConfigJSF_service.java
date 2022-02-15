package com.ibm.websphere.samples.daytrader.entities;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.Date;
import javax.ejb.EJBException;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import com.ibm.websphere.samples.daytrader.util.Log;
import com.ibm.websphere.samples.daytrader.util.TradeConfig;

 class AccountDataBeanConfigJSF_service 
 { 
 @Column(name = "BALANCE")
private BigDecimal balance;
@Column(name = "CREATIONDATE")
@Temporal(TemporalType.TIMESTAMP)
private Date creationDate;
@NotNull
@Column(name = "LOGOUTCOUNT", nullable = false)
private int logoutCount;
@TableGenerator(name = "accountIdGen", table = "KEYGENEJB", pkColumnName = "KEYNAME", valueColumnName = "KEYVAL", pkColumnValue = "account", allocationSize = 1000)
@Id
@GeneratedValue(strategy = GenerationType.TABLE, generator = "accountIdGen")
@Column(name = "ACCOUNTID", nullable = false)
private Integer accountID;
@NotNull
@Column(name = "LOGINCOUNT", nullable = false)
private int loginCount;
@Column(name = "LASTLOGIN")
@Temporal(TemporalType.TIMESTAMP)
private Date lastLogin;
@OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
private Collection<HoldingDataBean> holdings;
@OneToOne(fetch = FetchType.LAZY)
@JoinColumn(name = "PROFILE_USERID")
private AccountProfileDataBean profile;
@Transient
private String profileID;
@Column(name = "OPENBALANCE")
private BigDecimal openBalance;
@OneToMany(mappedBy = "account", fetch = FetchType.LAZY)
private Collection<OrderDataBean> orders;
public AccountDataBeanConfigJSF_service(){ 
} 

public AccountDataBeanConfigJSF_service(Integer accountID, BigDecimal openBalance, int loginCount, BigDecimal balance, String profileID, Date lastLogin, int logoutCount, Date creationDate){ 
setOpenBalance(openBalance);
setProfileID(profileID);
setLogoutCount(logoutCount);
setLastLogin(lastLogin);
setCreationDate(creationDate);
setAccountID(accountID);
setLoginCount(loginCount);
setBalance(balance);
} 

public AccountDataBeanConfigJSF_service(BigDecimal openBalance, int loginCount, BigDecimal balance, String profileID, Date lastLogin, int logoutCount, Date creationDate){ 
setOpenBalance(openBalance);
setProfileID(profileID);
setLogoutCount(logoutCount);
setLastLogin(lastLogin);
setCreationDate(creationDate);
setLoginCount(loginCount);
setBalance(balance);
} 


public BigDecimal getOpenBalance() {
    return openBalance;
}
public String getProfileID() {
    return profileID;
}
public static AccountDataBean getRandomInstance() {
    return new AccountDataBean(new Integer(TradeConfig.rndInt(100000)), TradeConfig.rndInt(10000), TradeConfig.rndInt(10000), new java.util.Date(), new java.util.Date(TradeConfig.rndInt(Integer.MAX_VALUE)), TradeConfig.rndBigDecimal(1000000.0f), TradeConfig.rndBigDecimal(1000000.0f), TradeConfig.rndUserID());
}
public BigDecimal getBalance() {
    return balance;
}
@Override
public String toString() {
    return "\n\tAccount Data for account: " + getAccountID() + "\n\t\t   loginCount:" + getLoginCount() + "\n\t\t  logoutCount:" + getLogoutCount() + "\n\t\t    lastLogin:" + getLastLogin() + "\n\t\t creationDate:" + getCreationDate() + "\n\t\t      balance:" + getBalance() + "\n\t\t  openBalance:" + getOpenBalance() + "\n\t\t    profileID:" + getProfileID();
}
public void setProfileID(String profileID) {
    this.profileID = profileID;
}
public void print() {
    Log.log(this.toString());
}
@Override
public boolean equals(Object object) {
    if (!(object instanceof AccountDataBean)) {
        return false;
    }
    AccountDataBean other = (AccountDataBean) object;
    if (this.accountID != other.accountID && (this.accountID == null || !this.accountID.equals(other.accountID))) {
        return false;
    }
    return true;
}
public void setLoginCount(int loginCount) {
    this.loginCount = loginCount;
}
public int getLoginCount() {
    return loginCount;
}
public void setCreationDate(Date creationDate) {
    this.creationDate = creationDate;
}
public void setLastLogin(Date lastLogin) {
    this.lastLogin = lastLogin;
}
public String toHTML() {
    return "<BR>Account Data for account: <B>" + getAccountID() + "</B>" + "<LI>   loginCount:" + getLoginCount() + "</LI>" + "<LI>  logoutCount:" + getLogoutCount() + "</LI>" + "<LI>    lastLogin:" + getLastLogin() + "</LI>" + "<LI> creationDate:" + getCreationDate() + "</LI>" + "<LI>      balance:" + getBalance() + "</LI>" + "<LI>  openBalance:" + getOpenBalance() + "</LI>" + "<LI>    profileID:" + getProfileID() + "</LI>";
}
public void setBalance(BigDecimal balance) {
    this.balance = balance;
}
public void login(String password) {
    AccountProfileDataBean profile = getProfile();
    if ((profile == null) || (profile.getPassword().equals(password) == false)) {
        String error = "AccountBean:Login failure for account: " + getAccountID() + ((profile == null) ? "null AccountProfile" : "\n\tIncorrect password-->" + profile.getUserID() + ":" + profile.getPassword());
        throw new EJBException(error);
    }
    setLastLogin(new Timestamp(System.currentTimeMillis()));
    setLoginCount(getLoginCount() + 1);
}
public int getLogoutCount() {
    return logoutCount;
}
public Collection<HoldingDataBean> getHoldings() {
    return holdings;
}
@Override
public int hashCode() {
    int hash = 0;
    hash += (this.accountID != null ? this.accountID.hashCode() : 0);
    return hash;
}
public void setProfile(AccountProfileDataBean profile) {
    this.profile = profile;
}
public Date getLastLogin() {
    return lastLogin;
}
public void setLogoutCount(int logoutCount) {
    this.logoutCount = logoutCount;
}
public AccountProfileDataBean getProfile() {
    return profile;
}
public void logout() {
    setLogoutCount(getLogoutCount() + 1);
}
public Integer getAccountID() {
    return accountID;
}
public void setOpenBalance(BigDecimal openBalance) {
    this.openBalance = openBalance;
}
public void setAccountID(Integer accountID) {
    this.accountID = accountID;
}
public Date getCreationDate() {
    return creationDate;
}
public Collection<OrderDataBean> getOrders() {
    return orders;
}
} 
