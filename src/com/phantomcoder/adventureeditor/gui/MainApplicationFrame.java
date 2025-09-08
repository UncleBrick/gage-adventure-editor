package com.phantomcoder.adventureeditor.gui;

import com.phantomcoder.adventureeditor.config.AppConfig;
import com.phantomcoder.adventureeditor.constants.FontConstants;
import com.phantomcoder.adventureeditor.constants.FrameConstants;
import com.phantomcoder.adventureeditor.constants.LayoutConstants;
import com.phantomcoder.adventureeditor.controller.ActionManager;
import com.phantomcoder.adventureeditor.controller.RoomController;
import com.phantomcoder.adventureeditor.gui.panels.EditorPanel;
import com.phantomcoder.adventureeditor.gui.panels.PreviewPanel;
import com.phantomcoder.adventureeditor.model.RoomData;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.JToolBar;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;

public class MainApplicationFrame extends JFrame {

    private JLabel statusLabel;
    private JSplitPane mainSplitPane;
    private EditorPanel editorPanel;
    private PreviewPanel previewPanel;

    public MainApplicationFrame() {
        AppConfig.loadConfig();
        initialize();
    }

    private void initialize() {
        setTitle(FrameConstants.MAIN_WINDOW_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(FrameConstants.PROGRAM_ICON);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        // --- CORRECTED ORDER ---
        // 1. Create the status bar and label first, so they exist before any controller tries to use them.
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
        statusLabel = new JLabel(" Ready");
        Font statusFont = new Font(FontConstants.DEFAULT_FONT_NAME, FontConstants.PLAIN_STYLE, FontConstants.STATUS_BAR_FONT_SIZE);
        statusLabel.setFont(statusFont);
        statusBar.add(statusLabel, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);

        // 2. Now, create the editor panel, which initializes the controllers.
        editorPanel = new EditorPanel(this);
        previewPanel = new PreviewPanel();

        // 3. Get the controllers and action manager that were just created.
        RoomController roomController = editorPanel.getRoomController();
        ActionManager actionManager = roomController.getActionManager();

        // 4. Create the menu bar and toolbar, which depend on the action manager.
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

        Font toolbarFont = new Font(FontConstants.DEFAULT_FONT_NAME, FontConstants.PLAIN_STYLE, FontConstants.TOOLBAR_FONT_SIZE);
        for (Component c : toolBar.getComponents()) {
            if (c instanceof JButton) {
                ((JButton) c).setFont(toolbarFont);
                ((JButton) c).setMargin(LayoutConstants.TOOLBAR_BUTTON_MARGIN);
            }
        }
        add(toolBar, BorderLayout.NORTH);

        // 5. Create the main split pane and add it to the frame.
        mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, editorPanel, previewPanel);
        mainSplitPane.setResizeWeight(0.75);
        mainSplitPane.setOneTouchExpandable(true);
        mainSplitPane.setDividerSize(8);
        add(mainSplitPane, BorderLayout.CENTER);

        // 6. Add window listener to hide the preview pane on first launch.
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                hidePreviewPane();
                removeWindowListener(this);
            }
        });
    }

    public void updatePreview(RoomData room, String fileName) {
        if (previewPanel != null) {
            previewPanel.getRoomPreviewPanel().updateRoomPreview(room, fileName);
        }
    }

    public void togglePreviewPane() {
        boolean isHidden = mainSplitPane.getDividerLocation() >= (mainSplitPane.getWidth() - mainSplitPane.getDividerSize() - 10);
        if (isHidden) {
            showPreviewPane();
        } else {
            hidePreviewPane();
        }
    }

    private void showPreviewPane() {
        mainSplitPane.setDividerLocation(0.75);
    }

    private void hidePreviewPane() {
        mainSplitPane.setDividerLocation(1.0);
    }

    public void setStatus(String status) {
        // This call is now safe because statusLabel is guaranteed to exist.
        statusLabel.setText(" " + status);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                MainApplicationFrame frame = new MainApplicationFrame();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}