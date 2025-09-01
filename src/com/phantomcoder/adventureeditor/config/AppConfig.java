package com.phantomcoder.adventureeditor.config;

import com.phantomcoder.adventureeditor.constants.AppConstants; // CHANGED

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class AppConfig {
    private static boolean isDebugging = false;
    private static boolean configLoaded = false;

    private AppConfig() { /* Prevent instantiation */ }

    public static synchronized void loadConfig() {
        if (configLoaded) {
            return;
        }

        Properties properties = new Properties();
        // --- CHANGED ---
        Path configPath = AppConstants.BASE_DIR.resolve("config.properties");

        try (InputStream input = Files.newInputStream(configPath)) {
            properties.load(input);
            String debugValue = properties.getProperty("debug.isDebugging", "false").trim();
            isDebugging = Boolean.parseBoolean(debugValue);

            System.out.println("--- Configuration Result ---");
            System.out.println("Read 'debug.isDebugging' value from file: '" + debugValue + "'");
            System.out.println("Final parsed boolean value is: " + isDebugging);
            System.out.println("--------------------------");

        } catch (IOException ex) {
            isDebugging = false;
            System.err.println("WARNING: Could not load configuration file at " + configPath + ". Using default settings.");
        }
        configLoaded = true;
    }

    public static boolean isDebuggingEnabled() {
        if (!configLoaded) {
            loadConfig();
        }
        return isDebugging;
    }
}