package se.liu.ida.tdp024.account.data.api.entity;

import java.io.Serializable;

public interface Transaction extends Serializable {
    
    int getId();
    
    String getType();
    int getAmount();
    String getDate();
    Boolean getStatus();
    
    Boolean setStatus(Boolean status);
    Boolean setAccount(Account account);
    
    Account getAccount(int id);
    
}