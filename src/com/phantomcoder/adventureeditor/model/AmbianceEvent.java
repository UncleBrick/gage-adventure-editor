package com.phantomcoder.adventureeditor.model;

/**
 * Represents a single ambient text event with its unique ID,
 * display text, and frequency of occurrence.
 */
public class AmbianceEvent {

    private String id;
    private String text;
    private int frequency;

    public AmbianceEvent(String id, String text, int frequency) {
        this.id = id;
        this.text = text;
        this.frequency = frequency;
    }

    // --- Getters ---
    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public int getFrequency() {
        return frequency;
    }

    // --- Setters ---
    public void setId(String id) {
        this.id = id;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}