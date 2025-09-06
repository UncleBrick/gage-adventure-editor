package com.phantomcoder.adventureeditor.model;

import java.util.Objects;

/**
 * A data model representing a single piece of ambient text that can occur in a room.
 */
public class AmbianceEvent {

    private String id;
    private String guid; // FIX: The new globally unique identifier field.
    private String text;
    private int frequency;

    /**
     * Default constructor for JSON deserialization.
     */
    public AmbianceEvent() {
    }

    /**
     * Convenience constructor for creating a new event.
     * @param id The human-readable ID.
     * @param text The descriptive text of the event.
     * @param frequency The chance (out of 100) of this event occurring.
     */
    public AmbianceEvent(String id, String text, int frequency) {
        this.id = id;
        this.text = text;
        this.frequency = frequency;
    }

    // --- Getters and Setters ---

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }

    // --- equals() and hashCode() ---

    /**
     * FIX: Updated to use the GUID for equality checks if available, as it's the
     * only truly unique identifier for an event.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AmbianceEvent that = (AmbianceEvent) o;
        // If GUIDs exist, they are the definitive source of truth for equality.
        if (guid != null && that.guid != null) {
            return guid.equals(that.guid);
        }
        // Fallback for objects that may not have GUIDs yet (e.g., during creation).
        return frequency == that.frequency &&
                Objects.equals(id, that.id) &&
                Objects.equals(text, that.text);
    }

    /**
     * FIX: Updated to use the GUID for the hash code if available.
     */
    @Override
    public int hashCode() {
        // If a GUID exists, it should be used for the hash code.
        if (guid != null) {
            return Objects.hash(guid);
        }
        // Fallback for objects without a GUID.
        return Objects.hash(id, text, frequency);
    }
}