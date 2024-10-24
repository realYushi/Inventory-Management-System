package me.yushi.inventorymanagementsystem.InventoryManagementSystem;

import me.yushi.inventorymanagementsystem.contoller.CategoryController;
import me.yushi.inventorymanagementsystem.contoller.InventoryTransactionController;
import me.yushi.inventorymanagementsystem.contoller.ProductController;
import me.yushi.inventorymanagementsystem.contoller.SupplierController;
import me.yushi.inventorymanagementsystem.database.HibernateUtil;
import me.yushi.inventorymanagementsystem.repository.CategoryRepository;
import me.yushi.inventorymanagementsystem.repository.InventoryTransactionRepository;
import me.yushi.inventorymanagementsystem.repository.ProductRepository;
import me.yushi.inventorymanagementsystem.repository.SupplierRepository;
import me.yushi.inventorymanagementsystem.service.CategoryService;
import me.yushi.inventorymanagementsystem.service.InventoryTransactionService;
import me.yushi.inventorymanagementsystem.service.ProductService;
import me.yushi.inventorymanagementsystem.service.SupplierService;
import me.yushi.inventorymanagementsystem.view.APP;

import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        try {
            // Initialize database connection
            if (HibernateUtil.getEntityManagerFactory() == null) {
                System.err.println("Failed to initialize database connection");
                return;
            }

            // Initialize repositories
            ProductRepository productRepository = new ProductRepository();
            CategoryRepository categoryRepository = new CategoryRepository();
            SupplierRepository supplierRepository = new SupplierRepository();
            InventoryTransactionRepository inventoryTransactionRepository = new InventoryTransactionRepository();

            // Initialize services
            ProductService productService = new ProductService(productRepository);
            CategoryService categoryService = new CategoryService(categoryRepository);
            SupplierService supplierService = new SupplierService(supplierRepository);
            InventoryTransactionService inventoryTransactionService = new InventoryTransactionService(inventoryTransactionRepository);

            // Initialize controllers
            ProductController productController = new ProductController(productService);
            CategoryController categoryController = new CategoryController(categoryService);
            SupplierController supplierController = new SupplierController(supplierService);
            InventoryTransactionController inventoryTransactionController = new InventoryTransactionController(inventoryTransactionService, productService);

            // Initialize and show GUI on EDT
            SwingUtilities.invokeLater(() -> {
                try {
                    APP app = new APP(productController, categoryController, supplierController, inventoryTransactionController);
                    app.setVisible(true);
                } catch (Exception e) {
                    System.err.println("Error initializing GUI: " + e.getMessage());
                    e.printStackTrace();
                }
            });

            // Register shutdown hook
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    HibernateUtil.shutdown();
                } catch (Exception e) {
                    System.err.println("Error during shutdown: " + e.getMessage());
                    e.printStackTrace();
                }
            }));
        } catch (Exception e) {
            System.err.println("Fatal error during application startup: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
