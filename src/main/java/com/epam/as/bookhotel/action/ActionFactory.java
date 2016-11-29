package com.epam.as.bookhotel.action;


import com.epam.as.bookhotel.util.PropertyManager;

import java.util.Map;

public class ActionFactory {

    private static final String actionPropertyFileName = "action.properties";
    private Map<String, String> actionMap;

    public ActionFactory() {
        actionFactoryConfigure();
    }

    private void actionFactoryConfigure() {
        PropertyManager propertyManager = new PropertyManager(actionPropertyFileName);
        actionMap = propertyManager.getPropertiesAsMap();
    }
}
