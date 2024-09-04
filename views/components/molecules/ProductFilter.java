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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class ProductFilter extends JPanel {

    private JTextField searchField;
    /*private JTextField minField;
    private JTextField maxField;*/
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

        /*JLabel minLabel = new JLabel("Min ");
        minField = new JTextField();
        minField.setFont(font);
        
        JLabel maxLabel = new JLabel("Max ");
        maxField = new JTextField();
        maxField.setFont(font);*/

        CategoryController categoryController = new CategoryController(new CategoryDAOImpl());
        Map<Integer, CategoryEntity> categories = categoryController.getAllCategories();

        DefaultComboBoxModel<CategoryEntity> categoryModel = new DefaultComboBoxModel<>();
        categoryComboBox = new JComboBox<>();
        categoryModel.addElement(new CategoryEntity(0, "-- Select category --"));
        categories.values().forEach(categoryModel::addElement);
        categoryComboBox.setModel(categoryModel);
        categoryComboBox.setFont(font);

        searchButton = new JButton("Search");

        JPanel searchPanel = createPanel();
        searchPanel.add(searchField);

        /*JPanel minPanel = createPanel();
        minPanel.add(minField);*/

        /*JPanel maxPanel = createPanel();
        maxPanel.add(maxField);*/
        
        JPanel buttonPanel =createPanel();
        buttonPanel.add(searchButton);

        JPanel categoryPanel = createPanel();
        categoryPanel.add(categoryComboBox);

        add(searchLabel);
        add(searchPanel);
//        add(minLabel);
//        add(minPanel);
//        add(maxLabel);
//        add(maxPanel);
        add(categoryPanel);
        add(buttonPanel);

    }

    public JButton getSearchButton() {
        return searchButton;
    }

    public JTextField getSearchField() {
        return searchField;
    }

    /*public JTextField getMinField() {
    return minField;
    }*/

    /*public JTextField getMaxField() {
    return maxField;
    }*/
    
    public JComboBox<CategoryEntity> getCategoryComboBox() {
        return categoryComboBox;
    }

    private JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(new EmptyBorder(4, 4, 4, 4));
        return panel;
    }
}
