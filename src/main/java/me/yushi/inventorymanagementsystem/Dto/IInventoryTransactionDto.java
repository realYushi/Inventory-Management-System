/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.Dto;

import java.util.Date;

/**
 *
 * @author yushi
 */
public interface IInventoryTransactionDto {
    int getTransactionID();

    int getProductID();

    int getQuantity();

    Date getDate();

    TransactionType getTransactionType();
    double getPrice();

    enum TransactionType {
        PURCHASE,
        SALE,
        SPOILAGE
    }
}
