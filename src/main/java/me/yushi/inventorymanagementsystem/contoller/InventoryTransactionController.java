/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import java.util.List;

import me.yushi.inventorymanagementsystem.model.InventoryTransaction;
import me.yushi.inventorymanagementsystem.model.Product;
import me.yushi.inventorymanagementsystem.service.InventoryTransactionService;
import me.yushi.inventorymanagementsystem.service.ProductService;

/**
 *
 * @author yushi
 */
public class InventoryTransactionController implements IInventoryTransactionController {

    InventoryTransactionService inventoryTransactionService;
    ProductService productService;

    public InventoryTransactionController(InventoryTransactionService inventoryTransactionService, ProductService productService) {
        this.inventoryTransactionService = inventoryTransactionService;
        this.productService = productService;
    }

    @Override
    // Create a new inventory transaction, save it to the repository, and return the
    // created
    public InventoryTransaction createInventoryTransaction(InventoryTransaction newInventoryTransaction) {
        // adjust the quantity of the product by the transation type
        changingQuantity(newInventoryTransaction, false);
        return inventoryTransactionService.createInventoryTransaction(newInventoryTransaction);
    }

    @Override
    // Update an inventory transaction, save it to the repository, and return the
    // updated
    public InventoryTransaction updateInventoryTransaction(InventoryTransaction updatedInventoryTransaction) {
        changingQuantity(updatedInventoryTransaction, false);
        return inventoryTransactionService.updateInventoryTransaction(updatedInventoryTransaction);
    }

    @Override
    // Get an inventory transaction by its ID
    public InventoryTransaction getInventoryTransactionByID(String inventoryTransationID) {
        return inventoryTransactionService.getInventoryTransactionByID(inventoryTransationID);
    }

    @Override
    // Delete an inventory transaction
    public boolean deleteInventoryTransaction(String inventoryTransationID) {
        this.changingQuantity(inventoryTransactionService.getInventoryTransactionByID(inventoryTransationID), true);
        return inventoryTransactionService.deleteInventoryTransaction(inventoryTransationID);
    }

    @Override
    // Get all inventory transactions
    public List<InventoryTransaction> getAllInventoryTransations() {
        return inventoryTransactionService.getAllInventoryTransations();
    }

    @Override
    // Get all products
    public List<Product> getAllProduct() {
        return productService.getAllProducts();
    }

    @Override
    // Get a product by its ID
    public Product getProduct(String produdctID) {
        return productService.getProductByID(produdctID);
    }

    // Adjust the quantity of the product by the transation type, if isDelete is
    // true, then the quantity will be reversed
    private void changingQuantity(InventoryTransaction transaction, boolean isDelete) {
        String prodcutID = transaction.getProductID();
        Product targetProdut = productService.getProductByID(prodcutID);
        if(targetProdut==null){
            return;
        }
        int productQuantity = targetProdut.getQuantity();
        int changedQuantity = transaction.getQuantity();
        // adjust the quantity of the product by the transation type
        switch (transaction.getTransactionType()) {
            case SALE:
                // If the transaction type is SALE, then the quantity of the product will be
                // decreased
                productQuantity = isDelete ? productQuantity + changedQuantity : productQuantity - changedQuantity;
                break;
            case PURCHASE:
                // If the transaction type is PURCHASE, then the quantity of the product will be
                // increased
                productQuantity = isDelete ? productQuantity - changedQuantity : productQuantity + changedQuantity;
                break;
            case SPOILAGE:
                // If the transaction type is SPOILAGE, then the quantity of the product will be
                // decreased
                productQuantity = isDelete ? productQuantity + changedQuantity : productQuantity - changedQuantity;
                break;
            default:
                throw new AssertionError();
        }
        // Update the product quantity
        targetProdut.setQuantity(productQuantity);
        
        productService.updateProduct(targetProdut);
    }

}
