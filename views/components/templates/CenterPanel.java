package com.multibahana.inventoryapp.views.components.templates;

import com.multibahana.inventoryapp.views.components.molecules.Navbar;
import com.multibahana.inventoryapp.views.components.organisms.Product;
import com.multibahana.inventoryapp.views.components.organisms.Receipt;
import com.multibahana.inventoryapp.views.components.organisms.Sales;
import com.multibahana.inventoryapp.views.components.organisms.Vendor;
import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CenterPanel extends JPanel {

    private JSplitPane centerSplitPane;
    private JPanel contentPanel;
    private Navbar navbar;
    private Map<String, JPanel> panelCache;
    private JButton activeButton;

    public CenterPanel() {
        panelCache = new ConcurrentHashMap<>();
        initUI();
        setupListeners();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        List<String> panels = Arrays.asList("Sales", "Receipt", "Product", "Vendor");

        for (String panelName : panels) {
            Runnable task = () -> panelCache.put(panelName, getPanel(panelName));
            Thread thread = new Thread(task);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(CenterPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        navbar = new Navbar();
        contentPanel = panelCache.get("Receipt");
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
