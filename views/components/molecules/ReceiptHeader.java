/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.multibahana.inventoryapp.views.components.molecules;

import com.multibahana.inventoryapp.context.TablesContext;
import com.multibahana.inventoryapp.controller.ProductInController;
import com.multibahana.inventoryapp.dao.ProductInDAOImpl;
import com.multibahana.inventoryapp.entities.ProductEntity;
import com.multibahana.inventoryapp.entities.ProductInEntity;
import com.multibahana.inventoryapp.entities.VendorEntity;
import com.multibahana.inventoryapp.utils.StringUtil;
import com.multibahana.inventoryapp.views.components.atoms.ReceiptionProductsTable;
import com.toedter.calendar.JDateChooser;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author mohammadnuridin
 */
public class ReceiptHeader extends JPanel {

    private JDateChooser dateChooser;
//    private final JSpinner amountSpinner;
    private JTextField noEvidenceField;
//    private final JButton addProductInButton;
//    private final JButton cancelProductInButton;
//    private final JPanel productChooser;
//    private JPanel choosenProductPanel;
    private JButton chooseProductsButton;
    private JPanel vendorChooser;
    private JPanel choosenVendorPanel;
    private JButton chooseVendorsButton;
    private ProductTable productTable;

    private static final int TEXT_FIELD_WIDTH = 150;
    private static final int TEXT_FIELD_HEIGHT = 25;
    private static final int LABEL_FONT_SIZE = 14;
    private static final int TEXT_FIELD_FONT_SIZE = 14;

    private VendorEntity selectedVendor;
    private ProductEntity selectedProduct;
    private ProductInController productInController;
    private ReceiptionProductsTable receiptionProductsTable;

    public ReceiptHeader(ReceiptionProductsTable receiptionProductsTable) {
        setLayout(new BorderLayout());
        this.receiptionProductsTable = receiptionProductsTable;

        JPanel containerPanel = new JPanel();
        containerPanel.setLayout(new GridLayout(4, 2, 6, 6));
//        containerPanel.setPreferredSize(new Dimension(650, 30));

        dateChooser = createDateChooser();
//        SpinnerModel amountModel = new SpinnerNumberModel(0, 0, 100, 1);
//        amountSpinner = new JSpinner(amountModel);
        noEvidenceField = createTextField();
//        addProductInButton = createButton("+ Add item", false);
//        cancelProductInButton = createButton("Cancel", true);

//        productChooser = new JPanel(new GridLayout(1, 2));
//        choosenProductPanel = createChoosePanel(" . . . ");
        chooseProductsButton = createButton(" [ + ] ", true);
        configureButton(chooseProductsButton);
//        choosenProductPanel.add(chooseProductsButton);
//        productChooser.add(choosenProductPanel);

        vendorChooser = new JPanel(new GridLayout(1, 2));
        choosenVendorPanel = createChoosePanel(" . . . ");
        chooseVendorsButton = createButton(" . . . ", true);
        configureButton(chooseVendorsButton);
        choosenVendorPanel.add(chooseVendorsButton);
        vendorChooser.add(choosenVendorPanel);

        containerPanel.add(createLabeledPanel("Date", dateChooser));
        containerPanel.add(createLabeledPanel("Received from", vendorChooser));
        containerPanel.add(createLabeledPanel("No evidence", noEvidenceField));
//        containerPanel.add(createLabeledPanel("Qty", amountSpinner));
        containerPanel.add(createButtonPanel(null, new JLabel("Add product"), chooseProductsButton));

//        addProductInButton.addActionListener(e -> onAddProductIn());
//        cancelProductInButton.addActionListener(e -> clearFields());
        chooseVendorsButton.addActionListener(e -> chooseVendor());
        chooseProductsButton.addActionListener(e -> chooseProduct());
        addDocumentListeners();

        add(containerPanel, BorderLayout.CENTER);

    }

    public ProductEntity getSelectedProduct() {
        return selectedProduct;
    }

    private JDateChooser createDateChooser() {
        JDateChooser chooser = new JDateChooser();
        chooser.setDateFormatString("yyyy-MM-dd");
        return chooser;
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
        JPanel panel = new JPanel(new GridLayout(1, 1));
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, LABEL_FONT_SIZE));
        panel.add(label);
        panel.add(component);
        return panel;
    }

    private JPanel createButtonPanel(String labelText, JComponent component, JButton button) {
        JPanel panel = new JPanel(new GridLayout(1, 2));
        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Arial", Font.BOLD, LABEL_FONT_SIZE));
        panel.add(label);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2));
        buttonPanel.add(component);
        buttonPanel.add(button);
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
        noEvidenceField.getDocument().addDocumentListener(documentListener);
    }

    private void updateButtonState() {
        boolean allFieldsFilled = !noEvidenceField.getText().trim().isEmpty()
                && dateChooser.getDate() != null;
//        addProductInButton.setEnabled(allFieldsFilled);
    }

    private void configureButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.BLUE);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
//        button.setBorderPainted(false);
    }

    public JPanel createChoosePanel(String longText) {
        JPanel choosenPanel = new JPanel();
        choosenPanel.setLayout(new BoxLayout(choosenPanel, BoxLayout.X_AXIS));

        String truncatedText = StringUtil.truncateText(longText, 10);

        JTextField choosenField = new JTextField(truncatedText);
        choosenField.setPreferredSize(new Dimension(300, choosenField.getPreferredSize().height));
        choosenField.setEditable(false);

        choosenPanel.add(choosenField);

        return choosenPanel;
    }

    public void clearFields() {
        noEvidenceField.setText("");
//        amountSpinner.setValue(0);
        dateChooser.setDate(null);
//        addProductInButton.setEnabled(false);

        this.selectedVendor = null;
        vendorChooser.remove(choosenVendorPanel);
        choosenVendorPanel = createChoosePanel(" . . . ");
        choosenVendorPanel.add(chooseVendorsButton);
        vendorChooser.add(choosenVendorPanel);

        vendorChooser.revalidate();
        vendorChooser.repaint();

        /*
        this.selectedProduct = null;
        productChooser.remove(choosenProductPanel);
        choosenProductPanel = createChoosePanel(" . . . ");
        choosenProductPanel.add(chooseProductsButton);
        productChooser.add(choosenProductPanel);
        
        productChooser.revalidate();
        productChooser.repaint();*/
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void onAddProductIn() {
        try {
            productInController = new ProductInController(new ProductInDAOImpl());

            if (selectedVendor == null) {
                showWarning("Please choose vendor first!");
                return;
            }

            if (selectedProduct == null) {
                showWarning("Please choose product first!");
                return;
            }
            Integer amount = 0;
            productInController.addProductIn(new ProductInEntity());
            ReceiptTable productInTable = TablesContext.getInstance().getCurrentReceiptTables();

            List<ProductInEntity> productIns = productInController.getAllProductIn();
            if (productInTable != null) {
                productInTable.loadTableData(productIns);
            }
            clearFields();
            showSuccess("Successfully added productIn");
        } catch (Exception e) {
            showWarning(e.getMessage());
        }
    }

    private void chooseVendor() {
        VendorTable vendorTable = new VendorTable();
        int result = JOptionPane.showConfirmDialog(
                null,
                vendorTable,
                "Choose Vendor",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            selectedVendor = vendorTable.getSelectedVendor();
            if (selectedVendor != null) {

                vendorChooser.remove(choosenVendorPanel);
                choosenVendorPanel = createChoosePanel(selectedVendor.getName());
                choosenVendorPanel.add(chooseVendorsButton);
                vendorChooser.add(choosenVendorPanel);

                vendorChooser.revalidate();
                vendorChooser.repaint();

            }
        }
    }

    private void chooseProduct() {

        this.productTable = new ProductTable();
        DefaultTableModel model = receiptionProductsTable.getTableModel();

        int result = JOptionPane.showConfirmDialog(
                null,
                this.productTable,
                "Choose product",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            if (productTable.getSelectedProduct() != null) {
                this.selectedProduct = this.productTable.getSelectedProduct();
                ProductEntity product = (ProductEntity) this.selectedProduct;

                List<Object[]> tableData = model.getDataVector().stream()
                        .map(row -> ((Vector<?>) row).toArray())
                        .collect(Collectors.toList());

                Object tempProductCode = null;
                for (Object[] item : tableData) {
                    if (item[1].equals(product.getProductCode())) {
                        showWarning("Product already exists in the table");
                        tempProductCode = item[0];
                        break;
                    }
                }

                if (tempProductCode == null) {
                    model.addRow(new Object[]{product.getId(), product.getProductCode(), product.getName(), product.getStock()});
                }
            }
        }

    }

    public VendorEntity getSelectedVendor() {
        return selectedVendor;
    }

    public JTextField getNoEvidenceField() {
        return noEvidenceField;
    }

    public JDateChooser getDateChooser() {
        return dateChooser;
    }

}
