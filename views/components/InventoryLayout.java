package com.multibahana.inventoryapp.views.components;

import com.multibahana.inventoryapp.views.components.templates.LeftPanel;
import com.multibahana.inventoryapp.views.components.templates.CenterPanel;
import javax.swing.JFrame;

import java.awt.BorderLayout;
import javax.swing.JSplitPane;

public class InventoryLayout extends JFrame {

    private JSplitPane leftSplitPane;

    public InventoryLayout() {
        setTitle("Inventory App");
        initUI();
        setLayout(new BorderLayout());
        add(leftSplitPane, BorderLayout.CENTER);
        setSize(1200, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void initUI() {
        LeftPanel leftPanel = new LeftPanel();
        CenterPanel centerPanel = new CenterPanel();

        leftSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, centerPanel);
        leftSplitPane.setDividerLocation(300);
    }
}
