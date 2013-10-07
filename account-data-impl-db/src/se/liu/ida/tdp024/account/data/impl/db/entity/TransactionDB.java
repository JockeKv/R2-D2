package se.liu.ida.tdp024.account.data.impl.db.entity;

import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;

@Entity
public class TransactionDB implements Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    
    private String transactionType;
    private String created;
    private String status;
    @ManyToOne(targetEntity=AccountDB.class)
    private Account account;
    private int amount;
    
    public TransactionDB(String transactionType, int amount) {
        this.transactionType = transactionType;
        this.amount = amount;
        this.created = new Date().toString();
        this.status = "FAILED";
        this.account = null;
    }
    
    public TransactionDB() {}
    
    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public String getType() {
        return this.transactionType;
    }

    @Override
    public int getAmount() {
        return this.amount;
    }

    @Override
    public String getCreated() {
        return this.created;
    }

    @Override
    public String getStatus() {
        return this.status;
    }

    @Override
    public Account getAccount() {
        return this.account;
    }

    @Override
    public Boolean setStatus(String status) {
        this.status = status;
        return this.status.equals(status);
    }

    @Override
    public Boolean setAccount(Account account) {
        this.account = account;
        return this.account == account;
    }
    
}
