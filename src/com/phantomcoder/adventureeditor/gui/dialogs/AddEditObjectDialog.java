package com.phantomcoder.adventureeditor.gui.dialogs;

import com.phantomcoder.adventureeditor.constants.FieldDefaults;
import com.phantomcoder.adventureeditor.constants.LayoutConstants;
import com.phantomcoder.adventureeditor.constants.ObjectFlagConstants;
import com.phantomcoder.adventureeditor.gui.panels.ObjectFlagsPanel;
import com.phantomcoder.adventureeditor.model.ObjectData;
import com.phantomcoder.adventureeditor.util.CharacterLimitFilter;
import com.phantomcoder.adventureeditor.util.UiHelper;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.text.AbstractDocument;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Set;

@SuppressWarnings("serial")
public class AddEditObjectDialog extends JDialog {

    private JTextField objectNameField, shortDescriptionField;
    private JTextArea longDescriptionArea;
    private JTextField keyIdField;
    private JCheckBox isLockableBox;
    private JButton saveButton, cancelButton;

    private final ObjectFlagsPanel objectFlagsPanel;
    private ObjectData resultObject = null;

    public AddEditObjectDialog(Frame owner, String location, String area, String roomFile) {
        super(owner, "Object Properties", true);

        // Create all the child panels
        JPanel metadataPanel = createMetadataPanel(location, area, roomFile);
        JPanel detailsPanel = createDetailsPanel();
        objectFlagsPanel = new ObjectFlagsPanel(); // Use the new dedicated panel
        JPanel lockingPanel = createLockingPanel();
        JPanel longDescriptionPanel = createLongDescriptionPanel();
        JPanel buttonPanel = createButtonPanel();

        // Main content panel uses a vertical BoxLayout
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(LayoutConstants.PANEL_PADDING);

        // Add panels in the new, consistent order
        contentPanel.add(metadataPanel);
        contentPanel.add(detailsPanel);
        contentPanel.add(objectFlagsPanel);
        contentPanel.add(lockingPanel);
        contentPanel.add(longDescriptionPanel);

        // Set maximum heights for fixed-size panels to prevent stretching
        metadataPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, metadataPanel.getPreferredSize().height));
        detailsPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, detailsPanel.getPreferredSize().height));
        lockingPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, lockingPanel.getPreferredSize().height));

        isLockableBox.addItemListener(e -> keyIdField.setEnabled(e.getStateChange() == ItemEvent.SELECTED));

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                objectNameField.requestFocusInWindow();
            }
        });

        pack();
        setResizable(true); // Allow resizing
        setLocationRelativeTo(owner);
    }

    public ObjectData getResultObject() {
        return resultObject;
    }

    private JPanel createMetadataPanel(String location, String area, String roomFile) {
        JPanel metadataPanel = new JPanel(new GridBagLayout());
        metadataPanel.setBorder(new TitledBorder("Object Metadata"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = LayoutConstants.DIALOG_FIELD_PADDING;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridy = 0;
        gbc.gridx = 0;
        metadataPanel.add(new JLabel("Location Name:"), gbc);
        gbc.gridx = 1;
        metadataPanel.add(new JLabel("Area Name:"), gbc);
        gbc.gridx = 2;
        metadataPanel.add(new JLabel("Room File Name:"), gbc);

        gbc.gridy = 1;
        JTextField locationField = new JTextField(location, FieldDefaults.OBJECT_NAME_COLUMNS);
        locationField.setEditable(false);
        locationField.setFocusable(false);
        gbc.gridx = 0;
        metadataPanel.add(locationField, gbc);

        JTextField areaField = new JTextField(area, FieldDefaults.OBJECT_NAME_COLUMNS);
        areaField.setEditable(false);
        areaField.setFocusable(false);
        gbc.gridx = 1;
        metadataPanel.add(areaField, gbc);

        JTextField roomFileField = new JTextField(roomFile, FieldDefaults.OBJECT_NAME_COLUMNS);
        roomFileField.setEditable(false);
        roomFileField.setFocusable(false);
        gbc.gridx = 2;
        metadataPanel.add(roomFileField, gbc);

        return metadataPanel;
    }

    private JPanel createDetailsPanel() {
        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setBorder(new TitledBorder("Object Details"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = LayoutConstants.DIALOG_FIELD_PADDING;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;
        gbc.weightx = 0.5;
        gbc.gridx = 0;
        detailsPanel.add(new JLabel("Object Name: (max " + FieldDefaults.MAX_ROOM_NAME_CHARS + " chars):"), gbc);
        gbc.gridx = 1;
        detailsPanel.add(new JLabel("Short Description: (max " + FieldDefaults.MAX_SHORT_DESC_CHARS + " chars):"), gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        objectNameField = new JTextField(FieldDefaults.OBJECT_NAME_COLUMNS);
        ((AbstractDocument) objectNameField.getDocument()).setDocumentFilter(new CharacterLimitFilter(FieldDefaults.MAX_ROOM_NAME_CHARS));
        detailsPanel.add(objectNameField, gbc);
        gbc.gridx = 1;
        shortDescriptionField = new JTextField(FieldDefaults.OBJECT_SHORT_DESC_COLUMNS);
        ((AbstractDocument) shortDescriptionField.getDocument()).setDocumentFilter(new CharacterLimitFilter(FieldDefaults.MAX_SHORT_DESC_CHARS));
        detailsPanel.add(shortDescriptionField, gbc);

        return detailsPanel;
    }

    private JPanel createLongDescriptionPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new TitledBorder("Long Description: (max " + FieldDefaults.MAX_LONG_DESC_CHARS + " chars):"));
        longDescriptionArea = new JTextArea(FieldDefaults.OBJECT_LONG_DESC_ROWS, FieldDefaults.OBJECT_LONG_DESC_COLS);
        longDescriptionArea.setLineWrap(true);
        longDescriptionArea.setWrapStyleWord(true);
        ((AbstractDocument) longDescriptionArea.getDocument()).setDocumentFilter(new CharacterLimitFilter(FieldDefaults.MAX_LONG_DESC_CHARS));
        JScrollPane scrollPane = new JScrollPane(longDescriptionArea);
        panel.add(scrollPane, BorderLayout.CENTER);
        return panel;
    }

    private JPanel createLockingPanel() {
        JPanel lockingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        lockingPanel.setBorder(new TitledBorder("Locking Logic"));
        isLockableBox = new JCheckBox("Is Lockable");
        lockingPanel.add(isLockableBox);
        lockingPanel.add(new JLabel("Key ID to Unlock:"));
        keyIdField = new JTextField(FieldDefaults.KEY_ID_COLUMNS);
        keyIdField.setEnabled(false);
        lockingPanel.add(keyIdField);
        return lockingPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBorder(BorderFactory.createEtchedBorder());
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        saveButton.addActionListener(e -> handleSave());
        cancelButton.addActionListener(e -> handleCancel());
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        return buttonPanel;
    }

    private void handleSave() {
        String name = objectNameField.getText().trim();
        String shortDesc = shortDescriptionField.getText().trim();
        if (name.isEmpty() || shortDesc.isEmpty()) {
            UiHelper.showErrorDialog(this, "Validation Error", "Object Name and Short Description cannot be empty.");
            return;
        }

        String longDesc = longDescriptionArea.getText().trim();
        Set<String> flags = objectFlagsPanel.getSelectedFlags();
        String keyId = null;
        if (isLockableBox.isSelected()) {
            flags.add(ObjectFlagConstants.IS_LOCKABLE);
            keyId = keyIdField.getText().trim();
        }

        this.resultObject = new ObjectData(name, shortDesc, longDesc, flags, keyId);
        dispose();
    }

    private void handleCancel() {
        this.resultObject = null;
        dispose();
    }
}