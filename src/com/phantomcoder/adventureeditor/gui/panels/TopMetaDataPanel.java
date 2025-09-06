package com.phantomcoder.adventureeditor.gui.panels;

import com.phantomcoder.adventureeditor.constants.FieldDefaults;
import com.phantomcoder.adventureeditor.constants.LayoutConstants;
import com.phantomcoder.adventureeditor.util.CharacterLimitFilter;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

@SuppressWarnings("serial")
public class TopMetaDataPanel extends JPanel {

    private JTextField locationNameField;
    private JTextField areaNameField;
    private JTextField fileNameField;

    public TopMetaDataPanel() {
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createTitledBorder("Project Metadata"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = LayoutConstants.FIELD_PADDING;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridy = 0;
        gbc.weightx = 0.33;
        gbc.gridx = 0;
        add(new JLabel("Location Name (max " + FieldDefaults.MAX_LOCATION_NAME_CHARS + " chars):"), gbc);
        gbc.gridx = 1;
        add(new JLabel("Area Name (max " + FieldDefaults.MAX_AREA_NAME_CHARS + " chars):"), gbc);
        gbc.gridx = 2;
        add(new JLabel("File Name (max " + FieldDefaults.MAX_FILE_NAME_CHARS + " chars):"), gbc);

        gbc.gridy = 1;
        locationNameField = new JTextField(15);
        ((AbstractDocument) locationNameField.getDocument()).setDocumentFilter(new CharacterLimitFilter(FieldDefaults.MAX_LOCATION_NAME_CHARS));
        gbc.gridx = 0;
        add(locationNameField, gbc);

        areaNameField = new JTextField(15);
        ((AbstractDocument) areaNameField.getDocument()).setDocumentFilter(new CharacterLimitFilter(FieldDefaults.MAX_AREA_NAME_CHARS));
        gbc.gridx = 1;
        add(areaNameField, gbc);

        fileNameField = new JTextField(15);
        ((AbstractDocument) fileNameField.getDocument()).setDocumentFilter(new CharacterLimitFilter(FieldDefaults.MAX_FILE_NAME_CHARS));
        gbc.gridx = 2;
        add(fileNameField, gbc);
    }

    public String getLocationName() { return locationNameField.getText(); }
    public String getAreaName() { return areaNameField.getText(); }
    public String getFileName() { return fileNameField.getText(); }
    public void setLocationName(String name) { locationNameField.setText(name); }
    public void setAreaName(String name) { areaNameField.setText(name); }
    public void setFileName(String fileName) { fileNameField.setText(fileName); }

    public void addChangeListener(DocumentListener listener) {
        locationNameField.getDocument().addDocumentListener(listener);
        areaNameField.getDocument().addDocumentListener(listener);
        fileNameField.getDocument().addDocumentListener(listener);
    }
}