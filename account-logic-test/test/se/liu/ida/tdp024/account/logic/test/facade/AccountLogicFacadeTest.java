package se.liu.ida.tdp024.account.logic.test.facade;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import se.liu.ida.tdp024.account.data.api.util.StorageFacade;
import se.liu.ida.tdp024.account.logic.api.facade.AccountLogicFacade;
import se.liu.ida.tdp024.account.logic.impl.facade.AccountLogicFacadeImpl;

public class AccountLogicFacadeTest {

    
    //--- Unit under test ---//
    public AccountLogicFacade accountLogicFacade = new AccountLogicFacadeImpl();
    public StorageFacade storageFacade;
    
    @After
    public void tearDown() {
        //storageFacade.emptyStorage();
    }
    
    
    
    @Test
    public void testCreate() {
        System.out.println("Testing Logic Layer..");
        
        
        // PERSONS
        
        
        System.out.println("Listing Persons:");
        String Personlist = accountLogicFacade.listPersons();
        System.out.println(Personlist); 
        Assert.assertEquals("[\"Anders\", \"Anders mamma\"]", Personlist);
        
        System.out.println("Get person with Key: AndersNyckel123");
        String PersonKey = accountLogicFacade.findPersonByKey("AndersNyckel123");
        System.out.println(PersonKey);
        Assert.assertEquals("Anders", PersonKey);
        
        System.out.println("Get person with Name: Anders");
        String PersonName = accountLogicFacade.findPersonByName("Anders");
        System.out.println(PersonName);
        Assert.assertEquals("AndersNyckel123", PersonName);
        
        
        // BANKS
        
        
        System.out.println("Listing Banks:");
        String Banklist = accountLogicFacade.listBanks();
        System.out.println(Banklist); 
        Assert.assertEquals("[\"Lehman Brothers\", \"ViagraBanken\"]", Banklist);
        
        System.out.println("Get Bank with Key: BankOfEvil");
        String BankKey = accountLogicFacade.findBankByKey("BankOfEvil");
        System.out.println(BankKey);
        Assert.assertEquals("Lehman Brothers", BankKey);
        
        System.out.println("Get Bank with Name: Anders");
        String BankName = accountLogicFacade.findBankByName("Lehman Brothers");
        System.out.println(BankName);
        Assert.assertEquals("BankOfEvil", BankName);
        
        
    }
}