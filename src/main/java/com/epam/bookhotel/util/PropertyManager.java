package com.epam.bookhotel.util;


import com.epam.bookhotel.exception.PropertyManagerException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Property manager for serve reading properties from file
 */

public class PropertyManager {

    private Properties properties;

    public PropertyManager(String propertyFileName) throws PropertyManagerException {

        try (InputStream in = PropertyManager.class.getClassLoader().getResourceAsStream(propertyFileName)) {
            if (in != null) {
                properties = new Properties();
                properties.load(in);
            } else
                throw new PropertyManagerException(String.format("Can't open file \"%s\" for loading properties", propertyFileName));
        } catch (IOException e) {
            throw new PropertyManagerException(e);
        }
    }

    public String getPropertyKey(String key) throws PropertyManagerException {

        String value;
        if (properties.containsKey(key)) value = properties.getProperty(key);
        else
            throw new PropertyManagerException(String.format("Property key \"%s\" not found in properties", key));
        return value;
    }

    public Properties getProperties() {

        return properties;
    }

}
