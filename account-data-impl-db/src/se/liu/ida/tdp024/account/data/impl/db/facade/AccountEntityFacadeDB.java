package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.entity.TransactionDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;

public class AccountEntityFacadeDB implements AccountEntityFacade {

    @Override
    public int create(String accountType, String name, String bank) {
        EntityManager em = EMF.getEntityManager();

        try {

            em.getTransaction().begin();

            Account account = new AccountDB(accountType, name, bank);

            em.persist(account);

            em.getTransaction().commit();

            return account.getId();

        } catch (Exception e) {

            return -1;

        } finally {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            em.close();
        }
    }

    @Override
    public Transaction createTransaction(String type, int id, int amount) {
        EntityManager em = EMF.getEntityManager();

        Transaction transaction = new TransactionDB(type, amount);
        
        try {

            em.getTransaction().begin();

            Account account = em.find(AccountDB.class, id, LockModeType.PESSIMISTIC_WRITE);
            
            transaction.setAccount(account);
            
            Boolean status = false;
            
            if(type.equals("DEBIT"))
                status = account.debitAccount(amount);
            else if(type.equals("CREDIT"))
                status = account.creditAccount(amount);
            else
                return transaction;
            
            transaction.setStatus(status);
            
            em.merge(account);

            em.getTransaction().commit();
            
            return transaction;

        } catch (Exception e) {

            transaction.setStatus(false);
            return transaction;

        } finally {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            em.close();
        }
    }

    @Override
    public List<Account> findAccount(String name) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Transaction> listTransactions(int id) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public Account find(int id) {
        EntityManager em = EMF.getEntityManager();

        try {

            return em.find(AccountDB.class, id);

        } catch (Exception e) {

            return null;

        } finally {
            em.close();
        }

    }
}
