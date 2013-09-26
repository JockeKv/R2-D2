package se.liu.ida.tdp024.account.logic.impl.facade;

import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;

public class AccountLogicFacadeImpl implements AccountLogicFacade {
    
   /* private AccountEntityFacade accountEntityFacade;
    
    public AccountLogicFacadeImpl(AccountEntityFacade accountEntityFacade) {
        this.accountEntityFacade = accountEntityFacade;
    }
    * */
    
    // PERSON
    
    @Override
    public String listPersons() {
        return "[\"Anders\", \"Anders mamma\"]";
    }

    @Override
    public String findPersonByKey(String key) {
        return "Anders";
    }

    @Override
    public String findPersonByName(String name) {
        return "AndersNyckel123";
    }

    // BANK
    
    @Override
    public String listBanks() {
        return "[\"Lehman Brothers\", \"ViagraBanken\"]";
    }

    @Override
    public String findBankByKey(String key) {
        return "Lehman Brothers";
    }

    @Override
    public String findBankByName(String name) {
        return "BankOfEvil";
    }
    
}
