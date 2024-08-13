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
public interface IProductDto {
    int getProductID();
    String getUnit();
    String getName();
    int getCategoryID();
    int getQuantity();
    double getPrice();
    Date getExpirationDate();
}
