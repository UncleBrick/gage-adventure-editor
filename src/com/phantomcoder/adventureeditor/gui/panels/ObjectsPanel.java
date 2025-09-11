package com.phantomcoder.adventureeditor.gui.panels;

import com.phantomcoder.adventureeditor.constants.LayoutConstants;
import com.phantomcoder.adventureeditor.controller.RoomController;
import com.phantomcoder.adventureeditor.model.ObjectData;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.List;

public class ObjectsPanel extends JPanel {

    private final RoomController roomController;
    private JSplitPane splitPane;
    private JTree objectTree;
    private DefaultTreeModel treeModel;
    private JTable objectDataTable; // RENAMED for clarity
    private DefaultTableModel tableModel;

    private JButton moveUpButton;
    private JButton moveDownButton;
    private JButton addButton;
    private JButton editButton;
    private JButton removeButton;
    private JButton applyButton;

    private List<ObjectData> localObjects;

    public ObjectsPanel(RoomController roomController) {
        super(new BorderLayout());
        this.roomController = roomController;
        this.localObjects = new ArrayList<>();

        initUI();
        addListeners();
    }

    private void initUI() {
        JPanel topPanel = createTopPanel();
        JPanel bottomPanel = createBottomPanel();

        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, topPanel, bottomPanel);
        splitPane.setResizeWeight(LayoutConstants.ROOM_EDITOR_DIVIDER_LOCATION);

        add(splitPane, BorderLayout.CENTER);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout(LayoutConstants.PANEL_HGAP, LayoutConstants.PANEL_VGAP));
        panel.setBorder(BorderFactory.createTitledBorder("Object Hierarchy"));

        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Room Objects");
        treeModel = new DefaultTreeModel(root);
        objectTree = new JTree(treeModel);
        objectTree.setRootVisible(true);

        JScrollPane treeScrollPane = new JScrollPane(objectTree);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        moveUpButton = new JButton("Move Up");
        moveDownButton = new JButton("Move Down");
        buttonPanel.add(moveUpButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, LayoutConstants.PANEL_VGAP)));
        buttonPanel.add(moveDownButton);

        panel.add(treeScrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.EAST);

        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new BorderLayout(LayoutConstants.PANEL_HGAP, LayoutConstants.PANEL_VGAP));
        panel.setBorder(BorderFactory.createTitledBorder("Object Details")); // Renamed title

        // NEW: Updated table columns for a data grid view
        String[] columnNames = {"ID", "GUID", "Name", "Flags"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        objectDataTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(objectDataTable);

        // NEW: Simplified button panel layout to fix alignment
        JPanel southButtonContainer = new JPanel(new BorderLayout());
        JPanel leftButtons = new JPanel(new FlowLayout(FlowLayout.LEFT));
        addButton = new JButton("Add...");
        editButton = new JButton("Edit...");
        removeButton = new JButton("Remove");
        leftButtons.add(addButton);
        leftButtons.add(editButton);
        leftButtons.add(removeButton);

        applyButton = new JButton("Apply Changes");
        // Add the apply button directly to the container, it will align right by default in this layout
        southButtonContainer.add(leftButtons, BorderLayout.WEST);
        southButtonContainer.add(applyButton, BorderLayout.EAST);

        panel.add(tableScrollPane, BorderLayout.CENTER);
        panel.add(southButtonContainer, BorderLayout.SOUTH);

        return panel;
    }

    private void addListeners() {
        objectTree.getSelectionModel().addTreeSelectionListener(e -> {
            DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) objectTree.getLastSelectedPathComponent();
            if (selectedNode != null && selectedNode.getUserObject() instanceof ObjectData) {
                updateObjectDataTable((ObjectData) selectedNode.getUserObject());
            } else {
                tableModel.setRowCount(0);
            }
        });

        // Placeholder listeners
        addButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Add Object dialog not yet implemented.", "Info", JOptionPane.INFORMATION_MESSAGE));
        editButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Edit Object dialog not yet implemented.", "Info", JOptionPane.INFORMATION_MESSAGE));
        removeButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Remove Object logic not yet implemented.", "Info", JOptionPane.INFORMATION_MESSAGE));
        applyButton.addActionListener(e -> JOptionPane.showMessageDialog(this, "Apply Changes logic not yet implemented.", "Info", JOptionPane.INFORMATION_MESSAGE));
    }

    public void populateObjects(List<ObjectData> objects) {
        this.localObjects.clear();
        // ... (rest of the method is the same)
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) treeModel.getRoot();
        root.removeAllChildren();
        if (objects != null) {
            for (ObjectData obj : objects) {
                root.add(new DefaultMutableTreeNode(obj));
            }
        }
        treeModel.reload();
        for (int i = 0; i < objectTree.getRowCount(); i++) {
            objectTree.expandRow(i);
        }
    }

    private void updateObjectDataTable(ObjectData selectedObject) {
        tableModel.setRowCount(0);

        if (selectedObject == null) return;

        // NEW: Populate the data grid with the object's main properties
        // This will require adding getters for id and guid to your ObjectData model
        /*
        String id = selectedObject.getId();
        String guid = selectedObject.getGuid();
        String name = selectedObject.getName();
        String flags = String.join(", ", selectedObject.getFlags());

        tableModel.addRow(new Object[]{id, guid, name, flags});
        */
    }

    // --- Getters for Controller Access ---
    public JButton getMoveUpButton() { return moveUpButton; }
    public JButton getMoveDownButton() { return moveDownButton; }
    public JButton getAddButton() { return addButton; }
    public JButton getEditButton() { return editButton; }
    public JButton getRemoveButton() { return removeButton; }
    public JButton getApplyButton() { return applyButton; }
    public JTree getObjectTree() { return objectTree; }
}