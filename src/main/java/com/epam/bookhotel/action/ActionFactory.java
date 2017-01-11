package com.epam.bookhotel.action;


import com.epam.bookhotel.exception.ActionFactoryException;
import com.epam.bookhotel.exception.PropertyManagerException;
import com.epam.bookhotel.util.PropertyManager;

import java.util.Properties;

/**
 * Factory for the production actions
 */

public class ActionFactory {

    private static final String ACTION_PROPERTY_FILE_NAME = "action.properties";
    private Properties actionProperties;

    /**
     * Load available actions from property file
     *
     * @throws ActionFactoryException general exceptions throws on any caught exceptions in factory
     */
    public void loadActions() throws ActionFactoryException {

        PropertyManager propertyManager;
        try {
            propertyManager = new PropertyManager(ACTION_PROPERTY_FILE_NAME);
        } catch (PropertyManagerException e) {
            throw new ActionFactoryException(e);
        }
        actionProperties = propertyManager.getProperties();

    }

    /**
     * Read and returns actions based on the received parameter
     *
     * @param actionName parameter with name of action
     * @return Action from property
     * @throws ActionFactoryException general exceptions throws on any caught exceptions in factory
     */

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
