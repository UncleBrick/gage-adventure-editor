package com.phantomcoder.adventureeditor.controller;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.util.Objects;

public class ActionManager {

    private final RoomController controller;

    public Action newAction, loadAction, saveAction, saveAsAction, closeAction, exitAction;
    public Action previewAction;
    public Action helpAction, aboutAction;
    public Action configureAction;

    public ActionManager(RoomController controller) {
        this.controller = controller;
        createActions();
    }

    private void createActions() {
        newAction = new AbstractAction("New", loadMenuIcon("/menu/normal/new_16.png")) {
            @Override public void actionPerformed(ActionEvent e) { controller.handleAddRoomAction(); }
        };
        loadAction = new AbstractAction("Load Room...", loadMenuIcon("/menu/normal/load_16.png")) {
            @Override public void actionPerformed(ActionEvent e) { controller.handleLoadRoomAction(); }
        };
        saveAction = new AbstractAction("Save", loadMenuIcon("/menu/normal/save_16.png")) {
            @Override public void actionPerformed(ActionEvent e) { controller.handleSaveCurrentRoomAction(); }
        };
        saveAsAction = new AbstractAction("Save As...", loadMenuIcon("/menu/normal/save_as_16.png")) {
            @Override public void actionPerformed(ActionEvent e) { /* controller.handleSaveAsAction(); */ }
        };
        closeAction = new AbstractAction("Close", loadMenuIcon("/menu/normal/close_16.png")) {
            @Override public void actionPerformed(ActionEvent e) { /* controller.handleCloseAction(); */ }
        };
        exitAction = new AbstractAction("Exit", loadMenuIcon("/menu/normal/exit_16.png")) {
            @Override public void actionPerformed(ActionEvent e) { System.exit(0); }
        };
        previewAction = new AbstractAction("Preview Pane", loadMenuIcon("/menu/normal/preview_16.png")) {
            @Override public void actionPerformed(ActionEvent e) { controller.togglePreviewWindow(); }
        };
        helpAction = new AbstractAction("Help", loadMenuIcon("/menu/normal/help_16.png")) {
            @Override public void actionPerformed(ActionEvent e) { controller.handleHelpAction(); }
        };
        aboutAction = new AbstractAction("About", loadMenuIcon("/menu/normal/about_16.png")) {
            @Override public void actionPerformed(ActionEvent e) { controller.handleAboutAction(); }
        };
        configureAction = new AbstractAction("Game Configuration...", loadMenuIcon("/menu/normal/configure_16.png")) {
            @Override public void actionPerformed(ActionEvent e) { controller.handleConfigureAction(); }
        };
    }

    public ImageIcon loadMenuIcon(String path) {
        try {
            return new ImageIcon(ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/icons" + path))));
        } catch (Exception e) {
            System.err.println("Could not load menu icon: " + path);
            return null;
        }
    }
}