/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.multibahana.inventoryapp.views.components.organisms;

import com.multibahana.inventoryapp.context.TablesContext;
import com.multibahana.inventoryapp.views.components.atoms.TitleCenter;
import com.multibahana.inventoryapp.views.components.molecules.StockTable;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

/**
 *
 * @author mohammadnuridin
 */
public class Sales extends JPanel {

    private JPanel tables;

    public Sales() {
        setLayout(new BorderLayout());

        tables = new JPanel(new BorderLayout());

        JLabel label = new JLabel("Coming soon");
        label.setHorizontalAlignment(JLabel.CENTER); 
        label.setVerticalAlignment(JLabel.CENTER); 

        tables.add(label, BorderLayout.CENTER);

        JPanel salesPanel = new JPanel(new BorderLayout());
        salesPanel.add(new TitleCenter("Sales"), BorderLayout.CENTER);
//        salesPanel.add(new StockForm(), BorderLayout.SOUTH);

        JSplitPane salesSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, salesPanel, tables);
        add(salesSplitPane, BorderLayout.CENTER);

    }

}
