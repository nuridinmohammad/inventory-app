package com.multibahana.inventoryapp.views.components.molecules;

import com.multibahana.inventoryapp.entities.CategoryEntity;
import com.toedter.calendar.JDateChooser;
import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

public class StockFilter extends JPanel {

    public StockFilter() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(new EmptyBorder(6, 6, 6, 6));

        Font font = new Font("Arial", Font.PLAIN, 16);

        JLabel searchLabel = new JLabel("Search : ");
        JTextField searchField = new JTextField();
        searchField.setFont(font);

        JDateChooser chooser = new JDateChooser();
        chooser.setDateFormatString("yyyy-MM-dd");
        chooser.setFont(font);

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1);
        JSpinner amountSpinner = new JSpinner(spinnerModel);
        ((JSpinner.DefaultEditor) amountSpinner.getEditor()).getTextField().setFont(font);

        JComboBox<CategoryEntity> categoryComboBox = new JComboBox<>();
        categoryComboBox.setFont(font);

        List<CategoryEntity> categoryList = new ArrayList<>();
        categoryList.add(new CategoryEntity(1, "-- Select category --"));
        categoryList.add(new CategoryEntity(2, "Category 2"));
        categoryList.add(new CategoryEntity(3, "Category 3"));
        categoryList.add(new CategoryEntity(4, "Category 4"));

        ComboBoxModel<CategoryEntity> categoryModel = new DefaultComboBoxModel<>(categoryList.toArray(new CategoryEntity[0]));
        categoryComboBox.setModel(categoryModel);

        // Membungkus setiap komponen dalam JPanel dengan EmptyBorder sebagai margin luar
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        searchPanel.setBorder(new EmptyBorder(4, 4, 4, 4));
        searchPanel.add(searchField);

        JPanel datePanel = new JPanel();
        datePanel.setLayout(new BoxLayout(datePanel, BoxLayout.Y_AXIS));
        datePanel.setBorder(new EmptyBorder(4, 4, 4, 4));
        datePanel.add(chooser);

        JPanel amountPanel = new JPanel();
        amountPanel.setLayout(new BoxLayout(amountPanel, BoxLayout.Y_AXIS));
        amountPanel.setBorder(new EmptyBorder(4, 4, 4, 4));
        amountPanel.add(amountSpinner);

        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
        categoryPanel.setBorder(new EmptyBorder(4, 4, 4, 4));
        categoryPanel.add(categoryComboBox);

        add(searchLabel);
        add(searchPanel);
        add(datePanel);
        add(amountPanel);
        add(categoryPanel);
    }
}
