package com.phantomcoder.adventureeditor.gui.dialogs;

import com.phantomcoder.adventureeditor.constants.LayoutConstants;
import com.phantomcoder.adventureeditor.model.AmbianceEvent;
import com.phantomcoder.adventureeditor.util.PathUtil;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class AddEditAmbianceDialog extends JDialog {

    private JTextField idField;
    private JTextArea descriptiveTextArea;
    private JSpinner frequencySpinner;
    private JButton saveButton, cancelButton;
    private AmbianceEvent ambianceEvent = null;
    private boolean isIdManuallySet = false;

    // Constructor for ADDING a new event
    public AddEditAmbianceDialog(Dialog owner) {
        super(owner, "Add Ambient Text", true);
        initComponents();
        setupSmartIdListeners();
        finalizeDialog(owner);
    }

    // Constructor for EDITING an existing event
    public AddEditAmbianceDialog(Dialog owner, AmbianceEvent eventToEdit) {
        super(owner, "Edit Ambient Text", true);
        initComponents();

        // Populate the form with the existing data
        idField.setText(eventToEdit.getId());
        descriptiveTextArea.setText(eventToEdit.getText());
        frequencySpinner.setValue(eventToEdit.getFrequency());

        // When editing, the ID cannot be changed and should not be auto-generated
        idField.setEditable(false);
        isIdManuallySet = true;

        finalizeDialog(owner);
    }

    // Private method to build the UI, used by both constructors (DRY)
    private void initComponents() {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(LayoutConstants.PANEL_PADDING);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = LayoutConstants.FIELD_PADDING;
        gbc.anchor = GridBagConstraints.WEST;

        // Row 0: ID
        gbc.gridy = 0;
        gbc.gridx = 0;
        gbc.weightx = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        contentPanel.add(new JLabel("ID (for scripting):"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        idField = new JTextField(20);
        contentPanel.add(idField, gbc);

        // Row 1: Descriptive Text
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        contentPanel.add(new JLabel("Descriptive Text:"), gbc);

        gbc.gridx = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        descriptiveTextArea = new JTextArea(5, 20);
        descriptiveTextArea.setLineWrap(true);
        descriptiveTextArea.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(descriptiveTextArea);
        contentPanel.add(scrollPane, gbc);

        // Row 2: Frequency
        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.weighty = 0.0;
        gbc.fill = GridBagConstraints.NONE;
        contentPanel.add(new JLabel("Frequency %:"), gbc);

        gbc.gridx = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        SpinnerNumberModel spinnerModel = new SpinnerNumberModel(50, 0, 100, 1);
        frequencySpinner = new JSpinner(spinnerModel);
        contentPanel.add(frequencySpinner, gbc);

        // --- Button Panel ---
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        saveButton = new JButton("Save");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        saveButton.addActionListener(e -> handleSave());
        cancelButton.addActionListener(e -> handleCancel());

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupSmartIdListeners() {
        idField.addKeyListener(new KeyAdapter() {
            @Override public void keyTyped(KeyEvent e) { isIdManuallySet = true; }
        });
        descriptiveTextArea.getDocument().addDocumentListener(new DocumentListener() {
            private void updateId() {
                if (!isIdManuallySet) {
                    idField.setText(generateIdFromText(descriptiveTextArea.getText()));
                }
            }
            @Override public void insertUpdate(DocumentEvent e) { updateId(); }
            @Override public void removeUpdate(DocumentEvent e) { updateId(); }
            @Override public void changedUpdate(DocumentEvent e) { updateId(); }
        });
    }

    private void finalizeDialog(Dialog owner) {
        addWindowListener(new WindowAdapter() {
            @Override public void windowOpened(WindowEvent e) {
                if (idField.isEditable()) { // Only focus text area for new entries
                    descriptiveTextArea.requestFocusInWindow();
                } else { // Focus text area for edits too, but it's a good general practice
                    descriptiveTextArea.requestFocusInWindow();
                }
            }
        });
        pack();
        setResizable(false);
        setLocationRelativeTo(owner);
    }

    private String generateIdFromText(String text) {
        if (text == null || text.trim().isEmpty()) {
            return "";
        }
        String shortVersion = Arrays.stream(text.trim().split("\\s+"))
                .limit(4)
                .collect(Collectors.joining(" "));
        return PathUtil.toOsSafeLowerCase(shortVersion);
    }

    public AmbianceEvent getAmbianceEvent() {
        return ambianceEvent;
    }

    private void handleSave() {
        String text = descriptiveTextArea.getText().trim();
        String id = idField.getText().trim();
        int frequency = (Integer) frequencySpinner.getValue();

        if (id.isEmpty() && !text.isEmpty() && idField.isEditable()) {
            id = generateIdFromText(text);
        }

        if (id.isEmpty() || text.isEmpty()) {
            JOptionPane.showMessageDialog(this, "ID and Descriptive Text cannot be empty.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        this.ambianceEvent = new AmbianceEvent(id, text, frequency);
        dispose();
    }

    private void handleCancel() {
        this.ambianceEvent = null;
        dispose();
    }
}