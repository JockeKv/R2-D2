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
        //storageFacade.emptyStorage();
    }
    
    
    
    @Test
    public void testCreate() {
        String res = accountLogicFacade.createAccount("CHECK", "Marcus Bendtsen", "SWEDBANK");
        System.out.println(res);
        
        res = accountLogicFacade.creditAccount(1, 100);
        System.out.println(res);
        
        res = accountLogicFacade.listTransactions(1);
        System.out.println(res);


        
    }
}