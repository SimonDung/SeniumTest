package org.example.config;

import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {
    private static final Properties properties = new Properties();

    static {
        loadProperties();
    }

    private ConfigManager() {
        // Prevent instantiation
    }

    private static void loadProperties() {
        // 1. Determine environment from system property '-Denv' (Default to 'local')
        String env = System.getProperty("env", "local").trim().toLowerCase();
        String fileName = String.format("config-%s.properties", env);

        // 2. Load environment file from classpath (src/test/resources)
        try (InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream(fileName)) {
            if (input == null) {
                throw new IllegalStateException("Could not find configuration file on classpath: " + fileName);
            }
            properties.load(input);
            System.out.println(" Successfully loaded environment configuration: " + fileName);
        } catch (Exception e) {
            throw new RuntimeException(" Failed to initialize ConfigManager with file: " + fileName, e);
        }
    }

    /**
     * Retrieves a property value.
     * Priority: System Property (-Dkey) > Property File (config-*.properties)
     */
    public static String get(String key) {
        // Allow command line overrides (e.g., -Dgrid.url=http://...)
        String systemProperty = System.getProperty(key);
        if (systemProperty != null && !systemProperty.isBlank()) {
            return systemProperty.trim();
        }

        String fileProperty = properties.getProperty(key);
        if (fileProperty == null) {
            throw new IllegalArgumentException("Property key '" + key + "' was not found in configuration.");
        }

        return fileProperty.trim();
    }

    /**
     * Retrieves a property value with a fallback default.
     */
    public static String get(String key, String defaultValue) {
        try {
            return get(key);
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }

    /**
     * Retrieves a boolean property value.
     */
    public static boolean getBoolean(String key, boolean defaultValue) {
        String value = get(key, String.valueOf(defaultValue));
        return Boolean.parseBoolean(value);
    }
}
