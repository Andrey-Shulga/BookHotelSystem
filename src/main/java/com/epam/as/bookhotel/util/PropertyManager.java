package com.epam.as.bookhotel.util;


import com.epam.as.bookhotel.exception.PropertyManagerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {

    private static final Logger logger = LoggerFactory.getLogger(PropertyManager.class);
    private static Properties properties;

    private PropertyManager() {
    }

    public static PropertyManager getInstance() {
        return PropertyManagerHolder.instance;
    }

    public void loadPropertyFromFile(String propertyFileName) throws PropertyManagerException {
        properties = new Properties();
        try (InputStream in = PropertyManager.class.getClassLoader().getResourceAsStream(propertyFileName)) {
            properties.load(in);
        } catch (IOException e) {
            throw new PropertyManagerException(e, propertyFileName);
        }

    }

    public Properties getProperties() throws PropertyManagerException {
        if (properties == null) throw new PropertyManagerException("Properties not found. Load property file fist");
        return properties;
    }

    private static class PropertyManagerHolder {
        private static final PropertyManager instance = new PropertyManager();
    }
}
