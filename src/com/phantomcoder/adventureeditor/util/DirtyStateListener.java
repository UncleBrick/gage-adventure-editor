package com.phantomcoder.adventureeditor.util;

import java.awt.Component;
import java.awt.Container;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

/**
 * A reusable listener that can be applied to a container to detect changes
 * in any of its child input components (text fields, checkboxes, etc.).
 * This is a clean, DRY approach to managing a "dirty" flag.
 */
public class DirtyStateListener implements DocumentListener, ItemListener {

    private final Runnable onStateChange;

    private DirtyStateListener(Runnable onStateChange) {
        this.onStateChange = onStateChange;
    }

    // --- Listener event methods ---
    // All events simply trigger the one Runnable action.

    @Override
    public void itemStateChanged(ItemEvent e) {
        onStateChange.run();
    }

    @Override
    public void insertUpdate(DocumentEvent e) {
        onStateChange.run();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        onStateChange.run();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        onStateChange.run();
    }

    /**
     * The main public method to apply this listener to a container.
     * @param container The container to search for input components.
     * @param onStateChange The action to run when any component's state changes.
     */
    public static void applyTo(Container container, Runnable onStateChange) {
        DirtyStateListener listener = new DirtyStateListener(onStateChange);
        attachToChildren(container, listener);
    }

    /**
     * A private helper that recursively searches a container for components
     * to attach the listener to.
     */
    private static void attachToChildren(Container container, DirtyStateListener listener) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JCheckBox) {
                ((JCheckBox) comp).addItemListener(listener);
            } else if (comp instanceof JComboBox) {
                ((JComboBox<?>) comp).addItemListener(listener);
            } else if (comp instanceof JTextComponent) {
                ((JTextComponent) comp).getDocument().addDocumentListener(listener);
            }
            // If the component is a container, search its children recursively.
            else if (comp instanceof Container) {
                attachToChildren((Container) comp, listener);
            }
        }
    }
}