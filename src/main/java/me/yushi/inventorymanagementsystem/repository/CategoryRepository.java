/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;
import java.util.List;
import jakarta.persistence.EntityManager;
import me.yushi.inventorymanagementsystem.model.Category;

/**
 *
 * @author yushi
 */
public class CategoryRepository implements ICategoryRepository {


    public CategoryRepository() {
    }

    @Override
    public Category createCategory(Category newCategory,EntityManager em) {
        em.persist(newCategory);
        return newCategory;
    }

    @Override
    public Category readCategory(String categoryID,EntityManager em) {
        Category category = em.find(Category.class, categoryID);
        return category;
    }

    @Override
    public Category updateCategory(Category newCategory,EntityManager em) {
        em.merge(newCategory);
        return newCategory;
    }

    @Override
    public boolean deleteCategory(String categoryID,EntityManager em) {
        Category category = em.find(Category.class, categoryID);
        if (category != null) {
            em.remove(category);
            return true;
        }
        return false;
    }

    @Override
    public List<Category> getAllCategories(EntityManager em) {
        return em.createQuery("SELECT c FROM Category c", Category.class).getResultList();
    }
    @Override
    public boolean haveLinkedProduct(String categoryID,EntityManager em){
        Category category = em.find(Category.class, categoryID);
        if(category.getProducts().isEmpty()){
            return false;
        }
        return true;
    }

}
