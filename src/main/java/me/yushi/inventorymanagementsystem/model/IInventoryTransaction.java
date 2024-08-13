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
    int getTransactionID();
    void setTransactionID(int transctionID);

    int getProductID();
    void setProductID(int productID);

    int getQuantity();
    void setQuantity(int quantity);

    Date getDate();
    void setDate(Date date);

    TransactionType getTransactionType();
    void setTransactionType(TransactionType type); 

    enum TransactionType{
        PURCHASE,
        SALE,
        SPOILAGE
    }
}
