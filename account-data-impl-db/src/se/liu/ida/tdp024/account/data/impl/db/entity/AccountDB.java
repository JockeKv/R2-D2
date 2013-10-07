package se.liu.ida.tdp024.account.data.impl.db.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

@Entity
public class AccountDB implements Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    private String accountType;
    private String personKey;
    private String bankKey;
    private int holdings;
    
    @OneToMany(mappedBy="account", targetEntity=TransactionDB.class)
    private List<Transaction> transactionList;

    public AccountDB() {
    }
    
    
    public AccountDB(String accountType, String name, String bank) {
        this.accountType = accountType;
        this.personKey = name;
        this.bankKey = bank;
    }
    
    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getPersonKey() {
        return this.personKey;
    }

    @Override
    public String getAccountType() {
        return this.accountType;
    }

    @Override
    public String getBankKey() {
       return this.bankKey;
    }

    @Override
    public boolean changeHoldings(int amount) {
        if((this.holdings + amount) < 0) {
            return false;
        }
        this.holdings += amount;
        return true;
    }

    @Override
    public int getHoldings() {
        return holdings;
    }
    
}
