package com.phantomcoder.adventureeditor.gui;

import com.phantomcoder.adventureeditor.controller.ActionManager;
import com.phantomcoder.adventureeditor.controller.RoomController;
import com.phantomcoder.adventureeditor.util.UiHelper; // Import the helper

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

public class EditorMenuBar extends JMenuBar {

    private JCheckBoxMenuItem previewPaneItem;

    public EditorMenuBar(RoomController roomController, ActionManager actionManager) {
        // --- File Menu ---
        JMenu fileMenu = new JMenu("File");
        fileMenu.add(new JMenuItem(actionManager.newAction));
        fileMenu.add(new JMenuItem(actionManager.loadAction));
        fileMenu.add(new JMenuItem(actionManager.saveAction));
        fileMenu.add(new JMenuItem(actionManager.saveAsAction));
        fileMenu.add(new JSeparator());
        fileMenu.add(new JMenuItem(actionManager.closeAction));
        fileMenu.add(new JSeparator());
        fileMenu.add(new JMenuItem(actionManager.exitAction));
        add(fileMenu);

        // --- Edit Menu ---
        JMenu editMenu = new JMenu("Edit");
        add(editMenu); // Placeholder

        // --- View Menu ---
        JMenu viewMenu = new JMenu("View");
        this.previewPaneItem = new JCheckBoxMenuItem(actionManager.previewAction);
        viewMenu.add(previewPaneItem);
        add(viewMenu);

        // --- Tools Menu ---
        JMenu toolsMenu = new JMenu("Tools");
        toolsMenu.add(new JMenuItem(actionManager.configureAction));
        add(toolsMenu);

        // --- Help Menu ---
        JMenu helpMenu = new JMenu("Help");
        helpMenu.add(new JMenuItem(actionManager.helpAction));
        helpMenu.add(new JSeparator());
        helpMenu.add(new JMenuItem(actionManager.aboutAction));
        add(helpMenu);
    }

    public JCheckBoxMenuItem getPreviewPaneItem() {
        return previewPaneItem;
    }
}