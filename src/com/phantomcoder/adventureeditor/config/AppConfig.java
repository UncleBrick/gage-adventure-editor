package com.phantomcoder.adventureeditor.config;

import com.phantomcoder.adventureeditor.constants.AppConstants;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public class AppConfig {
    private static boolean isDebugging = false;
    private static boolean showSaveWarning = true;
    private static boolean showWelcomeDialog = true;
    private static boolean configLoaded = false;

    // --- Path to the default configuration and user-specific overrides ---
    private static final Path CONFIG_PATH = AppConstants.BASE_DIR.resolve("config.properties");
    private static final Path USER_CONFIG_PATH = AppConstants.BASE_DIR.resolve("user.properties");

    private AppConfig() { /* Prevent instantiation */ }

    public static synchronized void loadConfig() {
        if (configLoaded) {
            return;
        }

        Properties properties = new Properties();

        // 1. Load the default config.properties file first.
        try (InputStream input = new FileInputStream(CONFIG_PATH.toFile())) {
            properties.load(input);
        } catch (IOException ex) {
            System.err.println("WARNING: Could not load default configuration file at " + CONFIG_PATH);
        }

        // 2. Load the user.properties file. Any settings here will override the defaults.
        if (Files.exists(USER_CONFIG_PATH)) {
            try (InputStream input = new FileInputStream(USER_CONFIG_PATH.toFile())) {
                properties.load(input);
            } catch (IOException ex) {
                System.err.println("WARNING: Could not load user configuration file at " + USER_CONFIG_PATH);
            }
        }

        // 3. Parse the final property values.
        isDebugging = Boolean.parseBoolean(properties.getProperty("debug.isDebugging", "false").trim());
        showSaveWarning = Boolean.parseBoolean(properties.getProperty("ui.warnings.show_on_empty_save", "true").trim());
        showWelcomeDialog = Boolean.parseBoolean(properties.getProperty(AppConstants.PREF_SHOW_WELCOME_DIALOG, "true").trim());

        configLoaded = true;
    }

    public static synchronized void saveConfig() {
        Properties userProperties = new Properties();

        // We only save the properties that the user can change through the UI.
        userProperties.setProperty("ui.warnings.show_on_empty_save", String.valueOf(showSaveWarning));
        userProperties.setProperty(AppConstants.PREF_SHOW_WELCOME_DIALOG, String.valueOf(showWelcomeDialog));

        // Write these properties to the user.properties file.
        try (OutputStream output = new FileOutputStream(USER_CONFIG_PATH.toFile())) {
            userProperties.store(output, "GAGE User-Specific Preferences");
        } catch (IOException ex) {
            System.err.println("ERROR: Could not save user configuration file at " + USER_CONFIG_PATH);
            ex.printStackTrace();
        }
    }

    public static boolean isDebuggingEnabled() {
        if (!configLoaded) {
            loadConfig();
        }
        return isDebugging;
    }

    public static boolean shouldShowSaveWarning() {
        if (!configLoaded) {
            loadConfig();
        }
        return showSaveWarning;
    }

    public static void setShouldShowSaveWarning(boolean show) {
        if (showSaveWarning != show) {
            showSaveWarning = show;
        }
    }

    public static boolean shouldShowWelcomeDialog() {
        if (!configLoaded) {
            loadConfig();
        }
        return showWelcomeDialog;
    }

    public static void setShouldShowWelcomeDialog(boolean show) {
        if (showWelcomeDialog != show) {
            showWelcomeDialog = show;
        }
    }
}