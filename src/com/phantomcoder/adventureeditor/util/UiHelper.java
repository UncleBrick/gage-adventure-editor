package com.phantomcoder.adventureeditor.util;

import com.phantomcoder.adventureeditor.constants.FontConstants;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import java.awt.Component;
import java.awt.Font;
import java.net.URL;

public class UiHelper {

    /**
     * A shared, public method for loading icons from the classpath.
     * @param path The path to the icon within the resources/icons directory.
     * @return An ImageIcon, or null if the icon cannot be found.
     */
    public static ImageIcon loadMenuIcon(String path) {
        try {
            URL imageUrl = UiHelper.class.getClassLoader().getResource("icons/" + path);
            if (imageUrl != null) {
                return new ImageIcon(imageUrl);
            }
        } catch (Exception e) {
            System.err.println("Could not load icon: " + path);
        }
        return null;
    }

    /**
     * Applies custom styling to a JTabbedPane.
     * @param tabbedPane The pane to style.
     */
    public static void styleTabbedPane(JTabbedPane tabbedPane) {
        // FIX: Create and apply the new, larger font for the tabs.
        Font tabFont = new Font(
                FontConstants.DEFAULT_FONT_NAME,
                FontConstants.PLAIN_STYLE,
                FontConstants.TAB_FONT_SIZE
        );
        tabbedPane.setFont(tabFont);

        // This is your original styling logic.
        for (int i = 0; i < tabbedPane.getTabCount(); i++) {
            tabbedPane.setToolTipTextAt(i, "Manage " + tabbedPane.getTitleAt(i));
        }
    }

    /**
     * Displays a standardized, always-on-top error dialog.
     * @param parent The parent component for the dialog.
     * @param title The title of the dialog window.
     * @param message The error message to display.
     */
    public static void showErrorDialog(Component parent, String title, String message) {
        JOptionPane optionPane = new JOptionPane(message, JOptionPane.ERROR_MESSAGE);
        JDialog dialog = optionPane.createDialog(parent, title);
        dialog.setAlwaysOnTop(true);
        dialog.setVisible(true);
    }
}