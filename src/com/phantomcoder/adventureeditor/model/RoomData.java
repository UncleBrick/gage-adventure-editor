package com.phantomcoder.adventureeditor.model;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class RoomData {

    private String locationName;
    private String areaName;
    private String subAreaName; // NEW
    private String roomName;
    private String shortDescription;
    private String longDescription;

    @SerializedName("roomTags")
    private Set<String> tags;

    // --- Transient data lists for parallel files ---
    private transient List<AmbianceEvent> ambianceEvents;
    private transient List<ObjectData> objects;
    private transient List<NpcData> npcs;
    private transient List<CommandData> commands;
    private transient List<ExitData> exits;
    private transient List<MagicData> magic;
    private transient List<QuestData> quests;

    /**
     * Default constructor used by the JSON loader.
     */
    public RoomData() {
        this.locationName = "";
        this.areaName = "";
        this.subAreaName = ""; // NEW
        this.tags = new HashSet<>();
        this.ambianceEvents = new ArrayList<>();
        this.objects = new ArrayList<>();
        this.npcs = new ArrayList<>();
        this.commands = new ArrayList<>();
        this.exits = new ArrayList<>();
        this.magic = new ArrayList<>();
        this.quests = new ArrayList<>();
    }

    // --- Getters and Setters ---

    public String getLocationName() { return locationName; }
    public void setLocationName(String locationName) { this.locationName = locationName; }
    public String getAreaName() { return areaName; }
    public void setAreaName(String areaName) { this.areaName = areaName; }
    public String getSubAreaName() { return subAreaName; } // NEW
    public void setSubAreaName(String subAreaName) { this.subAreaName = subAreaName; } // NEW
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
    public List<ObjectData> getObjects() { return objects; }
    public void setObjects(List<ObjectData> objects) { this.objects = objects; }
    public List<NpcData> getNpcs() { return npcs; }
    public void setNpcs(List<NpcData> npcs) { this.npcs = npcs; }
    public List<CommandData> getCommands() { return commands; }
    public void setCommands(List<CommandData> commands) { this.commands = commands; }
    public List<ExitData> getExits() { return exits; }
    public void setExits(List<ExitData> exits) { this.exits = exits; }
    public List<MagicData> getMagic() { return magic; }
    public void setMagic(List<MagicData> magic) { this.magic = magic; }
    public List<QuestData> getQuests() { return quests; }
    public void setQuests(List<QuestData> quests) { this.quests = quests; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomData roomData = (RoomData) o;
        return Objects.equals(locationName, roomData.locationName) &&
                Objects.equals(areaName, roomData.areaName) &&
                Objects.equals(subAreaName, roomData.subAreaName) && // NEW
                Objects.equals(roomName, roomData.roomName) &&
                Objects.equals(shortDescription, roomData.shortDescription) &&
                Objects.equals(longDescription, roomData.longDescription) &&
                Objects.equals(tags, roomData.tags) &&
                Objects.equals(ambianceEvents, roomData.ambianceEvents) &&
                Objects.equals(objects, roomData.objects) &&
                Objects.equals(npcs, roomData.npcs) &&
                Objects.equals(commands, roomData.commands) &&
                Objects.equals(exits, roomData.exits) &&
                Objects.equals(magic, roomData.magic) &&
                Objects.equals(quests, roomData.quests);
    }

    @Override
    public int hashCode() {
        return Objects.hash(locationName, areaName, subAreaName, roomName,
                shortDescription, longDescription, tags, ambianceEvents, objects,
                npcs, commands, exits, magic, quests);
    }
}

