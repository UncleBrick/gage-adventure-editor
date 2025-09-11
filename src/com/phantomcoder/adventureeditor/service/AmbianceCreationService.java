package com.phantomcoder.adventureeditor.service;

import com.phantomcoder.adventureeditor.constants.AppConstants;
import com.phantomcoder.adventureeditor.model.AmbianceEvent;
import com.phantomcoder.adventureeditor.util.PathUtil;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AmbianceCreationService {

    private AmbianceCreationService() {}

    public static String generateContentHash(String descriptiveText) {
        if (descriptiveText == null || descriptiveText.trim().isEmpty()) {
            return "empty";
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

    public static String generateFullId(String location, String area, String roomName, String vanityOrHash, List<AmbianceEvent> existingEvents) {
        String safeLocation = PathUtil.toSafeFileName(location);
        String safeArea = PathUtil.toSafeFileName(area);
        String safeRoomName = PathUtil.toSafeFileName(roomName);

        String baseId = String.format("ambtxt_%s_%s_%s_%s", safeLocation, safeArea, safeRoomName, vanityOrHash);

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