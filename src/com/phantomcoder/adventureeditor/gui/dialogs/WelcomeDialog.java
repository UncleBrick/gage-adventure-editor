package com.phantomcoder.adventureeditor.gui.dialogs;

import com.phantomcoder.adventureeditor.constants.FontConstants;
import com.phantomcoder.adventureeditor.constants.FrameConstants;
import com.phantomcoder.adventureeditor.constants.LayoutConstants;

import javax.swing.*;
import java.awt.*;

/**
 * A simple welcome dialog shown on application startup.
 */
public class WelcomeDialog extends JDialog {

    private final JCheckBox dontShowAgainCheckBox;

    public WelcomeDialog(Frame owner) {
        super(owner, "Welcome to GAGE", true);

        // --- Main Panel ---
        JPanel mainPanel = new JPanel(new BorderLayout(
                LayoutConstants.WELCOME_DIALOG_MAIN_HGAP,
                LayoutConstants.WELCOME_DIALOG_MAIN_VGAP
        ));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(
                LayoutConstants.WELCOME_DIALOG_PADDING.top,
                LayoutConstants.WELCOME_DIALOG_PADDING.left,
                LayoutConstants.WELCOME_DIALOG_PADDING.bottom,
                LayoutConstants.WELCOME_DIALOG_PADDING.right
        ));

        // --- Scaled Icon ---
        Image scaledIcon = FrameConstants.PROGRAM_ICON.getScaledInstance(
                FrameConstants.WELCOME_DIALOG_ICON_SIZE,
                FrameConstants.WELCOME_DIALOG_ICON_SIZE,
                Image.SCALE_SMOOTH
        );
        JLabel iconLabel = new JLabel(new ImageIcon(scaledIcon));
        mainPanel.add(iconLabel, BorderLayout.WEST);

        // --- Center Panel (Message) ---
        JPanel centerPanel = new JPanel(new BorderLayout(0, LayoutConstants.WELCOME_DIALOG_CENTER_VGAP));
        JLabel titleLabel = new JLabel("Welcome to the Generic Adventure Game Editor (GAGE)");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, FontConstants.WELCOME_DIALOG_TITLE_FONT_SIZE));

        // Corrected text with HTML for word wrapping
        JLabel instructionsLabel = new JLabel("<html><font size='+1'>To get started, simply fill in the Project Metadata fields for your new room, or load an<br>existing room using the toolbar.</font></html>");

        centerPanel.add(titleLabel, BorderLayout.NORTH);
        centerPanel.add(instructionsLabel, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // --- Bottom Panel (Checkbox and Button) ---
        JPanel bottomPanel = new JPanel(new BorderLayout());
        dontShowAgainCheckBox = new JCheckBox("Don't show this again");
        bottomPanel.add(dontShowAgainCheckBox, BorderLayout.WEST);

        JButton okButton = new JButton("OK");
        okButton.addActionListener(e -> setVisible(false));
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(okButton);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        pack();
        setResizable(false);
        setLocationRelativeTo(owner);
    }

    /**
     * Checks if the "Don't show this again" checkbox was selected by the user.
     * @return true if the checkbox was selected, false otherwise.
     */
    public boolean isDontShowAgainSelected() {
        return dontShowAgainCheckBox.isSelected();
    }
}