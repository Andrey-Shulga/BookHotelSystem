package com.epam.as.bookhotel.action;


import com.epam.as.bookhotel.util.PropertyManager;

import java.util.Map;

public class ActionFactory {

    private static final String actionPropertyFileName = "action.properties";
    private Map<String, String> actionMap;


    public ActionFactory() {
        loadActions();
    }

    private void loadActions() {
        PropertyManager propertyManager = new PropertyManager(actionPropertyFileName);
        actionMap = propertyManager.getPropertiesAsMap();
    }

    public Action getAction(String actionName) throws ActionException {
        Action action = null;
        String actionClassName = actionMap.get(actionName);
        try {
            Class actionClass = Class.forName(actionClassName);
            action = (Action) actionClass.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            throw new ActionException(e);
        }
        return action;
    }
}
