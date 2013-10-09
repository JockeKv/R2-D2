package se.liu.ida.tdp024.account.logic.impl.facade;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.exception.EntityInputParameterException;
import se.liu.ida.tdp024.account.data.api.exception.EntityNotFoundException;
import se.liu.ida.tdp024.account.data.api.exception.EntityServiceConfigurationException;
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
    
    private AccountJsonSerializer json = new AccountJsonSerializerImpl();
    
    
    
    
    @Override
    public String createAccount(String type, String name, String bank)
        throws
            EntityInputParameterException,
            EntityServiceConfigurationException {
        
            if(type == null) {
                throw new EntityInputParameterException("Accounttype can not be null.");
            }
            
            if(!type.equals("CHECK") && !type.equals("SAVINGS")) {
                throw new EntityInputParameterException("Accounttype must be 'CHECK' or 'SAVINGS'");
            }
        
            String nameKey = findPersonByName(name);
            if(nameKey.equals("null")) {
                return "FAILED";
            }
            
            String bankKey = findBankByName(bank);
            if(bankKey.equals("null")) {
                return "FAILED";
            }
            
            try {
                accountEntityFacade.createAccount(type, nameKey, bankKey);
            } 
            catch (EntityServiceConfigurationException e) {
                throw new EntityServiceConfigurationException("Failed to create account.");
            }
                        
            return "OK";
    }

    @Override
    public String creditAccount(int id, int amount)
        throws
            EntityNotFoundException,
            EntityServiceConfigurationException,
            EntityInputParameterException,
            Exception {
        
        try {
            findAccount(id);
        }
        catch(EntityNotFoundException e) {
            throw e;
        }
        
        Transaction transaction = new TransactionDB("CREDIT", amount);
        
        try {
            accountEntityFacade.modifyAccount(id, amount, "C");
            transaction.setAccount(accountEntityFacade.findAccount(id));
            
        }
        catch(Exception e) {
                transaction.setStatus("FAILED");
                accountEntityFacade.insertTransaction(transaction);
                throw e;
        }
        
        transaction.setStatus("OK");
        accountEntityFacade.insertTransaction(transaction);
        
        return "OK";
    }

    @Override
    public String debitAccount(int id, int amount)
        throws
            EntityNotFoundException,
            EntityServiceConfigurationException,
            EntityInputParameterException,
            Exception {
        
        try {
            findAccount(id);
        }
        catch(EntityNotFoundException e) {
            throw e;
        }
        
        Transaction transaction = new TransactionDB("DEBIT", amount);
        
        try {
            accountEntityFacade.modifyAccount(id, amount, "D");
            transaction.setAccount(accountEntityFacade.findAccount(id));
            
        }
        catch(Exception e) {
                transaction.setStatus("FAILED");
                accountEntityFacade.insertTransaction(transaction);
                throw e;
        }
        
        transaction.setStatus("OK");
        accountEntityFacade.insertTransaction(transaction);
        
        return "OK";
    }


    @Override
    public Account findAccount(int id)
        throws
            EntityNotFoundException,
            Exception{
        
        try {
            return accountEntityFacade.findAccount(id);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public String listAccounts(String name) 
        throws
            EntityServiceConfigurationException {
        
        List<Account> list;
        String nameKey = findPersonByName(name);
        if(nameKey.equals("null")) {
            return "[]";
        }
        try {
            list = accountEntityFacade.listAccounts(nameKey);
        }
        catch(EntityServiceConfigurationException e) {
            throw e;
        }
        return json.toJson(list);
    }

    @Override
    public String listTransactions(int id)
        throws
            Exception {
        List<Transaction> list;
        try {
            Account account = accountEntityFacade.findAccount(id);
            list = accountEntityFacade.listTransactions(account);
        }
        catch(EntityServiceConfigurationException e) {
            throw e;
        }
        return json.toJson(list);
    }
    
    
    // *** LOGIC FUNCTIONS ***

    private HTTPHelper helper = new HTTPHelperImpl();
    
    private static class Dummy {
        public Dummy() {}
        public String name;
        public String key;
    }
    
 
    @Override
    public String findPersonByName(String name) {
        String res = helper.get("http://enterprise-systems.appspot.com/person/find.name", "name", name);
        Dummy dummy = json.fromJson(res, Dummy.class);
       
        return dummy.key;
    }


    @Override
    public String findBankByName(String name) {
        String res = helper.get("http://enterprise-systems.appspot.com/bank/find.name", "name", name);
        
       Dummy dummy = json.fromJson(res, Dummy.class);
       
       return dummy.key;
    }

    
}

    
    
    