package com.epam.as.bookhotel.action;


import com.epam.as.bookhotel.exception.ActionFactoryException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.util.PropertyManager;

import java.util.Properties;

public class ActionFactory {

    private static final String ACTION_PROPERTY_FILE_NAME = "action.properties";
    private Properties actionProperties;

    public void loadActions() throws ActionFactoryException {

        PropertyManager propertyManager = null;
        try {
            propertyManager = new PropertyManager(ACTION_PROPERTY_FILE_NAME);
        } catch (PropertyManagerException e) {
            throw new ActionFactoryException(e);
        }
        actionProperties = propertyManager.getProperties();

    }

    public Action getAction(String actionName) throws ActionFactoryException {
        Action action;
        String actionClassName = actionProperties.getProperty(actionName);
        try {
            Class actionClass = Class.forName(actionClassName);
            action = (Action) actionClass.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new ActionFactoryException(e);
        }
        return action;
    }
}
