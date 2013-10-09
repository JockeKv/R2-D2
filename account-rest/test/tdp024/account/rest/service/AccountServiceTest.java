package tdp024.account.rest.service;

import javax.ws.rs.core.Response;
import junit.framework.Assert;
import org.junit.After;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.data.impl.db.util.StorageFacadeDB;
import se.liu.ida.tdp024.account.rest.service.AccountService;

public class AccountServiceTest {

    //-- Units under test ---//
    private StorageFacade storageFacade = new StorageFacadeDB();

    @After
    public void tearDown() {
        storageFacade.emptyStorage();

    }
    
    String type = "CHECK";
    String name = "Marcus Bendtsen";
    String bank = "SWEDBANK";
    int amount = 100;

    @Test
    public void testCreate() {
        
        AccountService accountservice = new AccountService();
        
        Response response = accountservice.create(type, name, bank);
        
        Assert.assertEquals(200, response.getStatus());
        
    }
    
    @Test
    public void testFind() {
        
        AccountService accountservice = new AccountService();
        
        Response response = accountservice.find(name);
        
        Assert.assertEquals(200, response.getStatus());
    }
    
    @Test
    public void testCreditDebit() {
        
        AccountService accountservice = new AccountService();
        
        accountservice.create(type, name, bank);
        
        Response response = accountservice.credit(1, amount);
        
        Assert.assertEquals(200, response.getStatus());
        
        response = accountservice.debit(1, amount);
        
        Assert.assertEquals(200, response.getStatus());
    }
    
    @Test
    public void testTransactions() {
        
        AccountService accountservice = new AccountService();
        
        accountservice.create(type, name, bank);
        accountservice.credit(1, amount);
        
        Response response = accountservice.transactions(1);
        
        Assert.assertEquals(200, response.getStatus());
    }
}