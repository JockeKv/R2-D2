package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;

public interface Account extends Serializable {
    
    int getId();
    
    String getPersonKey();
    String getAccountType();
    String getBankKey();
    
    int getHoldings();
    boolean changeHoldings(int amount);
    
}
