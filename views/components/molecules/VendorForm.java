package com.multibahana.inventoryapp.views.components.molecules;

import com.multibahana.inventoryapp.context.TablesContext;
import com.multibahana.inventoryapp.controllers.VendorController;
import com.multibahana.inventoryapp.daoimplements.VendorDAOImpl;
import com.multibahana.inventoryapp.entities.VendorEntity;
import com.multibahana.inventoryapp.utils.StringUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.List;

public class VendorForm extends JPanel {

    private static final int TEXT_FIELD_WIDTH = 150;
    private static final int TEXT_FIELD_HEIGHT = 25;
    private static final int LABEL_FONT_SIZE = 14;
    private static final int TEXT_FIELD_FONT_SIZE = 14;
    private static final int PANEL_PADDING = 10;

    private final JTextField name;
    private final JTextField address;
    private JPanel tempPanel;
    private final JButton addVendorButton;
    private final JButton cancelVendorButton;

    private VendorController vendorController;

    private JPanel containerPanel;

    public VendorForm() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(PANEL_PADDING + 10, PANEL_PADDING, PANEL_PADDING, PANEL_PADDING));

        containerPanel = new JPanel(new GridLayout(2, 2, 6, 6));

        Font font = new Font("Arial", Font.BOLD, 14);

        // Initialize fields and components
        name = createTextField();
        address = createTextField();
        tempPanel = new JPanel();
        cancelVendorButton = createButton("Cancel", true);
        addVendorButton = createButton("+ Add", false);

        containerPanel.add(createLabeledPanel("Name ", name));
        containerPanel.add(createLabeledPanel("Address ", address));
        containerPanel.add(tempPanel);
        containerPanel.add(createButtonPanel("Actions", addVendorButton, cancelVendorButton));

        // Add action listeners
        addVendorButton.addActionListener(e -> onAddVendor());
        cancelVendorButton.addActionListener(e -> clearFields());
        addDocumentListeners();

        add(containerPanel, BorderLayout.CENTER);
    }

    public JPanel createChoosePanel(String longText) {
        JPanel choosenVendorPanel = new JPanel();
        choosenVendorPanel.setLayout(new BoxLayout(choosenVendorPanel, BoxLayout.X_AXIS));

        String truncatedText = StringUtil.truncateText(longText, 10);

        JTextField choosenVendorField = new JTextField(truncatedText);
        choosenVendorField.setPreferredSize(new Dimension(300, choosenVendorField.getPreferredSize().height));
        choosenVendorField.setEditable(false);

        choosenVendorPanel.add(choosenVendorField);

        return choosenVendorPanel;
    }

    private JTextField createTextField() {
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT));
        textField.setFont(new Font("Arial", Font.BOLD, TEXT_FIELD_FONT_SIZE));
        return textField;
    }

    private JButton createButton(String text, boolean enabled) {
        JButton button = new JButton(text);
        button.setEnabled(enabled);
        return button;
    }

    private JPanel createLabeledPanel(String labelText, JComponent component) {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, LABEL_FONT_SIZE));
        panel.add(label);
        panel.add(component);
        return panel;
    }

    private JPanel createButtonPanel(String labelText, JButton... buttons) {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, LABEL_FONT_SIZE));
        panel.add(label);

        JPanel buttonPanel = new JPanel(new GridLayout(1, buttons.length));
        for (JButton button : buttons) {
            if (button != null) {
                buttonPanel.add(button);
            }
        }
        panel.add(buttonPanel);
        return panel;
    }

    private void addDocumentListeners() {
        DocumentListener documentListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateButtonState();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateButtonState();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateButtonState();
            }
        };
        name.getDocument().addDocumentListener(documentListener);
        address.getDocument().addDocumentListener(documentListener);
    }

    private void updateButtonState() {
        boolean allFieldsFilled = !name.getText().trim().isEmpty()
                && !address.getText().trim().isEmpty();
        addVendorButton.setEnabled(allFieldsFilled);
    }

    private void clearFields() {
        name.setText("");
        address.setText("");
        addVendorButton.setEnabled(false);
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    private void onAddVendor() {
        try {
            vendorController = new VendorController(new VendorDAOImpl());
            vendorController.addVendor(new VendorEntity(name.getText().trim(), address.getText().trim()));
            VendorTable vendorTable = TablesContext.getInstance().getCurrentVendorTables();
            
            List<VendorEntity> vendors = vendorController.getAllVendors();
            if (vendorTable != null) {
                vendorTable.loadTableData(vendors);
            }
            
            JOptionPane.showMessageDialog(this, "Successfully added vendor : " + name.getText(), "Success", JOptionPane.INFORMATION_MESSAGE);
            clearFields();
        } catch (Exception e) {
            showWarning(e.getMessage());
        }
    }

}
