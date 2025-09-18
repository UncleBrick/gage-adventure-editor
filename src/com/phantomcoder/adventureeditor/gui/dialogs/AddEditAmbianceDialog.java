package com.phantomcoder.adventureeditor.gui.dialogs;

import com.phantomcoder.adventureeditor.constants.AppConstants;
import com.phantomcoder.adventureeditor.constants.AmbianceFlagConstants;
import com.phantomcoder.adventureeditor.constants.FieldDefaults;
import com.phantomcoder.adventureeditor.constants.LayoutConstants;
import com.phantomcoder.adventureeditor.gui.panels.GenericFlagsPanel;
import com.phantomcoder.adventureeditor.gui.panels.WrappingPanel;
import com.phantomcoder.adventureeditor.model.AmbianceEvent;
import com.phantomcoder.adventureeditor.service.AmbianceCreationService;
import com.phantomcoder.adventureeditor.util.PathUtil;
import com.phantomcoder.adventureeditor.util.UiHelper;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.util.List;
import java.util.UUID;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class AddEditAmbianceDialog extends JDialog {

    private final JTextField customIdField;
    private final JTextField finalIdField;
    private final JTextField guidField;
    private final JTextArea descriptiveTextField;
    private final JSpinner frequencySpinner;
    private final JCheckBox useCustomIdCheckBox;
    private final GenericFlagsPanel flagsPanel;

    private AmbianceEvent event;
    private boolean saved = false;
    private String locationName;
    private String areaName;
    private String subAreaName;
    private String roomName;
    private List<AmbianceEvent> existingEvents;

    public AddEditAmbianceDialog(JFrame parent, String title) {
        super(parent, title, true);

        JPanel contentPanel = new JPanel(new BorderLayout(LayoutConstants.PANEL_HGAP, LayoutConstants.PANEL_VGAP));
        contentPanel.setBorder(LayoutConstants.PANEL_PADDING);

        // --- Top Panel for ID ---
        JPanel idPanel = new JPanel(new BorderLayout(LayoutConstants.BORDER_LAYOUT_HGAP, LayoutConstants.BORDER_LAYOUT_VGAP));
        guidField = new JTextField();
        guidField.setEditable(false);
        idPanel.add(new WrappingPanel("GUID (auto-generated):", guidField), BorderLayout.NORTH);

        useCustomIdCheckBox = new JCheckBox("Use Custom ID");
        customIdField = new JTextField();
        finalIdField = new JTextField();
        finalIdField.setEditable(false);

        JPanel customIdPanel = new JPanel(new BorderLayout(LayoutConstants.BORDER_LAYOUT_HGAP, LayoutConstants.BORDER_LAYOUT_VGAP));
        customIdPanel.add(new WrappingPanel("Custom ID:", customIdField), BorderLayout.CENTER);
        customIdPanel.add(useCustomIdCheckBox, BorderLayout.WEST);

        idPanel.add(customIdPanel, BorderLayout.CENTER);
        idPanel.add(new WrappingPanel("Final ID (read-only):", finalIdField), BorderLayout.SOUTH);

        // --- Center Panel ---
        JPanel centerPanel = new JPanel(new BorderLayout(LayoutConstants.BORDER_LAYOUT_HGAP, LayoutConstants.BORDER_LAYOUT_VGAP));
        descriptiveTextField = new JTextArea(FieldDefaults.AMBIANCE_TEXT_AREA_ROWS, FieldDefaults.AMBIANCE_TEXT_AREA_COLS);
        descriptiveTextField.setLineWrap(true);
        descriptiveTextField.setWrapStyleWord(true);
        JScrollPane descriptionScroller = new JScrollPane(descriptiveTextField);
        centerPanel.add(new WrappingPanel("Descriptive Text:", descriptionScroller), BorderLayout.CENTER);

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(
                FieldDefaults.DEFAULT_FREQUENCY, FieldDefaults.MIN_FREQUENCY, FieldDefaults.MAX_FREQUENCY, FieldDefaults.FREQUENCY_STEP);
        frequencySpinner = new JSpinner(spinnerModel);
        centerPanel.add(new WrappingPanel("Frequency %:", frequencySpinner), BorderLayout.SOUTH);

        // --- Flags Panel ---
        flagsPanel = new GenericFlagsPanel(AmbianceFlagConstants.CATEGORIZED_FLAGS);
        flagsPanel.setBorder(LayoutConstants.PANEL_PADDING);

        // --- Button Panel ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> onSave());
        cancelButton.addActionListener(e -> onCancel());
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // --- Assemble Dialog ---
        contentPanel.add(idPanel, BorderLayout.NORTH);
        contentPanel.add(centerPanel, BorderLayout.CENTER);
        contentPanel.add(flagsPanel, BorderLayout.SOUTH);

        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.add(contentPanel, BorderLayout.CENTER);
        mainContainer.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainContainer);

        // --- Add Listeners ---
        useCustomIdCheckBox.addItemListener(e -> {
            boolean isSelected = e.getStateChange() == ItemEvent.SELECTED;
            customIdField.setEnabled(isSelected);
            updateFinalIdPreview();
        });

        DocumentListener idUpdateListener = new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { updateFinalIdPreview(); }
            @Override public void removeUpdate(DocumentEvent e) { updateFinalIdPreview(); }
            @Override public void changedUpdate(DocumentEvent e) { updateFinalIdPreview(); }
        };
        customIdField.getDocument().addDocumentListener(idUpdateListener);
        descriptiveTextField.getDocument().addDocumentListener(idUpdateListener);

        pack();
        setLocationRelativeTo(parent);
    }

    private void updateFinalIdPreview() {
        String vanityOrSlug;
        try {
            if (useCustomIdCheckBox.isSelected()) {
                vanityOrSlug = PathUtil.toSafeFileName(customIdField.getText());
                if (vanityOrSlug.isEmpty()) {
                    vanityOrSlug = "custom";
                }
            } else {
                vanityOrSlug = AmbianceCreationService.generateSlugFromDescription(descriptiveTextField.getText());
            }

            String previewId = AmbianceCreationService.generateFullId(
                    locationName, areaName, subAreaName, roomName, vanityOrSlug, null
            );
            finalIdField.setText(previewId.substring(0, previewId.length() - 3));
        } catch (IllegalArgumentException e) {
            finalIdField.setText("Description too short...");
        }
    }

    public void setEventData(AmbianceEvent event, String loc, String area, String subArea, String room, List<AmbianceEvent> existing) {
        this.event = event;
        this.locationName = loc;
        this.areaName = area;
        this.subAreaName = subArea;
        this.roomName = room;
        this.existingEvents = existing;

        boolean isEditMode = (event != null && event.getGuid() != null && !event.getGuid().isEmpty());
        if (isEditMode) {
            boolean isOldFormat = event.getId() != null && !event.getId().startsWith("ambtxt_");
            useCustomIdCheckBox.setSelected(!isOldFormat);
            customIdField.setText("");
        } else {
            useCustomIdCheckBox.setSelected(false);
        }

        guidField.setText(event.getGuid());
        descriptiveTextField.setText(event.getText());
        frequencySpinner.setValue(event.getFrequency());
        flagsPanel.setSelectedFlags(event.getFlags());
        customIdField.setEnabled(useCustomIdCheckBox.isSelected());
        updateFinalIdPreview();
    }

    public AmbianceEvent getEventData() {
        return event;
    }

    public boolean isSaved() {
        return saved;
    }

    private void onSave() {
        String description = descriptiveTextField.getText();

        String vanityOrSlug;
        try {
            if (useCustomIdCheckBox.isSelected()) {
                vanityOrSlug = PathUtil.toSafeFileName(customIdField.getText());
                if (vanityOrSlug.isEmpty()) {
                    vanityOrSlug = "custom";
                }
            } else {
                vanityOrSlug = AmbianceCreationService.generateSlugFromDescription(description);
            }
        } catch (IllegalArgumentException e) {
            UiHelper.showErrorDialog(this, "Validation Error", e.getMessage());
            return;
        }

        if (event == null) {
            event = new AmbianceEvent();
        }

        if (event.getGuid() == null || event.getGuid().isEmpty()) {
            event.setGuid(UUID.randomUUID().toString());
        }

        String finalId = AmbianceCreationService.generateFullId(
                locationName, areaName, subAreaName, roomName, vanityOrSlug, existingEvents
        );
        event.setId(finalId);

        event.setText(description);
        event.setFrequency((Integer) frequencySpinner.getValue());
        event.setFlags(flagsPanel.getSelectedFlags());

        saved = true;
        setVisible(false);
    }

    private void onCancel() {
        saved = false;
        setVisible(false);
    }
}