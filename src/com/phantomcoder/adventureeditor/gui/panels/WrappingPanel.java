package com.phantomcoder.adventureeditor.gui.panels;

import javax.swing.JPanel;
import javax.swing.Scrollable;
import java.awt.Dimension;
import java.awt.Rectangle;

/**
 * A special JPanel that implements the Scrollable interface. This is used to
 * force a panel to wrap its width when placed inside a JScrollPane, preventing
 * horizontal scrolling and enabling components like FlowLayout to wrap correctly.
 */
public class WrappingPanel extends JPanel implements Scrollable {

    public WrappingPanel() {
        super();
    }

    @Override
    public Dimension getPreferredScrollableViewportSize() {
        return getPreferredSize();
    }

    @Override
    public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 16; // The default scroll speed
    }

    @Override
    public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
        return 16; // The default scroll speed
    }

    @Override
    public boolean getScrollableTracksViewportWidth() {
        // This is the magic method. Returning true tells the JScrollPane
        // to force this panel to match its width.
        return true;
    }

    @Override
    public boolean getScrollableTracksViewportHeight() {
        // Returning false allows the panel to grow vertically,
        // which enables the vertical scrollbar when needed.
        return false;
    }
}