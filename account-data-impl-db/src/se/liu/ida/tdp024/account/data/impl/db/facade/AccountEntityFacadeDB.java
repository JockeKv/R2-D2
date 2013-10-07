package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;

public class AccountEntityFacadeDB implements AccountEntityFacade {

    @Override
    public int createAccount(String accountType, String name, String bank) {
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
    public boolean insertTransaction(Transaction transaction) {
        EntityManager em = EMF.getEntityManager();
        
        try {

            em.getTransaction().begin();           
            
            em.persist(transaction);

            em.getTransaction().commit();
            
            return true;

        } catch (Exception e) {

            return false;

        } finally {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            em.close();
        }
    }

    @Override
    public List<Account> listAccounts(String name) {
        EntityManager em = EMF.getEntityManager();

        try {

            Query query = em.createQuery("SELECT t FROM AccountDB t WHERE t.personKey=:name");
            query.setParameter("name", name);
            return query.getResultList();

        } catch (Exception e) {

            return null;

        } finally {
            em.close();
        }
    }

    @Override
    public List<Transaction> listTransactions(Account account) {
        EntityManager em = EMF.getEntityManager();

        try {

            Query query = em.createQuery("SELECT t FROM TransactionDB t WHERE t.account=:account");
            query.setParameter("account", account);
            return query.getResultList();

        } catch (Exception e) {
            return null;

        } finally {
            em.close();
        }
    }

    @Override
    public Account findAccount(int id) {
        EntityManager em = EMF.getEntityManager();

        try {
            return em.find(AccountDB.class, id);

        } catch (Exception e) {
            return null;

        } finally {
            em.close();
        }

    }

    @Override
    public boolean modifyAccount(int id, int amount) {
        EntityManager em = EMF.getEntityManager();
        
        try {
            em.getTransaction().begin();

            Account account = em.find(AccountDB.class, id, LockModeType.PESSIMISTIC_WRITE);
            
            Boolean status = account.changeHoldings(amount);

            em.merge(account);

            em.getTransaction().commit();
            
            return status;

        } catch (Exception e) {

            return false;

        } finally {

            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }

            em.close();
        }
    }
}
