package com.multibahana.inventoryapp.views.components.molecules;

import com.multibahana.inventoryapp.controllers.CategoryController;
import com.multibahana.inventoryapp.daoimplements.CategoryDAOImpl;
import com.multibahana.inventoryapp.entities.CategoryEntity;
import java.awt.Font;
import java.util.List;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ProductFilter extends JPanel {

    private JTextField searchField;
    private JTextField minField;
    private JTextField maxField;
    private JComboBox<CategoryEntity> categoryComboBox;
    private JButton searchButton;
    private CategoryController categoryController;

    public ProductFilter() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(new EmptyBorder(6, 6, 6, 6));

        Font font = new Font("Arial", Font.PLAIN, 16);

        JLabel searchLabel = new JLabel("Search ");
        searchField = new JTextField();
        searchField.setFont(font);

        JLabel minLabel = new JLabel("Min ");
        minField = new JTextField();
        minField.setFont(font);

        JLabel maxLabel = new JLabel("Max ");
        maxField = new JTextField();
        maxField.setFont(font);

        CategoryController categoryController = new CategoryController(new CategoryDAOImpl());
        Map<Integer, CategoryEntity> categories = categoryController.getAllCategories();
        
        DefaultComboBoxModel<CategoryEntity> categoryModel = new DefaultComboBoxModel<>();
        categoryComboBox = new JComboBox<>();
        categoryModel.addElement(new CategoryEntity(0, "-- Select category --"));
        categories.values().forEach(categoryModel::addElement);
        categoryComboBox.setModel(categoryModel);
        categoryComboBox.setFont(font);

        searchButton = new JButton("Search");

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
        searchPanel.setBorder(new EmptyBorder(4, 4, 4, 4));
        searchPanel.add(searchField);

        JPanel minPanel = new JPanel();
        minPanel.setLayout(new BoxLayout(minPanel, BoxLayout.Y_AXIS));
        minPanel.setBorder(new EmptyBorder(4, 4, 4, 4));
        minPanel.add(minField);

        JPanel maxPanel = new JPanel();
        maxPanel.setLayout(new BoxLayout(maxPanel, BoxLayout.Y_AXIS));
        maxPanel.setBorder(new EmptyBorder(4, 4, 4, 4));
        maxPanel.add(maxField);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(new EmptyBorder(4, 4, 4, 4));
        buttonPanel.add(searchButton);

        JPanel categoryPanel = new JPanel();
        categoryPanel.setLayout(new BoxLayout(categoryPanel, BoxLayout.Y_AXIS));
        categoryPanel.setBorder(new EmptyBorder(4, 4, 4, 4));
        categoryPanel.add(categoryComboBox);

        add(searchLabel);
        add(searchPanel);
        add(minLabel);
        add(minPanel);
        add(maxLabel);
        add(maxPanel);
        add(categoryPanel);
        add(buttonPanel);

    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    public JTextField getMinField() {
        return minField;
    }

    public JTextField getMaxField() {
        return maxField;
    }

    public JComboBox<CategoryEntity> getCategoryComboBox() {
        return categoryComboBox;
    }
}
