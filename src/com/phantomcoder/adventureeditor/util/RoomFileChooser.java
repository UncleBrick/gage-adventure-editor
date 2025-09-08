package com.phantomcoder.adventureeditor.util;

import com.phantomcoder.adventureeditor.constants.DataConstants;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class RoomFileChooser {

    private static final FileNameExtensionFilter JSON_FILE_FILTER = new FileNameExtensionFilter("JSON Files (*.json)", "json");
    private static final String DIALOG_TITLE = "Load Room File";

    public static Optional<Path> showLoadRoomDialog(JFrame parent) {
        Path startPath;

        try {
            // The path now resolves to the 'data' directory instead of 'rooms'.
            startPath = PathUtil.getAppBaseDirectory()
                    .resolve(DataConstants.RESOURCES_DIRECTORY_NAME)
                    .resolve(DataConstants.DATA_DIRECTORY_NAME);

            if (Files.notExists(startPath)) {
                Files.createDirectories(startPath);
            }
        } catch (IOException e) {
            System.err.println("Could not create or find default data directory.");
            e.printStackTrace();
            startPath = Path.of(System.getProperty("user.home"));
        }

        JFileChooser fileChooser = new JFileChooser(startPath.toFile());
        fileChooser.setDialogTitle(DIALOG_TITLE);
        fileChooser.setFileFilter(JSON_FILE_FILTER);
        fileChooser.setAcceptAllFileFilterUsed(false);

        // A loop to ensure the dialog stays open until a valid file is selected or the user cancels.
        while (true) {
            int result = fileChooser.showOpenDialog(parent);

            // Check if the user approved the selection
            if (result == JFileChooser.APPROVE_OPTION) {
                Path selectedPath = fileChooser.getSelectedFile().toPath();
                File selectedFile = selectedPath.toFile();

                // Validation 1: Check the file extension
                if (!JSON_FILE_FILTER.accept(selectedFile)) {
                    UiHelper.showErrorDialog(parent, "Invalid File Type", "Please select a JSON File (*.json).");
                    continue; // Loop again, keeping the dialog open
                }

                // Validation 2: Check the parent directory name
                Path parentDir = selectedPath.getParent();
                if (parentDir == null || !parentDir.getFileName().toString().equals(DataConstants.ROOMS_DIRECTORY_NAME)) {
                    String message = "Please select a valid room file from within a '" + DataConstants.ROOMS_DIRECTORY_NAME + "' folder.";
                    UiHelper.showErrorDialog(parent, "Invalid File Location", message);
                    continue; // Loop again
                }

                // If both checks pass, return the valid path and exit the loop
                return Optional.of(selectedPath);
            } else {
                // The user canceled the dialog, so we return an empty Optional and exit
                return Optional.empty();
            }
        }
    }
}