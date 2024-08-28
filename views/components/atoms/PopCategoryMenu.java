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

public class PopCategoryMenu extends JPopupMenu {

    private JMenuItem addCategory;
    private JMenuItem editCategory;
    private JMenuItem deleteCategory;
    private JMenuItem refreshCategory;

    public PopCategoryMenu() {
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

        addCategory = new JMenuItem("Add category +");
        editCategory = new JMenuItem("Edit category");
        deleteCategory = new JMenuItem("Delete category");
        refreshCategory = new JMenuItem("Refresh");

        Font menuItemFont = new Font("Arial", Font.BOLD, 14);
        Insets menuItemPadding = new Insets(5, 10, 5, 10);
        Color menuItemColor = Color.GRAY;

        customizeMenuItem(addCategory, menuItemFont, menuItemPadding, menuItemColor);
        customizeMenuItem(editCategory, menuItemFont, menuItemPadding, menuItemColor);
        customizeMenuItem(deleteCategory, menuItemFont, menuItemPadding, menuItemColor);
        customizeMenuItem(refreshCategory, menuItemFont, menuItemPadding, menuItemColor);

        add(addCategory);
        add(editCategory);
        add(deleteCategory);
        add(refreshCategory);
    }

    private void customizeMenuItem(JMenuItem menuItem, Font font, Insets padding, Color color) {
        menuItem.setFont(font);
        menuItem.setBorder(new EmptyBorder(padding));
        menuItem.setForeground(color);
    }

    public JMenuItem getAddCategory() {
        return addCategory;
    }

    public JMenuItem getEditCategory() {
        return editCategory;
    }

    public JMenuItem getDeleteCategory() {
        return deleteCategory;
    }
    
    public JMenuItem getRefreshCategory() {
        return refreshCategory;
    }

}
