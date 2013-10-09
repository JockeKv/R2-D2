package se.liu.ida.tdp024.account.logic.test.facade;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.facade.AccountEntityFacadeDB;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;

public class AccountLogicFacadeTest {

    
    //--- Unit under test ---//
    public AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl(new AccountEntityFacadeDB());
    public StorageFacade storageFacade = new StorageFacadeDB();
    
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
    
    @Test
    public void testCreate() {

        Assert.assertEquals("OK", accountLogicFacade.createAccount("CHECK", "Marcus Bendtsen", "SWEDBANK"));
        Assert.assertEquals("FAILED", accountLogicFacade.createAccount(null, "Marcus Bendtsen", "SWEDBANK"));
        Assert.assertEquals("FAILED", accountLogicFacade.createAccount("CHECK", "Anders Ydremark", "SWEDBANK"));
        Assert.assertEquals("FAILED", accountLogicFacade.createAccount("CHECK", "Marcus Bendtsen", "DERP BANK"));
        Assert.assertEquals("FAILED", accountLogicFacade.createAccount("DOLLARS", "Marcus Bendtsen", "DERP BANK"));
        
    }
    
    @Test
    public void testDebitCreditAccount() {
        
        accountLogicFacade.createAccount("CHECK", "Marcus Bendtsen", "SWEDBANK");
        Assert.assertEquals("OK", accountLogicFacade.creditAccount(1, 100));
        Assert.assertEquals("OK", accountLogicFacade.debitAccount(1, 100));
        Assert.assertEquals("FAILED", accountLogicFacade.debitAccount(1, 100));
        Assert.assertEquals("FAILED", accountLogicFacade.creditAccount(57, 100));
        Assert.assertEquals("FAILED", accountLogicFacade.debitAccount(54, 100));
        
    }
    
    @Test
    public void testFindAccount() {
        Assert.assertEquals(null, accountLogicFacade.findAccount(432));
    }
    
    @Test
    public void testListAccounts() {
        Assert.assertEquals("[]", accountLogicFacade.listAccounts("Anders Ydremark"));
    }
}