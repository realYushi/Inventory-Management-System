/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.view;

import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Borders;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.EmptySpace;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.gui2.dialogs.MessageDialog;
import com.googlecode.lanterna.gui2.dialogs.MessageDialogButton;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;
import me.yushi.inventorymanagementsystem.model.Category;
import me.yushi.inventorymanagementsystem.model.InventoryTransaction;
import me.yushi.inventorymanagementsystem.model.Product;
import me.yushi.inventorymanagementsystem.model.Supplier;
import me.yushi.inventorymanagementsystem.repository.CategoryRepository;
import me.yushi.inventorymanagementsystem.repository.FileHandler;
import me.yushi.inventorymanagementsystem.repository.IUnitOfWork;
import me.yushi.inventorymanagementsystem.repository.InventoryTransactionRepository;
import me.yushi.inventorymanagementsystem.repository.ProductRepository;
import me.yushi.inventorymanagementsystem.repository.SupplierRepository;
import me.yushi.inventorymanagementsystem.repository.UnitOfWork;

/**
 *
 * @author yushi
 */
public class APP {

    private IUnitOfWork unitOfWork;

    public APP(String categoryFile, String transactionFile, String productFile, String supplierFile)
            throws IOException {
        // Initialize FileHandlers
        FileHandler<Category> categoryFileHandler = new FileHandler<>(Category.class, categoryFile);
        FileHandler<Product> productFileHandler = new FileHandler<>(Product.class, productFile);
        FileHandler<InventoryTransaction> transactionFileHandler = new FileHandler<>(InventoryTransaction.class,
                transactionFile);
        FileHandler<Supplier> supplierFileHandler = new FileHandler<>(Supplier.class, supplierFile);

        // Initialize Repositories
        CategoryRepository categoryRepository = new CategoryRepository(categoryFileHandler);
        ProductRepository productRepository = new ProductRepository(productFileHandler);
        InventoryTransactionRepository transactionRepository = new InventoryTransactionRepository(
                transactionFileHandler);
        SupplierRepository supplierRepository = new SupplierRepository(supplierFileHandler);

        // Initialize UnitOfWork
        this.unitOfWork = new UnitOfWork(categoryRepository, transactionRepository, productRepository,
                supplierRepository);

    }

    public void start() throws IOException {
        Terminal terminal = null;
        Screen screen = null;
        try {
            // Create terminal and screen
            terminal = new DefaultTerminalFactory().createTerminal();
            screen = new TerminalScreen(terminal);
            screen.startScreen();
            // Create text GUI and window
            WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
            BasicWindow window = new BasicWindow("Inventory Management System");
            // Create main panel
            Panel mainPanel = new Panel(new GridLayout(1));
            mainPanel.setLayoutData(
                    GridLayout.createLayoutData(GridLayout.Alignment.CENTER, GridLayout.Alignment.CENTER, true, true));
            // Create body panel
            Panel bodyPanel = new Panel(new LinearLayout(Direction.VERTICAL));
            bodyPanel.setLayoutData(LinearLayout.createLayoutData(LinearLayout.Alignment.Center));

            // Create top navigation panel
            Panel topNavPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
            topNavPanel.addComponent(new Button("Dashboard", () -> showDashboard(bodyPanel, unitOfWork, textGUI)));
            topNavPanel.addComponent(new Button("Product", () -> showProduct(bodyPanel, unitOfWork, textGUI)));
            topNavPanel.addComponent(new Button("Supplier", () -> showSupplier(bodyPanel, unitOfWork, textGUI)));
            topNavPanel.addComponent(new Button("Transaction", () -> showTransaction(bodyPanel, unitOfWork, textGUI)));
            topNavPanel.addComponent(new Button("Category", () -> showCategory(bodyPanel, unitOfWork, textGUI)));
            topNavPanel.addComponent(new Button("Exit", () -> confirmExit(textGUI)));

            // Create bottom panel for help
            Panel bottomPanel = new Panel(new LinearLayout(Direction.VERTICAL));
            bottomPanel.addComponent(new EmptySpace(new TerminalSize(0, 1)));
            bottomPanel.withBorder(Borders.singleLine("Help"));
            bottomPanel.addComponent(
                    new Label("Help: Use arrow keys for navigation. Press Enter or Spacebar to activate buttons. "));
            bottomPanel.addComponent(new Label("If display is abnormal, adjust the terminal size."));

            // Add sub-panels to main panel
            mainPanel.addComponent(topNavPanel);
            mainPanel.addComponent(bodyPanel);
            mainPanel.addComponent(bottomPanel);

            // Set main panel as the content of the window
            window.setComponent(mainPanel);

            // Add window to the GUI and run
            textGUI.addWindowAndWait(window);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (screen != null) {
                try {
                    screen.stopScreen();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Navigation item
    private static void showDashboard(Panel bodyPanel, IUnitOfWork unitOfWork, WindowBasedTextGUI textGUI) {
        bodyPanel.removeAllComponents();
        bodyPanel.addComponent(new DashboardView(unitOfWork, textGUI));
    }

    // Navigation item
    private static void showProduct(Panel bodyPanel, IUnitOfWork unitOfWork, WindowBasedTextGUI textGUI) {
        bodyPanel.removeAllComponents();
        bodyPanel.addComponent(new ProductView(unitOfWork, textGUI));
    }

    // Navigation item
    private static void showSupplier(Panel bodyPanel, IUnitOfWork unitOfWork, WindowBasedTextGUI textGUI) {
        bodyPanel.removeAllComponents();
        bodyPanel.addComponent(new SupplierView(unitOfWork, textGUI));
    }

    // navigation item
    private static void showTransaction(Panel bodyPanel, IUnitOfWork unitOfWork, WindowBasedTextGUI textGUI) {
        bodyPanel.removeAllComponents();
        bodyPanel.addComponent(new InventoryTransactionView(unitOfWork, textGUI));
    }

    // navigation item
    private static void showCategory(Panel bodyPanel, IUnitOfWork unitOfWork, WindowBasedTextGUI textGUI) {
        bodyPanel.removeAllComponents();
        bodyPanel.addComponent(new CategoryView(unitOfWork, textGUI));
    }

    // Exit confirmation dialog
    private static void confirmExit(WindowBasedTextGUI textGUI) {
        boolean confirmExit = MessageDialog.showMessageDialog(textGUI, "Exit", "Are you sure you want to exit?",
                MessageDialogButton.Yes, MessageDialogButton.No) == MessageDialogButton.Yes;

        if (confirmExit) {
            textGUI.getActiveWindow().close();
            System.exit(0);
        }
    }
}
