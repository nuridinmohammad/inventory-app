package com.multibahana.inventoryapp.views.components.molecules;

import com.multibahana.inventoryapp.context.LeftPanelContext;
import com.multibahana.inventoryapp.context.TablesContext;
import com.multibahana.inventoryapp.controllers.ProductController;
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
import javax.swing.tree.DefaultMutableTreeNode;

import javax.swing.tree.TreePath;

public class ProductForm extends JPanel {

    private static final int TEXT_FIELD_WIDTH = 150;
    private static final int TEXT_FIELD_HEIGHT = 25;
    private static final int LABEL_FONT_SIZE = 14;
    private static final int TEXT_FIELD_FONT_SIZE = 14;
    private static final int PANEL_PADDING = 10;

    private JTextField name;
    private JTextField price;
    private JButton addProductButton;
    private JButton cancelProductButton;
    private ProductController productController;
    private JPanel containerPanel;
//    private JLabel category;

    public ProductForm() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(PANEL_PADDING + 10, PANEL_PADDING, PANEL_PADDING, PANEL_PADDING));

        containerPanel = new JPanel(new GridLayout(3, 2, 6, 6));


        // Initialize fields and components
        name = createTextField();
        price = createTextField();
//        category = new JLabel("");
        cancelProductButton = createButton("Cancel", true);
        addProductButton = createButton("+ Add", false);

        containerPanel.add(createLabeledPanel("Name ", name));
        containerPanel.add(createLabeledPanel("Price (Rp. )", price));
//        containerPanel.add(createLabeledPanel("Category ", category));
        containerPanel.add(new JPanel());
        containerPanel.add(createButtonPanel("Actions", addProductButton, cancelProductButton));

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
        price.getDocument().addDocumentListener(documentListener);
    }

    private void updateButtonState() {
        boolean allFieldsFilled = !name.getText().trim().isEmpty()
                && !price.getText().trim().isEmpty();
        addProductButton.setEnabled(allFieldsFilled);
    }

    private void clearFields() {
        name.setText("");
        price.setText("");
        addProductButton.setEnabled(false);
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    private void onAddProduct() {
        try {
            TreePath selectedPath = LeftPanelContext.getInstance().getTreePath();
            if (selectedPath != null) {
                DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
                CategoryEntity categoryEntity = (CategoryEntity) treeNode.getUserObject();

                productController = new ProductController(new ProductDAOImpl());
                productController.addProduct(new ProductEntity(name.getText(), Double.valueOf(price.getText()), categoryEntity.getId()));
                JOptionPane.showMessageDialog(this, "Successfully added product: " + name.getText(), "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();

                ProductTable productTable = TablesContext.getInstance().getCurrentProductTables();

                List<ProductEntity> products = productController.getAllProductsByCategoryId(categoryEntity.getId());
                if (productTable != null) {
                    productTable.loadTableData(products);
                }
            } else {
                showWarning("Please select Category first!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            showWarning(e.getMessage());
        }
    }

}
