package com.multibahana.inventoryapp.views.components.atoms;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

public class TitleCenter extends JPanel {

    public TitleCenter(String title) {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel(title, JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); 
        titleLabel.setForeground(Color.BLACK); 
        
        titleLabel.setBorder(new EmptyBorder(10, 20, 10, 20));
        
        add(titleLabel, BorderLayout.CENTER);
        
        setBackground(Color.LIGHT_GRAY);
//        setBorder(new EmptyBorder(20, 20, 20, 20));
    }
}
