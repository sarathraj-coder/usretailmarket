/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.retailshops.offermarket.dao;

import com.retailshops.offermarket.model.Outlet;
import com.retailshops.offermarket.dao.exceptions.NonexistentEntityException;
import com.retailshops.offermarket.dao.exceptions.RollbackFailureException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.UserTransaction;
import org.bson.types.ObjectId;

/**
 *
 * @author sarathraj
 */
public class OutletDAO implements Serializable {

 
	private EntityManager em;
    public void create(Outlet outlet) throws RollbackFailureException, Exception {
       // EntityManager em = null;
        try {
           // utx.begin();
          //  em = getEntityManager();
            em.persist(outlet);
           // utx.commit();
        } catch (Exception ex) {
            try {
              //  utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Outlet outlet) throws NonexistentEntityException, RollbackFailureException, Exception {
       // EntityManager em = null;
        try {
           // utx.begin();
          //  em = getEntityManager();
            outlet = em.merge(outlet);
           // utx.commit();
        } catch (Exception ex) {
            try {
             //   utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
//               // ObjectId id = outlet.getId();
//                if (findOutlet(id) == null) {
//                    throw new NonexistentEntityException("The outlet with id " + id + " no longer exists.");
//                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ObjectId id) throws NonexistentEntityException, RollbackFailureException, Exception {
       // EntityManager em = null;
        try {
           // utx.begin();
           // em = getEntityManager();
            Outlet outlet;
            try {
                outlet = em.getReference(Outlet.class, id);
               //outlet.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The outlet with id " + id + " no longer exists.", enfe);
            }
            em.remove(outlet);
           // utx.commit();
        } catch (Exception ex) {
            try {
            //    utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Outlet> findOutletEntities() {
        return findOutletEntities(true, -1, -1);
    }

    public List<Outlet> findOutletEntities(int maxResults, int firstResult) {
        return findOutletEntities(false, maxResults, firstResult);
    }

    private List<Outlet> findOutletEntities(boolean all, int maxResults, int firstResult) {
      //  EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Outlet.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Outlet findOutlet(ObjectId id) {
      //  EntityManager em = getEntityManager();
        try {
            return em.find(Outlet.class, id);
        } finally {
            em.close();
        }
    }

    public int getOutletCount() {
      //  EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Outlet> rt = cq.from(Outlet.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
