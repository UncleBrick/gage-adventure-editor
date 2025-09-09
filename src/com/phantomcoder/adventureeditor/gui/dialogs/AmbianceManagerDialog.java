package com.phantomcoder.adventureeditor.gui.dialogs;

import com.phantomcoder.adventureeditor.constants.DialogConstants;
import com.phantomcoder.adventureeditor.constants.LayoutConstants;
import com.phantomcoder.adventureeditor.controller.RoomController;
import com.phantomcoder.adventureeditor.model.AmbianceEvent;
import com.phantomcoder.adventureeditor.model.RoomData;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

public class AmbianceManagerDialog extends JDialog {

    private final RoomController roomController;
    private JTable table;
    private DefaultTableModel tableModel;
    private List<AmbianceEvent> events;

    public AmbianceManagerDialog(JFrame parent, RoomController roomController) {
        super(parent, "Ambiance Manager", true);
        this.roomController = roomController;

        String[] columnNames = {"ID", "GUID", "Ambient Text", "Frequency %"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);

        table.getColumnModel().getColumn(0).setPreferredWidth(DialogConstants.AMBIANCE_ID_COL_WIDTH);
        table.getColumnModel().getColumn(1).setPreferredWidth(DialogConstants.AMBIANCE_GUID_COL_WIDTH);
        table.getColumnModel().getColumn(2).setPreferredWidth(DialogConstants.AMBIANCE_TEXT_COL_WIDTH);
        table.getColumnModel().getColumn(3).setPreferredWidth(DialogConstants.AMBIANCE_FREQ_COL_WIDTH);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(DialogConstants.AMBIANCE_DIALOG_SIZE);

        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add...");
        JButton editButton = new JButton("Edit...");
        JButton removeButton = new JButton("Remove");
        JButton applyButton = new JButton("Apply Changes");
        JButton cancelButton = new JButton("Cancel");

        addButton.addActionListener(e -> addEvent());
        editButton.addActionListener(e -> editEvent());
        removeButton.addActionListener(e -> removeEvent());
        applyButton.addActionListener(e -> applyChanges());
        cancelButton.addActionListener(e -> setVisible(false));

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(new JPanel());
        buttonPanel.add(applyButton);
        buttonPanel.add(cancelButton);

        JPanel contentPanel = new JPanel(new BorderLayout(LayoutConstants.PANEL_HGAP, LayoutConstants.PANEL_VGAP));
        contentPanel.setBorder(LayoutConstants.PANEL_PADDING);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(contentPanel);
        pack();
        setLocationRelativeTo(parent);
    }

    public void displayEvents(List<AmbianceEvent> originalEvents) {
        this.events = new ArrayList<>(originalEvents);
        tableModel.setRowCount(0);
        for (AmbianceEvent event : events) {
            tableModel.addRow(new Object[]{event.getId(), event.getGuid(), event.getText(), event.getFrequency()});
        }
    }

    private AddEditAmbianceDialog createDialog(String title) {
        RoomData currentRoom = roomController.getRoomService().getCurrentRoom();
        return new AddEditAmbianceDialog(
                (JFrame) getParent(),
                title,
                currentRoom.getLocationName(),
                currentRoom.getAreaName(),
                currentRoom.getRoomName(),
                this.events // Pass the list of existing events for duplicate checking
        );
    }

    private void addEvent() {
        AddEditAmbianceDialog dialog = createDialog("Add Ambient Text");
        dialog.setEventData(new AmbianceEvent()); // Pass a new, empty event
        dialog.setVisible(true);

        if (dialog.isSaved()) {
            AmbianceEvent newEvent = dialog.getEventData();
            newEvent.setGuid(UUID.randomUUID().toString());

            events.add(newEvent);
            tableModel.addRow(new Object[]{newEvent.getId(), newEvent.getGuid(), newEvent.getText(), newEvent.getFrequency()});
        }
    }

    private void editEvent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            AmbianceEvent selectedEvent = events.get(selectedRow);
            AddEditAmbianceDialog dialog = createDialog("Edit Ambient Text");
            dialog.setEventData(selectedEvent);
            dialog.setVisible(true);

            if (dialog.isSaved()) {
                AmbianceEvent updatedEvent = dialog.getEventData();
                events.set(selectedRow, updatedEvent);
                tableModel.setValueAt(updatedEvent.getId(), selectedRow, 0);
                tableModel.setValueAt(updatedEvent.getGuid(), selectedRow, 1);
                tableModel.setValueAt(updatedEvent.getText(), selectedRow, 2);
                tableModel.setValueAt(updatedEvent.getFrequency(), selectedRow, 3);
            }
        }
    }

    private void removeEvent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            events.remove(selectedRow);
            tableModel.removeRow(selectedRow);
        }
    }

    private void applyChanges() {
        roomController.updateAmbianceData(events);
        setVisible(false);
    }
}