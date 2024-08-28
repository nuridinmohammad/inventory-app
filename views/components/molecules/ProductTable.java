package com.multibahana.inventoryapp.views.components.molecules;

import com.multibahana.inventoryapp.context.LeftPanelContext;
import com.multibahana.inventoryapp.controllers.CategoryController;
import com.multibahana.inventoryapp.controllers.ProductController;
import com.multibahana.inventoryapp.daoimplements.CategoryDAOImpl;
import com.multibahana.inventoryapp.daoimplements.ProductDAOImpl;
import com.multibahana.inventoryapp.entities.CategoryEntity;
import com.multibahana.inventoryapp.entities.ProductEntity;
import com.multibahana.inventoryapp.views.components.atoms.PopRowMenu;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Map;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

public class ProductTable extends JPanel {

    private final JTable table;
    private final DefaultTableModel tableModel;
    private PopRowMenu popupMenu;
    private ProductController productController;
    private DefaultComboBoxModel<CategoryEntity> categoryModel;
    private ProductEntity selectedProduct;
    private ProductFilter productFilter;

    public ProductTable() {
        setLayout(new BorderLayout());
        tableModel = createTableModel();
        table = createTable(tableModel);
        productController = new ProductController(new ProductDAOImpl());
        productFilter = new ProductFilter();

        JScrollPane scrollPane = new JScrollPane(table);
        JSplitPane tableSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, productFilter, scrollPane);
        tableSplitPane.setDividerLocation(50);

        add(tableSplitPane, BorderLayout.CENTER);

        popupMenu = new PopRowMenu();

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    // Handle row selection logic here
                    selectedProduct = getProductFromRow(selectedRow);
                }
            }
        });

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = table.rowAtPoint(e.getPoint());
                    if (row >= 0 && row < table.getRowCount()) {
                        table.setRowSelectionInterval(row, row);
                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }
        });

        popupMenu.getEditRow().addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                editRowItem(selectedRow);
            } else {
                showWarning("Please select a row to edit");
            }
        });

        popupMenu.getDeleteRow().addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                deleteRowItem(selectedRow);
            } else {
                showWarning("Please select a row to delete");
            }
        });

        popupMenu.getRefresh().addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                refreshItems();
            } else {
                showWarning("Please select a row to delete");
            }
        });

        productFilter.getSearchButton().addActionListener(e -> {
            String searchValue = productFilter.getSearchField().getText().trim();
            String minValue = productFilter.getMinField().getText().trim();
            String maxValue = productFilter.getMaxField().getText().trim();
            CategoryEntity categoryValue = (CategoryEntity) productFilter.getCategoryComboBox().getSelectedItem();

            Integer min = null;
            Integer max = null;

            try {
                if (!searchValue.isEmpty()) {
                    searchValue = searchValue;
                } else {
                    searchValue = null;
                }

                if (!minValue.isEmpty()) {
                    min = Integer.valueOf(minValue);
                }

                if (!maxValue.isEmpty()) {
                    max = Integer.valueOf(maxValue);
                }

                if (categoryValue != null && categoryValue.getId() == 0) {
                    categoryValue = null;
                }

                List<ProductEntity> products = productController.getAllProducts(searchValue, min, max, categoryValue);
                loadTableData(products);

            } catch (NumberFormatException ex) {
                ex.printStackTrace();
                showWarning("Min/Max value must be a valid number.");
            }
        });

        loadInitialData();
    }

    private ProductEntity getProductFromRow(int rowIndex) {
        Integer id = (Integer) table.getValueAt(rowIndex, 0);
        String name = (String) table.getValueAt(rowIndex, 1);
        Double price = (Double) table.getValueAt(rowIndex, 2);
        CategoryEntity categoryEntity = (CategoryEntity) table.getValueAt(rowIndex, 3);
        return new ProductEntity(id, name, price, categoryEntity.getId());
    }

    public ProductEntity getSelectedProduct() {
        return selectedProduct;
    }

    private DefaultTableModel createTableModel() {
        String[] columnNames = {"ID", "Name", "Price", "Category"};
        return new DefaultTableModel(columnNames, 0);
    }

    private JTable createTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        Font tableFont = new Font("Arial", Font.BOLD, 14);
        table.setFont(tableFont);
        table.setRowHeight(30);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 16));

        table.setPreferredScrollableViewportSize(new Dimension(700, 260));
        table.setFillsViewportHeight(true);

        return table;
    }

    private void loadInitialData() {
        List<ProductEntity> products = productController.getAllProducts();
        loadTableData(products);
    }

    public void loadTableData(List<ProductEntity> products) {
        CategoryController categoryController = new CategoryController(new CategoryDAOImpl());
        Map<Integer, CategoryEntity> categoryEntities = categoryController.getAllCategories();

        tableModel.setRowCount(0);

        for (ProductEntity product : products) {
            CategoryEntity categoryEntity = categoryEntities.get(product.getCategoryId());
            CategoryEntity categoryName = (categoryEntity != null) ? categoryEntity : null;

            Object[] rowData = {
                product.getId(),
                product.getName(),
                product.getPrice(),
                categoryName
            };

            tableModel.addRow(rowData);
        }
    }

    public void deleteRowItem(int selectedRow) {
        int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete this item?",
                "Delete Item",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            try {
                Integer id = (Integer) table.getValueAt(selectedRow, 0);
                productController.deleteProduct(id);

                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.removeRow(selectedRow);
                showSuccess("Item deleted successfully!");
            } catch (Exception e) {
                e.printStackTrace();
                showError("Error deleting item: " + e.getMessage());
            }
        }
    }

    public void editRowItem(int selectedRow) {
        Integer id = (Integer) table.getValueAt(selectedRow, 0);
        String defaultName = table.getValueAt(selectedRow, 1) != null ? table.getValueAt(selectedRow, 1).toString() : "";
        double defaultPrice = table.getValueAt(selectedRow, 2) != null ? Double.parseDouble(table.getValueAt(selectedRow, 2).toString()) : 0.0;

        while (true) {
            JPanel editPanel = new JPanel(new GridLayout(4, 2, 10, 10));
            Font font = new Font("Arial", Font.BOLD, 16);
            EmptyBorder padding = new EmptyBorder(5, 5, 5, 5);

            JTextField idField = new JTextField(id != null ? id.toString() : "");
            idField.setFont(font);
            idField.setBorder(padding);
            idField.setEditable(false);

            JTextField nameField = new JTextField(defaultName);
            nameField.setFont(font);
            nameField.setBorder(padding);

            JTextField priceField = new JTextField(Double.toString(defaultPrice));
            priceField.setFont(font);
            priceField.setBorder(padding);

            JComboBox<CategoryEntity> categoryComboBox = createCategoryComboBox(selectedRow);

            editPanel.add(new JLabel("ID:"));
            editPanel.add(idField);
            editPanel.add(new JLabel("Name:"));
            editPanel.add(nameField);
            editPanel.add(new JLabel("Price:"));
            editPanel.add(priceField);
            editPanel.add(new JLabel("Category:"));
            editPanel.add(categoryComboBox);

            int result = JOptionPane.showConfirmDialog(
                    this,
                    editPanel,
                    "Edit Item",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                try {
                    String newName = nameField.getText().trim();
                    String priceText = priceField.getText().trim();

                    // Validate name
                    if (newName.isEmpty()) {
                        showWarning("Name should not be empty!");
                        nameField.requestFocus();
                        continue;
                    }

                    // Validate price
                    if (priceText.isEmpty()) {
                        showWarning("Price should not be empty!");
                        priceField.requestFocus();
                        continue;
                    }

                    Double newPrice = Double.parseDouble(priceText);

                    if (newPrice < 0) {
                        showWarning("Price should not be negative!");
                        priceField.requestFocus();
                        continue;
                    }

                    // If all inputs are valid, update the product and exit the loop
                    CategoryEntity categoryEntity = (CategoryEntity) categoryComboBox.getSelectedItem();
                    ProductEntity updatedProduct = new ProductEntity(id, newName, newPrice, categoryEntity.getId());
                    productController.updateProduct(updatedProduct);

                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.setValueAt(newName, selectedRow, 1);
                    model.setValueAt(newPrice, selectedRow, 2);

                    showSuccess("Item updated successfully!");
                    break;
                } catch (NumberFormatException e) {
                    showWarning("Invalid price value! Please enter a valid number.");
                    priceField.requestFocus();
                } catch (Exception e) {
                    e.printStackTrace();
                    showError("Error updating item: " + e.getMessage());
                    break;
                }
            } else {
                break;
            }
        }
    }

    private void refreshItems() {
        try {

            TreePath selectedPath = LeftPanelContext.getInstance().getTreePath();
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
            CategoryEntity categoryEntity = (CategoryEntity) selectedNode.getUserObject();

            List<ProductEntity> products = productController.getAllProductsByCategoryId(categoryEntity.getId());
            loadTableData(products);
            showSuccess("All items are successfully refresh");
        } catch (Exception e) {
            showError("All items are failed refresh");
        }
    }

    private JComboBox<CategoryEntity> createCategoryComboBox(Integer selectedRow) {
        categoryModel = new DefaultComboBoxModel<>();
        JComboBox<CategoryEntity> categoryComboBox = new JComboBox<>(categoryModel);
        CategoryController categoryController = new CategoryController(new CategoryDAOImpl());
        Map<Integer, CategoryEntity> categories = categoryController.getAllCategories();
        setSelectedItemComboBox(categoryModel, selectedRow);
        categories.values().forEach(categoryModel::addElement);
        categoryComboBox.setModel(categoryModel);
        return categoryComboBox;
    }

    private void showWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    private void showSuccess(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public void setSelectedItemComboBox(DefaultComboBoxModel<CategoryEntity> categoryModel, Integer selectedRow) {
        Object category = table.getValueAt(selectedRow, 3);
        CategoryEntity defaultCategory = (CategoryEntity) category;
        if (defaultCategory != null) {
            categoryModel.setSelectedItem(defaultCategory);
        }
    }

}
