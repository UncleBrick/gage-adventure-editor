package com.phantomcoder.adventureeditor.model;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * A data model representing a single piece of ambient text that can occur in a room.
 */
public class AmbianceEvent {

    private String id;
    private String guid;
    private String text;
    private int frequency;
    private Set<String> flags; // New field

    public AmbianceEvent() {
        this.flags = new HashSet<>();
    }

    public AmbianceEvent(String id, String text, int frequency) {
        this.id = id;
        this.text = text;
        this.frequency = frequency;
        this.flags = new HashSet<>();
    }

    // --- Getters and Setters ---

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getGuid() { return guid; }
    public void setGuid(String guid) { this.guid = guid; }
    public String getText() { return text; }
    public void setText(String text) { this.text = text; }
    public int getFrequency() { return frequency; }
    public void setFrequency(int frequency) { this.frequency = frequency; }
    public Set<String> getFlags() { return flags; }
    public void setFlags(Set<String> flags) { this.flags = flags; }

    // --- equals() and hashCode() ---

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AmbianceEvent that = (AmbianceEvent) o;

        // GUID is the primary unique identifier.
        if (guid != null && that.guid != null && !guid.isEmpty() && !that.guid.isEmpty()) {
            return guid.equals(that.guid);
        }

        // Fallback for objects without a GUID, now includes flags.
        return frequency == that.frequency &&
                Objects.equals(id, that.id) &&
                Objects.equals(text, that.text) &&
                Objects.equals(flags, that.flags);
    }

    @Override
    public int hashCode() {
        if (guid != null && !guid.isEmpty()) {
            return Objects.hash(guid);
        }
        return Objects.hash(id, text, frequency, flags);
    }
}