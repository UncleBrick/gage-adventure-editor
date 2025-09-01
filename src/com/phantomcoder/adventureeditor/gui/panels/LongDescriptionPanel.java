package com.phantomcoder.adventureeditor.gui.panels;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentListener;
import java.awt.BorderLayout;

/**
 * A panel that holds the JTextArea for the room's long description.
 */
public class LongDescriptionPanel extends JPanel {

    private JTextArea longDescriptionArea;

    public LongDescriptionPanel() {
        setLayout(new BorderLayout()); // Use BorderLayout to make the text area fill the panel
        setBorder(BorderFactory.createTitledBorder("Long Description (max 1024 characters)"));

        longDescriptionArea = new JTextArea();
        longDescriptionArea.setLineWrap(true);
        longDescriptionArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(longDescriptionArea);
        add(scrollPane, BorderLayout.CENTER);
    }

    // Getters for the controller to access the text area
    public String getLongDescription() {
        return longDescriptionArea.getText();
    }

    public void setLongDescription(String description) {
        longDescriptionArea.setText(description);
    }

    // Add this method to the end of your LongDescriptionPanel.java file

    public void addChangeListener(DocumentListener listener) {
        longDescriptionArea.getDocument().addDocumentListener(listener);
    }
}