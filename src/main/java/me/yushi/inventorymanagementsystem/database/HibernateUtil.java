/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.database;

import java.util.HashMap;
import java.util.Map;

import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Environment;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 *
 * @author yushi
 */
public class HibernateUtil {
    private static EntityManagerFactory entityManagerFactory;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            Map<String, Object> settings = new HashMap<>();
            settings.put("jakarta.persistence.jdbc.driver", "org.apache.derby.jdbc.EmbeddedDriver");
            settings.put("jakarta.persistence.jdbc.url", "jdbc:derby:Inventory-Management-DB;create=true");
            settings.put(Environment.DIALECT, "org.hibernate.dialect.DerbyDialect");
            settings.put(Environment.HBM2DDL_AUTO, "update");

            settings.put(AvailableSettings.LOADED_CLASSES, new Class<?>[]{
                    me.yushi.inventorymanagementsystem.model.Product.class,
                    me.yushi.inventorymanagementsystem.model.Category.class,
                    me.yushi.inventorymanagementsystem.model.Supplier.class,
                    me.yushi.inventorymanagementsystem.model.InventoryTransaction.class,
            });

            entityManagerFactory = Persistence.createEntityManagerFactory("Inventory-Management-Unit", settings);

        }
        return entityManagerFactory;
    }

    public static void shutdown() {
        if (entityManagerFactory != null) {
            entityManagerFactory.close();
        }
    }
}