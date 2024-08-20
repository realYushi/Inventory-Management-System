/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.contoller;

import java.util.List;
import static me.yushi.inventorymanagementsystem.Dto.IInventoryTransactionDto.TransactionType.PURCHASE;
import static me.yushi.inventorymanagementsystem.Dto.IInventoryTransactionDto.TransactionType.SALE;
import static me.yushi.inventorymanagementsystem.Dto.IInventoryTransactionDto.TransactionType.SPOILAGE;
import me.yushi.inventorymanagementsystem.Dto.InventoryTransactionDto;
import me.yushi.inventorymanagementsystem.Dto.ProductDto;
import me.yushi.inventorymanagementsystem.repository.InventoryTransactionRepository;
import me.yushi.inventorymanagementsystem.repository.ProductRepository;
import me.yushi.inventorymanagementsystem.service.InventoryTransactionService;
import me.yushi.inventorymanagementsystem.service.ProductService;

/**
 *
 * @author yushi
 */
public class InventoryTransactionController implements IInventoryTransactionController {

    InventoryTransactionService inventoryTransactionService;
    ProductService productService;

    public InventoryTransactionController(InventoryTransactionRepository transactionRepository, ProductRepository productRepository) {
        this.inventoryTransactionService = new InventoryTransactionService(transactionRepository);
        this.productService = new ProductService(productRepository);
    }

    @Override
    public InventoryTransactionDto createInventoryTransaction(InventoryTransactionDto newIInventoryTransactionDto) {
        changingQuantity(newIInventoryTransactionDto);

        return inventoryTransactionService.createInventoryTransaction(newIInventoryTransactionDto);
    }

    @Override
    public InventoryTransactionDto updateInventoryTransaction(InventoryTransactionDto updatedInventoryTransactionDto) {
        changingQuantity(updatedInventoryTransactionDto);
        return inventoryTransactionService.updateInventoryTransaction(updatedInventoryTransactionDto);
    }

    @Override
    public InventoryTransactionDto getInventoryTransactionByID(String inventoryTransationID) {
        return inventoryTransactionService.getInventoryTransactionByID(inventoryTransationID);
    }

    @Override
    public boolean deleteInventoryTransaction(String inventoryTransationID) {
        return inventoryTransactionService.deleteInventoryTransaction(inventoryTransationID);
    }

    @Override
    public List<InventoryTransactionDto> getAllInventoryTransations() {
        return inventoryTransactionService.getAllInventoryTransations();
    }

    @Override
    public List<ProductDto> getAllProduct() {
        return productService.getAllProducts();
    }

    @Override
    public ProductDto getProduct(String produdctID) {
        return productService.getProductByID(produdctID);
    }

    private void changingQuantity(InventoryTransactionDto transactionDto) {
        String prodcutID = transactionDto.getProductID();
        ProductDto targetProdut = productService.getProductByID(prodcutID);
        int productQuantity = targetProdut.getQuantity();
        int changedQuantity = transactionDto.getQuantity();
        switch (transactionDto.getTransactionType()) {
            case SALE:
                productQuantity = productQuantity - changedQuantity;
                break;
            case PURCHASE:
                productQuantity = productQuantity + changedQuantity;
                break;
            case SPOILAGE:
                productQuantity = productQuantity - changedQuantity;
                break;
            default:
                throw new AssertionError();
        }
        productService.updateProduct(new ProductDto.Builder().
                productID(targetProdut.getProductID()).
                price(targetProdut.getPrice()).
                quantity(productQuantity).
                categoryID(targetProdut.getCategoryID()).
                unit(targetProdut.getUnit()).
                name(targetProdut.getName()).
                build());
    }

}
