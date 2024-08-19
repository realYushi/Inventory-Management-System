/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;

import java.util.List;
import java.util.Map;

/**
 *
 * @author yushi
 */
public interface IFileHandler <T> {
    List<T> readFromFile();
    void writeToFile(Map<String,T> objectList);
}
