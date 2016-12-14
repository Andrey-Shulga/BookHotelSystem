package com.epam.as.bookhotel.util;


import com.epam.as.bookhotel.exception.PropertyManagerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {

    private static final Logger logger = LoggerFactory.getLogger(PropertyManager.class);
    private static final String PROPERTY_NOT_LOADED_ERROR_MESSAGE = "Properties not loaded.";
    private Properties properties;

    public PropertyManager(String propertyFileName) throws PropertyManagerException {
        properties = new Properties();
        try (InputStream in = PropertyManager.class.getClassLoader().getResourceAsStream(propertyFileName)) {
            properties.load(in);
        } catch (IOException e) {
            throw new PropertyManagerException(e, propertyFileName);
        }
    }

    public String getPropertyKey(String key) throws PropertyManagerException {
        if (properties == null) throw new PropertyManagerException(PROPERTY_NOT_LOADED_ERROR_MESSAGE);
        return properties.getProperty(key);
    }

    public Properties getProperties() {
        return properties;
    }

}
