package com.multibahana.inventoryapp.context;

import javax.swing.tree.TreePath;

public class LeftPanelContext {
    private static LeftPanelContext instance;
    private TreePath treePath;

    private LeftPanelContext() {}

    public static synchronized LeftPanelContext getInstance() {
        if (instance == null) {
            instance = new LeftPanelContext();
        }
        return instance;
    }

    public TreePath getTreePath() {
        return treePath;
    }

    public void setTreePath(TreePath treePath) {
        this.treePath = treePath;
    }

}
