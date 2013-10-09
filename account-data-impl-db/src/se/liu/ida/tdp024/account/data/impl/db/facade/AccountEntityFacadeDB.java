package se.liu.ida.tdp024.account.data.impl.db.facade;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import se.liu.ida.tdp024.account.data.api.entity.Account;
import se.liu.ida.tdp024.account.data.api.entity.Transaction;
import se.liu.ida.tdp024.account.data.api.exception.EntityServiceConfigurationException;
import se.liu.ida.tdp024.account.data.api.exception.EntityNotFoundException;
import se.liu.ida.tdp024.account.data.api.facade.AccountEntityFacade;
import se.liu.ida.tdp024.account.data.impl.db.entity.AccountDB;
import se.liu.ida.tdp024.account.data.impl.db.util.EMF;

public class AccountEntityFacadeDB implements AccountEntityFacade {

    @Override
    public int createAccount(String accountType, String name, String bank) 
        throws
            EntityServiceConfigurationException {
        
        EntityManager em = EMF.getEntityManager();

        try {

            em.getTransaction().begin();

            Account account = new AccountDB(accountType, name, bank);

            em.persist(account);

            em.getTransaction().commit();

            return account.getId();

        } catch (Exception e) {
            
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            
            throw new EntityServiceConfigurationException("Database broken :(");

        } finally {

            em.close();
        }
    }

    @Override
    public boolean insertTransaction(Transaction transaction) 
        throws
            EntityServiceConfigurationException {
        
        EntityManager em = EMF.getEntityManager();
        
        try {

            em.getTransaction().begin();           
            
            em.persist(transaction);

            em.getTransaction().commit();
            
            return true;

        } catch (Exception e) {
            
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            
            throw new EntityServiceConfigurationException("Database broken :(");

        } finally {

            em.close();
        }
    }

    @Override
    public List<Account> listAccounts(String name) 
        throws
            EntityServiceConfigurationException {
        
        EntityManager em = EMF.getEntityManager();

        try {

            Query query = em.createQuery("SELECT t FROM AccountDB t WHERE t.personKey=:name");
            query.setParameter("name", name);
            return query.getResultList();

        } catch (Exception e) {

            throw new EntityServiceConfigurationException("Database broken :(");

        } finally {
            em.close();
        }
    }

    @Override
    public List<Transaction> listTransactions(Account account) 
        throws
            EntityServiceConfigurationException {
        
        EntityManager em = EMF.getEntityManager();

        try {

            Query query = em.createQuery("SELECT t FROM TransactionDB t WHERE t.account=:account");
            query.setParameter("account", account);
            return query.getResultList();

        } catch (Exception e) {
            
            throw new EntityServiceConfigurationException("Database broken :(");

        } finally {
            em.close();
        }
    }

    @Override
    public Account findAccount(int id) 
        throws
            EntityServiceConfigurationException,
            EntityNotFoundException {
        
        EntityManager em = EMF.getEntityManager();

        try {
            Account account = em.find(AccountDB.class, id);
            if(account == null) {
                throw new EntityNotFoundException("No account with id: "+id);
            }
            return account;

        } catch (Exception e) {
            
            throw new EntityServiceConfigurationException("Database broken :(");    
        } finally {
            
            em.close();
        }

    }

    @Override
    public boolean modifyAccount(int id, int amount) 
        throws
            EntityServiceConfigurationException,
            EntityNotFoundException {
        
        EntityManager em = EMF.getEntityManager();
        
        try {
            em.getTransaction().begin();

            Account account = em.find(AccountDB.class, id, LockModeType.PESSIMISTIC_WRITE);
            
            if(account == null) {
                throw new EntityNotFoundException("Could not find account with id: "+id);
            }
            
            Boolean status = account.changeHoldings(amount);

            em.merge(account);

            em.getTransaction().commit();
            
            return status;

        } catch (Exception e) {
            
             if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
             
            throw new EntityServiceConfigurationException("Database broken :(");

        } finally {

            em.close();
        }
    }
}
