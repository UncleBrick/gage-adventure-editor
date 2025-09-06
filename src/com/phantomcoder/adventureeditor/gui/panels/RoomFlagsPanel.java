package com.phantomcoder.adventureeditor.gui.panels;

import com.phantomcoder.adventureeditor.constants.RoomFlagConstants;

import java.awt.FlowLayout;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

public class RoomFlagsPanel extends JTabbedPane {

    private final Map<String, JCheckBox> flagCheckboxes = new HashMap<>();

    public RoomFlagsPanel() {
        initialize();
    }

    private void initialize() {
        // Loop through each category of flags defined in the constants file
        for (Map.Entry<String, List<String>> entry : RoomFlagConstants.CATEGORIZED_FLAGS.entrySet()) {
            String category = entry.getKey();
            List<String> flagNames = entry.getValue();

            // FIX: Create a JPanel with a FlowLayout instead of a JTextPane.
            // This is the standard way to create a non-wrapping, scrolling list of components.
            JPanel flagsContainerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));

            // Create and add a checkbox for each flag in the current category
            for (String flagName : flagNames) {
                JCheckBox checkBox = new JCheckBox(flagName);
                flagCheckboxes.put(flagName, checkBox);
                flagsContainerPanel.add(checkBox);
            }

            // Place the JPanel inside a JScrollPane
            JScrollPane scrollPane = new JScrollPane(flagsContainerPanel);
            scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
            scrollPane.setBorder(null); // Cleaner look inside a tabbed pane

            // Add the completed scroll pane as a new tab
            addTab(category, scrollPane);
        }
    }

    /**
     * Gets the set of selected tag strings from all checkboxes.
     * @return A Set of strings representing the selected flags.
     */
    public Set<String> getSelectedTags() {
        Set<String> selectedTags = new HashSet<>();
        for (Map.Entry<String, JCheckBox> entry : flagCheckboxes.entrySet()) {
            if (entry.getValue().isSelected()) {
                selectedTags.add(entry.getKey());
            }
        }
        return selectedTags;
    }

    /**
     * Sets the selected state of the checkboxes based on a set of tag strings.
     * @param tags The set of tags that should be selected.
     */
    public void setSelectedTags(Set<String> tags) {
        for (Map.Entry<String, JCheckBox> entry : flagCheckboxes.entrySet()) {
            entry.getValue().setSelected(tags.contains(entry.getKey()));
        }
    }
}