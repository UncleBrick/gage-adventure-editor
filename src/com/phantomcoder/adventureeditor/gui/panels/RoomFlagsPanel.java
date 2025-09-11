package com.phantomcoder.adventureeditor.gui.panels;

import com.phantomcoder.adventureeditor.constants.RoomFlagConstants;
import java.awt.BorderLayout;
import java.awt.event.ItemListener;
import java.util.Set;
import javax.swing.JPanel;

/**
 * A panel that displays the categorized flags for a room. This class is now a
 * simple wrapper around the reusable GenericFlagsPanel.
 */
public class RoomFlagsPanel extends JPanel {

    private final GenericFlagsPanel genericFlagsPanel;

    public RoomFlagsPanel() {
        super(new BorderLayout());
        // Create an instance of the generic panel, passing it the specific flags for rooms.
        this.genericFlagsPanel = new GenericFlagsPanel(RoomFlagConstants.CATEGORIZED_FLAGS);
        add(genericFlagsPanel, BorderLayout.CENTER);
    }

    /**
     * Gets the set of selected tag strings from the panel.
     * @return A Set of strings representing the selected flags.
     */
    public Set<String> getSelectedTags() {
        return genericFlagsPanel.getSelectedFlags();
    }

    /**
     * Sets the selected state of the checkboxes based on a set of tag strings.
     * @param tags The set of tags that should be selected.
     */
    public void setSelectedTags(Set<String> tags) {
        genericFlagsPanel.setSelectedFlags(tags);
    }

    /**
     * Attaches a single ItemListener to all checkboxes in the panel.
     * @param listener The listener to attach.
     */
    public void addChangeListener(ItemListener listener) {
        genericFlagsPanel.addChangeListener(listener);
    }
}