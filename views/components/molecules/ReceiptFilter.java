package com.multibahana.inventoryapp.views.components.molecules;

import com.multibahana.inventoryapp.controllers.VendorController;
import com.multibahana.inventoryapp.daoimplements.VendorDAOImpl;
import com.multibahana.inventoryapp.entities.VendorEntity;
import com.toedter.calendar.JDateChooser;

import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.util.List;

public class ReceiptFilter extends JPanel {

    private JTextField noEvidenceField;
    private JDateChooser chooser;
    private JComboBox<VendorEntity> vendorComboBox;
    private JButton searchButton;
    private VendorController vendorController;

    public ReceiptFilter() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(new EmptyBorder(6, 6, 6, 6));

        vendorController = new VendorController(new VendorDAOImpl());

        Font font = new Font("Arial", Font.PLAIN, 16);

        JLabel noEvidenceFieldLabel = new JLabel("Search ");
        noEvidenceField = new JTextField();
        noEvidenceField.setFont(font);

        chooser = new JDateChooser();
        chooser.setDateFormatString("yyyy-MM-dd");
        chooser.setFont(font);

        DefaultComboBoxModel<VendorEntity> vendorModel = new DefaultComboBoxModel<>();
        List<VendorEntity> vendors = vendorController.getAllVendors();

        vendorComboBox = new JComboBox<>();
        vendorComboBox.setModel(vendorModel);
        vendorComboBox.setFont(font);
        vendorModel.addElement(new VendorEntity(0, "-- Select vendor --"));
        vendors.stream().forEach(item -> vendorModel.addElement(item));

        searchButton = new JButton("Search");

        JPanel noEvidenceFieldPanel = createPanel(noEvidenceField, font);
        JPanel datePanel = createPanel(chooser, font);
        JPanel vendorPanel = createPanel(vendorComboBox, font);
        JPanel searchButtonPanel = createPanel(searchButton, font);

        add(noEvidenceFieldLabel);
        add(noEvidenceFieldPanel);
        add(datePanel);
        add(vendorPanel);
        add(searchButtonPanel);
    }

    public <T extends JComponent> JPanel createPanel(T component, Font font) {
        component.setFont(font);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(4, 4, 4, 4));
        panel.add(component);
        return panel;
    }

    public JTextField getSearchField() {
        return noEvidenceField;
    }

    public JDateChooser getDateChooser() {
        return chooser;
    }

    public JComboBox<VendorEntity> getVendorComboBox() {
        return vendorComboBox;
    }

    public JButton getSearchButton() {
        return searchButton;
    }
}
