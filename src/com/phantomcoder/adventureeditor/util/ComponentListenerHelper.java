package com.phantomcoder.adventureeditor.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ItemListener;
import javax.swing.JCheckBox;

/**
 * A utility class to help with attaching listeners to components within a container hierarchy.
 * This follows SRP and DRY principles for reusable listener logic.
 */
public class ComponentListenerHelper {

    /**
     * Recursively traverses a container and attaches an ItemListener to all JCheckBox components found.
     *
     * @param container    The top-level container to search within (e.g., a JPanel or JTabbedPane).
     * @param itemListener The listener to attach to each checkbox.
     */
    public static void attachListenerToCheckBoxes(Container container, ItemListener itemListener) {
        // Iterate through all components in the current container
        for (Component comp : container.getComponents()) {
            // If the component is a JCheckBox, add the listener
            if (comp instanceof JCheckBox) {
                ((JCheckBox) comp).addItemListener(itemListener);
            }
            // If the component is itself a container, search inside it recursively
            else if (comp instanceof Container) {
                attachListenerToCheckBoxes((Container) comp, itemListener);
            }
        }
    }
}