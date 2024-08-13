/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.model;

import java.util.Date;
import me.yushi.inventorymanagementsystem.Dto.IInventoryTransactionDto;

/**
 *
 * @author yushi
 */
public class InventoryTransaction implements IInventoryTransaction {

    private int transactionID;
    private int productID;
    private int quantity;
    private Date date;
    private TransactionType transactionType;

    public InventoryTransaction(IInventoryTransactionDto inventoryTransactionDto) {
        this.transactionID = inventoryTransactionDto.getTransactionID();
        this.productID = inventoryTransactionDto.getProductID();
        this.quantity = inventoryTransactionDto.getQuantity();
        this.date = inventoryTransactionDto.getDate();
        this.transactionType = IInventoryTransaction.TransactionType
                .valueOf(inventoryTransactionDto.getTransactionType().name());

    }

    @Override
    public int getTransactionID() {
        return this.transactionID;
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setTransactionID(int transctionID) {
        this.transactionID = transctionID;
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getProductID() {
        return this.productID;
    }

    @Override
    public void setProductID(int productID) {
        this.productID = productID;
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getQuantity() {
        return this.quantity;
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Date getDate() {
        return this.date;
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public TransactionType getTransactionType() {
        return this.transactionType;
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
        // nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
