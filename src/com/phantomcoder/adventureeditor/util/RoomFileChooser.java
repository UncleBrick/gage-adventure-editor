package com.phantomcoder.adventureeditor.util;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Component;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

public class RoomFileChooser {

    private static final FileNameExtensionFilter JSON_FILE_FILTER = new FileNameExtensionFilter("JSON Files (*.json)", "json");
    private static final String DIALOG_TITLE = "Load File";
    // --- PATH SEGMENT UPDATED ---
    private static final String RESOURCES_RELATIVE_PATH_SEGMENT = Paths.get("resources", "data").toString();

    private RoomFileChooser() {
        // Private constructor to prevent instantiation of this utility class
    }

    public static Optional<Path> showLoadRoomDialog(Component parentComponent) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(DIALOG_TITLE);

        fileChooser.addChoosableFileFilter(JSON_FILE_FILTER);
        fileChooser.setFileFilter(JSON_FILE_FILTER);
        fileChooser.setAcceptAllFileFilterUsed(false);

        Path initialDirectory = PathUtil.getAppBaseDirectory().resolve(RESOURCES_RELATIVE_PATH_SEGMENT).toAbsolutePath();

        if (Files.exists(initialDirectory) && Files.isDirectory(initialDirectory)) {
            fileChooser.setCurrentDirectory(initialDirectory.toFile());
        } else {
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        }

        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

        int userSelection = fileChooser.showOpenDialog(parentComponent);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            if (JSON_FILE_FILTER.accept(selectedFile)) {
                return Optional.of(selectedFile.toPath());
            } else {
                JOptionPane.showMessageDialog(parentComponent, "Please select a JSON File (*.json).", "Invalid File Type", JOptionPane.WARNING_MESSAGE);
                return Optional.empty();
            }
        } else {
            return Optional.empty();
        }
    }
}