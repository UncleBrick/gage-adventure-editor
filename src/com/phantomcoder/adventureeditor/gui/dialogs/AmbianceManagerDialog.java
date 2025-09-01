package com.phantomcoder.adventureeditor.gui.dialogs;

import com.phantomcoder.adventureeditor.constants.FrameConstants;
import com.phantomcoder.adventureeditor.constants.LayoutConstants;
import com.phantomcoder.adventureeditor.controller.RoomController;
import com.phantomcoder.adventureeditor.model.AmbianceEvent;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class AmbianceManagerDialog extends JDialog {

    private JTable ambianceTable;
    private DefaultTableModel tableModel;
    private JButton addButton, editButton, removeButton, applyButton, cancelButton;
    private List<AmbianceEvent> ambianceEvents;
    private final RoomController roomController;
    private boolean isDirty = false;

    public AmbianceManagerDialog(Frame owner, RoomController controller) {
        super(owner, "Ambiance Manager", false);
        this.roomController = controller;

        String[] columnNames = {"ID", "Ambient Text", "Frequency %"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        ambianceTable = new JTable(tableModel);
        ambianceTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane tableScrollPane = new JScrollPane(ambianceTable);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(6, 1, LayoutConstants.PANEL_HGAP, LayoutConstants.PANEL_VGAP));
        addButton = new JButton("Add...");
        editButton = new JButton("Edit...");
        removeButton = new JButton("Remove");
        applyButton = new JButton("Apply Changes");
        cancelButton = new JButton("Cancel");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(removeButton);
        buttonPanel.add(new JSeparator());
        buttonPanel.add(applyButton);
        buttonPanel.add(cancelButton);

        JPanel eastPanel = new JPanel(new BorderLayout());
        eastPanel.add(buttonPanel, BorderLayout.NORTH);

        getContentPane().setLayout(new BorderLayout(LayoutConstants.PANEL_HGAP, LayoutConstants.PANEL_VGAP));
        JComponent contentPane = (JComponent) getContentPane();
        contentPane.setBorder(LayoutConstants.PANEL_PADDING);
        contentPane.add(tableScrollPane, BorderLayout.CENTER);
        contentPane.add(eastPanel, BorderLayout.EAST);

        addButton.addActionListener(e -> handleAdd());
        editButton.addActionListener(e -> handleEdit());
        removeButton.addActionListener(e -> handleRemove());
        applyButton.addActionListener(e -> handleApply());
        cancelButton.addActionListener(e -> handleCancel());

        ambianceTable.getSelectionModel().addListSelectionListener(e -> updateButtonStates());
        tableModel.addTableModelListener(e -> {
            isDirty = true;
            updateButtonStates();
        });

        setSize(FrameConstants.AMBIANCE_DIALOG_WIDTH, FrameConstants.AMBIANCE_DIALOG_HEIGHT);
        setLocationRelativeTo(owner);
        updateButtonStates();
    }

    private void handleAdd() {
        AddEditAmbianceDialog addDialog = new AddEditAmbianceDialog(this);
        addDialog.setVisible(true);
        AmbianceEvent newEvent = addDialog.getAmbianceEvent();
        if (newEvent != null) {
            ambianceEvents.add(newEvent);
            tableModel.addRow(new Object[]{newEvent.getId(), newEvent.getText(), newEvent.getFrequency()});
        }
    }

    private void handleEdit() {
        int selectedRow = ambianceTable.getSelectedRow();
        if (selectedRow == -1) return; // Should not happen if button is enabled, but good practice

        AmbianceEvent eventToEdit = ambianceEvents.get(selectedRow);
        AddEditAmbianceDialog editDialog = new AddEditAmbianceDialog(this, eventToEdit);
        editDialog.setVisible(true);

        AmbianceEvent updatedEvent = editDialog.getAmbianceEvent();
        if (updatedEvent != null) {
            ambianceEvents.set(selectedRow, updatedEvent);
            tableModel.setValueAt(updatedEvent.getText(), selectedRow, 1);
            tableModel.setValueAt(updatedEvent.getFrequency(), selectedRow, 2);
        }
    }

    private void handleRemove() {
        int selectedRow = ambianceTable.getSelectedRow();
        if (selectedRow == -1) return;

        int choice = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to remove this ambient text?",
                "Confirm Removal",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (choice == JOptionPane.YES_OPTION) {
            ambianceEvents.remove(selectedRow);
            tableModel.removeRow(selectedRow);
        }
    }

    private void handleApply() {
        roomController.updateAmbianceData(this.ambianceEvents);
        isDirty = false;
        updateButtonStates();
        setVisible(false);
    }

    private void handleCancel() {
        setVisible(false);
    }

    private void updateButtonStates() {
        boolean isRowSelected = ambianceTable.getSelectedRow() != -1;
        editButton.setEnabled(isRowSelected);
        removeButton.setEnabled(isRowSelected);
        applyButton.setEnabled(isDirty);
    }

    public void displayEvents(List<AmbianceEvent> events) {
        // Stop listening to table changes while we programmatically change it
        TableModelListener[] listeners = ((DefaultTableModel) tableModel).getTableModelListeners();
        for (TableModelListener l : listeners) { tableModel.removeTableModelListener(l); }

        tableModel.setRowCount(0);
        this.ambianceEvents = new ArrayList<>(events);
        for (AmbianceEvent event : this.ambianceEvents) {
            tableModel.addRow(new Object[]{event.getId(), event.getText(), event.getFrequency()});
        }

        // Add the listener back after we're done
        for (TableModelListener l : listeners) { tableModel.addTableModelListener(l); }

        isDirty = false;
        updateButtonStates();
    }
}