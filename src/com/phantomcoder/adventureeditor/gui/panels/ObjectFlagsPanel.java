package com.phantomcoder.adventureeditor.gui.panels;

import com.phantomcoder.adventureeditor.constants.ObjectFlagConstants;
import java.awt.BorderLayout;
import java.awt.event.ItemListener;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * A self-contained panel that creates and manages all JCheckBoxes for object
 * flags using the reusable GenericFlagsPanel.
 */
public class ObjectFlagsPanel extends JPanel {

    private final GenericFlagsPanel genericFlagsPanel;

    public ObjectFlagsPanel() {
        super(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Object Flags"));
        // Create an instance of the generic panel, passing it the specific flags for objects.
        this.genericFlagsPanel = new GenericFlagsPanel(ObjectFlagConstants.ALL_FLAG_GROUPS);
        add(genericFlagsPanel, BorderLayout.CENTER);
    }

    /**
     * Attaches a single ItemListener to all checkboxes in the panel.
     * @param listener The listener to attach.
     */
    public void addChangeListener(ItemListener listener) {
        genericFlagsPanel.addChangeListener(listener);
    }

    /**
     * Gets the set of selected flag constants from the panel.
     * @return A Set of strings representing the selected flags.
     */
    public Set<String> getSelectedFlags() {
        return genericFlagsPanel.getSelectedFlags();
    }

    /**
     * Sets the selected state of the checkboxes based on a set of flag constants.
     * @param flags The set of flags that should be selected.
     */
    public void setSelectedFlags(Set<String> flags) {
        genericFlagsPanel.setSelectedFlags(flags);
    }
}