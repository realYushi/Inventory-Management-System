package me.yushi.inventorymanagementsystem.contoller;

import java.util.List;
import me.yushi.inventorymanagementsystem.model.Category;
import me.yushi.inventorymanagementsystem.model.Product;
import me.yushi.inventorymanagementsystem.model.Supplier;
import me.yushi.inventorymanagementsystem.service.ProductService;

/**
 *
 * @author yushi
 */
public class ProductController implements IProductController {

    private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Business logic for CRUD operations
    @Override
    public Product createProduct(Product newProduct) {
        if (newProduct == null) {
            System.out.println("Product is null");
            return null;
        }
        return productService.createProduct(newProduct);
    }

    @Override
    public Product updateProduct(Product updatedProduct) {
        if (updatedProduct == null) {
            System.out.println("Updated Product is null");
            return null;
        }
        return productService.updateProduct(updatedProduct);
    }

    @Override
    public Product getProductByID(String productID) {
        if (productID == null || productID.isEmpty()) {
            System.out.println("Product ID is null or empty");
            return null;
        }
        return productService.getProductByID(productID);
    }

    @Override
    public boolean deleteProduct(String productID) {
        if (productID == null || productID.isEmpty()) {
            System.out.println("Product ID is null or empty");
            return false;
        }
        return productService.deleteProduct(productID);
    }

    @Override
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @Override
    public Category getCategory(Product product) {
        if (product == null) {
            System.out.println("Product is null");
            return null;
        }
        return product.getCategory();
    }

    @Override
    public Supplier getSupplier(Product product) {
        if (product == null) {
            System.out.println("Product is null");
            return null;
        }
        return product.getSupplier();
    }
}
