/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

/**
 *
 * @author yushi
 */
public interface IDIContainer {
    <T> void register(Class<T> type, T instance);
    <T> T resolve(Class<T> type);
}
