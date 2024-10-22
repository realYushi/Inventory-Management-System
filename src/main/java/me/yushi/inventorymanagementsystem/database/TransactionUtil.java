package me.yushi.inventorymanagementsystem.database;

import java.util.function.Function;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class TransactionUtil {
    public static <T> T executeTransaction(Function<EntityManager, T> action) {
        EntityManager em = HibernateUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = null;
        T result = null;
        try {
            transaction = em.getTransaction();
            transaction.begin();
            result = action.apply(em);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
        }
        return result;
    }
}
