package me.yushi.inventorymanagementsystem.model;

import java.util.Date;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class InventoryTransaction implements IInventoryTransaction {
    @Id
    private String transactionID;

    @ManyToOne
    @JoinColumn(name = "productID", nullable = false)
    private Product product;

    private int quantity;
    private Date date;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private double price;

    public InventoryTransaction() {
    }

    public InventoryTransaction(String transactionID, Product product, int quantity, Date date, TransactionType transactionType, double price) {
        this.transactionID = (transactionID == "" ? UUID.randomUUID().toString() : transactionID);
        this.quantity = quantity;
        this.date = date;
        this.transactionType = transactionType;
        this.price = price;
        this.product = product;
        // Note: product needs to be set separately via setter
    }

    @Override
    public String getTransactionID() {
        return this.transactionID;
    }

    @Override
    public String getProductID() {
        return  this.product.getProductID();
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Product getProduct() {
        return this.product;
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
    public Date getDate() {
        return this.date;
    }

    @Override
    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public TransactionType getTransactionType() {
        return this.transactionType;
    }

    @Override
    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    @Override
    public double getPrice() {
        return this.price;
    }

    @Override
    public void setPrice(double price) {
        this.price = price;
    }
}
