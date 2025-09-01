package com.phantomcoder.adventureeditor.gui;

import com.phantomcoder.adventureeditor.controller.ActionManager;
import com.phantomcoder.adventureeditor.controller.RoomController;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class EditorMenuBar extends JMenuBar {

    private JCheckBoxMenuItem previewPaneItem;

    public EditorMenuBar(RoomController roomController, ActionManager actionManager) {
        // --- File Menu ---
        JMenu fileMenu = new JMenu("File");
        JMenuItem newItem = new JMenuItem(actionManager.newAction);
        newItem.setRolloverIcon(actionManager.loadMenuIcon("/menu/hot/new_16.png"));
        JMenuItem loadItem = new JMenuItem(actionManager.loadAction);
        loadItem.setRolloverIcon(actionManager.loadMenuIcon("/menu/hot/load_16.png"));
        JMenuItem closeItem = new JMenuItem(actionManager.closeAction);
        closeItem.setRolloverIcon(actionManager.loadMenuIcon("/menu/hot/close_16.png"));
        closeItem.setDisabledIcon(actionManager.loadMenuIcon("/menu/disabled/close_16.png"));
        JMenuItem saveItem = new JMenuItem(actionManager.saveAction);
        saveItem.setRolloverIcon(actionManager.loadMenuIcon("/menu/hot/save_16.png"));
        saveItem.setDisabledIcon(actionManager.loadMenuIcon("/menu/disabled/save_16.png"));
        JMenuItem saveAsItem = new JMenuItem(actionManager.saveAsAction);
        saveAsItem.setRolloverIcon(actionManager.loadMenuIcon("/menu/hot/save_as_16.png"));
        saveAsItem.setDisabledIcon(actionManager.loadMenuIcon("/menu/disabled/save_as_16.png"));
        JMenuItem exitItem = new JMenuItem(actionManager.exitAction);
        exitItem.setRolloverIcon(actionManager.loadMenuIcon("/menu/hot/exit_16.png"));
        fileMenu.add(newItem);
        fileMenu.add(loadItem);
        fileMenu.add(closeItem);
        fileMenu.addSeparator();
        fileMenu.add(saveItem);
        fileMenu.add(saveAsItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        // --- Edit Menu ---
        JMenu editMenu = new JMenu("Edit");
        JMenuItem configureItem = new JMenuItem(actionManager.configureAction);
        configureItem.setRolloverIcon(actionManager.loadMenuIcon("/menu/hot/configure_16.png"));
        editMenu.add(configureItem);

        // --- View Menu ---
        JMenu viewMenu = new JMenu("View");
        previewPaneItem = new JCheckBoxMenuItem(actionManager.previewAction);
        previewPaneItem.setRolloverIcon(actionManager.loadMenuIcon("/menu/hot/preview_16.png"));
        viewMenu.add(previewPaneItem);

        // --- Help Menu ---
        JMenu helpMenu = new JMenu("Help");
        JMenuItem helpItem = new JMenuItem(actionManager.helpAction);
        helpItem.setRolloverIcon(actionManager.loadMenuIcon("/menu/hot/help_16.png"));
        JMenuItem aboutItem = new JMenuItem(actionManager.aboutAction);
        aboutItem.setRolloverIcon(actionManager.loadMenuIcon("/menu/hot/about_16.png"));
        helpMenu.add(helpItem);
        helpMenu.add(aboutItem);

        // --- Add menus to the menu bar in order ---
        add(fileMenu);
        add(editMenu);
        add(viewMenu);
        add(helpMenu);
    }

    public JCheckBoxMenuItem getPreviewPaneItem() {
        return previewPaneItem;
    }
}