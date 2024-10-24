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
@Table(name = "Supplier")
public class Supplier implements ISupplier {
    @Id
    private String supplierID;

    @Column(name = "supplierName", nullable = false)
    private String supplierName;

    @OneToMany(mappedBy = "supplier")
    private List<Product> products = new ArrayList<>();

    public Supplier() {
    }

    public Supplier(String supplierID, String supplierName) {
        this.supplierID = (supplierID == null ? UUID.randomUUID().toString() : supplierID);
        this.supplierName = supplierName;
    }

    @Override
    public String getSupplierID() {
        return this.supplierID;
    }

    @Override
    public String getSupplierName() {
        return this.supplierName;
    }

    @Override
    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    @Override
    public List<IProduct> getProducts() {
        return new ArrayList<>(this.products);
    }

    public void addProduct(Product product) {
        if (product != null && !products.contains(product)) {
            products.add(product);
            product.setSupplier(this);
        }
    }

    public void removeProduct(Product product) {
        if (product != null && products.contains(product)) {
            products.remove(product);
            product.setSupplier(null);
        }
    }
}
