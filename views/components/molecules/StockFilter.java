package com.multibahana.inventoryapp.views.components.molecules;

import com.multibahana.inventoryapp.controllers.CategoryController;
import com.multibahana.inventoryapp.daoimplements.CategoryDAOImpl;
import com.multibahana.inventoryapp.entities.CategoryEntity;
import java.awt.Font;
import java.util.Map;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class StockFilter extends JPanel {

    private JTextField searchField;
    private JComboBox<CategoryEntity> categoryComboBox;
    private JButton searchButton;

    public StockFilter() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(new EmptyBorder(6, 6, 6, 6));
        Font font = new Font("Arial", Font.PLAIN, 16);

        JLabel searchLabel = new JLabel("Search : ");
        searchField = new JTextField();
        searchField.setFont(font);

        categoryComboBox = createCategoryComboBox(font);

        searchButton = new JButton("Search");

        JPanel searchPanel = createPanel(searchField, font);
        JPanel categoryPanel = createPanel(categoryComboBox, font);
        JPanel searchButtonPanel = createPanel(searchButton, font);

        add(searchLabel);
        add(searchPanel);
        add(categoryPanel);
        add(searchButtonPanel);
    }

    public <T extends JComponent> JPanel createPanel(T component, Font font) {
        component.setFont(font);
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(4, 4, 4, 4));
        panel.add(component);

        return panel;
    }

    public JComboBox<CategoryEntity> createCategoryComboBox(Font font) {
        CategoryController categoryController = new CategoryController(new CategoryDAOImpl());
        Map<Integer, CategoryEntity> categories = categoryController.getAllCategories();

        DefaultComboBoxModel<CategoryEntity> categoryModel = new DefaultComboBoxModel<>();
        JComboBox<CategoryEntity> categoryComboBox = new JComboBox<>();
        categoryModel.addElement(new CategoryEntity(0, "-- Select category --"));
        categories.values().forEach(categoryModel::addElement);
        categoryComboBox.setModel(categoryModel);
        categoryComboBox.setFont(font);

        return categoryComboBox;
    }

    // Getters for the components
    public JTextField getSearchField() {
        return searchField;
    }

    public JComboBox<CategoryEntity> getCategoryComboBox() {
        return categoryComboBox;
    }

    public JButton getSearchButton() {
        return searchButton;
    }
}
