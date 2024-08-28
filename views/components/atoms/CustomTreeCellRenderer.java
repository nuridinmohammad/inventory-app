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
import java.awt.Component;
import java.awt.Font;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;

public class CustomTreeCellRenderer extends DefaultTreeCellRenderer {

    private ImageIcon categoryIcon;
    private ImageIcon categoryIconHome;

    public CustomTreeCellRenderer() {
        ImageIcon icon = new ImageIcon("/home/mohammadnuridin/Developer/JAVA/TANGERANG/CountryApp/src/images/folder.png");
        ImageIcon iconHome = new ImageIcon("/home/mohammadnuridin/Developer/JAVA/TANGERANG/CountryApp/src/images/home.png");

        Image img = icon.getImage();
        Image imgHome = iconHome.getImage();

        Image newImg = img.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);
        Image newImgHome = imgHome.getScaledInstance(25, 25, java.awt.Image.SCALE_SMOOTH);

        categoryIcon = new ImageIcon(newImg);
        categoryIconHome = new ImageIcon(newImgHome);
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
        Font font = getFont().deriveFont(Font.PLAIN, 16);
        setFont(font);

        if (value instanceof DefaultMutableTreeNode) {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
            Object userObject = node.getUserObject();
            if (userObject != null) {
                String userObjectString = userObject.toString();
                if ("Category".equals(userObjectString)) {
                    setBackground(Color.BLUE);
                    setForeground(Color.BLACK);
                    setIcon(categoryIconHome);
                } else {
                    setIcon(leaf ? categoryIcon : categoryIcon);
                }
            } else {
                setBackground(Color.WHITE);
                setForeground(Color.BLACK);
                setIcon(leaf ? categoryIcon : categoryIcon);
            }
        }

        return this;
    }

}
