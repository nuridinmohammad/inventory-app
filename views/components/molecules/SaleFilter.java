package com.multibahana.inventoryapp.views.components.molecules;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class SaleFilter extends JPanel {

    private JTextField searchProduct;
    private JButton chooseProductButton;

    public SaleFilter() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(new EmptyBorder(6, 6, 6, 6));

        Font font = new Font("Arial", Font.BOLD, 17);

        JLabel searchProductLabel = new JLabel("Search product ");
        searchProduct = new JTextField();
        searchProduct.setFont(font);
        searchProduct.setBorder(new EmptyBorder(8, 8, 8, 8));

        JLabel chooseProductLabel = new JLabel("Choose product ");
        chooseProductButton = new JButton(" . . . ");
        configureButton(chooseProductButton);

        JPanel searchProductPanel = createPanel();
        searchProductPanel.add(searchProduct);

        JPanel chooseProductButtonPanel = createPanel();
        chooseProductButtonPanel.add(chooseProductButton);

        // Create and configure tempPanel
        JPanel tempPanel = new JPanel();
        tempPanel.setPreferredSize(new Dimension(40, 10)); 
        tempPanel.setMaximumSize(new Dimension(40, 10));  
        tempPanel.setLayout(new BoxLayout(tempPanel, BoxLayout.X_AXIS));

        add(searchProductLabel);
        add(searchProductPanel);
        add(tempPanel); 
        add(chooseProductLabel);
        add(chooseProductButtonPanel);
    }

    public JTextField getSearchProduct() {
        return searchProduct;
    }
    
    public JButton getChooseProductButton() {
        return chooseProductButton;
    }

    private JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(4, 4, 4, 4));
        return panel;
    }
    
    private void configureButton(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
//        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(Color.BLUE);
        button.setPreferredSize(new Dimension(40, button.getPreferredSize().height));
    }
}
