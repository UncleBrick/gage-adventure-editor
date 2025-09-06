package com.phantomcoder.adventureeditor.gui.dialogs;

import com.phantomcoder.adventureeditor.constants.FieldDefaults;
import com.phantomcoder.adventureeditor.constants.LayoutConstants;
import com.phantomcoder.adventureeditor.gui.panels.WrappingPanel;
import com.phantomcoder.adventureeditor.model.AmbianceEvent;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.JScrollPane;

public class AddEditAmbianceDialog extends JDialog {

    private JTextField idField;
    private JTextField guidField;
    private JTextArea descriptiveTextField;
    private JSpinner frequencySpinner;
    private AmbianceEvent event;
    private boolean saved = false;

    public AddEditAmbianceDialog(JFrame parent, String title) {
        super(parent, title, true);

        JPanel contentPanel = new JPanel(new BorderLayout(LayoutConstants.PANEL_HGAP, LayoutConstants.PANEL_VGAP));
        contentPanel.setBorder(LayoutConstants.PANEL_PADDING);

        JPanel fieldsPanel = new JPanel(new BorderLayout(LayoutConstants.BORDER_LAYOUT_HGAP, LayoutConstants.BORDER_LAYOUT_VGAP));

        idField = new JTextField();
        guidField = new JTextField();
        guidField.setEditable(false);
        descriptiveTextField = new JTextArea(FieldDefaults.AMBIANCE_TEXT_AREA_ROWS, FieldDefaults.AMBIANCE_TEXT_AREA_COLS);
        descriptiveTextField.setLineWrap(true);
        descriptiveTextField.setWrapStyleWord(true);
        JScrollPane descriptionScroller = new JScrollPane(descriptiveTextField);

        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(
                FieldDefaults.DEFAULT_FREQUENCY,
                FieldDefaults.MIN_FREQUENCY,
                FieldDefaults.MAX_FREQUENCY,
                FieldDefaults.FREQUENCY_STEP
        );
        frequencySpinner = new JSpinner(spinnerModel);

        fieldsPanel.add(new WrappingPanel("ID (for scripting):", idField), BorderLayout.NORTH);

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

    public void setEventData(AmbianceEvent event) {
        this.event = event;
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
        event.setId(idField.getText());
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