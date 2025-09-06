package com.phantomcoder.adventureeditor.gui.panels;

import com.phantomcoder.adventureeditor.constants.LayoutConstants;

import java.awt.BorderLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A simple container panel that wraps a JComponent with a JLabel above it.
 * This is used to consistently group labels with their input fields.
 */
public class WrappingPanel extends JPanel {

    /**
     * Default constructor for basic JPanel functionality.
     */
    public WrappingPanel() {
        // A no-argument constructor is still good to have.
    }

    /**
     * Creates a new panel that arranges a label to the north (top) and the
     * provided component in the center.
     * @param labelText The text for the JLabel.
     * @param component The JComponent (e.g., JTextField, JComboBox) to display.
     */
    public WrappingPanel(String labelText, JComponent component) {
        // Use a BorderLayout with a 5px vertical gap for spacing.
        setLayout(new BorderLayout(LayoutConstants.BORDER_LAYOUT_HGAP, LayoutConstants.BORDER_LAYOUT_VGAP));

        // Create the label and add it to the top.
        JLabel label = new JLabel(labelText);

        // FIX: Changed from BorderLayout.WEST to BorderLayout.NORTH
        add(label, BorderLayout.NORTH);

        // Add the provided component to the center.
        add(component, BorderLayout.CENTER);
    }
}