package com.multibahana.inventoryapp.views.components.molecules;

import com.multibahana.inventoryapp.context.LeftPanelContext;
import com.multibahana.inventoryapp.controllers.CategoryController;
import com.multibahana.inventoryapp.controllers.ProductController;
import com.multibahana.inventoryapp.controllers.StockController;
import com.multibahana.inventoryapp.controllers.VendorController;
import com.multibahana.inventoryapp.daoimplements.CategoryDAOImpl;
import com.multibahana.inventoryapp.daoimplements.ProductDAOImpl;
import com.multibahana.inventoryapp.daoimplements.StockDAOImpl;
import com.multibahana.inventoryapp.daoimplements.VendorDAOImpl;
import com.multibahana.inventoryapp.entities.CategoryEntity;
import com.multibahana.inventoryapp.entities.ProductEntity;
import com.multibahana.inventoryapp.entities.StockEntity;
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

public class StockTable extends JPanel {

    private JTable table;
    private DefaultTableModel tableModel;
    private StockFilter stockFilter;

    private PopRowMenu popupMenu;
    private String[] columnNames;
    private StockController stockController;

    public StockTable() {
        setLayout(new BorderLayout());
        initUI();
    }

    private void initUI() {
        tableModel = createTableModel();
        this.stockController = new StockController(new StockDAOImpl());
        table = createTable(tableModel);
        stockFilter = new StockFilter();

        table.setFont(new Font("Arial", Font.BOLD, 14));
        table.setRowHeight(30);

        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(new Font("Arial", Font.BOLD, 16));

        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(new CustomCellRenderer());
        }

        JScrollPane scrollPane = new JScrollPane(table);
        JSplitPane tableSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, stockFilter, scrollPane);
        tableSplitPane.setDividerLocation(50);
        add(tableSplitPane, BorderLayout.CENTER);

        popupMenu = new PopRowMenu();
        popupMenu.remove(popupMenu.getEditRow());
        popupMenu.remove(popupMenu.getDeleteRow());

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

        popupMenu.getRefresh().addActionListener(e -> {
            int selectedRow = table.getSelectedRow();
            if (selectedRow != -1) {
                refreshItems();
            } else {
                JOptionPane.showMessageDialog(StockTable.this,
                        "Please select a row to delete",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
            }
        });

        stockFilter.getSearchButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String searchValue = stockFilter.getSearchField().getText();
                CategoryEntity categoryValue = (CategoryEntity) stockFilter.getCategoryComboBox().getSelectedItem();

                if (!searchValue.isEmpty()) {
                    searchValue = searchValue;
                } else {
                    searchValue = null;
                }

                if (categoryValue != null && categoryValue.getId() == 0) {
                    categoryValue = null;
                }

                List<StockEntity> stocks = stockController.getAllStocksStatic(searchValue, categoryValue);
                loadTableData(stocks);

            }
        });

        loadInitialData();

    }

    private void loadInitialData() {
        List<StockEntity> stocks = stockController.getAllStocksStatic();
        loadTableData(stocks);
    }

    public void loadTableData(List<StockEntity> stocks) {
        tableModel.setRowCount(0);

        for (StockEntity stock : stocks) {
            Object[] rowData = {
                stock.getProductId(),
                stock.getProductName(),
                stock.getAmount(),
                stock.getCategoryName(),};

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

        return table;
    }

    private DefaultTableModel createTableModel() {
        String[] columnNames = {"Product ID", "Product name", "Qty", "Category"};
        return new DefaultTableModel(columnNames, 0);
    }

    private void refreshItems() {
        try {

            List<StockEntity> stocks = stockController.getAllStocksStatic();
            loadTableData(stocks);
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
