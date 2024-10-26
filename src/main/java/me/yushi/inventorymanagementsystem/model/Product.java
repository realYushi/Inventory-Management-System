package me.yushi.inventorymanagementsystem.model;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "Product")
public class Product implements IProduct {
    @Id
    private String productID;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "categoryID", nullable = false)
    private Category category;

    @ManyToOne
    @JoinColumn(name = "supplierID", nullable = false)
    private Supplier supplier;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InventoryTransaction> transactions = new ArrayList<>();

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "unit", nullable = false)
    private String unit;

    @Column(name = "price", nullable = false)
    private double price;

    public Product() {
    }

    public Product(String productID, String name, Category category, Supplier supplier, int quantity, String unit,
            double price) {
        // if productID is empty, generate a uuid
        this.productID = (productID.isEmpty() ? UUID.randomUUID().toString() : productID);
        this.name = name;
        this.quantity = quantity;
        this.unit = unit;
        this.price = price;
        this.category = category;
        this.supplier = supplier;
    }

    @Override
    public String getProductID() {
        return this.productID;
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Category getCategory() {
        return this.category;
    }

    @Override
    public int getQuantity() {
        return this.quantity;
    }

    @Override
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    public Supplier getSupplier() {
        return this.supplier;
    }

    @Override
    public String getUnit() {
        return this.unit;
    }

    @Override
    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<InventoryTransaction> getTransactions() {
        return transactions;
    }

    public void addTransaction(InventoryTransaction transaction) {
        transactions.add(transaction);
        transaction.setProduct(this);
    }

    public void removeTransaction(InventoryTransaction transaction) {
        transactions.remove(transaction);
        transaction.setProduct(null);
    }
}
