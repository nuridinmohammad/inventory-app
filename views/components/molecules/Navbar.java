package com.multibahana.inventoryapp.views.components.molecules;

import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Navbar extends JPanel {

    private String navbarItem;
    private NavbarListener listener;
    private List<JButton> buttons;

    public Navbar(NavbarListener listener) {
        this.listener = listener;
        this.buttons = new ArrayList<>();
        setLayout(new BorderLayout());

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.setOpaque(false);

        JButton toSale = createButton("Sales");
        buttonsPanel.add(toSale);

        JButton toProduct = createButton("Product");
        buttonsPanel.add(toProduct);

        JButton toReceipt = createButton("Receipt");
        buttonsPanel.add(toReceipt);

        JButton toVendor = createButton("Vendor");
        buttonsPanel.add(toVendor);

        JButton toStock = createButton("Stock");
        buttonsPanel.add(toStock);

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

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                navbarItem = button.getText();
                updateButtonColors(button);
                if (listener != null) {
                    listener.onNavbarItemSelected(navbarItem);
                }
            }
        });

        return button;
    }

    private void updateButtonColors(JButton activeButton) {
        for (JButton button : buttons) {
            if (button == activeButton) {
                button.setForeground(Color.YELLOW);
            } else {
                button.setForeground(Color.WHITE);
            }
        }
    }

    public interface NavbarListener {
        void onNavbarItemSelected(String navbarItem);
    }
}
