package se.liu.ida.tdp024.account.data.test.facade;

import java.util.List;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.TransactionDB;
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
    
    String accountType1 = "CHECK";
    String accountType2 = "DEBIT";
    String personKey = "Joakim Kvarnkvist";
    String bankKey = "No Savings Bank";
    int creditamount = 100;
    int debitamount = -50;
    String type = "CREDIT";
    Account account;
    Transaction transaction;
    Transaction transaction2;
    
    @Test
    public void testCreate() {
        
        int id = accountEntityFacade.createAccount(accountType1, personKey, bankKey);
        
        account = accountEntityFacade.findAccount(id);

        Assert.assertEquals(accountType1, account.getAccountType());
        Assert.assertEquals(personKey, account.getPersonKey());
        Assert.assertEquals(bankKey, account.getBankKey());
    }
    
    @Test
    public void testModifyAccount() {
        
        // Create account and then modify it, adding 100 and getting true
        int id = accountEntityFacade.createAccount(accountType1, personKey, bankKey);
        Assert.assertEquals(true, accountEntityFacade.modifyAccount(id, creditamount));
        
        
        // Find the account with that id and check if the amount now is 100
        account = accountEntityFacade.findAccount(id);
        Assert.assertEquals(creditamount, account.getHoldings());
        
        // Debit the account with 50
        Assert.assertEquals(true, accountEntityFacade.modifyAccount(id, debitamount));
        
        // Find the account again check if the amount now is 50
        account = accountEntityFacade.findAccount(id);
        Assert.assertEquals(50, account.getHoldings());
    }
    
    @Test
    public void testInsertTransaction() {
        
        transaction = new TransactionDB("CREDIT", creditamount);
        Assert.assertEquals(true, accountEntityFacade.insertTransaction(transaction));
        
        transaction = new TransactionDB("DEBIT", debitamount);
        Assert.assertEquals(true, accountEntityFacade.insertTransaction(transaction));
        
        Assert.assertEquals(false, accountEntityFacade.insertTransaction(null));
        
    }
    
    @Test
    public void testListAccounts() {
        
       accountEntityFacade.createAccount(accountType1, personKey, bankKey);
       accountEntityFacade.createAccount(accountType2, personKey, bankKey);
       accountEntityFacade.createAccount(accountType1, personKey, bankKey);
       accountEntityFacade.createAccount(accountType2, personKey, bankKey);

       
       List<Account> list = accountEntityFacade.listAccounts(personKey);
       Assert.assertEquals(4, list.size());
    }
    
    @Test
    public void testListTransactions() {
        
        int id = accountEntityFacade.createAccount(accountType1, personKey, bankKey);
        int id2 = accountEntityFacade.createAccount(accountType1, "Arne", bankKey);
        account = accountEntityFacade.findAccount(id);
        Account account2 = accountEntityFacade.findAccount(id2);
        
        transaction = new TransactionDB("DEBIT", debitamount);
        transaction2 = new TransactionDB("CREDIT", creditamount);
        transaction.setAccount(account);
        transaction2.setAccount(account);
        accountEntityFacade.insertTransaction(transaction);
        accountEntityFacade.insertTransaction(transaction2);
        
        List<Transaction> list = accountEntityFacade.listTransactions(account);
        Assert.assertEquals(2, list.size());
        
        List<Transaction> list2 = accountEntityFacade.listTransactions(account2);
        Assert.assertEquals(0, list2.size());
    }
}