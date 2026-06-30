package com.framework.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertyFile {
    private final Properties properties = new Properties();

    public PropertyFile() {
        try (FileInputStream inputStream = new FileInputStream(FrameworkConstants.CONFIG_PATH)) {
            properties.load(inputStream);
        } catch (IOException exception) {
            throw new RuntimeException("Unable to load config properties", exception);
        }
    }

    public String getValueProperty(String key) {
        return properties.getProperty(key);
    }

    public String getValueProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }
}
