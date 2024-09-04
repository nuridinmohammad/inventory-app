/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.multibahana.inventoryapp.views.components.organisms;

import com.multibahana.inventoryapp.views.components.atoms.TitleCenter;
import com.multibahana.inventoryapp.views.components.molecules.SaleTable;
import java.awt.BorderLayout;
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

        tables = new SaleTable();
        
        JPanel salesPanel = new JPanel(new BorderLayout());
        salesPanel.add(new TitleCenter("SALES (Comming soon)"), BorderLayout.CENTER);
//        salesPanel.add(new SaleFilter(), BorderLayout.SOUTH);

        JSplitPane salesSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, salesPanel, tables);
        add(salesSplitPane, BorderLayout.CENTER);

    }

}
