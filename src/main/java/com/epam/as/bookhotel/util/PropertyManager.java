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
    private static PropertyManager instance;
    private static Properties properties;

    public static PropertyManager getInstance() {
        if (instance == null) {
            instance = new PropertyManager();
        }
        return instance;
    }

    public static String getProperty(String key) throws PropertyManagerException {
        if (properties == null) {
            throw new PropertyManagerException("Properties were not loaded from file. Use loadPropertyFromFile() method");
        }
        return properties.getProperty(key);
    }

    public void loadPropertyFromFile(String propertyFileName) throws PropertyManagerException {
        properties = new Properties();
        try (InputStream in = PropertyManager.class.getClassLoader().getResourceAsStream(propertyFileName)) {
            properties.load(in);
        } catch (IOException e) {

            throw new PropertyManagerException(e, propertyFileName);
        }

    }

    public Map<String, String> getPropertiesAsMap() throws PropertyManagerException {

        Map<String, String> propertyMap = new HashMap<>();
        if (properties == null) {
            throw new PropertyManagerException("Properties were not loaded from file. Use loadPropertyFromFile() method");
        }
        Enumeration<?> propertyNames = properties.propertyNames();
        while (propertyNames.hasMoreElements()) {
            String key = (String) propertyNames.nextElement();
            String value = properties.getProperty(key);
            propertyMap.put(key, value);
        }
        return propertyMap;
    }
}
