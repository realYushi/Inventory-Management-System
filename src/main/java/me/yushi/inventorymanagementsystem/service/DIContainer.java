/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.service;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author yushi
 */
public class DIContainer implements IDIContainer {

    private final Map<Class<?>, Object> instances = new HashMap<>();

    @Override
    public <T> void register(Class<T> type, T instance) {
        instances.put(type, instance);
    }

    @Override
    public <T> T resolve(Class<T> type) {
        T instance = (T) instances.get(type);

        if (instance == null) {
            throw new IllegalArgumentException("No registered instance found for type: " + type.getName());
        }

        return instance;
    }
}
