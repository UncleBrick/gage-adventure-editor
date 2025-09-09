package com.phantomcoder.adventureeditor.gui;

import com.phantomcoder.adventureeditor.config.AppConfig;
import com.phantomcoder.adventureeditor.constants.FontConstants;
import com.phantomcoder.adventureeditor.constants.FrameConstants;
import com.phantomcoder.adventureeditor.constants.LayoutConstants;
import com.phantomcoder.adventureeditor.controller.ActionManager;
import com.phantomcoder.adventureeditor.controller.RoomController;
import com.phantomcoder.adventureeditor.gui.dialogs.WelcomeDialog;
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
        // The welcome dialog logic has been moved out of the constructor.
    }

    private void initialize() {
        setTitle(FrameConstants.MAIN_WINDOW_TITLE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(FrameConstants.PROGRAM_ICON);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLayout(new BorderLayout());

        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBorder(new BevelBorder(BevelBorder.LOWERED));
        statusLabel = new JLabel(" Ready");
        Font statusFont = new Font(FontConstants.DEFAULT_FONT_NAME, FontConstants.PLAIN_STYLE, FontConstants.STATUS_BAR_FONT_SIZE);
        statusLabel.setFont(statusFont);
        statusBar.add(statusLabel, BorderLayout.CENTER);
        add(statusBar, BorderLayout.SOUTH);

        editorPanel = new EditorPanel(this);
        previewPanel = new PreviewPanel();

        RoomController roomController = editorPanel.getRoomController();
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

        Font toolbarFont = new Font(FontConstants.DEFAULT_FONT_NAME, FontConstants.PLAIN_STYLE, FontConstants.TOOLBAR_FONT_SIZE);
        for (Component c : toolBar.getComponents()) {
            if (c instanceof JButton) {
                ((JButton) c).setFont(toolbarFont);
                ((JButton) c).setMargin(LayoutConstants.TOOLBAR_BUTTON_MARGIN);
            }
        }
        add(toolBar, BorderLayout.NORTH);

        mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, editorPanel, previewPanel);
        mainSplitPane.setResizeWeight(0.75);
        mainSplitPane.setOneTouchExpandable(true);
        mainSplitPane.setDividerSize(8);
        add(mainSplitPane, BorderLayout.CENTER);

        // The logic to show the welcome dialog is now here.
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                hidePreviewPane();
                showWelcomeDialogIfNeeded();
                removeWindowListener(this); // This listener only needs to run once.
            }
        });
    }

    /**
     * Checks the user's preference and shows the WelcomeDialog if needed.
     */
    private void showWelcomeDialogIfNeeded() {
        if (AppConfig.shouldShowWelcomeDialog()) {
            WelcomeDialog welcomeDialog = new WelcomeDialog(this);
            welcomeDialog.setVisible(true);
            if (welcomeDialog.isDontShowAgainSelected()) {
                AppConfig.setShouldShowWelcomeDialog(false);
                AppConfig.saveConfig();
            }
        }
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