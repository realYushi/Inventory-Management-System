package me.yushi.inventorymanagementsystem.database;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class HibernateUtil {
    private static EntityManagerFactory entityManagerFactory;
    private static final String PERSISTENCE_UNIT_NAME = "Inventory-Management-Unit";

    public static EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            try {
                System.out.println("Attempting to create EntityManagerFactory...");
                entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
                
                if (entityManagerFactory == null) {
                    throw new IllegalStateException("EntityManagerFactory creation returned null");
                }
                
                System.out.println("EntityManagerFactory created successfully");
                
                // Test the connection
                entityManagerFactory.createEntityManager().close();
                System.out.println("Database connection test successful");
                
            } catch (Exception e) {
                System.err.println("Error initializing Hibernate: " + e.getMessage());
                e.printStackTrace();
                
                if (e.getCause() != null) {
                    System.err.println("Caused by: " + e.getCause().getMessage());
                    e.getCause().printStackTrace();
                }
                
                entityManagerFactory = null;
            }
        }
        return entityManagerFactory;
    }

    public static void shutdown() {
        if (entityManagerFactory != null && entityManagerFactory.isOpen()) {
            try {
                System.out.println("Shutting down Hibernate...");
                entityManagerFactory.close();
                System.out.println("Hibernate shutdown complete");
            } catch (Exception e) {
                System.err.println("Error during Hibernate shutdown: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}
