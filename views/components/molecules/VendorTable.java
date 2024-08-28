package com.multibahana.inventoryapp.views.components.molecules;

import com.multibahana.inventoryapp.controllers.VendorController;
import com.multibahana.inventoryapp.daoimplements.VendorDAOImpl;
import com.multibahana.inventoryapp.entities.VendorEntity;
import com.multibahana.inventoryapp.views.components.atoms.PopRowMenu;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.border.EmptyBorder;

public class VendorTable extends JPanel {

    private final JTable table;
    private final DefaultTableModel tableModel;
    private VendorController vendorController;
    private PopRowMenu popupMenu;
    private VendorEntity selectedVendor;
    private VendorFilter vendorFilter;

    public VendorTable() {
        setLayout(new BorderLayout());
        this.vendorController = new VendorController(new VendorDAOImpl());

        tableModel = createTableModel();
        table = createTable(tableModel);
        popupMenu = new PopRowMenu();
        vendorFilter = new VendorFilter();

        JScrollPane scrollPane = new JScrollPane(table);
        JSplitPane tableSplitPane = createSplitPane(scrollPane);

        add(tableSplitPane, BorderLayout.CENTER);
        loadInitialData();

        table.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    selectedVendor = getVendorFromRow(selectedRow);

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

        vendorFilter.getSearchButton().addActionListener(e -> {
            String name = vendorFilter.getSearchNameField().getText();
            String address = vendorFilter.getSearchAddressField().getText();

            if (!name.isEmpty()) {
                name = name;
            } else {
                name = null;
            }
            
            if (!address.isEmpty()) {
                address = address;
            } else {
                address = null;
            }

            List<VendorEntity> vendors = vendorController.getAllVendors(name, address);
            loadTableData(vendors);

        });
    }

    private VendorEntity getVendorFromRow(int rowIndex) {
        Integer id = (Integer) table.getValueAt(rowIndex, 0);
        String name = (String) table.getValueAt(rowIndex, 1);
        String address = (String) table.getValueAt(rowIndex, 2);
        return new VendorEntity(id, name, address);
    }

    public VendorEntity getSelectedVendor() {
        return selectedVendor;
    }

    private DefaultTableModel createTableModel() {
        String[] columnNames = {"ID", "Name", "Address"};
        return new DefaultTableModel(columnNames, 0);
    }

    private JTable createTable(DefaultTableModel model) {
        JTable table = new JTable(model);
        Font tableFont = new Font("Arial", Font.BOLD, 14);

        table.setFont(tableFont);
        table.setRowHeight(30);
        configureTableHeader(table);

        table.setPreferredScrollableViewportSize(new Dimension(600, 260));
        table.setFillsViewportHeight(true);

        return table;
    }

    private void configureTableHeader(JTable table) {
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 16));
    }

    private JSplitPane createSplitPane(JScrollPane scrollPane) {
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, vendorFilter, scrollPane);
        splitPane.setDividerLocation(50);
        return splitPane;
    }

    private void loadInitialData() {
        List<VendorEntity> vendors = vendorController.getAllVendors();
        loadTableData(vendors);
    }

    public void loadTableData(List<VendorEntity> vendors) {
        tableModel.setRowCount(0);

        for (VendorEntity vendor : vendors) {

            Object[] rowData = {
                vendor.getId(),
                vendor.getName(),
                vendor.getAddress()};

            tableModel.addRow(rowData);
        }
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

    public void editRowItem(int selectedRow) {
        Integer id = (Integer) table.getValueAt(selectedRow, 0);
        String defaultName = table.getValueAt(selectedRow, 1) != null ? table.getValueAt(selectedRow, 1).toString() : "";
        String defaultAddress = table.getValueAt(selectedRow, 2) != null ? table.getValueAt(selectedRow, 2).toString() : "";

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

            JTextField addressField = new JTextField(defaultAddress);
            addressField.setFont(font);
            addressField.setBorder(padding);

            editPanel.add(new JLabel("ID:"));
            editPanel.add(idField);
            editPanel.add(new JLabel("Name:"));
            editPanel.add(nameField);
            editPanel.add(new JLabel("Address:"));
            editPanel.add(addressField);

            int result = JOptionPane.showConfirmDialog(
                    this,
                    editPanel,
                    "Edit Vendor",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                try {
                    String newName = nameField.getText().trim();
                    String newAddress = addressField.getText().trim();

                    // Validate name
                    if (newName.isEmpty()) {
                        showWarning("Name should not be empty!");
                        nameField.requestFocus();
                        continue;
                    }

                    // Validate address
                    if (newAddress.isEmpty()) {
                        showWarning("Address should not be empty!");
                        addressField.requestFocus();
                        continue;
                    }

                    // If all inputs are valid, update the vendor and exit the loop
                    VendorEntity updatedVendor = new VendorEntity(id, newName, newAddress);
                    vendorController.updateVendor(updatedVendor);

                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    model.setValueAt(newName, selectedRow, 1);
                    model.setValueAt(newAddress, selectedRow, 2);

                    showSuccess("Vendor updated successfully!");
                    break;
                } catch (Exception e) {
                    e.printStackTrace();
                    showError("Error updating vendor: " + e.getMessage());
                    break;
                }
            } else {
                break;
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
                vendorController.deleteVendor(id);

                DefaultTableModel model = (DefaultTableModel) table.getModel();
                model.removeRow(selectedRow);
                showSuccess("Item deleted successfully!");
            } catch (Exception e) {
                e.printStackTrace();
                showError("Error deleting item: " + e.getMessage());
            }
        }
    }

    private void refreshItems() {
        try {
            List<VendorEntity> vendors = vendorController.getAllVendors();
            loadTableData(vendors);
            showSuccess("All items are successfully refresh");
        } catch (Exception e) {
            showError("All items are failed refresh");
        }
    }
}
