package com.phantomcoder.adventureeditor.gui.dialogs;

import com.phantomcoder.adventureeditor.constants.LayoutConstants;
import com.phantomcoder.adventureeditor.controller.RoomController;
import com.phantomcoder.adventureeditor.model.AmbianceEvent;
import com.phantomcoder.adventureeditor.model.RoomData;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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

        String[] columnNames = {"ID", "GUID", "Ambient Text", "Frequency %", "Flags"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        table = new JTable(tableModel);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getTableHeader().setReorderingAllowed(false);

        table.getColumnModel().getColumn(0).setPreferredWidth(250);
        table.getColumnModel().getColumn(1).setPreferredWidth(220);
        table.getColumnModel().getColumn(2).setPreferredWidth(250);
        table.getColumnModel().getColumn(3).setPreferredWidth(80);
        table.getColumnModel().getColumn(4).setPreferredWidth(150);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new java.awt.Dimension(1000, 300));

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
            tableModel.addRow(new Object[]{event.getId(), event.getGuid(), event.getText(), event.getFrequency(), formatFlags(event.getFlags())});
        }
    }

    private String formatFlags(Set<String> flags) {
        if (flags == null || flags.isEmpty()) {
            return "";
        }
        return String.join(", ", flags);
    }

    private AddEditAmbianceDialog createDialog(String title) {
        return new AddEditAmbianceDialog((JFrame) getParent(), title);
    }

    private void addEvent() {
        AddEditAmbianceDialog dialog = createDialog("Add Ambient Text");
        RoomData currentRoom = roomController.getRoomService().getCurrentRoom();

        dialog.setEventData(new AmbianceEvent(),
                currentRoom.getLocationName(),
                currentRoom.getAreaName(),
                currentRoom.getRoomName(),
                this.events);

        dialog.setVisible(true);

        if (dialog.isSaved()) {
            AmbianceEvent newEvent = dialog.getEventData();
            if (newEvent.getGuid() == null || newEvent.getGuid().isEmpty()) {
                newEvent.setGuid(UUID.randomUUID().toString());
            }
            events.add(newEvent);
            tableModel.addRow(new Object[]{newEvent.getId(), newEvent.getGuid(), newEvent.getText(), newEvent.getFrequency(), formatFlags(newEvent.getFlags())});
        }
    }

    private void editEvent() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow >= 0) {
            AmbianceEvent selectedEvent = events.get(selectedRow);
            AddEditAmbianceDialog dialog = createDialog("Edit Ambient Text");
            RoomData currentRoom = roomController.getRoomService().getCurrentRoom();

            dialog.setEventData(selectedEvent,
                    currentRoom.getLocationName(),
                    currentRoom.getAreaName(),
                    currentRoom.getRoomName(),
                    this.events);

            dialog.setVisible(true);

            if (dialog.isSaved()) {
                AmbianceEvent updatedEvent = dialog.getEventData();
                events.set(selectedRow, updatedEvent);
                tableModel.setValueAt(updatedEvent.getId(), selectedRow, 0);
                tableModel.setValueAt(updatedEvent.getGuid(), selectedRow, 1);
                tableModel.setValueAt(updatedEvent.getText(), selectedRow, 2);
                tableModel.setValueAt(updatedEvent.getFrequency(), selectedRow, 3);
                tableModel.setValueAt(formatFlags(updatedEvent.getFlags()), selectedRow, 4);
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