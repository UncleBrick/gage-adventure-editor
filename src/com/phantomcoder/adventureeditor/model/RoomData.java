package com.phantomcoder.adventureeditor.model;

import java.util.HashSet;
import java.util.Set;

public class RoomData {
    private String locationName;
    private String areaName;
    private String filename;
    private String roomName;
    private String shortDescription;
    private String longDescription;
    private Set<String> roomTags;

    public RoomData(String locationName, String areaName, String filename, String roomName,
                    String shortDescription, String longDescription, Set<String> roomTags) {
        this.locationName = locationName;
        this.areaName = areaName;
        this.filename = filename;
        this.roomName = roomName;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.roomTags = (roomTags != null) ? roomTags : new HashSet<>();
    }

    // --- Getters ---
    public String getLocationName() { return locationName; }
    public String getAreaName() { return areaName; }
    public String getFilename() { return filename; }
    public String getRoomName() { return roomName; }
    public String getShortDescription() { return shortDescription; }
    public String getLongDescription() { return longDescription; }
    public Set<String> getRoomTags() { return roomTags; }

    // --- NEW SETTER METHOD ---
    public void setRoomTags(Set<String> roomTags) {
        this.roomTags = roomTags;
    }
}