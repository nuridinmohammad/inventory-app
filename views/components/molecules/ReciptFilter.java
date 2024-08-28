package com.multibahana.inventoryapp.views.components.molecules;

import com.toedter.calendar.JDateChooser;
import java.awt.Font;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ReciptFilter extends JPanel {

    public ReciptFilter() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(new EmptyBorder(6, 6, 6, 6)); 

        Font font = new Font("Arial", Font.PLAIN, 16);
        
        JLabel searchLabel = new JLabel("Search : ");
        JTextField searchField = new JTextField();
        searchField.setFont(font);
        
        JDateChooser chooser = new JDateChooser();
        chooser.setDateFormatString("yyyy-MM-dd");
        chooser.setFont(font);
        
        JComboBox<String> vendorComboBox = new JComboBox<>();
        vendorComboBox.addItem("-- Select vendor --");
        vendorComboBox.setFont(font);

        // Membungkus setiap komponen dalam JPanel dengan EmptyBorder sebagai margin luar
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        searchPanel.setBorder(new EmptyBorder(4, 4, 4, 4));
        searchPanel.add(searchField);

        JPanel datePanel = new JPanel();
        datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.Y_AXIS));
        datePanel.setBorder(new EmptyBorder(4, 4, 4, 4));
        datePanel.add(chooser);

        JPanel vendorPanel = new JPanel();
        vendorPanel.setLayout(new BoxLayout(vendorPanel, BoxLayout.Y_AXIS));
        vendorPanel.setBorder(new EmptyBorder(4, 4, 4, 4));
        vendorPanel.add(vendorComboBox);

        add(searchLabel);
        add(searchPanel);
        add(datePanel);
        add(vendorPanel);
    }
}
