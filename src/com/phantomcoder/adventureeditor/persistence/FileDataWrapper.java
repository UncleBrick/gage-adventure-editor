package com.phantomcoder.adventureeditor.persistence;

/**
 * A generic wrapper class used to create a standard, self-describing format for
 * all JSON save files. It envelops the core data with metadata like a file type
 * and version number.
 *
 * @param <T> The type of the data being wrapped (e.g., RoomData, List<AmbianceEvent>).
 */
public class FileDataWrapper<T> {

    private final String file_type;
    private final String version;
    private final T data;

    /**
     * Constructs a new FileDataWrapper.
     * @param file_type A human-readable string describing the data type.
     * @param version The version of the data format.
     * @param data The actual data payload to be saved.
     */
    public FileDataWrapper(String file_type, String version, T data) {
        this.file_type = file_type;
        this.version = version;
        this.data = data;
    }

    /**
     * Gets the file type metadata.
     * @return The file type string.
     */
    public String getFileType() {
        return file_type;
    }

    /**
     * Gets the data format version.
     * @return The version string.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Gets the core data payload.
     * @return The wrapped data of type T.
     */
    public T getData() {
        return data;
    }
}