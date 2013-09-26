package se.liu.ida.tdp024.account.data.api.facade;

public interface AccountEntityFacade {
    
    
    String create(String accountType, String name, String bank);
    
    String find(String name);
    
    String debit(int account, int amount);
    
    String credit(int account, int amount);
    
    String transactions(int id);
    
}
