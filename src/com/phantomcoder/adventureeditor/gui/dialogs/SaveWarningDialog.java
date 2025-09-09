package com.phantomcoder.adventureeditor.gui.dialogs;

import com.phantomcoder.adventureeditor.constants.DialogConstants;
import com.phantomcoder.adventureeditor.constants.LayoutConstants;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * A custom dialog that warns the user when they try to save a room with empty
 * optional fields. It provides the option to continue or cancel the save.
 */
public class SaveWarningDialog extends JDialog {

    private final JCheckBox dontShowAgainCheckBox;
    private int userChoice;

    public SaveWarningDialog(Frame owner, List<String> emptyFields) {
        super(owner, "Save Warning", true);

        // --- Main Panel ---
        JPanel mainPanel = new JPanel(new BorderLayout(LayoutConstants.PANEL_HGAP, LayoutConstants.PANEL_VGAP));
        mainPanel.setBorder(LayoutConstants.PANEL_PADDING);

        // --- Top Message ---
        JLabel messageLabel = new JLabel("<html>Are you sure you want to save? The following optional fields are empty:</html>");
        mainPanel.add(messageLabel, BorderLayout.NORTH);

        // --- Center List of Fields ---
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String field : emptyFields) {
            listModel.addElement(field);
        }
        JList<String> fieldList = new JList<>(listModel);
        JScrollPane scrollPane = new JScrollPane(fieldList);
        scrollPane.setPreferredSize(new Dimension(
                DialogConstants.SAVE_WARNING_LIST_WIDTH,
                DialogConstants.SAVE_WARNING_LIST_HEIGHT
        ));
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // --- Bottom Panel (Checkbox and Buttons) ---
        JPanel bottomPanel = new JPanel(new BorderLayout());
        dontShowAgainCheckBox = new JCheckBox("Don't show this again");
        bottomPanel.add(dontShowAgainCheckBox, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton continueButton = new JButton("Continue Save");
        JButton cancelButton = new JButton("Cancel");

        continueButton.addActionListener(e -> {
            userChoice = DialogConstants.CONTINUE_OPTION;
            setVisible(false);
        });
        cancelButton.addActionListener(e -> {
            userChoice = DialogConstants.CANCEL_OPTION;
            setVisible(false);
        });

        buttonPanel.add(continueButton);
        buttonPanel.add(cancelButton);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        pack();
        setLocationRelativeTo(owner);
    }

    /**
     * Shows the modal dialog and waits for the user's choice.
     * @return An integer representing the user's choice (CONTINUE_OPTION or CANCEL_OPTION).
     */
    public int showDialog() {
        // Default to cancel if the user closes the window with the 'X' button
        this.userChoice = DialogConstants.CANCEL_OPTION;
        setVisible(true);
        return userChoice;
    }

    /**
     * Checks if the "Don't show this again" checkbox was selected.
     * @return true if the checkbox was selected, false otherwise.
     */
    public boolean isDontShowAgainSelected() {
        return dontShowAgainCheckBox.isSelected();
    }
}