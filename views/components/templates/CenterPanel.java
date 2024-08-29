package com.multibahana.inventoryapp.views.components.templates;

import com.multibahana.inventoryapp.views.components.molecules.Navbar;
import com.multibahana.inventoryapp.views.components.organisms.Product;
import com.multibahana.inventoryapp.views.components.organisms.Receipt;
import com.multibahana.inventoryapp.views.components.organisms.Sales;
import com.multibahana.inventoryapp.views.components.organisms.Stock;
import com.multibahana.inventoryapp.views.components.organisms.Vendor;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.util.HashMap;
import java.util.Map;

public class CenterPanel extends JPanel {

    private JSplitPane centerSplitPane;
    private JPanel contentPanel;
    private Navbar navbar;
    private Map<String, JPanel> panelCache;
    private JButton activeButton;

    public CenterPanel() {
        panelCache = new HashMap<>();
        initUI();
        setupListeners();
    }

    private void initUI() {
        setLayout(new BorderLayout());
        panelCache.put("Sales", new Sales());
        panelCache.put("Product", new Product());
        panelCache.put("Receipt", new Receipt());
        panelCache.put("Vendor", new Vendor());
        panelCache.put("Stock", new Stock());

        navbar = new Navbar();
        contentPanel = getPanel("Sales");
        centerSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, navbar, contentPanel);
        add(centerSplitPane, BorderLayout.CENTER);
    }

    private void setupListeners() {
        navbar.getButtons().forEach(button -> button.addActionListener(e -> {
            if (activeButton != null) {
                activeButton.setForeground(Color.WHITE);
            }
            button.setForeground(Color.YELLOW);
            activeButton = button;

            String buttonText = button.getText();
            updateContentPanel(getPanel(buttonText));
        }));
    }

    private JPanel getPanel(String panelName) {
        if (panelCache.containsKey(panelName)) {
            return panelCache.get(panelName);
        } else {
            JPanel panel = null;
            switch (panelName) {
                case "Sales":
                    panel = new Sales();
                    break;
                case "Product":
                    panel = new Product();
                    break;
                case "Receipt":
                    panel = new Receipt();
                    break;
                case "Vendor":
                    panel = new Vendor();
                    break;
                case "Stock":
                    panel = new Stock();
                    break;
                default:
                    System.out.println("Unknown option: " + panelName);
                    return null;
            }

            panelCache.put(panelName, panel);
            return panel;
        }
    }

    private void updateContentPanel(JPanel newPanel) {
        if (newPanel != null && newPanel != contentPanel) {
            centerSplitPane.setBottomComponent(newPanel);
            contentPanel = newPanel;
            revalidate();
            repaint();
        }
    }
}
