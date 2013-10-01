package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

public interface AccountEntityFacade {
    
    int createAccount(String accountType, String name, String bank);
    
    boolean insertTransaction(Transaction transaction);
    
    boolean modifyAccount(int id, int amount);
    
    List<Account> listAccounts(String name);
    List<Transaction> listTransactions(Account account);
    
    Account findAccount(int id);
}
