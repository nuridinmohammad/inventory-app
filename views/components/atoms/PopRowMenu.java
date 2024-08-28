/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.multibahana.inventoryapp.views.components.atoms;

/**
 *
 * @author mohammadnuridin
 */

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

public class PopRowMenu extends JPopupMenu {

    private JMenuItem editRow;
    private JMenuItem deleteRow;
    private JMenuItem refresh;

    public PopRowMenu() {
        setName("Menu");

        JLabel header = new JLabel("Menu Options");
        header.setFont(new Font("Arial", Font.BOLD, 16));
        header.setForeground(Color.darkGray);
        header.setOpaque(true);
        header.setBackground(getBackground());

        int headerTopPadding = 10;
        int headerLeftPadding = 10;
        int headerBottomPadding = 5;
        int headerRightPadding = 10;
        header.setBorder(new EmptyBorder(headerTopPadding, headerLeftPadding, headerBottomPadding, headerRightPadding));

        add(header);

        editRow = new JMenuItem("Edit item");
        deleteRow = new JMenuItem("Delete item");
        refresh = new JMenuItem("Refresh");

        Font menuItemFont = new Font("Arial", Font.BOLD, 14);
        Insets menuItemPadding = new Insets(5, 10, 5, 10);
        Color menuItemColor = Color.GRAY;

        customizeMenuItem(editRow, menuItemFont, menuItemPadding, menuItemColor);
        customizeMenuItem(deleteRow, menuItemFont, menuItemPadding, menuItemColor);
        customizeMenuItem(refresh, menuItemFont, menuItemPadding, menuItemColor);

        add(editRow);
        add(deleteRow);
        add(refresh);
    }

    private void customizeMenuItem(JMenuItem menuItem, Font font, Insets padding, Color color) {
        menuItem.setFont(font);
        menuItem.setBorder(new EmptyBorder(padding));
        menuItem.setForeground(color);
    }


    public JMenuItem getEditRow() {
        return editRow;
    }

    public JMenuItem getDeleteRow() {
        return deleteRow;
    }
   
    public JMenuItem getRefresh() {
        return refresh;
    }

}
