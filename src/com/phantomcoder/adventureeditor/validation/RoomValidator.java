package com.phantomcoder.adventureeditor.validation;

import com.phantomcoder.adventureeditor.constants.FieldDefaults; // Assuming you move limits here
import com.phantomcoder.adventureeditor.model.RoomData;
import java.util.ArrayList;
import java.util.List;

public class RoomValidator {

    public List<String> validate(RoomData room) {
        List<String> errors = new ArrayList<>();

        if (room == null) {
            errors.add("Room data cannot be null.");
            return errors;
        }

        // Validate Location Name
        if (room.getLocationName() == null || room.getLocationName().trim().isEmpty()) {
            errors.add("Location Name cannot be empty.");
        }

        // Validate Area Name
        if (room.getAreaName() == null || room.getAreaName().trim().isEmpty()) {
            errors.add("Area Name cannot be empty.");
        }

        // Validate Room Name
        if (room.getRoomName() == null || room.getRoomName().trim().isEmpty()) {
            errors.add("Room Name cannot be empty.");
        } else if (room.getRoomName().trim().length() > FieldDefaults.MAX_ROOM_NAME_CHARS) {
            errors.add("Room Name exceeds the maximum length of " + FieldDefaults.MAX_ROOM_NAME_CHARS + " characters.");
        }

        // Validate Short Description
        if (room.getShortDescription() == null || room.getShortDescription().trim().isEmpty()) {
            errors.add("Short Description cannot be empty.");
        } else if (room.getShortDescription().trim().length() > FieldDefaults.MAX_SHORT_DESC_CHARS) {
            errors.add("Short Description exceeds the maximum length of " + FieldDefaults.MAX_SHORT_DESC_CHARS + " characters.");
        }

        // Validate Long Description
        if (room.getLongDescription() == null || room.getLongDescription().trim().isEmpty()) {
            errors.add("Long Description cannot be empty.");
        } else if (room.getLongDescription().trim().length() > FieldDefaults.MAX_LONG_DESC_CHARS) {
            errors.add("Long Description exceeds the maximum length of " + FieldDefaults.MAX_LONG_DESC_CHARS + " characters.");
        }

        // Note: Validation for room tags (e.g., must have at least one) could be added here if needed.

        return errors;
    }
}