package com.multibahana.inventoryapp.views.components.molecules;

import com.multibahana.inventoryapp.context.TablesContext;
import com.multibahana.inventoryapp.controllers.CategoryController;
import com.multibahana.inventoryapp.controllers.ProductController;
import com.multibahana.inventoryapp.daoimplements.CategoryDAOImpl;
import com.multibahana.inventoryapp.daoimplements.ProductDAOImpl;
import com.multibahana.inventoryapp.entities.CategoryEntity;
import com.multibahana.inventoryapp.entities.ProductEntity;
import com.multibahana.inventoryapp.utils.StringUtil;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class ProductForm extends JPanel {

    private static final int TEXT_FIELD_WIDTH = 150;
    private static final int TEXT_FIELD_HEIGHT = 25;
    private static final int LABEL_FONT_SIZE = 14;
    private static final int TEXT_FIELD_FONT_SIZE = 14;
    private static final int PANEL_PADDING = 10;

    private JTextField name;
    private JTextField price;
    private JTextField productCode;
    private JTextField sellPrice;
    private JComboBox<CategoryEntity> category;
    private JButton addProductButton;
    private JButton cancelProductButton;
    private ProductController productController;
    private CategoryController categoryController;
    private JPanel containerPanel;
    private DefaultComboBoxModel<CategoryEntity> categoryModel;

    public ProductForm() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(PANEL_PADDING + 10, PANEL_PADDING, PANEL_PADDING, PANEL_PADDING));

        containerPanel = new JPanel(new GridLayout(3, 2, 6, 6));

        // Initialize fields and components
        name = createTextField();
        productCode = createTextField();
        price = createTextField();
        sellPrice = createTextField();
        category = createComboBox();
        cancelProductButton = createButton("Cancel", true);
        addProductButton = createButton("+ Add", false);

        containerPanel.add(createLabeledPanel("Name ", name));
        containerPanel.add(createLabeledPanel("Buy price ", price));
        containerPanel.add(createLabeledPanel("Product code ", productCode));
        containerPanel.add(createLabeledPanel("Sell price ", sellPrice));
        containerPanel.add(createLabeledPanel("Category", category));
        containerPanel.add(createButtonPanel("Actions ", addProductButton, cancelProductButton));

        // Add action listeners
        addProductButton.addActionListener(e -> onAddProduct());
        cancelProductButton.addActionListener(e -> clearFields());
        addDocumentListeners();

        add(containerPanel, BorderLayout.CENTER);
    }

    public JPanel createChoosePanel(String longText) {
        JPanel choosenProductPanel = new JPanel();
        choosenProductPanel.setLayout(new BoxLayout(choosenProductPanel, BoxLayout.X_AXIS));

        String truncatedText = StringUtil.truncateText(longText, 10);

        JTextField choosenProductField = new JTextField(truncatedText);
        choosenProductField.setPreferredSize(new Dimension(300, choosenProductField.getPreferredSize().height));
        choosenProductField.setEditable(false);

        choosenProductPanel.add(choosenProductField);

        return choosenProductPanel;
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
        productCode.getDocument().addDocumentListener(documentListener);
        price.getDocument().addDocumentListener(documentListener);
        sellPrice.getDocument().addDocumentListener(documentListener);
        category.addActionListener(e -> {
            updateButtonState();
        });
    }

    private void updateButtonState() {
        CategoryEntity categoryObj = (CategoryEntity) categoryModel.getSelectedItem();

        boolean allFieldsFilled = !sellPrice.getText().trim().isEmpty()
                && !name.getText().trim().isEmpty()
                && !price.getText().trim().isEmpty()
                && !productCode.getText().trim().isEmpty()
                && !categoryObj.getName().equals("-- Select category --");

        addProductButton.setEnabled(allFieldsFilled);
    }

    private void clearFields() {
        name.setText("");
        price.setText("");
        productCode.setText("");
        sellPrice.setText("");
        addProductButton.setEnabled(false);
        categoryModel.setSelectedItem(new CategoryEntity(0, "-- Select category --"));
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    private void onAddProduct() {
        String priceText = price.getText();
        String sellPriceText = sellPrice.getText();

        if (!StringUtil.isDouble(priceText)) {
            showWarning("Harga tidak valid. Harap masukkan angka yang benar.");
            return;
        }

        if (!StringUtil.isDouble(sellPriceText)) {
            showWarning("Harga jual tidak valid. Harap masukkan angka yang benar.");
            return;
        }

        double priceValue = Double.parseDouble(priceText);
        double sellPriceValue = Double.parseDouble(sellPriceText);

        CategoryEntity categoryEntity = (CategoryEntity) category.getSelectedItem();

        productController = new ProductController(new ProductDAOImpl());
        productController.addProduct(new ProductEntity(name.getText(), priceValue, 0, categoryEntity.getId(), productCode.getText(), sellPriceValue));

        JOptionPane.showMessageDialog(this, "Successfully added product: " + name.getText(), "Success", JOptionPane.INFORMATION_MESSAGE);
        clearFields();

        ProductTable productTable = TablesContext.getInstance().getCurrentProductTables();

        List<ProductEntity> products = productController.getAllProducts();
        if (productTable != null) {
            productTable.loadTableData(products);
        }
    }

    private JComboBox<CategoryEntity> createComboBox() {
        JComboBox<CategoryEntity> comboBox = new JComboBox<>();
        categoryModel = new DefaultComboBoxModel<>();
        categoryController = new CategoryController(new CategoryDAOImpl());
        Map<Integer, CategoryEntity> categories = categoryController.getAllCategories();

        categoryModel.addElement(new CategoryEntity(0, "-- Select category --"));
        for (CategoryEntity category : categories.values()) {
            if (!category.getId().equals(1)) {
                categoryModel.addElement(category);
            }
        }
        comboBox.setModel(categoryModel);
        return comboBox;
    }

}
