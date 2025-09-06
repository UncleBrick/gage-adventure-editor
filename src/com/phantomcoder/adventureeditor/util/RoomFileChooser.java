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
            // FIX: The path now resolves to the 'data' directory instead of 'rooms'.
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

        int result = fileChooser.showOpenDialog(parent);
        if (result == JFileChooser.APPROVE_OPTION) {
            Path selectedPath = fileChooser.getSelectedFile().toPath();

            if (!JSON_FILE_FILTER.accept(selectedPath.toFile())) {
                UiHelper.showErrorDialog(parent, "Invalid File Type", "Please select a JSON File (*.json).");
                return Optional.empty();
            }

            Path parentDir = selectedPath.getParent();
            if (parentDir == null || !parentDir.getFileName().toString().equals(DataConstants.ROOMS_DIRECTORY_NAME)) {
                String message = "Please select a valid room file from within a '" + DataConstants.ROOMS_DIRECTORY_NAME + "' folder.";
                UiHelper.showErrorDialog(parent, "Invalid File Location", message);
                return Optional.empty();
            }

            return Optional.of(selectedPath);
        }

        return Optional.empty();
    }
}