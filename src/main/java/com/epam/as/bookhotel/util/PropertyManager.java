package com.epam.as.bookhotel.util;


import com.epam.as.bookhotel.exception.PropertyManagerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class PropertyManager {

    private static final Logger logger = LoggerFactory.getLogger(PropertyManager.class);
    private Properties properties;

    public PropertyManager(String propertyFileName) throws PropertyManagerException {

        properties = new Properties();
        try (InputStream in = PropertyManager.class.getClassLoader().getResourceAsStream(propertyFileName)) {
            properties.load(in);
        } catch (IOException e) {
            logger.error("Can't open file {} for reading properties.", e);
            throw new PropertyManagerException(e);

        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public Map<String, String> getPropertiesAsMap() {

        Map<String, String> propertyMap = new HashMap<>();
        Enumeration<?> propertyNames = properties.propertyNames();
        while (propertyNames.hasMoreElements()) {
            String key = (String) propertyNames.nextElement();
            String value = properties.getProperty(key);
            propertyMap.put(key, value);
        }
        return propertyMap;
    }
}
