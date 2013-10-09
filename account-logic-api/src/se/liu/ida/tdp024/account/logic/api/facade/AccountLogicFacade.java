package se.liu.ida.tdp024.account.logic.api.facade;

import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.exception.EntityInputParameterException;
import se.liu.ida.tdp024.account.data.api.exception.EntityNotFoundException;
import se.liu.ida.tdp024.account.data.api.exception.EntityServiceConfigurationException;


public interface AccountLogicFacade {
    
    // DATA INTERACTON FUNCTIONS
    
    String createAccount(String type, String name, String bank)
        throws
            EntityInputParameterException,
            EntityServiceConfigurationException;
    
    String creditAccount(int id, int amount)
        throws
            EntityInputParameterException,
            EntityServiceConfigurationException,
            EntityNotFoundException,
            Exception;
    
    String debitAccount(int id, int amount)
        throws
            EntityInputParameterException,
            EntityServiceConfigurationException,
            EntityNotFoundException,
            Exception;
    
    Account findAccount(int id)
        throws
            EntityNotFoundException,
            EntityServiceConfigurationException,
            Exception;
    
    String listAccounts(String name)
        throws
            EntityServiceConfigurationException;
    
    String listTransactions(int id)
        throws
            Exception;
    
    
    
    // LOGIC FUNCTIONS
    
    // PERSON
    String findPersonByName(String name)
        throws 
            EntityInputParameterException,
            EntityServiceConfigurationException,
            EntityNotFoundException;
    
    String findBankByName(String name)
        throws 
            EntityInputParameterException,
            EntityServiceConfigurationException,
            EntityNotFoundException;
    
}
