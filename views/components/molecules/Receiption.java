package com.multibahana.inventoryapp.views.components.molecules;

import com.multibahana.inventoryapp.controller.EvidenceController;
import com.multibahana.inventoryapp.controller.ProductInController;
import com.multibahana.inventoryapp.controllers.ProductController;
import com.multibahana.inventoryapp.dao.EvidenceDAOImpl;
import com.multibahana.inventoryapp.dao.ProductInDAOImpl;
import com.multibahana.inventoryapp.daoimplements.ProductDAOImpl;
import com.multibahana.inventoryapp.entities.EvidenceEntity;
import com.multibahana.inventoryapp.entities.ProductEntity;
import com.multibahana.inventoryapp.entities.ProductInEntity;
import com.multibahana.inventoryapp.entities.VendorEntity;
import com.multibahana.inventoryapp.views.components.atoms.ReceiptionProductsTable;
import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

public class Receiption extends JPanel {

    private static final int PANEL_PADDING = 10;
    private JPanel containerPanel;
    private JSplitPane receiptSplitPane;
    private ReceiptHeader receiptHeader;
    private ReceiptionProductsTable receiptionProductsTable;
    private ReceiptTable receiptTable;
    private ProductInController productInController;
    private ProductController productController;

    public Receiption() {
        setLayout(new BorderLayout());
        setBorder(new EmptyBorder(PANEL_PADDING + 10, PANEL_PADDING, PANEL_PADDING, PANEL_PADDING));
        containerPanel = new JPanel(new BorderLayout());
        containerPanel.setPreferredSize(new Dimension(660, 440));

        receiptionProductsTable = new ReceiptionProductsTable();
        receiptHeader = new ReceiptHeader(receiptionProductsTable);
        receiptTable = new ReceiptTable();
        productInController = new ProductInController(new ProductInDAOImpl());
        productController = new ProductController(new ProductDAOImpl());

        receiptSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, receiptHeader, receiptionProductsTable);
        containerPanel.add(receiptSplitPane, BorderLayout.CENTER);

        JPanel container = new JPanel(new BorderLayout());
        JButton addReceiption = new JButton("Add receiption [ + ]");
        configureButton(addReceiption);
        container.add(addReceiption, BorderLayout.EAST);
        addReceiption.addActionListener(e -> addReceiption());

        add(container, BorderLayout.CENTER);
    }

    private void configureButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setForeground(Color.BLUE);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
//        button.setBorderPainted(false);
    }

    private void addReceiption() {
        int result = JOptionPane.showConfirmDialog(
                null,
                containerPanel,
                "Add Receiption",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            Integer rowCount = receiptionProductsTable.getTableModel().getRowCount();
            VendorEntity vendor = receiptHeader.getSelectedVendor();
            EvidenceController evidenceController = new EvidenceController(new EvidenceDAOImpl());
            String evidenceText = receiptHeader.getNoEvidenceField().getText();
            if (!evidenceText.isEmpty() && evidenceText != null && rowCount > 0) {
                evidenceText = receiptHeader.getNoEvidenceField().getText();
                Integer evidenceId = evidenceController.addEvidence(new EvidenceEntity(evidenceText));
                JDateChooser dateChooserHeader = receiptHeader.getDateChooser();

                List<ProductInEntity> productInList = receiptionProductsTable.getTableModel().getDataVector().stream()
                        .map(row -> {
                            Vector<?> rowData = (Vector<?>) row;
                            ProductInEntity productIn = new ProductInEntity();

                            try {
                                productIn.setProductId(Integer.parseInt(rowData.get(0).toString()));
                                productIn.setVendorId(vendor.getId());
                                productIn.setEvidenceId(evidenceId);
                                productIn.setDate(dateChooserHeader.getDate());
                                productIn.setQuantity(Integer.parseInt(rowData.get(3).toString()));
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            }

                            return productIn;
                        })
                        .collect(Collectors.toList());

                if (productInList.size() > 0) {
                    productInList.forEach(productIn -> {
                        ProductInEntity productInEntity = new ProductInEntity(productIn.getProductId(), productIn.getVendorId(), productIn.getEvidenceId(), productIn.getDate(), productIn.getQuantity());
                        ProductEntity product = new ProductEntity();
                        ProductEntity tempProduct = productController.getProductById(productIn.getProductId());
                        Integer tempStock = tempProduct.getStock();
                        tempStock += productIn.getQuantity();

                        product.setId(productIn.getProductId());
                        product.setStock(tempStock);

                        productInController.addProductIn(productInEntity);
                        productController.updateStockProduct(product);

                    });
                } else {
                    System.out.println("Data has doesn't exist");
                }
            }
            receiptionProductsTable.getTableModel().setRowCount(0);
            receiptHeader.clearFields();
        } else {
            receiptionProductsTable.getTableModel().setRowCount(0);
            receiptHeader.clearFields();
        }
    }

}
