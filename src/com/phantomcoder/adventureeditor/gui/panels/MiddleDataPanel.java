package com.phantomcoder.adventureeditor.gui.panels;

import com.phantomcoder.adventureeditor.constants.LayoutConstants;
import com.phantomcoder.adventureeditor.util.ComponentListenerHelper;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentListener;

public class MiddleDataPanel extends JPanel {

    private JTextField roomNameField;
    private JTextField shortDescriptionField;
    private JButton manageAmbianceButton;
    private JButton manageTimeStatesButton;
    private RoomFlagsPanel roomFlagsPanel;

    public MiddleDataPanel() {
        setLayout(new BorderLayout(LayoutConstants.PANEL_HGAP, LayoutConstants.PANEL_VGAP));

        JPanel detailsPanel = new JPanel(new GridLayout(
                LayoutConstants.BUTTON_PANEL_ROWS,
                LayoutConstants.BUTTON_PANEL_COLS,
                LayoutConstants.PANEL_HGAP,
                0
        ));
        detailsPanel.setBorder(new TitledBorder("Room Details"));

        roomNameField = new JTextField();
        shortDescriptionField = new JTextField();

        detailsPanel.add(new WrappingPanel("Room Name: (max 50 chars)", roomNameField));
        detailsPanel.add(new WrappingPanel("Short Description: (max 100 chars)", shortDescriptionField));

        roomFlagsPanel = new RoomFlagsPanel();
        roomFlagsPanel.setBorder(new TitledBorder(""));
        // The line below was removed as UiHelper.styleTabbedPane is now handled internally.

        JPanel buttonsPanel = new JPanel(new GridLayout(
                LayoutConstants.BUTTON_PANEL_ROWS,
                LayoutConstants.BUTTON_PANEL_COLS,
                LayoutConstants.BUTTON_PANEL_HGAP,
                LayoutConstants.BUTTON_PANEL_VGAP
        ));

        manageAmbianceButton = new JButton("Manage Ambient Text...");
        JPanel ambiancePanel = new JPanel(new BorderLayout());
        ambiancePanel.setBorder(new TitledBorder("Ambiance"));
        ambiancePanel.add(manageAmbianceButton, BorderLayout.CENTER);

        manageTimeStatesButton = new JButton("Manage Time States...");
        JPanel timeStatesPanel = new JPanel(new BorderLayout());
        timeStatesPanel.setBorder(new TitledBorder("Time States"));
        timeStatesPanel.add(manageTimeStatesButton, BorderLayout.CENTER);

        buttonsPanel.add(ambiancePanel);
        buttonsPanel.add(timeStatesPanel);

        add(detailsPanel, BorderLayout.NORTH);
        add(roomFlagsPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);
    }

    public void addChangeListener(DocumentListener docListener, ItemListener itemListener) {
        roomNameField.getDocument().addDocumentListener(docListener);
        shortDescriptionField.getDocument().addDocumentListener(docListener);
        roomFlagsPanel.addChangeListener(itemListener); // Now delegates to the panel
    }

    // --- Getters and Setters ---
    public String getRoomName() { return roomNameField.getText(); }
    public void setRoomName(String name) { roomNameField.setText(name); }
    public String getShortDescription() { return shortDescriptionField.getText(); }
    public void setShortDescription(String desc) { shortDescriptionField.setText(desc); }
    public JButton getManageAmbianceButton() { return manageAmbianceButton; }
    public RoomFlagsPanel getRoomFlagsPanel() { return roomFlagsPanel; }

    public JButton getManageTimeStatesButton() {
        return manageTimeStatesButton;
    }
}