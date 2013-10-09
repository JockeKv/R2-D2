package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.exception.EntityInputParameterException;
import se.liu.ida.tdp024.account.data.api.exception.EntityNotFoundException;
import se.liu.ida.tdp024.account.data.api.exception.EntityServiceConfigurationException;

public interface AccountEntityFacade {
    
    int createAccount(String accountType, String name, String bank)
            throws
            EntityServiceConfigurationException;
    
    boolean insertTransaction(Transaction transaction)
            throws
            EntityInputParameterException,
            EntityServiceConfigurationException;
    
    boolean modifyAccount(int id, int amount, String mod)
            throws
            EntityInputParameterException,
            EntityServiceConfigurationException,
            EntityNotFoundException;
    
    List<Account> listAccounts(String name)
            throws
            EntityServiceConfigurationException;
    
    List<Transaction> listTransactions(Account account)
            throws
            EntityServiceConfigurationException;
    
    Account findAccount(int id)
            throws
            EntityServiceConfigurationException,
            EntityNotFoundException;
}
