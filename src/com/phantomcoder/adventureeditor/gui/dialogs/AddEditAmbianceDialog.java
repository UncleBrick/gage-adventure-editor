package com.phantomcoder.adventureeditor.gui.dialogs;

import com.phantomcoder.adventureeditor.constants.FieldDefaults;
import com.phantomcoder.adventureeditor.constants.LayoutConstants;
import com.phantomcoder.adventureeditor.gui.panels.WrappingPanel;
import com.phantomcoder.adventureeditor.model.AmbianceEvent;
import com.phantomcoder.adventureeditor.service.AmbianceCreationService;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;
import javax.swing.JButton;
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

    private final JTextField idField;
    private final JTextField guidField;
    private final JTextArea descriptiveTextField;
    private final JSpinner frequencySpinner;
    private AmbianceEvent event;
    private boolean saved = false;
    private boolean isEditMode = false;

    private final String location;
    private final String area;
    private final String roomName;
    private final List<AmbianceEvent> existingEvents;

    public AddEditAmbianceDialog(JFrame parent, String title, String location, String area, String roomName, List<AmbianceEvent> existingEvents) {
        super(parent, title, true);
        this.location = location;
        this.area = area;
        this.roomName = roomName;
        this.existingEvents = existingEvents;

        JPanel contentPanel = new JPanel(new BorderLayout(LayoutConstants.PANEL_HGAP, LayoutConstants.PANEL_VGAP));
        contentPanel.setBorder(LayoutConstants.PANEL_PADDING);

        JPanel fieldsPanel = new JPanel(new BorderLayout(LayoutConstants.BORDER_LAYOUT_HGAP, LayoutConstants.BORDER_LAYOUT_VGAP));

        idField = new JTextField();
        idField.setEditable(false);

        guidField = new JTextField();
        guidField.setEditable(false);

        descriptiveTextField = new JTextArea(FieldDefaults.AMBIANCE_TEXT_AREA_ROWS, FieldDefaults.AMBIANCE_TEXT_AREA_COLS);
        descriptiveTextField.setLineWrap(true);
        descriptiveTextField.setWrapStyleWord(true);
        JScrollPane descriptionScroller = new JScrollPane(descriptiveTextField);

        descriptiveTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { updateIdField(); }
            @Override public void removeUpdate(DocumentEvent e) { updateIdField(); }
            @Override public void changedUpdate(DocumentEvent e) { updateIdField(); }
        });

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(
                FieldDefaults.DEFAULT_FREQUENCY, FieldDefaults.MIN_FREQUENCY, FieldDefaults.MAX_FREQUENCY, FieldDefaults.FREQUENCY_STEP);
        frequencySpinner = new JSpinner(spinnerModel);

        fieldsPanel.add(new WrappingPanel("ID (auto-generated):", idField), BorderLayout.NORTH);

        JPanel centerFieldsPanel = new JPanel(new BorderLayout(LayoutConstants.BORDER_LAYOUT_HGAP, LayoutConstants.BORDER_LAYOUT_VGAP));
        centerFieldsPanel.add(new WrappingPanel("GUID (auto-generated):", guidField), BorderLayout.NORTH);
        centerFieldsPanel.add(new WrappingPanel("Descriptive Text:", descriptionScroller), BorderLayout.CENTER);

        fieldsPanel.add(centerFieldsPanel, BorderLayout.CENTER);
        fieldsPanel.add(new WrappingPanel("Frequency %:", frequencySpinner), BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> onSave());
        cancelButton.addActionListener(e -> onCancel());

        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        contentPanel.add(fieldsPanel, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(contentPanel);
        pack();
        setLocationRelativeTo(parent);
    }

    private void updateIdField() {
        if (isEditMode) {
            return;
        }
        String contentHash = AmbianceCreationService.generateContentHash(descriptiveTextField.getText());
        idField.setText(contentHash);
    }

    public void setEventData(AmbianceEvent event) {
        this.event = event;
        this.isEditMode = (event != null && event.getGuid() != null && !event.getGuid().isEmpty());

        idField.setText(event.getId());
        guidField.setText(event.getGuid());
        descriptiveTextField.setText(event.getText());
        frequencySpinner.setValue(event.getFrequency());
    }

    public AmbianceEvent getEventData() {
        return event;
    }

    public boolean isSaved() {
        return saved;
    }

    private void onSave() {
        if (event == null) {
            event = new AmbianceEvent();
        }

        if (!isEditMode) {
            String contentHash = idField.getText(); // Use the hash already generated in the text field
            String fullId = AmbianceCreationService.generateFullId(
                    this.location, this.area, this.roomName, contentHash, this.existingEvents
            );
            event.setId(fullId);
        }

        event.setText(descriptiveTextField.getText());
        event.setFrequency((Integer) frequencySpinner.getValue());

        saved = true;
        setVisible(false);
    }

    private void onCancel() {
        saved = false;
        setVisible(false);
    }
}