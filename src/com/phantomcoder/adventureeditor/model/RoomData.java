package com.phantomcoder.adventureeditor.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RoomData {

    private String locationName;
    private String areaName;
    private String roomName;
    private String shortDescription;
    private String longDescription;

    // This annotation tells the JSON parser to use the key "roomTags"
    // for this field, which matches your JSON file and naming convention.
    @SerializedName("roomTags")
    private Set<String> tags;

    private List<AmbianceEvent> ambianceEvents;

    /**
     * Default constructor used by the JSON loader.
     */
    public RoomData() {
        this.locationName = "";
        this.areaName = "";
        this.tags = new HashSet<>();
        this.ambianceEvents = new ArrayList<>();
    }

    /**
     * FIX: Added the parameterized constructor required by your RoomCreationService.
     * Note: The 'fileName' parameter from your original service was removed,
     * as the RoomData model doesn't store the file name itself.
     */
    public RoomData(String locationName, String areaName, String roomName,
                    String shortDescription, String longDescription, Set<String> tags) {
        this.locationName = locationName;
        this.areaName = areaName;
        this.roomName = roomName;
        this.shortDescription = shortDescription;
        this.longDescription = longDescription;
        this.tags = tags;
        this.ambianceEvents = new ArrayList<>(); // Initialize to an empty list
    }

    // --- Getters and Setters ---

    public String getLocationName() { return locationName; }
    public void setLocationName(String locationName) { this.locationName = locationName; }
    public String getAreaName() { return areaName; }
    public void setAreaName(String areaName) { this.areaName = areaName; }
    public String getRoomName() { return roomName; }
    public void setRoomName(String roomName) { this.roomName = roomName; }
    public String getShortDescription() { return shortDescription; }
    public void setShortDescription(String shortDescription) { this.shortDescription = shortDescription; }
    public String getLongDescription() { return longDescription; }
    public void setLongDescription(String longDescription) { this.longDescription = longDescription; }
    public Set<String> getTags() { return tags; }
    public void setTags(Set<String> tags) { this.tags = tags; }
    public List<AmbianceEvent> getAmbianceEvents() { return ambianceEvents; }
    public void setAmbianceEvents(List<AmbianceEvent> ambianceEvents) { this.ambianceEvents = ambianceEvents; }
}