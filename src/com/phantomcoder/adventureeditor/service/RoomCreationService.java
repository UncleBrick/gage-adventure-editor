package com.phantomcoder.adventureeditor.service;

import com.phantomcoder.adventureeditor.model.RoomData;
import com.phantomcoder.adventureeditor.validation.RoomValidator;
import java.util.Set;

/**
 * A service responsible for the business logic of creating new RoomData objects.
 */
public class RoomCreationService {

    private final RoomValidator validator = new RoomValidator();

    /**
     * Creates a new RoomData object after populating and validating it.
     *
     * @param location The location name.
     * @param area The area name.
     * @param roomName The room's proper name.
     * @param shortDesc The short description.
     * @param longDesc The long description.
     * @param tags The set of flags.
     * @return A populated and validated RoomData object.
     * @throws IllegalArgumentException if any validation fails.
     */
    public RoomData createRoom(String location, String area, String roomName,
                               String shortDesc, String longDesc, Set<String> tags) {

        // Step 1: Create an empty RoomData object.
        RoomData newRoom = new RoomData();

        // Step 2: Use the public setter methods to populate the object's fields.
        newRoom.setLocationName(location);
        newRoom.setAreaName(area);
        newRoom.setRoomName(roomName);
        newRoom.setShortDescription(shortDesc);
        newRoom.setLongDescription(longDesc);
        newRoom.setTags(tags);

        // Step 3: Validate the completed object.
        validator.validate(newRoom);

        // Step 4: Return the validated object.
        return newRoom;
    }
}