package com.epam.as.bookhotel.validator;


import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.util.PropertyManager;

import java.util.List;
import java.util.Map;

public class RegisterFormValidator {

    private static final String formPropertyFileName = "form.properties";
    Map<String, List<Validator>> filedValidators;

    private void loadValidators() throws PropertyManagerException {
        PropertyManager propertyManager = new PropertyManager();
        propertyManager.loadPropertyFromFile(formPropertyFileName);
    }
}
