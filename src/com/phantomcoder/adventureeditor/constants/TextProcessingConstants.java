package com.phantomcoder.adventureeditor.constants;

import java.util.List;

/**
 * Contains constants related to text processing, such as stopword lists for
 * generating human-readable ID slugs.
 */
public final class TextProcessingConstants {

    /**
     * A list of common English "stopwords" to be filtered out when generating
     * ID slugs from descriptions. This ensures the slug is composed of more
     * meaningful, significant words.
     */
    public static final List<String> STOP_WORDS = List.of(
            "a", "an", "and", "are", "as", "at", "be", "by", "for", "from",
            "has", "he", "in", "is", "it", "its", "of", "on", "that", "the",
            "to", "was", "were", "will", "with"
    );

    private TextProcessingConstants() { /* Prevent instantiation */ }
}
