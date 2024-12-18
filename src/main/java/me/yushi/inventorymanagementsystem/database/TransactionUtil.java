package me.yushi.inventorymanagementsystem.database;

import java.util.function.Function;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

public class TransactionUtil {
    public static <T> T executeTransaction(Function<EntityManager, T> action) {
        // Get the EntityManagerFactory
        EntityManagerFactory emf = HibernateUtil.getEntityManagerFactory();
        if (emf == null) {
            throw new RuntimeException("EntityManagerFactory is null");
        }

        EntityManager em = null;
        EntityTransaction transaction = null;
        T result = null;

        try {
            // Create an EntityManager
            em = emf.createEntityManager();  
            if (em == null) {
                throw new RuntimeException("Failed to create EntityManager");
            }

            // Get a transaction
            transaction = em.getTransaction();
            transaction.begin();
            // Perform the action            
            result = action.apply(em);
            // Commit the transaction
            transaction.commit();
        } catch (Exception e) {
            System.err.println("Error in transaction: " + e.getMessage());
            e.printStackTrace();
            // If the transaction is still active, roll it back
            if (transaction != null && transaction.isActive()) {
                try {
                    transaction.rollback();
                } catch (Exception rollbackException) {
                    System.err.println("Error rolling back transaction: " + rollbackException.getMessage());
                    rollbackException.printStackTrace();
                }
            }
            throw new RuntimeException("Transaction failed", e);
        } finally {
            // Close the EntityManager
            if (em != null && em.isOpen()) {
                try {
                    em.close();
                } catch (Exception closeException) {
                    System.err.println("Error closing EntityManager: " + closeException.getMessage());
                    closeException.printStackTrace();
                }
            }
        }
        return result;
    }
}
