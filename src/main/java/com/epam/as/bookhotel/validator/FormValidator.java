package com.epam.as.bookhotel.validator;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FormValidator {

    private static final String formPropertyFileName = "forms.properties";
    private static final String propertyDot = ".";
    Map<String, List<Validator>> filedValidators;
    String formName;

    public List<String> validate(String formName, HttpServletRequest request) {
        List<String> message = new ArrayList<>();
        formName = formName + propertyDot;
        
        return null;
    }
}
