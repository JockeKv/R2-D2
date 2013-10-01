package se.liu.ida.tdp024.account.data.test.facade;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;

public class AccountEntityFacadeTest {
    
    //---- Unit under test ----//
    private AccountEntityFacade accountEntityFacade = new AccountEntityFacadeDB();
    private StorageFacade storageFacade = new StorageFacadeDB();
    
    @After
    public void tearDown() {
        storageFacade.emptyStorage();
    }
    
    @Test
    public void testCreate() {
        
        String accountType = "CHECK";
        String personKey = "Joakim Kvarnkvist";
        String bankKey = "No Savings Bank";
        int amount = 100;
        String type = "CREDIT";
        
        int id = accountEntityFacade.create(accountType, personKey, bankKey);
        
        Account account = accountEntityFacade.find(id);
        
        System.out.println("Account Type: "+ account.getAccountType());
        Assert.assertEquals(accountType, account.getAccountType());
        
        System.out.println("Person Key: "+ account.getPersonKey());
        Assert.assertEquals(personKey, account.getPersonKey());
        
        System.out.println("Bank Key: "+ account.getBankKey());
        Assert.assertEquals(bankKey, account.getBankKey());
        
        System.out.print("Holdings on " + account.getPersonKey() + " " + account.getAccountType() + " account = ");
        System.out.println(account.getHoldings());
        
        Transaction transaction = accountEntityFacade.createTransaction(type, id, amount);
        
        account = accountEntityFacade.find(id);
        
        System.out.print("And after transaction " + account.getPersonKey() + " " + account.getAccountType() + " account contains = ");
        System.out.println(account.getHoldings());
        
        Assert.assertEquals(amount, transaction.getAmount());
        Assert.assertEquals(type, transaction.getType());
        
    }
}