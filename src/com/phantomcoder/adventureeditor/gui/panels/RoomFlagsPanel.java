package com.phantomcoder.adventureeditor.gui.panels;

import com.phantomcoder.adventureeditor.constants.RoomFlagConstants;
import com.phantomcoder.adventureeditor.util.UiHelper;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import java.awt.BorderLayout;
import java.awt.event.ItemListener;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A self-contained panel that dynamically creates, lays out, and manages all
 * the JCheckBoxes for room flags using a JTextPane inside a JTabbedPane
 * to ensure correct wrapping and scrolling.
 */
public class RoomFlagsPanel extends JPanel {

    private final Map<String, JCheckBox> flagCheckBoxes = new HashMap<>();

    public RoomFlagsPanel() {
        super(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        for (Map.Entry<String, List<String>> entry : RoomFlagConstants.ALL_FLAG_GROUPS.entrySet()) {
            String groupTitle = entry.getKey();
            List<String> flags = entry.getValue();

            // Use the reliable JTextPane for wrapping content
            JTextPane textPane = new JTextPane();
            textPane.setEditable(false);
            textPane.setOpaque(true);
            textPane.setBackground(UIManager.getColor("Panel.background"));
            textPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

            Collections.sort(flags);

            for (String flagConstant : flags) {
                String label = flagConstant.replace("_", " ");
                JCheckBox checkBox = new JCheckBox(label);
                flagCheckBoxes.put(flagConstant, checkBox);
                textPane.insertComponent(checkBox);

                try {
                    textPane.getDocument().insertString(textPane.getDocument().getLength(), " ", null);
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }

            // Wrap the JTextPane in a JScrollPane for scrolling
            JScrollPane scrollPane = new JScrollPane(textPane);
            scrollPane.setBorder(null);

            // Add the scroll pane to the tab
            tabbedPane.addTab(groupTitle, scrollPane);
        }

        // Apply our consistent styling to the tabs
        UiHelper.styleTabbedPane(tabbedPane);

        add(tabbedPane, BorderLayout.CENTER);
    }

    public void addChangeListener(ItemListener listener) {
        for (JCheckBox checkBox : flagCheckBoxes.values()) {
            checkBox.addItemListener(listener);
        }
    }

    public Set<String> getSelectedTags() {
        Set<String> selected = new HashSet<>();
        for (Map.Entry<String, JCheckBox> entry : flagCheckBoxes.entrySet()) {
            if (entry.getValue().isSelected()) {
                selected.add(entry.getKey());
            }
        }
        return selected;
    }

    public void setSelectedTags(Set<String> tags) {
        if (tags == null) {
            tags = new HashSet<>();
        }
        for (Map.Entry<String, JCheckBox> entry : flagCheckBoxes.entrySet()) {
            entry.getValue().setSelected(tags.contains(entry.getKey()));
        }
    }
}