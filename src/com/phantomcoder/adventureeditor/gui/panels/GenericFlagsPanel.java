package com.phantomcoder.adventureeditor.gui.panels;

import com.phantomcoder.adventureeditor.constants.FrameConstants;
import com.phantomcoder.adventureeditor.util.UiHelper;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ItemListener;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A generic, reusable panel that dynamically creates a tabbed interface with
 * checkboxes for any given set of categorized flags.
 */
public class GenericFlagsPanel extends JPanel {

    private final Map<String, JCheckBox> flagCheckBoxes = new HashMap<>();

    public GenericFlagsPanel(Map<String, List<String>> categorizedFlags) {
        super(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);

        // Dynamically create a tab for each category in the map.
        if (categorizedFlags != null) {
            for (Map.Entry<String, List<String>> entry : categorizedFlags.entrySet()) {
                createTab(tabbedPane, entry.getKey(), entry.getValue());
            }
        }

        // Add the placeholder tab for user-defined flags.
        createTab(tabbedPane, FrameConstants.USER_DEFINED_FLAGS_TAB_TITLE, null);

        UiHelper.styleTabbedPane(tabbedPane);
        add(tabbedPane, BorderLayout.CENTER);
    }

    private void createTab(JTabbedPane tabbedPane, String title, List<String> flags) {
        JPanel flagsContainerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));

        if (flags != null) {
            for (String flagConstant : flags) {
                String label = flagConstant.replace("_", " ");
                JCheckBox checkBox = new JCheckBox(label);
                flagCheckBoxes.put(flagConstant, checkBox);
                flagsContainerPanel.add(checkBox);
            }
        }

        JScrollPane scrollPane = new JScrollPane(flagsContainerPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPane.setBorder(null);

        tabbedPane.addTab(title, scrollPane);
    }

    public void addChangeListener(ItemListener listener) {
        for (JCheckBox checkBox : flagCheckBoxes.values()) {
            checkBox.addItemListener(listener);
        }
    }

    public Set<String> getSelectedFlags() {
        Set<String> selected = new HashSet<>();
        for (Map.Entry<String, JCheckBox> entry : flagCheckBoxes.entrySet()) {
            if (entry.getValue().isSelected()) {
                selected.add(entry.getKey());
            }
        }
        return selected;
    }

    public void setSelectedFlags(Set<String> flags) {
        if (flags == null) {
            flags = new HashSet<>();
        }
        for (Map.Entry<String, JCheckBox> entry : flagCheckBoxes.entrySet()) {
            entry.getValue().setSelected(flags.contains(entry.getKey()));
        }
    }
}