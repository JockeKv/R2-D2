package se.liu.ida.tdp024.account.logic.impl.facade;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.util.http.HTTPHelper;
import se.liu.ida.tdp024.account.util.http.HTTPHelperImpl;

public class AccountLogicFacadeImpl implements AccountLogicFacade {
    
    private AccountEntityFacade accountEntityFacade;
    
    public AccountLogicFacadeImpl(AccountEntityFacade accountEntityFacade) {
        this.accountEntityFacade = accountEntityFacade;
    }
    
    
    public String createAccount(String type, String name, String bank) {
        String nameKey = findPersonByName(name);
        String bankKey = findBankByName(bank);
        
        int id = accountEntityFacade.create("CHECK", nameKey, bankKey);
        
        Account account = accountEntityFacade.find(id);
        
        String res = account.getAccountType()+","+account.getPersonKey()+","+account.getBankKey();
        
        return res;
    }
    
    
    HTTPHelper helper = new HTTPHelperImpl();
    
    // PERSON
    
    @Override
    public String listPersons() {
        String res = helper.get("http://enterprise-systems.appspot.com/person/list");
        
        return res;
    }

    @Override
    public String findPersonByKey(String key) {
        String res = helper.get("http://enterprise-systems.appspot.com/person/find.key", "key", key);
        
        return res;
    }

    @Override
    public String findPersonByName(String name) {
        String res = helper.get("http://enterprise-systems.appspot.com/person/find.name", "name", name);
        
        return res;
    }

    // BANK
    
    @Override
    public String listBanks() {
        String res = helper.get("http://enterprise-systems.appspot.com/bank/list");
        
        return res;
        }

    @Override
    public String findBankByKey(String key) {
        String res = helper.get("http://enterprise-systems.appspot.com/bank/find.key", "key", key);
        
        return res;
    }

    @Override
    public String findBankByName(String name) {
        String res = helper.get("http://enterprise-systems.appspot.com/bank/find.name", "name", name);
        
        return res;
    }
    
}
