/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.repository;
import java.util.List;
import jakarta.persistence.EntityManager;
import me.yushi.inventorymanagementsystem.model.Product;

/**
 *
 * @author yushi
 */
public class ProductRepository implements IProductRepository {


    public ProductRepository() {
    }

    @Override
    public Product createProduct(Product newProduct,EntityManager em) {
        em.persist(newProduct);
        return newProduct;
    }

    @Override
    public Product readProduct(String productID,EntityManager em) {
        return em.find(Product.class, productID);
    }

    @Override
    public Product updateProduct(Product updatedProduct,EntityManager em) {
        return em.merge(updatedProduct);
    }

    @Override
    public boolean deleteProduct(String productID,EntityManager em) {
        if (em.find(Product.class, productID) != null) {
            em.remove(em.find(Product.class, productID));
            return true;
        }
        return false;
        
    }

    @Override
    public List<Product> getAllProducts(EntityManager em) {
        return em.createQuery("SELECT p FROM Product p", Product.class).getResultList();
    }

    @Override
    public String getCategoryID(String productID, EntityManager em) {
        return em.find(Product.class, productID).getCategoryID();
    }

    @Override
    public String getSupplierID(String productID, EntityManager em) {
        return em.find(Product.class, productID).getSupplierID();
    }
    


}
