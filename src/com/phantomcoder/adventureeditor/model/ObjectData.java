package com.phantomcoder.adventureeditor.model;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a single object or item in the game world. It holds all
 * properties for the object.
 */
public class ObjectData {

    private final String name;
    private final String shortDescription;
    private final String longDescription; // NEW FIELD
    private final Set<String> flags;
    private final String keyIdToUnlock;

    // UPDATED CONSTRUCTOR
    public ObjectData(String name, String shortDescription, String longDescription, Set<String> flags, String keyIdToUnlock) {
        this.name = name;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription; // NEW
        this.flags = (flags != null) ? flags : new HashSet<>();
        this.keyIdToUnlock = keyIdToUnlock;
    }

    // --- Getters ---
    public String getName() { return name; }
    public String getShortDescription() { return shortDescription; }
    public String getLongDescription() { return longDescription; } // NEW GETTER
    public Set<String> getFlags() { return flags; }
    public String getKeyIdToUnlock() { return keyIdToUnlock; }


    /**
     * Overriding toString() is a special trick for JTree.
     * The JTree will automatically call this method to get the text to display
     * for each node in the tree.
     * @return The name of the object.
     */
    @Override
    public String toString() {
        return this.name;
    }
}