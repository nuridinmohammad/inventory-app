package com.multibahana.inventoryapp.views.components.molecules;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.util.ArrayList;
import java.util.List;

public class Navbar extends JPanel {

    private List<JButton> buttons;
    private JButton toSaleButton;
    private JButton toProductButton;
    private JButton toReceiptButton;
    private JButton toVendorButton;

    public Navbar() {
        this.buttons = new ArrayList<>();
        setLayout(new BorderLayout());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.setOpaque(false);

        toSaleButton = createButton("Sales (Comming soon)");
        buttonsPanel.add(toSaleButton);

        toProductButton = createButton("Product");
        buttonsPanel.add(toProductButton);

        toReceiptButton = createButton("Receipt");
        buttonsPanel.add(toReceiptButton);

        toVendorButton = createButton("Vendor");
        buttonsPanel.add(toVendorButton);

        add(buttonsPanel, BorderLayout.EAST);
        setBackground(Color.DARK_GRAY);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);

        buttons.add(button);

        return button;
    }

    /*private void updateButtonColors(JButton activeButton) {
    for (JButton button : buttons) {
    if (button == activeButton) {
    button.setForeground(Color.YELLOW);
    } else {
    button.setForeground(Color.WHITE);
    }
    }
    }*/

    // Getters for the buttons
    public JButton getToSaleButton() {
        return toSaleButton;
    }

    public JButton getToProductButton() {
        return toProductButton;
    }

    public JButton getToReceiptButton() {
        return toReceiptButton;
    }

    public JButton getToVendorButton() {
        return toVendorButton;
    }


    public List<JButton> getButtons() {
        return buttons;
    }
}
