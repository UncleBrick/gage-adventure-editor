package com.phantomcoder.adventureeditor.gui;

import com.phantomcoder.adventureeditor.config.AppConfig;
import com.phantomcoder.adventureeditor.constants.FrameConstants;
import com.phantomcoder.adventureeditor.controller.ActionManager;
import com.phantomcoder.adventureeditor.controller.RoomController;
import com.phantomcoder.adventureeditor.gui.panels.ObjectsPanel;
import com.phantomcoder.adventureeditor.gui.panels.RoomEditorPanel;
import com.phantomcoder.adventureeditor.util.UiHelper; // NEW IMPORT
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.EventQueue;

@SuppressWarnings("serial")
public class GameEditorFrame extends JFrame {

    private JLabel statusLabel;

    public GameEditorFrame() {
        initialize();
    }

    private void initialize() {
        AppConfig.loadConfig();
        setTitle(FrameConstants.MAIN_WINDOW_TITLE);
        setSize(FrameConstants.MAIN_FRAME_WIDTH, FrameConstants.MAIN_FRAME_HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setIconImage(FrameConstants.PROGRAM_ICON);

        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        RoomEditorPanel roomEditorPanel = new RoomEditorPanel(this);
        RoomController roomController = roomEditorPanel.getRoomController();
        ActionManager actionManager = roomController.getActionManager();

        EditorMenuBar menuBar = new EditorMenuBar(roomController, actionManager);
        setJMenuBar(menuBar);
        roomController.setPreviewMenuItem(menuBar.getPreviewPaneItem());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setTabPlacement(JTabbedPane.BOTTOM); // Keep placement logic here

        ObjectsPanel objectsPanel = new ObjectsPanel();

        tabbedPane.addTab("Rooms", roomEditorPanel);
        tabbedPane.addTab("Objects", objectsPanel);
        tabbedPane.addTab("NPCs", new JPanel());
        tabbedPane.addTab("Quests", new JPanel());
        tabbedPane.addTab("Exits", new JPanel());
        tabbedPane.addTab("Commands", new JPanel());

        roomController.setObjectsPanel(objectsPanel);

        // Call the new, centralized helper method to style the tabs
        UiHelper.styleTabbedPane(tabbedPane);

        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
        statusLabel = new JLabel(" Ready");
        statusBar.add(statusLabel, BorderLayout.CENTER);

        contentPane.add(tabbedPane, BorderLayout.CENTER);
        contentPane.add(statusBar, BorderLayout.SOUTH);
    }

    // The old setUniformTabWidths method is now removed from this file

    public void setStatus(String status) {
        statusLabel.setText(" " + status);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                GameEditorFrame frame = new GameEditorFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}