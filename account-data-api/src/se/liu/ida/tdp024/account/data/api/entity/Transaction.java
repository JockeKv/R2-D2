package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;

public interface Transaction extends Serializable {
    
    int getId();
    
    String getType();
    int getAmount();
    String getCreated();
    String getStatus();
    
    Boolean setStatus(String status);
    Boolean setAccount(Account account);
    
    Account getAccount();
    
}