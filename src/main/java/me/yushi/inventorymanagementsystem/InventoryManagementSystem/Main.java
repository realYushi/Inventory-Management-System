package me.yushi.inventorymanagementsystem.InventoryManagementSystem;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.GridLayout;
import com.googlecode.lanterna.gui2.Label;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.gui2.WindowBasedTextGUI;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {
        Terminal terminal = null;
        Screen screen = null;
        try {
            terminal = new DefaultTerminalFactory().createTerminal();
            screen = new TerminalScreen(terminal);
            screen.startScreen();

            WindowBasedTextGUI textGUI = new MultiWindowTextGUI(screen);
            BasicWindow window = new BasicWindow("Inventory Management System");

            Panel mainPanel = new Panel(new GridLayout(1));
                  // Create body panel
            Panel bodyPanel = new Panel(new LinearLayout(Direction.VERTICAL));

            // Create top navigation panel
            Panel topNavPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
            topNavPanel.addComponent(new Button("Dashboard", () -> showDashboard(bodyPanel)));
            topNavPanel.addComponent(new Button("Product", () -> showProduct(bodyPanel)));
            topNavPanel.addComponent(new Button("Supplier", () -> showSupplier(bodyPanel)));
            topNavPanel.addComponent(new Button("Transaction", () -> showTransaction(bodyPanel)));
            topNavPanel.addComponent(new Button("Category", () -> showCategory(bodyPanel)));

      

            // Create bottom panel for help
            Panel bottomPanel = new Panel(new LinearLayout(Direction.HORIZONTAL));
            bottomPanel.addComponent(new Label("Help: Press F1 for more info"));

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

    private static void showDashboard(Panel bodyPanel) {
        bodyPanel.removeAllComponents();
        bodyPanel.addComponent(new Label("Dashboard Content"));
    }

    private static void showProduct(Panel bodyPanel) {
        bodyPanel.removeAllComponents();
        bodyPanel.addComponent(new Label("Product Content"));
    }

    private static void showSupplier(Panel bodyPanel) {
        bodyPanel.removeAllComponents();
        bodyPanel.addComponent(new Label("Supplier Content"));
    }

    private static void showTransaction(Panel bodyPanel) {
        bodyPanel.removeAllComponents();
        bodyPanel.addComponent(new Label("Transaction Content"));
    }

    private static void showCategory(Panel bodyPanel) {
        bodyPanel.removeAllComponents();
        bodyPanel.addComponent(new Label("Category Content"));
    }

}
