package com.multibahana.inventoryapp.views.components.templates;

import com.multibahana.inventoryapp.views.components.molecules.Navbar;
import com.multibahana.inventoryapp.views.components.organisms.Product;
import com.multibahana.inventoryapp.views.components.organisms.Receipt;
import com.multibahana.inventoryapp.views.components.organisms.Sales;
import com.multibahana.inventoryapp.views.components.organisms.Stock;
//import com.multibahana.inventoryapp.views.components.organisms.Stock;
import com.multibahana.inventoryapp.views.components.organisms.Vendor;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JSplitPane;

public class CenterPanel extends JPanel implements Navbar.NavbarListener {

    private JSplitPane centerSplitPane;
    private JPanel contentPanel;

    public CenterPanel() {
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        Navbar navbar = new Navbar(this);
        contentPanel = new Receipt();
        centerSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, navbar, contentPanel);
        add(centerSplitPane, BorderLayout.CENTER);
    }

    @Override
    public void onNavbarItemSelected(String navbarItem) {
        centerSplitPane.remove(contentPanel);

        if (navbarItem.equals("Product")) {
            contentPanel = new Product();
        } else if (navbarItem.equals("Receipt")) {
            contentPanel = new Receipt();
        } else if (navbarItem.equals("Vendor")) {
            contentPanel = new Vendor();
        }else if (navbarItem.equals("Stock")) {
            contentPanel = new Stock();
        }else if (navbarItem.equals("Sales")) {
            contentPanel = new Sales();
        }

        centerSplitPane.setRightComponent(contentPanel);
        revalidate();
        repaint();
    }

}
