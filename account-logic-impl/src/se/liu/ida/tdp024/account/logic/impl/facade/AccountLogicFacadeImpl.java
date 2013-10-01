package se.liu.ida.tdp024.account.logic.impl.facade;

import java.util.Date;
import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.TransactionDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.util.http.HTTPHelper;
import se.liu.ida.tdp024.account.util.http.HTTPHelperImpl;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializer;
import se.liu.ida.tdp024.account.util.json.AccountJsonSerializerImpl;

public class AccountLogicFacadeImpl implements AccountLogicFacade {
    
    private AccountEntityFacade accountEntityFacade;
    
    public AccountLogicFacadeImpl(AccountEntityFacade accountEntityFacade) {
        this.accountEntityFacade = accountEntityFacade;
    }
    
   // *** DATA INTERACTION FUNCTIONS ***
    
    AccountJsonSerializer json = new AccountJsonSerializerImpl();
    
    @Override
    public String createAccount(String type, String name, String bank) {            
            if(!type.equals("CHECK") || !type.equals("SAVINGS"))
                return "Invalid account-type";
        
            String nameKey = findPersonByName(name);
            if(nameKey.equals("null"))
                return "Name does not exist";
            
            String bankKey = findBankByName(bank);
            if(bankKey.equals("null"))
                return "Bank does not exist";
            
            if(accountEntityFacade.createAccount(type, nameKey, bankKey) < 0)
                return "Account creation failed";
            
            
            return "Account successfully created!";
    }

    @Override
    public String creditAccount(int id, int amount) {
        if(findAccount(id) == null)
            return "No such account";
        String timeStamp = new Date().toString();
        Transaction transaction = new TransactionDB("CREDIT", amount);
        if(amount > 0) {
            Boolean status = accountEntityFacade.modifyAccount(id, amount);
            transaction.setStatus(status);
        }
        if(accountEntityFacade.insertTransaction(transaction))
            return transaction.getStatus().toString();
        
        return "FAILED :(";
    }

    @Override
    public String debitAccount(int id, int amount) {
        if(findAccount(id) == null)
            return "No such account";
        String timeStamp = new Date().toString();
        Transaction transaction = new TransactionDB("DEBIT", amount);
        if(amount > 0) {
            int mod = amount - (amount*2);
            Boolean status = accountEntityFacade.modifyAccount(id, mod);
            transaction.setStatus(status);
        }
        if(accountEntityFacade.insertTransaction(transaction))
            return transaction.getStatus().toString();
        
        return "FAILED :(";
    }

    @Override
    public Boolean createTransaction() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Account findAccount(int id) {
        return accountEntityFacade.findAccount(id);
    }

    @Override
    public String listAccounts(String name) {
        String nameKey = findPersonByName(name);
        if(nameKey.equals("null"))
            return "Name does not exist";
        List<Account> list = accountEntityFacade.listAccounts(nameKey);
        return json.toJson(list);
    }

    @Override
    public String listTransactions(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    // *** LOGIC FUNCTIONS ***

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

    
    
    