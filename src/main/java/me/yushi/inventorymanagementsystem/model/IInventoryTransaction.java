/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.model;

import java.util.Date;

/**
 *
 * @author yushi
 */
public interface IInventoryTransaction {
    String getTransactionID();

    String getProductID();

    int getQuantity();

    void setQuantity(int quantity);

    Date getDate();

    void setDate(Date date);

    TransactionType getTransactionType();

    void setTransactionType(TransactionType type);
    double getPrice();
    void setPrice(double price);

    enum TransactionType {
        PURCHASE,
        SALE,
        SPOILAGE
    }
}
