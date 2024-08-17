/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

/**
 *
 * @author yushi
 */
public interface IMapper <D,M> {

    D toDto(M model);
    M toModel(D dto);
    
}
