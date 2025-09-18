package com.phantomcoder.adventureeditor.service;

import com.phantomcoder.adventureeditor.constants.AppConstants;
import com.phantomcoder.adventureeditor.constants.TextProcessingConstants;
import com.phantomcoder.adventureeditor.model.AmbianceEvent;
import com.phantomcoder.adventureeditor.util.PathUtil;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AmbianceCreationService {

    private AmbianceCreationService() {}

    public static String generateSlugFromDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new IllegalArgumentException("Description cannot be empty.");
        }

        String[] words = description.trim().toLowerCase().replaceAll("[^a-z0-9\\s]", "").split("\\s+");

        if (words.length < AppConstants.MIN_DESC_WORDS_FOR_SLUG) {
            throw new IllegalArgumentException("Description must be at least " + AppConstants.MIN_DESC_WORDS_FOR_SLUG + " words.");
        }

        return Arrays.stream(words)
                .filter(word -> !TextProcessingConstants.STOP_WORDS.contains(word))
                .limit(AppConstants.SLUG_WORD_COUNT)
                .collect(Collectors.joining("_"));
    }

    public static String generateFullId(String location, String area, String subArea, String roomName, String vanityOrSlug, List<AmbianceEvent> existingEvents) {
        String safeLocation = PathUtil.toSafeFileName(location);
        String safeArea = PathUtil.toSafeFileName(area);
        String safeSubArea = PathUtil.toSafeFileName(subArea);
        String safeRoomName = PathUtil.toSafeFileName(roomName);

        String baseId;
        if (safeSubArea.isEmpty()) {
            baseId = String.format("ambtxt_%s_%s_%s_%s", safeLocation, safeArea, safeRoomName, vanityOrSlug);
        } else {
            baseId = String.format("ambtxt_%s_%s_%s_%s_%s", safeLocation, safeArea, safeSubArea, safeRoomName, vanityOrSlug);
        }

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

