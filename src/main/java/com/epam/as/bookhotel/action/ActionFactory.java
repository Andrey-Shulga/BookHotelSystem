package com.epam.as.bookhotel.action;


import com.epam.as.bookhotel.exception.ActionException;
import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.util.PropertyManager;

import java.util.Map;

public class ActionFactory {

    private static final String actionPropertyFileName = "action.properties";
    private Map<String, String> actionMap;

    public void loadActions() throws PropertyManagerException {
        PropertyManager.getInstance().loadPropertyFromFile(actionPropertyFileName);

        actionMap = PropertyManager.getInstance().getPropertiesAsMap();
    }

    public Action getAction(String actionName) throws ActionException {
        Action action;
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
