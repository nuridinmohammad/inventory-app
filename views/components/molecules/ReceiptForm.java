package com.multibahana.inventoryapp.views.components.molecules;

import com.multibahana.inventoryapp.context.TablesContext;
import com.multibahana.inventoryapp.controllers.StockController;
import com.multibahana.inventoryapp.daoimplements.StockDAOImpl;
import com.multibahana.inventoryapp.entities.ProductEntity;
import com.multibahana.inventoryapp.entities.StockEntity;
import com.multibahana.inventoryapp.entities.VendorEntity;
import com.multibahana.inventoryapp.utils.StringUtil;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.List;

public class ReceiptForm extends JPanel {

    private static final int TEXT_FIELD_WIDTH = 150;
    private static final int TEXT_FIELD_HEIGHT = 25;
    private static final int LABEL_FONT_SIZE = 14;
    private static final int TEXT_FIELD_FONT_SIZE = 14;
    private static final int PANEL_PADDING = 10;

    private final JTextField noEvidenceField;
    private final JSpinner amountSpinner;
    private final JDateChooser dateChooser;
    private JPanel choosenProductPanel;
    private JPanel choosenVendorPanel;

    private JButton chooseProductsButton;
    private JButton chooseVendorsButton;
    private final JButton cancelStockButton;
    private final JButton addStockButton;

    private StockController stockController;
    private JPanel containerPanel;
    private JPanel productChooser;
    private JPanel vendorChooser;

    private VendorEntity selectedVendor;
    private ProductEntity selectedProduct;

    public ReceiptForm() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(PANEL_PADDING + 10, PANEL_PADDING, PANEL_PADDING, PANEL_PADDING));

        containerPanel = new JPanel(new GridLayout(3, 2, 6, 6));

        dateChooser = createDateChooser();
        SpinnerModel amountModel = new SpinnerNumberModel(0, 0, 100, 1);
        amountSpinner = new JSpinner(amountModel);
        noEvidenceField = createTextField();
        addStockButton = createButton("+ Add item", false);
        cancelStockButton = createButton("Cancel", true);

        productChooser = new JPanel(new GridLayout(1, 2));
        choosenProductPanel = createChoosePanel(" . . . ");
        chooseProductsButton = createButton(" . . . ", true);
        configureButton(chooseProductsButton);
        choosenProductPanel.add(chooseProductsButton);
        productChooser.add(choosenProductPanel);

        vendorChooser = new JPanel(new GridLayout(1, 2));
        choosenVendorPanel = createChoosePanel(" . . . ");
        chooseVendorsButton = createButton(" . . . ", true);
        configureButton(chooseVendorsButton);
        choosenVendorPanel.add(chooseVendorsButton);
        vendorChooser.add(choosenVendorPanel);

        containerPanel.add(createLabeledPanel("Date", dateChooser));
        containerPanel.add(createLabeledPanel("Received from", vendorChooser));
        containerPanel.add(createLabeledPanel("No evidence", noEvidenceField));
        containerPanel.add(createLabeledPanel("Choose product", productChooser));
        containerPanel.add(createLabeledPanel("Qty", amountSpinner));
        containerPanel.add(createButtonPanel("Actions", addStockButton, cancelStockButton));

        // Add action listeners
        addStockButton.addActionListener(e -> onAddStock());
        cancelStockButton.addActionListener(e -> clearFields());
        chooseProductsButton.addActionListener(e -> chooseProduct());
        chooseVendorsButton.addActionListener(e -> chooseVendor());
        addDocumentListeners();

        add(containerPanel, BorderLayout.CENTER);
    }

    private void configureButton(JButton button) {
        button.setOpaque(false);
        button.setContentAreaFilled(false);
//        button.setBorderPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 12));
        button.setForeground(Color.BLUE);
        button.setPreferredSize(new Dimension(25, button.getPreferredSize().height));
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
        noEvidenceField.getDocument().addDocumentListener(documentListener);
    }

    private void updateButtonState() {
        boolean allFieldsFilled = !noEvidenceField.getText().trim().isEmpty()
                && dateChooser.getDate() != null;
        addStockButton.setEnabled(allFieldsFilled);
    }

    private void clearFields() {
        noEvidenceField.setText("");
        amountSpinner.setValue(0);
        dateChooser.setDate(null);
        addStockButton.setEnabled(false);

        this.selectedVendor = null;
        vendorChooser.remove(choosenVendorPanel);
        choosenVendorPanel = createChoosePanel(" . . . ");
        choosenVendorPanel.add(chooseVendorsButton);
        vendorChooser.add(choosenVendorPanel);

        vendorChooser.revalidate();
        vendorChooser.repaint();

        this.selectedProduct = null;
        productChooser.remove(choosenProductPanel);
        choosenProductPanel = createChoosePanel(" . . . ");
        choosenProductPanel.add(chooseProductsButton);
        productChooser.add(choosenProductPanel);

        productChooser.revalidate();
        productChooser.repaint();

    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void onAddStock() {
        try {
            stockController = new StockController(new StockDAOImpl());

            if (selectedVendor == null) {
                showWarning("Please choose vendor first!");
                return;
            }

            if (selectedProduct == null) {
                showWarning("Please choose product first!");
                return;
            }
            Integer amount = (Integer) amountSpinner.getValue();
            stockController.addStock(new StockEntity(noEvidenceField.getText(), dateChooser.getDate(), amount, selectedProduct.getId(), selectedVendor.getId()));
            ReceiptTable stockTable = TablesContext.getInstance().getCurrentReceiptTables();
            
            List<StockEntity> stocks = stockController.getAllStocks();
            if (stockTable != null) {
                stockTable.loadTableData(stocks);
            }
            clearFields();
            showSuccess("Successfully added stock");
        } catch (Exception e) {
            showWarning(e.getMessage());
        }
    }

    private void chooseProduct() {
        ProductTable productTable = new ProductTable();
        int result = JOptionPane.showConfirmDialog(
                containerPanel,
                productTable,
                "Choose Product",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            selectedProduct = productTable.getSelectedProduct();
            if (selectedProduct != null) {
                productChooser.remove(choosenProductPanel);
                choosenProductPanel = createChoosePanel(selectedProduct.getName());
                choosenProductPanel.add(chooseProductsButton);
                productChooser.add(choosenProductPanel);

                productChooser.revalidate();
                productChooser.repaint();
            }
        }
    }

    private void chooseVendor() {
        VendorTable vendorTable = new VendorTable();
        int result = JOptionPane.showConfirmDialog(
                containerPanel,
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

}
