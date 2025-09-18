package com.phantomcoder.adventureeditor.model;

import java.util.Objects;
import java.util.UUID;

/**
 * A data model representing a single school of magic. This can be one of the
 * default schools or a custom school created by the user.
 */
public class MagicSchoolData {

    private String guid;
    private String name;
    private String description;
    private String opposingSchoolGuid;

    /**
     * Default constructor.
     */
    public MagicSchoolData() {
        // Generate a new unique ID by default.
        this.guid = UUID.randomUUID().toString();
    }

    /**
     * Constructs a new MagicSchoolData object with specified properties.
     *
     * @param name               The display name of the magic school.
     * @param description        A short description of the school.
     * @param opposingSchoolGuid The GUID of the opposing school, or null if none.
     */
    public MagicSchoolData(String name, String description, String opposingSchoolGuid) {
        this.guid = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.opposingSchoolGuid = opposingSchoolGuid;
    }

    // --- Getters and Setters ---

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getOpposingSchoolGuid() {
        return opposingSchoolGuid;
    }

    public void setOpposingSchoolGuid(String opposingSchoolGuid) {
        this.opposingSchoolGuid = opposingSchoolGuid;
    }

    // --- equals() and hashCode() for object comparison ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MagicSchoolData that = (MagicSchoolData) o;
        return Objects.equals(guid, that.guid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(guid);
    }
}
