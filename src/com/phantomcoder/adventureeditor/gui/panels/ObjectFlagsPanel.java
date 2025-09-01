package com.phantomcoder.adventureeditor.gui.panels;

import com.phantomcoder.adventureeditor.constants.ObjectFlagConstants;
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
 * A self-contained panel that dynamically creates and manages all JCheckBoxes
 * for object flags using a JTabbedPane for organization.
 */
public class ObjectFlagsPanel extends JPanel {

    private final Map<String, JCheckBox> flagCheckBoxes = new HashMap<>();

    public ObjectFlagsPanel() {
        super(new BorderLayout());
        setBorder(BorderFactory.createTitledBorder("Object Flags"));

        JTabbedPane tabbedPane = new JTabbedPane();

        // --- NEW ---
        // Set the policy to use scroll buttons instead of wrapping tabs
        tabbedPane.setTabLayoutPolicy(JTabbedPane.SCROLL_TAB_LAYOUT);
        // --- END NEW ---

        for (Map.Entry<String, List<String>> entry : ObjectFlagConstants.ALL_FLAG_GROUPS.entrySet()) {
            String groupTitle = entry.getKey();
            List<String> flags = entry.getValue();

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
            JScrollPane scrollPane = new JScrollPane(textPane);
            scrollPane.setBorder(null);
            tabbedPane.addTab(groupTitle, scrollPane);
        }

        UiHelper.styleTabbedPane(tabbedPane);
        add(tabbedPane, BorderLayout.CENTER);
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

    public void setSelectedFlags(Set<String> tags) {
        if (tags == null) {
            tags = new HashSet<>();
        }
        for (Map.Entry<String, JCheckBox> entry : flagCheckBoxes.entrySet()) {
            entry.getValue().setSelected(tags.contains(entry.getKey()));
        }
    }
}