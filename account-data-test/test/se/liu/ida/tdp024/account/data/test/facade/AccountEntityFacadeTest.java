package se.liu.ida.tdp024.account.data.test.facade;

import junit.framework.Assert;
import org.junit.After;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
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
    
    String accountType = "CHECK";
    String personKey = "Joakim Kvarnkvist";
    String bankKey = "No Savings Bank";
    int amount = 100;
    String type = "CREDIT";
    Account account;
    
    @Test
    public void testCreate() {
        
        int id = accountEntityFacade.createAccount(accountType, personKey, bankKey);
        
        account = accountEntityFacade.findAccount(id);
        
        System.out.println("Account Type: "+ account.getAccountType());
        Assert.assertEquals(accountType, account.getAccountType());
        
        System.out.println("Person Key: "+ account.getPersonKey());
        Assert.assertEquals(personKey, account.getPersonKey());
        
        System.out.println("Bank Key: "+ account.getBankKey());
        Assert.assertEquals(bankKey, account.getBankKey());
        
        System.out.print("Holdings on " + account.getPersonKey() + " " + account.getAccountType() + " account = ");
        System.out.println(account.getHoldings());
        
    }
}