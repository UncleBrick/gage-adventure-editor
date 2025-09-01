package com.phantomcoder.adventureeditor.gui.panels;

import com.phantomcoder.adventureeditor.constants.LayoutConstants;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.GridLayout;

@SuppressWarnings("serial")
public class ActionButtonsPanel extends JPanel {

    public ActionButtonsPanel(Action newAction, Action loadAction, Action saveAction) {
        setLayout(new GridLayout(
                LayoutConstants.ACTIONS_PANEL_ROWS,
                LayoutConstants.ACTIONS_PANEL_COLS,
                LayoutConstants.PANEL_HGAP,
                LayoutConstants.PANEL_VGAP
        ));
        setBorder(BorderFactory.createTitledBorder("Actions"));

        JButton newRoomButton = new JButton(newAction);
        newRoomButton.setText("New Room");

        JButton loadRoomButton = new JButton(loadAction);
        loadRoomButton.setText("Load Room");

        JButton saveRoomButton = new JButton(saveAction);
        saveRoomButton.setText("Save Room");

        add(newRoomButton);
        add(loadRoomButton);
        add(saveRoomButton);
    }
}