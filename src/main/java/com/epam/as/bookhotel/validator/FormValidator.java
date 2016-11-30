package com.epam.as.bookhotel.validator;


import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.util.PropertyManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public class FormValidator {

    private static final String formPropertyFileName = "forms.properties";
    Map<String, List<Validator>> filedValidators;

    public static FormValidator getFormValidator(String formCommandName) throws PropertyManagerException {

        PropertyManager propertyManager = new PropertyManager();
        propertyManager.loadPropertyFromFile(formPropertyFileName);
        Map<String, String> formPropertyMap = propertyManager.getPropertiesAsMap();
        FormValidator formValidator = null;
        String loginField = "";
        //  formValidator.filedValidators.put();

        return formValidator;
    }

    public static List<Validator> getValidatorList(String formCommandName) throws PropertyManagerException {
        PropertyManager propertyManager = new PropertyManager();
        propertyManager.loadPropertyFromFile(formPropertyFileName);

        return null;

    }

    public List<String> validate(HttpServletRequest request) {
        return null;
    }
}
