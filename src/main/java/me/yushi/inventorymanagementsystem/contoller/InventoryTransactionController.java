/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.InventoryTransactionDto;
import me.yushi.inventorymanagementsystem.Dto.ProductDto;
import me.yushi.inventorymanagementsystem.repository.IUnitOfWork;
import me.yushi.inventorymanagementsystem.service.InventoryTransactionService;
import me.yushi.inventorymanagementsystem.service.ProductService;

/**
 *
 * @author yushi
 */
public class InventoryTransactionController implements IInventoryTransactionController {

    InventoryTransactionService inventoryTransactionService;
    ProductService productService;

    public InventoryTransactionController(IUnitOfWork unitOfWork) {
        this.inventoryTransactionService = new InventoryTransactionService(unitOfWork);
        this.productService = new ProductService(unitOfWork);
    }

    @Override
    // Create a new inventory transaction, save it to the repository, and return the
    // created
    public InventoryTransactionDto createInventoryTransaction(InventoryTransactionDto newIInventoryTransactionDto) {
        // adjust the quantity of the product by the transation type
        changingQuantity(newIInventoryTransactionDto, false);
        return inventoryTransactionService.createInventoryTransaction(newIInventoryTransactionDto);
    }

    @Override
    // Update an inventory transaction, save it to the repository, and return the
    // updated
    public InventoryTransactionDto updateInventoryTransaction(InventoryTransactionDto updatedInventoryTransactionDto) {
        changingQuantity(updatedInventoryTransactionDto, false);
        return inventoryTransactionService.updateInventoryTransaction(updatedInventoryTransactionDto);
    }

    @Override
    // Get an inventory transaction by its ID
    public InventoryTransactionDto getInventoryTransactionByID(String inventoryTransationID) {
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
    public List<InventoryTransactionDto> getAllInventoryTransations() {
        return inventoryTransactionService.getAllInventoryTransations();
    }

    @Override
    // Get all products
    public List<ProductDto> getAllProduct() {
        return productService.getAllProducts();
    }

    @Override
    // Get a product by its ID
    public ProductDto getProduct(String produdctID) {
        return productService.getProductByID(produdctID);
    }

    // Adjust the quantity of the product by the transation type, if isDelete is
    // true, then the quantity will be reversed
    private void changingQuantity(InventoryTransactionDto transactionDto, boolean isDelete) {
        String prodcutID = transactionDto.getProductID();
        ProductDto targetProdut = productService.getProductByID(prodcutID);
        if(targetProdut==null){
            return;
        }
        int productQuantity = targetProdut.getQuantity();
        int changedQuantity = transactionDto.getQuantity();
        // adjust the quantity of the product by the transation type
        switch (transactionDto.getTransactionType()) {
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
        productService.updateProduct(new ProductDto.Builder().productID(targetProdut.getProductID())
                .price(targetProdut.getPrice()).quantity(productQuantity).categoryID(targetProdut.getCategoryID())
                .unit(targetProdut.getUnit()).name(targetProdut.getName()).build());
    }

}
