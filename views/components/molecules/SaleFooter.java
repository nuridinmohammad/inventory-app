package com.multibahana.inventoryapp.views.components.molecules;

import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class SaleFooter extends JPanel {

    private JLabel grandTotalLabel;
    private JLabel payLabel;
    private JLabel changeLabel;

    public SaleFooter() {
        setLayout(new GridLayout(1, 2));

        JPanel leftPanel = new JPanel();
        leftPanel.add(new JLabel());

        JPanel rightPanel = createRightPanel();
        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        add(leftPanel);
        add(rightPanel);
    }

    private JPanel createRightPanel() {
        JPanel panel = new JPanel(new GridLayout(7, 2, 5, 5));

        panel.add(new JLabel("Grand total"));
        grandTotalLabel = createAlignedLabel("Rp. 10.000,-");
        panel.add(grandTotalLabel);

        panel.add(new JLabel("Pay"));
        payLabel = createAlignedLabel("Rp. 20.000,-");
        panel.add(payLabel);

        panel.add(new JLabel("Change"));
        changeLabel = createAlignedLabel("Rp. 10.000,-");
        panel.add(changeLabel);

        panel.add(new JPanel());
        panel.add(new JButton("Submit"));

        return panel;
    }

    private JLabel createAlignedLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 16));
        label.setHorizontalAlignment(JLabel.RIGHT);
        return label;
    }

}
