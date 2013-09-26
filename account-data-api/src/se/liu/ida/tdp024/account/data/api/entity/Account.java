package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;

public interface Account extends Serializable {
    
    int getId();
    
    String getPersonKey();
    String getAccountType();
    String getBankKey();
    
    Boolean debitAccount(int amount);
    Boolean creditAccount(int amount);
    
    int getHoldings();
}
