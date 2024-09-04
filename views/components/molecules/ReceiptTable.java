package com.multibahana.inventoryapp.views.components.molecules;

import com.multibahana.inventoryapp.controller.EvidenceController;
import com.multibahana.inventoryapp.controller.ProductInController;
import com.multibahana.inventoryapp.controllers.ProductController;
import com.multibahana.inventoryapp.controllers.VendorController;
import com.multibahana.inventoryapp.dao.EvidenceDAOImpl;
import com.multibahana.inventoryapp.dao.ProductInDAOImpl;
import com.multibahana.inventoryapp.daoimplements.ProductDAOImpl;
import com.multibahana.inventoryapp.daoimplements.VendorDAOImpl;
import com.multibahana.inventoryapp.entities.EvidenceEntity;
import com.multibahana.inventoryapp.entities.ProductEntity;
import com.multibahana.inventoryapp.entities.ProductInEntity;
import com.multibahana.inventoryapp.entities.VendorEntity;
import com.multibahana.inventoryapp.views.components.atoms.PopRowMenu;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import javax.swing.border.EmptyBorder;

public class ReceiptTable extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;

    private PopRowMenu popupMenu;
    private String[] columnNames;
    private ReceiptFilter receiptFilter;
    private ProductInController productInController;

    public ReceiptTable() {
        setLayout(new BorderLayout());
        initUI();
    }

    private void initUI() {
        tableModel = createTableModel();
        this.productInController = new ProductInController(new ProductInDAOImpl());
        table = createTable(tableModel);
        receiptFilter = new ReceiptFilter();

        table.setFont(new Font("Arial", Font.BOLD, 14));
        table.setRowHeight(30);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 16));

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(new CustomCellRenderer());
        }

        JScrollPane scrollPane = new JScrollPane(table);
        JSplitPane tableSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, receiptFilter, scrollPane);
        tableSplitPane.setDividerLocation(50);
        add(tableSplitPane, BorderLayout.CENTER);

        popupMenu = new PopRowMenu();

        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow >= 0) {
                    }
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
                JOptionPane.showMessageDialog(ReceiptTable.this,
                        "Please select a row to edit",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        popupMenu.getDeleteRow().addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                deleteRowItem(selectedRow);
            } else {
                JOptionPane.showMessageDialog(ReceiptTable.this,
                        "Please select a row to delete",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        popupMenu.getRefresh().addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                refreshItems();
            } else {
                JOptionPane.showMessageDialog(ReceiptTable.this,
                        "Please select a row to delete",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        receiptFilter.getSearchButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchValue = receiptFilter.getSearchField().getText();
                Date dateValue = receiptFilter.getDateChooser().getDate();
                VendorEntity vendorValue = (VendorEntity) receiptFilter.getVendorComboBox().getSelectedItem();

                if (!searchValue.isEmpty()) {
                    searchValue = searchValue;
                } else {
                    searchValue = null;
                }

                if (vendorValue != null && vendorValue.getId() == 0) {
                    vendorValue = null;
                }

                if (dateValue != null) {
                    dateValue = dateValue;
                }

                List<ProductInEntity> productsIn = productInController.getAllProductIn(searchValue, dateValue, vendorValue);
                loadTableData(productsIn);
            }
        });

        loadInitialData();

    }

    private void loadInitialData() {
        List<ProductInEntity> productsIn = productInController.getAllProductIn();
        loadTableData(productsIn);
    }

    public void loadTableData(List<ProductInEntity> productsIn) {
        ProductController productController = new ProductController(new ProductDAOImpl());
        List<ProductEntity> productEntities = productController.getAllProducts();
        Map<Integer, ProductEntity> productMap = productEntities.stream()
                .collect(Collectors.toMap(ProductEntity::getId, product -> product));

        VendorController vendorController = new VendorController(new VendorDAOImpl());
        List<VendorEntity> vendorEntities = vendorController.getAllVendors();
        Map<Integer, VendorEntity> vendorMap = vendorEntities.stream()
                .collect(Collectors.toMap(VendorEntity::getId, vendor -> vendor));

        EvidenceController evidenceController = new EvidenceController(new EvidenceDAOImpl());
        List<EvidenceEntity> evidenceEntities = evidenceController.getAllEvidences();
        Map<Integer, EvidenceEntity> evidenceMap = evidenceEntities.stream()
                .collect(Collectors.toMap(EvidenceEntity::getId, evidence -> evidence));

        tableModel.setRowCount(0);

        for (ProductInEntity productIn : productsIn) {
            ProductEntity product = productMap.get(productIn.getProductId());
            VendorEntity vendor = vendorMap.get(productIn.getVendorId());
            EvidenceEntity evidence = evidenceMap.get(productIn.getEvidenceId());

            Object[] rowData = {
                productIn.getId(),
                productIn.getDate(),
                evidence != null ? evidence : "Unknown Evidence",
                product != null ? product : "Unknown Product",
                productIn.getQuantity(),
                vendor != null ? vendor : "Unknown Vendor"
            };

            tableModel.addRow(rowData);
        }
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
        table.getColumnModel().getColumn(0).setMinWidth(0);
        table.getColumnModel().getColumn(0).setMaxWidth(0);
        table.getColumnModel().getColumn(0).setWidth(0);
        table.getColumnModel().getColumn(0).setPreferredWidth(0);

        return table;
    }

    private DefaultTableModel createTableModel() {
        String[] columnNames = {"ID", "Date receipt", "No evidence", "Product name", "Qty", "Vendor"};
        return new DefaultTableModel(columnNames, 0);
    }

    public void editRowItem(int selectedRow) {
        Integer id = (Integer) table.getValueAt(selectedRow, 0);
        Date date = (Date) table.getValueAt(selectedRow, 1);
        String noEvidence = (String) table.getValueAt(selectedRow, 2);
        ProductEntity product = (ProductEntity) table.getValueAt(selectedRow, 3);
        ProductInEntity productIn = productInController.getProductInById(id);
        Integer amount = (Integer) productIn.getQuantity();
        VendorEntity vendor = (VendorEntity) table.getValueAt(selectedRow, 4);

        var defaultAmount = amount != null ? amount : 0;

        boolean validInput = false;

        while (!validInput) {
            JPanel editPanel = new JPanel(new GridLayout(6, 2, 10, 10));
            Font font = new Font("Arial", Font.BOLD, 16);
            EmptyBorder padding = new EmptyBorder(5, 5, 5, 5);

            JTextField idField = new JTextField(id != null ? id.toString() : "");
            idField.setEditable(false);
            idField.setFont(font);
            idField.setBorder(padding);

            JDateChooser dateChooser = new JDateChooser(date);
            dateChooser.setDateFormatString("yyyy-MM-dd");

            JTextField noEvidenceField = new JTextField(noEvidence);
            noEvidenceField.setFont(font);
            noEvidenceField.setBorder(padding);

            JTextField nameField = new JTextField(product.getName());
            nameField.setFont(font);
            nameField.setBorder(padding);
            nameField.setEditable(false);

            SpinnerNumberModel spinnerModel = new SpinnerNumberModel(defaultAmount, 0, Integer.MAX_VALUE, 1);
            JSpinner amountSpinner = new JSpinner(spinnerModel);
            ((JSpinner.DefaultEditor) amountSpinner.getEditor()).getTextField().setFont(font);

            JTextField vendorField = new JTextField(vendor.getName());
            vendorField.setFont(font);
            vendorField.setBorder(padding);
            vendorField.setEditable(false);

            editPanel.add(new JLabel("ID"));
            editPanel.add(idField);
            editPanel.add(new JLabel("Date"));
            editPanel.add(dateChooser);
            editPanel.add(new JLabel("No evidence"));
            editPanel.add(noEvidenceField);
            editPanel.add(new JLabel("Name"));
            editPanel.add(nameField);
            editPanel.add(new JLabel("Amount"));
            editPanel.add(amountSpinner);
            editPanel.add(new JLabel("Vendor"));
            editPanel.add(vendorField);

            int result = JOptionPane.showConfirmDialog(
                    this,
                    editPanel,
                    "Edit Item",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                try {
                    Date newDate = dateChooser.getDate();
                    String newNoEvidence = noEvidenceField.getText();
                    Integer newAmount = (Integer) amountSpinner.getValue();

                    if (newDate.equals(null)) {
                        JOptionPane.showMessageDialog(
                                this,
                                "Date should not be empty!",
                                "Warning",
                                JOptionPane.WARNING_MESSAGE
                        );
                        continue;
                    }
                    if (newNoEvidence.isEmpty()) {
                        JOptionPane.showMessageDialog(
                                this,
                                "No evidence should not be empty!",
                                "Warning",
                                JOptionPane.WARNING_MESSAGE
                        );
                        continue;
                    }

                    ProductInController productInController = new ProductInController(new ProductInDAOImpl());
                    productInController.updateProductIn(new ProductInEntity(id, product.getId(), vendor.getId(), 1, date, 1));

                    JOptionPane.showMessageDialog(
                            this,
                            "Item updated successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    validInput = true;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(
                            this,
                            "Invalid input format. Please enter valid numbers for price.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(
                            this,
                            "Error updating item: " + e.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            } else {
                validInput = true;
            }
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
                productInController.deleteProductIn(id);

                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.removeRow(selectedRow);

                JOptionPane.showMessageDialog(
                        this,
                        "Item deleted successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(
                        this,
                        "Error deleting item: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void refreshItems() {
        try {

            List<ProductInEntity> productsIn = productInController.getAllProductIn();
            loadTableData(productsIn);
            JOptionPane.showMessageDialog(
                    this,
                    "All items are successfully refresh",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );
        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    this,
                    "All items are failed refresh",
                    "Failed",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private static class CustomCellRenderer extends DefaultTableCellRenderer {

        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            cell.setFont(new Font("Arial", Font.BOLD, 14));
            return cell;
        }
    }
}
