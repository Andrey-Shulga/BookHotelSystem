package com.epam.as.bookhotel.action;


import com.epam.as.bookhotel.exception.ActionException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.util.PropertyManager;

import java.util.Properties;

public class ActionFactory {

    private static final String actionPropertyFileName = "action.properties";
    private Properties actionProperties;

    public void loadActions() throws PropertyManagerException {
        PropertyManager propertyManager = new PropertyManager(actionPropertyFileName);
        actionProperties = propertyManager.getProperties();
    }

    public Action getAction(String actionName) throws ActionException {
        Action action;
        String actionClassName = actionProperties.getProperty(actionName);
        try {
            Class actionClass = Class.forName(actionClassName);
            action = (Action) actionClass.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new ActionException(e);
        }
        return action;
    }
}
