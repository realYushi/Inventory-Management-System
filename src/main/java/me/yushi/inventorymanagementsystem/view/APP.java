/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package me.yushi.inventorymanagementsystem.view;

import com.googlecode.lanterna.gui2.BasicWindow;
import com.googlecode.lanterna.gui2.Button;
import com.googlecode.lanterna.gui2.Direction;
import com.googlecode.lanterna.gui2.LinearLayout;
import com.googlecode.lanterna.gui2.MultiWindowTextGUI;
import com.googlecode.lanterna.gui2.Panel;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import com.googlecode.lanterna.terminal.Terminal;

/**
 *
 * @author yushi
 */
public class APP {
    private String categoryPath;
    private String inventoryTransationPath;
    private String productPath;
    private String supplierPath;
    

    public APP(String category,String inventory,String product,String supplier) {
        this.categoryPath=category;
        this.inventoryTransationPath=inventory;
        this.productPath=product;
        this.supplierPath=supplier;
    }
    public void start(){
       try {
                // Create terminal and screen
                Terminal terminal = new DefaultTerminalFactory().createTerminal();
                Screen screen = new TerminalScreen(terminal);
                screen.startScreen();

                // Create window
                BasicWindow window = new BasicWindow("My First Lanterna App");

                // Create a panel to hold components
                Panel panel = new Panel();
                panel.setLayoutManager(new LinearLayout(Direction.VERTICAL));

                // Add a button to the panel
                Button button = new Button("Click Me!");
                button.addListener(new Button.Listener() {
                    @Override
                    public void onTriggered(Button button) {
                        System.out.println("Button clicked!");
                    }
                });
                panel.addComponent(button);

                // Set the panel as the window's component
                window.setComponent(panel);

                // Create the GUI and add the window
                MultiWindowTextGUI gui = new MultiWindowTextGUI(screen);
                gui.addWindowAndWait(window);

                // Stop the screen when done
                screen.stopScreen();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } 
    }
    
