package com.multibahana.inventoryapp.views.components.templates;

import com.multibahana.inventoryapp.context.LeftPanelContext;
import com.multibahana.inventoryapp.context.TablesContext;
import com.multibahana.inventoryapp.controllers.CategoryController;
import com.multibahana.inventoryapp.controllers.CategoryRelationController;
import com.multibahana.inventoryapp.controllers.ProductController;
import com.multibahana.inventoryapp.dao.CategoryDAO;
import com.multibahana.inventoryapp.dao.CategoryRelationDAO;
import com.multibahana.inventoryapp.daoimplements.CategoryDAOImpl;
import com.multibahana.inventoryapp.daoimplements.CategoryRelationDAOImpl;
import com.multibahana.inventoryapp.daoimplements.ProductDAOImpl;
import com.multibahana.inventoryapp.entities.CategoryEntity;
import com.multibahana.inventoryapp.entities.CategoryRelationEntity;
import com.multibahana.inventoryapp.entities.ProductEntity;

import com.multibahana.inventoryapp.views.components.atoms.CustomTreeCellRenderer;
import com.multibahana.inventoryapp.views.components.atoms.PopCategoryMenu;
import com.multibahana.inventoryapp.views.components.molecules.ProductForm;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

public class LeftPanel extends JPanel {

    private CategoryController categoryController;
    private CategoryRelationController categoryRelationController;
    private JTree categoryTree;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode root;
    private PopCategoryMenu popMenu;
    private ProductController productController;
    private ProductDAOImpl productDAOImpl;

    public LeftPanel() {
        CategoryDAO categoryDAO = new CategoryDAOImpl();
        CategoryRelationDAO categoryRelationDAO = new CategoryRelationDAOImpl();

        this.categoryController = new CategoryController(categoryDAO);
        this.categoryRelationController = new CategoryRelationController(categoryRelationDAO);
        productDAOImpl = new ProductDAOImpl();
        productController = new ProductController(productDAOImpl);
        popMenu = new PopCategoryMenu();

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        root = new DefaultMutableTreeNode("Category Products");
        treeModel = new DefaultTreeModel(root);
        categoryTree = new JTree(treeModel);

        categoryTree.setCellRenderer(new CustomTreeCellRenderer());
        JScrollPane treeScrollPane = new JScrollPane(categoryTree);

        add(treeScrollPane, BorderLayout.CENTER);
        loadInitTreeNode();

        categoryTree.setSelectionRow(0);
        TreePath selectedPath = categoryTree.getSelectionPath();
        if (selectedPath != null) {
            LeftPanelContext.getInstance().setTreePath(selectedPath);
        }

        categoryTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                categoryTree.setSelectionPath(e.getNewLeadSelectionPath());
                TreePath selectedPath = categoryTree.getSelectionPath();

                if (selectedPath != null) {
                    LeftPanelContext.getInstance().setTreePath(selectedPath);
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) selectedPath.getLastPathComponent();
                    CategoryEntity categoryEntity = (CategoryEntity) selectedNode.getUserObject();
                    List<ProductEntity> products = productController.getAllProductsByCategoryId(categoryEntity.getId());
                    TablesContext.getInstance().getCurrentProductTables().loadTableData(products);
                }
            }
        });

        categoryTree.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    TreePath path = categoryTree.getPathForLocation(e.getX(), e.getY());

                    if (path != null) {
                        categoryTree.setSelectionPath(path);
                        TreePath selectedPath = categoryTree.getSelectionPath();
                        if (selectedPath != null) {
                            LeftPanelContext.getInstance().setTreePath(selectedPath);
                        }
                        popMenu.show(categoryTree, e.getX(), e.getY());
                    }
                }
            }
        });

        popMenu.getAddCategory().addActionListener(e -> {
            TreePath path = categoryTree.getSelectionPath();
            if (path != null) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
                addCategory(selectedNode);
            }
        });

        popMenu.getDeleteCategory().addActionListener(e -> {
            TreePath path = categoryTree.getSelectionPath();
            if (path != null) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
                deleteCategory(selectedNode);
            }
        });

        popMenu.getEditCategory().addActionListener(e -> {
            TreePath path = categoryTree.getSelectionPath();
            if (path != null) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) path.getLastPathComponent();
                editCategory(selectedNode);
            }
        });

        popMenu.getRefreshCategory().addActionListener(e -> {
            loadInitTreeNode();
        });
    }

    public JTree getCategoryTree() {
        return categoryTree;
    }

    private void loadInitTreeNode() {
        Map<Integer, CategoryEntity> categorys = categoryController.getAllCategories();
        List<CategoryRelationEntity> categoryRelations = categoryRelationController.getAllCategoryRelations();

        root.removeAllChildren();

        CategoryEntity rootCategory = categorys.get(1);
        if (rootCategory != null) {
            populateTreeNode(root, rootCategory, categorys, categoryRelations);
        }

        treeModel.reload(root);
        categoryTree.repaint();
    }

    private void populateTreeNode(DefaultMutableTreeNode parentNode, CategoryEntity parentCategory, Map<Integer, CategoryEntity> categorys, List<CategoryRelationEntity> categoryRelations) {
        parentNode.setUserObject(parentCategory);

        for (CategoryRelationEntity relation : categoryRelations) {
            if (relation.getAncestorId() == parentCategory.getId()) {
                CategoryEntity childCategory = categorys.get(relation.getDescendantId());
                if (childCategory != null) {
                    DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(childCategory);
                    parentNode.add(childNode);
                    populateTreeNode(childNode, childCategory, categorys, categoryRelations);
                }
            }
        }
    }

    public void addCategory(DefaultMutableTreeNode selectedNode) {
        if (selectedNode == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a Category node",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        while (true) {
            JPanel containerPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;

            JLabel nameLabel = new JLabel("Name:");
            gbc.gridy = 0;
            containerPanel.add(nameLabel, gbc);

            JTextField categoryNameField = new JTextField(20);
            categoryNameField.setFont(new Font("Arial", Font.BOLD, 16));
            categoryNameField.setForeground(Color.BLACK);
            categoryNameField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY, 1),
                    BorderFactory.createEmptyBorder(6, 6, 6, 6)
            ));
            gbc.gridx = 1;
            containerPanel.add(categoryNameField, gbc);

            int result = JOptionPane.showConfirmDialog(
                    this,
                    containerPanel,
                    "Add New Category",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (result == JOptionPane.CANCEL_OPTION) {
                break;
            }

            String categoryName = categoryNameField.getText().trim();

            if (categoryName.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Category name should not be empty!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
                continue;
            }

            try {
                CategoryEntity categoryEntity = new CategoryEntity();
                categoryEntity.setName(categoryName);

                Integer categoryId = categoryController.addCategory(categoryEntity);
                CategoryEntity userObject = (CategoryEntity) selectedNode.getUserObject();

                CategoryRelationEntity categoryRelationEntity = new CategoryRelationEntity(userObject.getId(), categoryId, categoryName);
                categoryRelationController.addCategoryRelation(categoryRelationEntity);

                CategoryEntity newCategory = new CategoryEntity(categoryId, categoryName);
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(newCategory);
                selectedNode.add(newNode);
                treeModel.reload(selectedNode);

                JOptionPane.showMessageDialog(
                        this,
                        "Category added successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

                break;

            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(
                        this,
                        "Error adding category: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void deleteCategory(DefaultMutableTreeNode selectedNode) {
        if (selectedNode == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a Category node",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        CategoryEntity currentCategory = (CategoryEntity) selectedNode.getUserObject();

        int result = JOptionPane.showConfirmDialog(
                this,
                "Are you sure want to delete this category: " + currentCategory.getName() + "?",
                "Delete Category",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            try {
                traverseAndDelete(currentCategory.getId());
                productController.deleteProductByCategoryId(currentCategory.getId());
                categoryController.deleteCategory(currentCategory.getId());

                DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) selectedNode.getParent();
                if (parentNode != null) {
                    parentNode.remove(selectedNode);
                    DefaultTreeModel model = (DefaultTreeModel) categoryTree.getModel();
                    model.reload(parentNode);
                    JOptionPane.showMessageDialog(
                            this,
                            "Category deleted successfully!",
                            "Success",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(
                        this,
                        "Error deleting category: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    private void editCategory(DefaultMutableTreeNode selectedNode) {
        if (selectedNode == null) {
            JOptionPane.showMessageDialog(
                    this,
                    "Please select a Category node",
                    "Warning",
                    JOptionPane.WARNING_MESSAGE
            );
            return;
        }

        CategoryEntity currentCategory = (CategoryEntity) selectedNode.getUserObject();

        while (true) {
            JPanel containerPanel = new JPanel(new GridBagLayout());
            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 10, 10);
            gbc.fill = GridBagConstraints.HORIZONTAL;
            gbc.gridx = 0;

            // Name Label and TextField
            gbc.gridy = 0;
            JLabel nameLabel = new JLabel("Name:");
            containerPanel.add(nameLabel, gbc);

            JTextField categoryNameField = new JTextField(20);
            categoryNameField.setText(currentCategory.getName());
            categoryNameField.setFont(new Font("Arial", Font.BOLD, 16));
            categoryNameField.setForeground(Color.BLACK);
            categoryNameField.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Color.GRAY, 1),
                    BorderFactory.createEmptyBorder(6, 6, 6, 6)
            ));
            gbc.gridx = 1;
            containerPanel.add(categoryNameField, gbc);

            // Move to Label and ComboBox
            gbc.gridy = 1;
            gbc.gridx = 0;
            JLabel moveToLabel = new JLabel("Move to:");
            containerPanel.add(moveToLabel, gbc);

            JComboBox<CategoryRelationEntity> categoryComboBox = new JComboBox<>();
            DefaultComboBoxModel<CategoryRelationEntity> categoryModel = new DefaultComboBoxModel<>();
            TreeNode parentNode = selectedNode.getParent();
            DefaultMutableTreeNode parentCategory = (DefaultMutableTreeNode) parentNode;
            CategoryEntity parentCategoryEntity = (CategoryEntity) parentCategory.getUserObject();

            categoryModel.addElement(new CategoryRelationEntity(parentCategoryEntity.getId(), currentCategory.getId(), "--Select category--"));

            CategoryEntity categoryEntity = categoryController.getCategory(1);
            categoryModel.addElement(new CategoryRelationEntity(categoryEntity.getId(), categoryEntity.getId(), categoryEntity.getName()));

            // Add relevant category entities to the combo box
            List<CategoryRelationEntity> categoryEntities = categoryRelationController.getAllCategoryRelations();
            for (CategoryRelationEntity item : categoryEntities) {
                if (item.getDescendantId() != currentCategory.getId()) {
                    categoryModel.addElement(item);
                }
            }

            categoryComboBox.setModel(categoryModel);

            gbc.gridx = 1;
            containerPanel.add(categoryComboBox, gbc);

            // Show dialog
            int result = JOptionPane.showConfirmDialog(
                    this,
                    containerPanel,
                    "Edit Category",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE
            );

            if (result == JOptionPane.CANCEL_OPTION) {
                break;
            }

            String categoryName = categoryNameField.getText().trim();
            CategoryRelationEntity moveToCategory = (CategoryRelationEntity) categoryComboBox.getSelectedItem();

            if (categoryName.isEmpty()) {
                JOptionPane.showMessageDialog(
                        this,
                        "Category name should not be empty!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
                continue;
            }

            if (moveToCategory == null) {
                JOptionPane.showMessageDialog(
                        this,
                        "Please select a valid category!",
                        "Warning",
                        JOptionPane.WARNING_MESSAGE
                );
                continue;
            }

            try {
                currentCategory.setName(categoryName);
                categoryController.updateCategory(currentCategory.getId(), categoryName);
                if (!moveToCategory.getName().equals("--Select category--")) {
                    categoryRelationController.updateCategoryRelation(new CategoryRelationEntity(moveToCategory.getDescendantId(), currentCategory.getId(), categoryName));
                    CategoryRelationEntity categoryRelationEntity = categoryRelationController.getCategoryRelationByIds(moveToCategory.getDescendantId(), currentCategory.getId());
                    loadTreeNode(categoryRelationEntity, selectedNode, currentCategory, categoryName, moveToCategory);
                } else {
                    categoryRelationController.updateCategoryRelation(new CategoryRelationEntity(moveToCategory.getAncestorId(), currentCategory.getId(), categoryName));
                    DefaultTreeModel model = (DefaultTreeModel) categoryTree.getModel();
                    model.reload(selectedNode);
                }
                JOptionPane.showMessageDialog(
                        this,
                        "Category edited successfully!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );
                break;  // Exit the loop after successful update
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(
                        this,
                        "Error editing category: " + e.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        }
    }

    public void traverseAndDelete(Integer currentCategoryId) throws Exception {
        List<CategoryRelationEntity> categoryRelationEntities = categoryRelationController.getAllCategoryRelationsByAncestorId(currentCategoryId);
        for (CategoryRelationEntity item : categoryRelationEntities) {
            traverseAndDelete(item.getDescendantId());
            productController.deleteProductByCategoryId(item.getDescendantId());
            categoryController.deleteCategory(item.getDescendantId());
        }
    }

    private void loadTreeNode(CategoryRelationEntity categoryRelationEntity, DefaultMutableTreeNode selectedNode, CategoryEntity currentCategory, String categoryName, CategoryRelationEntity moveToCategory) {
        DefaultMutableTreeNode root = this.root;
        DefaultTreeModel model = (DefaultTreeModel) this.categoryTree.getModel();

        if (moveToCategory.getName().equals("Category Products")) {
            moveNodeToRoot(selectedNode, currentCategory, categoryName, root, model);
        } else {
            moveNodeToCategory(categoryRelationEntity, selectedNode, currentCategory, categoryName, root, model);
        }
    }

    private void moveNodeToRoot(DefaultMutableTreeNode selectedNode, CategoryEntity currentCategory, String categoryName, DefaultMutableTreeNode root, DefaultTreeModel model) {
        removeNodeFromParent(selectedNode, model);

        CategoryEntity newCategory = new CategoryEntity(currentCategory.getId(), categoryName);
        DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(newCategory);
        populateTreeNodeChild(currentCategory.getId(), newNode);

        root.add(newNode);

        reloadAndExpand(root, newNode, model);
    }

    private void moveNodeToCategory(CategoryRelationEntity categoryRelationEntity, DefaultMutableTreeNode selectedNode, CategoryEntity currentCategory, String categoryName, DefaultMutableTreeNode root, DefaultTreeModel model) {
        List<DefaultMutableTreeNode> allChildren = getAllChildren(root);

        for (DefaultMutableTreeNode node : allChildren) {
            CategoryEntity nodeToEntity = (CategoryEntity) node.getUserObject();
            if (nodeToEntity.getId().equals(categoryRelationEntity.getAncestorId())) {
                removeNodeFromParent(selectedNode, model);

                CategoryEntity newCategory = new CategoryEntity(currentCategory.getId(), categoryName);
                DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(newCategory);
                populateTreeNodeChild(currentCategory.getId(), newNode);
                node.add(newNode);

                reloadAndExpand(node, newNode, model);
            }
        }
    }

    private void removeNodeFromParent(DefaultMutableTreeNode node, DefaultTreeModel model) {
        DefaultMutableTreeNode parent = (DefaultMutableTreeNode) node.getParent();
        node.removeFromParent();
        model.reload(parent);
        this.categoryTree.expandPath(new TreePath(parent.getPath()));
    }

    private void reloadAndExpand(DefaultMutableTreeNode node, DefaultMutableTreeNode newNode, DefaultTreeModel model) {
        TreePath path = new TreePath(node.getPath());
        model.reload(node);
        this.categoryTree.expandPath(path);
        this.categoryTree.setSelectionPath(path.pathByAddingChild(newNode));
    }

    private void populateTreeNodeChild(Integer currentCategoryId, DefaultMutableTreeNode newNodeParent) {
        List<CategoryRelationEntity> categoryOfChildren = categoryRelationController.getAllCategoryRelationsByAncestorId(currentCategoryId);
        for (CategoryRelationEntity item : categoryOfChildren) {
            CategoryEntity newCategoryChild = new CategoryEntity(item.getDescendantId(), item.getName());
            DefaultMutableTreeNode newNodeChild = new DefaultMutableTreeNode(newCategoryChild);
            newNodeParent.add(newNodeChild);
            populateTreeNodeChild(item.getDescendantId(), newNodeChild);

        }
    }

    private List<DefaultMutableTreeNode> getAllChildren(DefaultMutableTreeNode node) {
        List<DefaultMutableTreeNode> childrenList = new ArrayList<>();
        Enumeration<?> children = node.children();
        while (children.hasMoreElements()) {
            DefaultMutableTreeNode childNode = (DefaultMutableTreeNode) children.nextElement();
            childrenList.add(childNode);
            childrenList.addAll(getAllChildren(childNode));
        }
        return childrenList;
    }
}
