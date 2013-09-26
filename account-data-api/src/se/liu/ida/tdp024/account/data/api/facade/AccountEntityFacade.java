package se.liu.ida.tdp024.account.data.api.facade;

import java.util.List;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

public interface AccountEntityFacade {
    
    int create(String accountType, String name, String bank);
    
    Transaction createTransaction(String type, int id, int amount);
    
    List<Account> findAccount(String name);
    List<Transaction> listTransactions(int id);
    
    Account find(int id);
}
