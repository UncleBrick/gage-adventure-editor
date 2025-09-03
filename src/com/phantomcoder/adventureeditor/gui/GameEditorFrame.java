package com.phantomcoder.adventureeditor.gui;

import com.phantomcoder.adventureeditor.config.AppConfig;
import com.phantomcoder.adventureeditor.constants.FrameConstants;
import com.phantomcoder.adventureeditor.controller.ActionManager;
import com.phantomcoder.adventureeditor.controller.RoomController;
import com.phantomcoder.adventureeditor.gui.panels.ObjectsPanel;
import com.phantomcoder.adventureeditor.gui.panels.RoomEditorPanel;
import com.phantomcoder.adventureeditor.util.UiHelper;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import java.awt.BorderLayout;
import java.awt.Component; // NEW IMPORT
import java.awt.Container;
import java.awt.EventQueue;
import java.awt.Font;      // NEW IMPORT
import java.awt.Insets;   // NEW IMPORT

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

        JToolBar toolBar = new JToolBar();
        toolBar.setFloatable(false);
        toolBar.add(new JButton(actionManager.newAction));
        toolBar.add(new JButton(actionManager.loadAction));
        toolBar.add(new JButton(actionManager.saveAction));
        toolBar.addSeparator();
        toolBar.add(new JButton(actionManager.previewAction));

        // --- NEW: Enlarge Toolbar Buttons ---
        Font toolbarFont = new Font("SansSerif", Font.PLAIN, 12);
        Insets buttonMargin = new Insets(2, 5, 2, 5);
        for (Component c : toolBar.getComponents()) {
            if (c instanceof JButton) {
                JButton button = (JButton) c;
                button.setFont(toolbarFont);
                button.setMargin(buttonMargin);
            }
        }
        // --- END NEW ---

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setTabPlacement(JTabbedPane.BOTTOM);

        ObjectsPanel objectsPanel = new ObjectsPanel();

        tabbedPane.addTab("Rooms", roomEditorPanel);
        tabbedPane.addTab("Objects", objectsPanel);
        tabbedPane.addTab("NPCs", new JPanel());
        tabbedPane.addTab("Quests", new JPanel());
        tabbedPane.addTab("Exits", new JPanel());
        tabbedPane.addTab("Commands", new JPanel());
        tabbedPane.addTab("Dialogue", new JPanel());

        roomController.setObjectsPanel(objectsPanel);
        UiHelper.styleTabbedPane(tabbedPane);

        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
        statusLabel = new JLabel(" Ready");
        statusBar.add(statusLabel, BorderLayout.CENTER);

        contentPane.add(toolBar, BorderLayout.NORTH);
        contentPane.add(tabbedPane, BorderLayout.CENTER);
        contentPane.add(statusBar, BorderLayout.SOUTH);
    }

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