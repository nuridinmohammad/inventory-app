package com.multibahana.inventoryapp.views.components.molecules;

import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class VendorFilter extends JPanel {

    public VendorFilter() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(new EmptyBorder(6, 6, 6, 6)); 

        Font font = new Font("Arial", Font.PLAIN, 16);
        
        JLabel searchNameLabel = new JLabel("Search name ");
        JTextField searchNameField = new JTextField();
        searchNameField.setFont(font);
        
        JLabel searchAddressLabel = new JLabel("Search address ");
        JTextField searchAddressField = new JTextField();
        searchAddressField.setFont(font);
        
        // Membungkus setiap JTextField dalam JPanel dengan EmptyBorder sebagai margin luar
        JPanel searchNamePanel = new JPanel();
        searchNamePanel.setLayout(new BoxLayout(searchNamePanel, BoxLayout.Y_AXIS));
        searchNamePanel.setBorder(new EmptyBorder(4, 4, 4, 4));
        searchNamePanel.add(searchNameField);

        JPanel searchAddressPanel = new JPanel();
        searchAddressPanel.setLayout(new BoxLayout(searchAddressPanel, BoxLayout.Y_AXIS));
        searchAddressPanel.setBorder(new EmptyBorder(4, 4, 4, 4));
        searchAddressPanel.add(searchAddressField);

        add(searchNameLabel);
        add(searchNamePanel);
        add(searchAddressLabel);
        add(searchAddressPanel);
    }
}
