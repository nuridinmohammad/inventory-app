package com.multibahana.inventoryapp.views.components.molecules;

import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class VendorFilter extends JPanel {

    private JTextField searchNameField;
    private JTextField searchAddressField;
    private JButton searchButton;

    public VendorFilter() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(new EmptyBorder(6, 6, 6, 6));

        Font font = new Font("Arial", Font.PLAIN, 16);

        JLabel searchNameLabel = new JLabel("Search name ");
        searchNameField = new JTextField();
        searchNameField.setFont(font);

        JLabel searchAddressLabel = new JLabel("Search address ");
        searchAddressField = new JTextField();
        searchAddressField.setFont(font);
        
        searchButton = new JButton("Search");
        searchButton.setFont(font);

        JPanel searchNamePanel = createPanel();
        searchNamePanel.add(searchNameField);

        JPanel searchAddressPanel = createPanel();
        searchAddressPanel.add(searchAddressField);

        JPanel searchButtonPanel = createPanel();
        searchButtonPanel.add(searchButton);

        add(searchNameLabel);
        add(searchNamePanel);
        add(searchAddressLabel);
        add(searchAddressPanel);
        add(searchButtonPanel);
    }

    // Getter methods
    public JTextField getSearchNameField() {
        return searchNameField;
    }

    public JTextField getSearchAddressField() {
        return searchAddressField;
    }
    
    public JButton getSearchButton() {
        return searchButton;
    }

    private JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(4, 4, 4, 4));
        return panel;
    }
}
