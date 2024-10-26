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

    public InventoryTransactionController(InventoryTransactionService inventoryTransactionService,
            ProductService productService) {
        this.inventoryTransactionService = inventoryTransactionService;
        this.productService = productService;
    }

    @Override
    // Business logic for CRUD operations
    public InventoryTransaction createInventoryTransaction(InventoryTransaction newInventoryTransaction) {
        // change the quantity of the product by the transaction type
        changingQuantity(newInventoryTransaction, false);
        return inventoryTransactionService.createInventoryTransaction(newInventoryTransaction);
    }

    @Override
    public InventoryTransaction updateInventoryTransaction(InventoryTransaction updatedInventoryTransaction) {
        // change the quantity of the product, if the transaction is updated
        InventoryTransaction oldTransaction = inventoryTransactionService
                .getInventoryTransactionByID(updatedInventoryTransaction.getTransactionID());
        if (oldTransaction != null) {
            changingQuantity(oldTransaction, true);
        }
        // change the quantity of the product by the transaction type
        changingQuantity(updatedInventoryTransaction, false);
        return inventoryTransactionService.updateInventoryTransaction(updatedInventoryTransaction);
    }

    @Override
    public InventoryTransaction getInventoryTransactionByID(String inventoryTransationID) {
        return inventoryTransactionService.getInventoryTransactionByID(inventoryTransationID);
    }

    @Override
    public boolean deleteInventoryTransaction(String inventoryTransationID) {
        this.changingQuantity(inventoryTransactionService.getInventoryTransactionByID(inventoryTransationID), true);
        return inventoryTransactionService.deleteInventoryTransaction(inventoryTransationID);
    }

    @Override
    public List<InventoryTransaction> getAllInventoryTransations() {
        return inventoryTransactionService.getAllInventoryTransations();
    }

    @Override
    public List<Product> getAllProduct() {
        return productService.getAllProducts();
    }

    @Override
    public Product getProduct(String produdctID) {
        return productService.getProductByID(produdctID);
    }

    // Adjust the quantity of the product by the transation type, if isDelete is
    // true, then the quantity will be reversed
    private void changingQuantity(InventoryTransaction transaction, boolean isDelete) {
        String prodcutID = transaction.getProductID();
        Product targetProdut = productService.getProductByID(prodcutID);
        // If the product is not found, then return
        if (targetProdut == null) {
            return;
        }
        // Get the quantity of the product
        int productQuantity = targetProdut.getQuantity();
        int changedQuantity = transaction.getQuantity();
        // adjust the quantity of the product by the transation type
        switch (transaction.getTransactionType()) {
        case SALE:
            productQuantity = isDelete ? productQuantity + changedQuantity : productQuantity - changedQuantity;
            break;
        case PURCHASE:
            productQuantity = isDelete ? productQuantity - changedQuantity : productQuantity + changedQuantity;
            break;
        case SPOILAGE:
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
