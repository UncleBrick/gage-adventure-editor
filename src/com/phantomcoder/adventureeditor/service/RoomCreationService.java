package com.phantomcoder.adventureeditor.service;

import com.phantomcoder.adventureeditor.gui.panels.LongDescriptionPanel;
import com.phantomcoder.adventureeditor.gui.panels.MiddleDataPanel;
import com.phantomcoder.adventureeditor.gui.panels.TopMetaDataPanel;
import com.phantomcoder.adventureeditor.model.RoomData;
import com.phantomcoder.adventureeditor.validation.RoomValidator;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public class RoomCreationService {

    private final MiddleDataPanel middleDataPanel;
    private final LongDescriptionPanel longDescriptionPanel;
    private final TopMetaDataPanel topMetaDataPanel;
    private final RoomValidator roomValidator;

    public RoomCreationService(MiddleDataPanel middleDataPanel, LongDescriptionPanel longDescriptionPanel, TopMetaDataPanel topMetaDataPanel, RoomValidator roomValidator) {
        this.middleDataPanel = Objects.requireNonNull(middleDataPanel, "MiddleDataPanel cannot be null");
        this.longDescriptionPanel = Objects.requireNonNull(longDescriptionPanel, "LongDescriptionPanel cannot be null");
        this.topMetaDataPanel = Objects.requireNonNull(topMetaDataPanel, "TopMetaDataPanel cannot be null");
        this.roomValidator = Objects.requireNonNull(roomValidator, "RoomValidator cannot be null");
    }

    public RoomData createRoomData() throws IllegalArgumentException {
        String locationName = topMetaDataPanel.getLocationName();
        String areaName = topMetaDataPanel.getAreaName();
        String fileName = topMetaDataPanel.getFileName();
        String roomName = middleDataPanel.getRoomName();
        String shortDescription = middleDataPanel.getShortDescription();
        String longDescription = longDescriptionPanel.getLongDescription();

        // Get the tags from the new dedicated flags panel
        Set<String> roomTags = middleDataPanel.getRoomFlagsPanel().getSelectedTags();

        // Use the correct constructor for RoomData
        RoomData newRoom = new RoomData(
                locationName,
                areaName,
                fileName,
                roomName,
                shortDescription,
                longDescription, // Pass the long description
                roomTags
        );

        List<String> errors = roomValidator.validate(newRoom);
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException(String.join("\n", errors));
        }

        return newRoom;
    }
}