package se.liu.ida.tdp024.account.logic.api.facade;


public interface AccountLogicFacade {
    
    String createAccount(String type, String name, String bank);
    
    
    // PERSON
    String listPersons();
    String findPersonByKey(String key);
    String findPersonByName(String name);  

    // BANK
    String listBanks();
    String findBankByKey(String key);
    String findBankByName(String name);
    
}
