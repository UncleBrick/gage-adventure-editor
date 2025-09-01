package com.phantomcoder.adventureeditor.gui.panels;

import com.phantomcoder.adventureeditor.constants.FontConstants;
import com.phantomcoder.adventureeditor.constants.LayoutConstants;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;

@SuppressWarnings("serial")
public class ObjectsPanel extends JPanel {

    private JTree objectTree;
    private JButton addButton, editButton, removeButton, moveUpButton, moveDownButton;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode rootNode;

    public ObjectsPanel() {
        setLayout(new BorderLayout(LayoutConstants.PANEL_HGAP, LayoutConstants.PANEL_VGAP));
        setBorder(LayoutConstants.PANEL_PADDING);

        rootNode = new DefaultMutableTreeNode("No Room Loaded");
        treeModel = new DefaultTreeModel(rootNode);

        objectTree = new JTree(treeModel);
        objectTree.setFont(new Font(FontConstants.DEFAULT_FONT_NAME, FontConstants.PLAIN_STYLE, FontConstants.TREE_FONT_SIZE));
        objectTree.setRootVisible(true);

        JScrollPane treeScrollPane = new JScrollPane(objectTree);
        treeScrollPane.setBorder(BorderFactory.createEtchedBorder());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(
                LayoutConstants.OBJECTS_BUTTON_GRID_ROWS,
                LayoutConstants.OBJECTS_BUTTON_GRID_COLS,
                LayoutConstants.PANEL_HGAP,
                LayoutConstants.PANEL_VGAP
        ));

        addButton = new JButton("Add...");
        editButton = new JButton("Edit...");
        removeButton = new JButton("Remove");
        moveUpButton = new JButton("Move Up");
        moveDownButton = new JButton("Move Down");

        editButton.setEnabled(false);
        removeButton.setEnabled(false);
        moveUpButton.setEnabled(false);
        moveDownButton.setEnabled(false);

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(moveUpButton);
        buttonPanel.add(moveDownButton);

        JPanel eastPanel = new JPanel(new BorderLayout());
        eastPanel.add(buttonPanel, BorderLayout.NORTH);

        add(treeScrollPane, BorderLayout.CENTER);
        add(eastPanel, BorderLayout.EAST);

        objectTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) objectTree.getLastSelectedPathComponent();
                boolean isNodeSelected = selectedNode != null && !selectedNode.isRoot();
                editButton.setEnabled(isNodeSelected);
                removeButton.setEnabled(isNodeSelected);
                boolean canMoveUp = false;
                boolean canMoveDown = false;
                if (isNodeSelected) {
                    DefaultMutableTreeNode parent = (DefaultMutableTreeNode) selectedNode.getParent();
                    int index = parent.getIndex(selectedNode);
                    canMoveUp = (index > 0);
                    canMoveDown = (index < parent.getChildCount() - 1);
                }
                moveUpButton.setEnabled(canMoveUp);
                moveDownButton.setEnabled(canMoveDown);
            }
        });
    }

    public JButton getAddButton() { return addButton; }
    public JButton getEditButton() { return editButton; }

    public void updateTitle(String location, String area, String roomName) {
        String newTitle = String.format("%s - %s - %s", location, area, roomName);
        rootNode.setUserObject(newTitle);
        treeModel.reload(rootNode);
    }

    public void resetTitle() {
        rootNode.setUserObject("No Room Loaded");
        treeModel.reload(rootNode);
    }
}