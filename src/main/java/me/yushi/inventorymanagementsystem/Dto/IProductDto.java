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
    String getProductID();
    String getUnit();
    String getName();
    String getCategoryID();
    int getQuantity();
    double getPrice();
    Date getExpirationDate();
}
