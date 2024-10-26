package me.yushi.inventorymanagementsystem.model;

import java.util.UUID;
import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Category")
public class Category implements ICategory {
    @Id
    private String categoryID;

    @Column(name = "categoryName", nullable = false)
    private String categoryName;

    @OneToMany(mappedBy = "category")
    private List<Product> products = new ArrayList<>();

    public Category() {
    }

    public Category(String categoryName, String categoryID) {
        this.categoryName = categoryName;
        // if categoryID is empty, generate a uuid
        this.categoryID = (categoryID.isEmpty() ? UUID.randomUUID().toString() : categoryID);
    }

    @Override
    public String getCategoryID() {
        return this.categoryID;
    }

    @Override
    public String getCategoryName() {
        return this.categoryName;
    }

    @Override
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public List<IProduct> getProducts() {
        return new ArrayList<>(this.products);
    }

    public void addProduct(Product product) {
        if (product != null && !products.contains(product)) {
            products.add(product);
            product.setCategory(this);
        }
    }

    public void removeProduct(Product product) {
        if (product != null && products.contains(product)) {
            products.remove(product);
            product.setCategory(null);
        }
    }
}
