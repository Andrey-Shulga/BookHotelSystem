package com.epam.as.bookhotel.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {

    private static final Logger logger = LoggerFactory.getLogger(PropertyManager.class);
    private final Properties properties;

    public PropertyManager(String propertyFileName) {

        this.properties = new Properties();
        try (InputStream in = PropertyManager.class.getClassLoader().getResourceAsStream(propertyFileName)) {
            properties.load(in);
        } catch (IOException e) {
            logger.error("Can't open file {} for reading properties.", e);
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
