package se.liu.ida.tdp024.account.logic.api.facade;


public interface AccountLogicFacade {
    
    // PERSON
    String listPersons();
    String findPersonByKey(String key);
    String findPersonByName(String name);  

    // BANK
    String listBanks();
    String findBankByKey(String key);
    String findBankByName(String name);
    
}
