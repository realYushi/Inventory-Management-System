package me.yushi.inventorymanagementsystem.view;

import com.googlecode.lanterna.gui2.*;
import com.googlecode.lanterna.gui2.dialogs.*;
import com.googlecode.lanterna.gui2.table.Table;
import java.util.List;
import me.yushi.inventorymanagementsystem.Dto.CategoryDto;
import me.yushi.inventorymanagementsystem.Dto.IProductDto;
import me.yushi.inventorymanagementsystem.Dto.ProductDto;
import me.yushi.inventorymanagementsystem.contoller.ProductController;
import me.yushi.inventorymanagementsystem.repository.CategoryRepository;
import me.yushi.inventorymanagementsystem.repository.ProductRepository;

/**
 * Product management view using Lanterna GUI framework.
 * @author yushi
 */
public class ProductView extends Panel {

    private ProductController controller;
    private Table<String> productTable;
    private WindowBasedTextGUI textGUI;
    private int selectedRow = -1;

    public ProductView(ProductRepository productRepository, CategoryRepository categoryRepository, WindowBasedTextGUI textGUI) {
        this.controller = new ProductController(productRepository, categoryRepository);
        this.textGUI = textGUI;
        setupUI();
        loadProducts();
        addTableSelectionListener();
    }

    private void setupUI() {
        Panel mainPanel = new Panel();
        mainPanel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

        mainPanel.addComponent(new Label("Product Management").setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center)));

        productTable = new Table<>(" ", "ID", "Name", "Category", "Quantity", "Unit","Price" );
        mainPanel.addComponent(productTable);

        Panel buttonPanel = new Panel();
        buttonPanel.setLayoutManager(new LinearLayout(Direction.HORIZONTAL));

        Button refreshButton = new Button("Refresh", this::loadProducts);
        buttonPanel.addComponent(refreshButton);

        Button addProductButton = new Button("Add Product", this::addProduct);
        buttonPanel.addComponent(addProductButton);

        Button updateProductButton = new Button("Update Product", this::updateProduct);
        buttonPanel.addComponent(updateProductButton);

        Button deleteProductButton = new Button("Delete Product", this::deleteProduct);
        buttonPanel.addComponent(deleteProductButton);

        mainPanel.addComponent(buttonPanel);
        this.addComponent(mainPanel);
    }

    private void loadProducts() {
        productTable.getTableModel().clear();
        List<ProductDto> products = controller.getAllProducts();
        for (IProductDto product : products) {
            CategoryDto category = controller.getCategory(product.getCategoryID());
            String categoryName = category != null ? category.getCategoryName() : "N/A";
            String price = String.valueOf(product.getPrice())+" $";
            productTable.getTableModel().addRow("",
                product.getProductID(),
                product.getName(),
                categoryName,
                String.valueOf(product.getQuantity()),
                product.getUnit(),
                price
                );
        }
    }
    
    private void addProduct() {
        String productName = TextInputDialog.showDialog(textGUI, "Add Product", "Product Name:", "");
        String productQuantity=TextInputDialog.showDialog(textGUI, "Add Product", "Product Quantity:", "");
        String productUnit =TextInputDialog.showDialog(textGUI, "Add Product", "Product Unit:", "");
        String productPrice= TextInputDialog.showDialog(textGUI, "Add Product", "Product Price:", "");
        String categoryID = selectCategory();
        if (productName != null && !productName.trim().isEmpty() && categoryID != null) {
            ProductDto product = new ProductDto.Builder()
                    .name(productName)
                    .quantity(Integer.parseInt(productQuantity))
                    .price(Double.parseDouble(productPrice))
                    .unit(productUnit)
                    .categoryID(categoryID)
                    .build();
            IProductDto newProduct = controller.createProduct(product);
            if (newProduct != null) {
                loadProducts();
                MessageDialog.showMessageDialog(textGUI, "Success", "Product added successfully!", MessageDialogButton.OK);
            } else {
                MessageDialog.showMessageDialog(textGUI, "Error", "Failed to add product.", MessageDialogButton.OK);
            }
        } else {
            MessageDialog.showMessageDialog(textGUI, "Error", "Invalid input or no category selected.", MessageDialogButton.OK);
        }
    }
    
    private void updateProduct() {
        selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            MessageDialog.showMessageDialog(textGUI, "Error", "No product selected.", MessageDialogButton.OK);
            return;
        }
        String productID = productTable.getTableModel().getRow(selectedRow).get(1);
        
        String newProductName = TextInputDialog.showDialog(textGUI, "Add Product", "Product Name:", "");
        String newProductQuantity=TextInputDialog.showDialog(textGUI, "Add Product", "Product Quantity:", "");
        String newProductUnit =TextInputDialog.showDialog(textGUI, "Add Product", "Product Unit:", "");
        String newProductPrice= TextInputDialog.showDialog(textGUI, "Add Product", "Product Price:", "");
        String newCategoryID = selectCategory();
        if (newProductName != null && !newProductName.trim().isEmpty() && newCategoryID != null) {
            ProductDto updatedProduct = new ProductDto.Builder()
                    .name(newProductName)
                    .quantity(Integer.parseInt(newProductQuantity))
                    .price(Double.parseDouble(newProductPrice))
                    .unit(newProductUnit)
                    .categoryID(newCategoryID)
                    .productID(productID)
                    .build();
            ProductDto result = controller.updateProduct(updatedProduct);
            if (result != null) {
                loadProducts();
                MessageDialog.showMessageDialog(textGUI, "Success", "Product updated successfully!", MessageDialogButton.OK);
            } else {
                MessageDialog.showMessageDialog(textGUI, "Error", "Failed to update product.", MessageDialogButton.OK);
            }
        } else {
            MessageDialog.showMessageDialog(textGUI, "Error", "Invalid input or no category selected.", MessageDialogButton.OK);
        }
        selectedRow = -1;
    }

    private void deleteProduct() {
        selectedRow = productTable.getSelectedRow();
        if (selectedRow == -1) {
            MessageDialog.showMessageDialog(textGUI, "Error", "No product selected.", MessageDialogButton.OK);
            return;
        }
        String productID = productTable.getTableModel().getRow(selectedRow).get(1);
        Boolean result = controller.deleteProduct(productID);
        if (result != null) {
            loadProducts();
            MessageDialog.showMessageDialog(textGUI, "Success", "Product deleted successfully!", MessageDialogButton.OK);
        } else {
            MessageDialog.showMessageDialog(textGUI, "Error", "Failed to delete product.", MessageDialogButton.OK);
        }
        selectedRow = -1;
    }

    private void addTableSelectionListener() {
        productTable.setSelectAction(() -> {
            int selectedRow = productTable.getSelectedRow();
            if (selectedRow != -1) {
                for (int row = 0; row < productTable.getTableModel().getRowCount(); row++) {
                    productTable.getTableModel().setCell(0, row, "");
                }
                productTable.getTableModel().setCell(0, selectedRow, "*");
            }
        });
    }

    private String selectCategory() {
        List<CategoryDto> categoryDtos = controller.getAllCategory();
        if (categoryDtos.isEmpty()) {
            MessageDialog.showMessageDialog(textGUI, "No Category", "There are no categories available.", MessageDialogButton.OK);
            return null;
        }
        String[] categoryNames = categoryDtos.stream().map(CategoryDto::getCategoryName).toArray(String[]::new);
        String selectedCategoryName = ListSelectDialog.showDialog(textGUI, "Select Category", "Choose a Category:", categoryNames);
        if (selectedCategoryName != null) {
            return categoryDtos.stream()
                            .filter(s -> s.getCategoryName().equals(selectedCategoryName))
                            .findFirst()
                            .map(CategoryDto::getCategoryID)
                            .orElse(null);
        }
        return null;
    }
}