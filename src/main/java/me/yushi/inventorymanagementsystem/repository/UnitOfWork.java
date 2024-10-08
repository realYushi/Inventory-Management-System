/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;

/**
 *
 * @author yushi
 */
public class UnitOfWork implements IUnitOfWork {

    private final CategoryRepository categoryRepository;
    private final InventoryTransactionRepository inventoryTransactionRepository;
    private final ProductRepository productRepository;
    private final SupplierRepository supplierRepository;

    public UnitOfWork(CategoryRepository categoryRepository,
            InventoryTransactionRepository inventoryTransactionRepository,
            ProductRepository productRepository,
            SupplierRepository supplierRepository) {
        // Initialize Repositories
        this.categoryRepository = categoryRepository;
        this.inventoryTransactionRepository = inventoryTransactionRepository;
        this.productRepository = productRepository;
        this.supplierRepository = supplierRepository;

    }

    @Override
    public CategoryRepository getCategoryRepository() {
        // Return CategoryRepository
        return this.categoryRepository;
    }

    @Override
    public InventoryTransactionRepository getInventoryTransactionRepository() {
        // Return InventoryTransactionRepository
        return this.inventoryTransactionRepository;
    }

    @Override
    public ProductRepository getProductRepository() {
        // Return ProductRepository
        return this.productRepository;
    }

    @Override
    public SupplierRepository getSupplierRepository() {
        // Return SupplierRepository
        return this.supplierRepository;
    }

}
