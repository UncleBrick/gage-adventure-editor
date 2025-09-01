package com.phantomcoder.adventureeditor.util;

import com.phantomcoder.adventureeditor.constants.FontConstants;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;

public final class UiHelper {

    private UiHelper() { /* Prevent instantiation */ }

    public static JPanel createFlagPanel(String title, JCheckBox... checkBoxes) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(new TitledBorder(title));
        for (JCheckBox checkBox : checkBoxes) {
            panel.add(checkBox);
        }
        return panel;
    }

    public static void showErrorDialog(Component parent, String title, String message) {
        JOptionPane.showMessageDialog(parent, message, title, JOptionPane.ERROR_MESSAGE);
    }

    /**
     * Styles a JTabbedPane with a consistent font and uniform tab widths.
     * @param tabbedPane The JTabbedPane to style.
     */
    public static void styleTabbedPane(JTabbedPane tabbedPane) {
        // Set a consistent font for the tabs
        tabbedPane.setFont(new Font(
                FontConstants.DEFAULT_FONT_NAME,
                FontConstants.PLAIN_STYLE,
                FontConstants.TAB_FONT_SIZE
        ));

        // Calculate the widest tab and make all tabs that width
        int maxTabWidth = 0;
        FontMetrics metrics = tabbedPane.getFontMetrics(tabbedPane.getFont());
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            int currentWidth = metrics.stringWidth(tabbedPane.getTitleAt(i)) + 40; // Add padding
            if (currentWidth > maxTabWidth) {
                maxTabWidth = currentWidth;
            }
        }

        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            String title = tabbedPane.getTitleAt(i);
            JLabel label = new JLabel(title, SwingConstants.CENTER);
            JPanel tab_panel = new JPanel(new BorderLayout());
            tab_panel.setOpaque(false);
            tab_panel.setPreferredSize(new Dimension(maxTabWidth, metrics.getHeight() + 10)); // Add padding
            tab_panel.add(label, BorderLayout.CENTER);
            tabbedPane.setTabComponentAt(i, tab_panel);
        }
    }
}