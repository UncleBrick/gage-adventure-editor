package com.phantomcoder.adventureeditor.service;

import com.phantomcoder.adventureeditor.constants.AppConstants;
import com.phantomcoder.adventureeditor.model.AmbianceEvent;
import com.phantomcoder.adventureeditor.util.PathUtil;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A service responsible for handling the business logic of creating AmbianceEvent
 * objects, including the generation of unique and human-readable IDs as per the
 * design specification.
 */
public class AmbianceCreationService {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private AmbianceCreationService() {}

    /**
     * Generates the 5-letter content hash from an ambiance's descriptive text.
     * This follows the rule of taking the first letter of the first five words,
     * and padding with random characters if the resulting hash is shorter than 5 characters.
     *
     * @param descriptiveText The full descriptive text of the ambiance event.
     * @return A 5-letter content hash string.
     */
    public static String generateContentHash(String descriptiveText) {
        if (descriptiveText == null || descriptiveText.trim().isEmpty()) {
            return "";
        }

        String[] words = descriptiveText.trim().split("\\s+");
        StringBuilder hashBuilder = new StringBuilder();

        int limit = Math.min(words.length, AppConstants.AMBIANCE_HASH_LENGTH);
        for (int i = 0; i < limit; i++) {
            if (!words[i].isEmpty()) {
                hashBuilder.append(words[i].charAt(0));
            }
        }

        while (hashBuilder.length() < AppConstants.AMBIANCE_HASH_LENGTH) {
            char randomChar = (char) ThreadLocalRandom.current().nextInt('a', 'z' + 1);
            hashBuilder.append(randomChar);
        }

        return hashBuilder.toString().toLowerCase();
    }

    /**
     * Generates a full, unique, human-readable ID for an ambiance event.
     *
     * @param location        The location name of the room.
     * @param area            The area name of the room.
     * @param roomName        The name of the room.
     * @param contentHash     The 5-letter hash generated from the descriptive text.
     * @param existingEvents  A list of existing ambiance events in the room to check for duplicates.
     * @return A unique ID string (e.g., "ambtxt_location_area_room_hash_00").
     */
    public static String generateFullId(String location, String area, String roomName, String contentHash, List<AmbianceEvent> existingEvents) {
        String safeLocation = PathUtil.toSafeFileName(location);
        String safeArea = PathUtil.toSafeFileName(area);
        String safeRoomName = PathUtil.toSafeFileName(roomName);

        String baseId = String.format("ambtxt_%s_%s_%s_%s", safeLocation, safeArea, safeRoomName, contentHash);

        int variant = 0;
        while (true) {
            String candidateId = String.format("%s_%02d", baseId, variant);
            boolean isDuplicate = false;
            if (existingEvents != null) {
                for (AmbianceEvent event : existingEvents) {
                    if (event.getId() != null && event.getId().equals(candidateId)) {
                        isDuplicate = true;
                        break;
                    }
                }
            }

            if (!isDuplicate) {
                return candidateId;
            }
            variant++;
        }
    }
}