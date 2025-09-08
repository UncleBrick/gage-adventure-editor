package com.phantomcoder.adventureeditor.controller;

import com.phantomcoder.adventureeditor.gui.MainApplicationFrame;
import com.phantomcoder.adventureeditor.util.UiHelper;
import javax.swing.AbstractAction;
import javax.swing.JOptionPane;
import java.awt.event.ActionEvent;

public class ActionManager {

    private final RoomController controller;
    private final MainApplicationFrame mainFrame;
    public final AbstractAction newAction;
    public final AbstractAction loadAction;
    public final AbstractAction saveAction;
    public final AbstractAction saveAsAction;
    public final AbstractAction closeAction;
    public final AbstractAction exitAction;
    public final AbstractAction previewAction;
    public final AbstractAction helpAction;
    public final AbstractAction aboutAction;
    public final AbstractAction configureAction;

    public ActionManager(RoomController controller, MainApplicationFrame mainFrame) {
        this.controller = controller;
        this.mainFrame = mainFrame;

        newAction = new AbstractAction("New Room", UiHelper.loadMenuIcon("menu/new_lg.png")) {
            @Override public void actionPerformed(ActionEvent e) { controller.handleAddRoomAction(); }
        };

        loadAction = new AbstractAction("Load Room...", UiHelper.loadMenuIcon("menu/load_lg.png")) {
            @Override public void actionPerformed(ActionEvent e) { controller.handleLoadRoomAction(); }
        };

        saveAction = new AbstractAction("Save Room", UiHelper.loadMenuIcon("menu/save_lg.png")) {
            @Override public void actionPerformed(ActionEvent e) { controller.handleSaveCurrentRoomAction(); }
        };

        saveAsAction = new AbstractAction("Save Room As...", UiHelper.loadMenuIcon("menu/save_as_lg.png")) {
            @Override public void actionPerformed(ActionEvent e) { /* Not yet implemented */ }
        };

        closeAction = new AbstractAction("Close Room", UiHelper.loadMenuIcon("menu/close_lg.png")) {
            @Override public void actionPerformed(ActionEvent e) { /* Not yet implemented */ }
        };

        exitAction = new AbstractAction("Exit", UiHelper.loadMenuIcon("menu/exit_lg.png")) {
            @Override public void actionPerformed(ActionEvent e) { System.exit(0); }
        };

        previewAction = new AbstractAction("Preview Pane", UiHelper.loadMenuIcon("menu/preview_lg.png")) {
            @Override public void actionPerformed(ActionEvent e) {
                mainFrame.togglePreviewPane();
            }
        };

        helpAction = new AbstractAction("Help", UiHelper.loadMenuIcon("menu/help_lg.png")) {
            @Override public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(mainFrame,
                        "Help documentation is not yet available.",
                        "Help",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        };

        aboutAction = new AbstractAction("About", UiHelper.loadMenuIcon("menu/about_lg.png")) {
            @Override public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(mainFrame,
                        "GAGE - Generic Adventure Game Editor\nVersion 1.0 (MDI Refactor)",
                        "About GAGE",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        };

        configureAction = new AbstractAction("Game Configuration...", UiHelper.loadMenuIcon("menu/configure_lg.png")) {
            @Override public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(mainFrame,
                        "Game Configuration is not yet implemented.",
                        "Coming Soon",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        };
    }
}