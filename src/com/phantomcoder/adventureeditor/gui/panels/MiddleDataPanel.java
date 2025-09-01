package com.phantomcoder.adventureeditor.gui.panels;

import com.phantomcoder.adventureeditor.constants.LayoutConstants;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentListener;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemListener;

public class MiddleDataPanel extends JPanel {

    // CORRECTED: 'final' removed from these three variables
    private JTextField roomNameField;
    private JTextField shortDescriptionField;
    private JButton manageAmbianceButton;

    // This can remain final as it's initialized in the constructor
    private final RoomFlagsPanel roomFlagsPanel;

    public MiddleDataPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        JPanel fieldsPanel = createFieldsPanel();
        roomFlagsPanel = new RoomFlagsPanel(); // Create an instance of our new panel
        JPanel ambiancePanel = createAmbiancePanel();

        // Constrain the height of the fixed-size panels
        int fieldsHeight = fieldsPanel.getPreferredSize().height;
        fieldsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, fieldsHeight));

        int ambianceHeight = ambiancePanel.getPreferredSize().height;
        ambiancePanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, ambianceHeight));

        add(fieldsPanel);
        add(roomFlagsPanel); // Add the new panel
        add(ambiancePanel);

        // Add glue to push everything to the top
        add(Box.createVerticalGlue());
    }

    private JPanel createFieldsPanel() {
        JPanel fieldsPanel = new JPanel(new GridBagLayout());
        fieldsPanel.setBorder(new TitledBorder("Room Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = LayoutConstants.FIELD_PADDING;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.gridx = 0;
        fieldsPanel.add(new JLabel("Room Name: (max 50 characters)"), gbc);
        gbc.gridx = 1;
        fieldsPanel.add(new JLabel("Short Description: (max 100 characters)"), gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        roomNameField = new JTextField(30);
        fieldsPanel.add(roomNameField, gbc);
        gbc.gridx = 1;
        shortDescriptionField = new JTextField(30);
        fieldsPanel.add(shortDescriptionField, gbc);

        return fieldsPanel;
    }

    private JPanel createAmbiancePanel() {
        JPanel ambiancePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        ambiancePanel.setBorder(new TitledBorder("Ambiance"));
        manageAmbianceButton = new JButton("Manage Ambient Text...");
        ambiancePanel.add(manageAmbianceButton);
        return ambiancePanel;
    }

    public void addChangeListener(DocumentListener docListener, ItemListener itemListener) {
        roomNameField.getDocument().addDocumentListener(docListener);
        shortDescriptionField.getDocument().addDocumentListener(docListener);
        roomFlagsPanel.addChangeListener(itemListener); // Delegate to the child panel
    }

    public RoomFlagsPanel getRoomFlagsPanel() {
        return roomFlagsPanel;
    }

    public JButton getManageAmbianceButton() { return manageAmbianceButton; }
    public String getRoomName() { return roomNameField.getText(); }
    public void setRoomName(String name) { roomNameField.setText(name); }
    public String getShortDescription() { return shortDescriptionField.getText(); }
    public void setShortDescription(String description) { shortDescriptionField.setText(description); }
}