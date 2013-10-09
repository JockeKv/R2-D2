package se.liu.ida.tdp024.account.logic.api.facade;

import se.liu.ida.tdp024.account.data.api.entity.Account;


public interface AccountLogicFacade {
    
    // DATA INTERACTON FUNCTIONS
    
    String createAccount(String type, String name, String bank);
    
    String creditAccount(int id, int amount);
    String debitAccount(int id, int amount);
    
    Account findAccount(int id);
    
    String listAccounts(String name);
    String listTransactions(int id);
    
    // LOGIC FUNCTIONS
    
    // PERSON
    String findPersonByName(String name);  

    // BANK
    String findBankByName(String name);
    
}
