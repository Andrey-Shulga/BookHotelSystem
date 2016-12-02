package com.epam.as.bookhotel.validator;


import com.epam.as.bookhotel.exception.PropertyManagerException;
import com.epam.as.bookhotel.exception.ValidatorException;
import com.epam.as.bookhotel.util.PropertyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class FormValidator {

    private static final Logger logger = LoggerFactory.getLogger(FormValidator.class);
    private static final String FORM_PROPERTY_FILE_NAME = "forms.properties";
    private static final String PROPERTY_KEY_DOT = ".";
    private static final String REGEX_NUMBER = "[0-9]*";
    private static Properties formProperties;

    public FormValidator() throws PropertyManagerException {
        if (formProperties == null) {
            loadFormProperties();
        }
    }

    private void loadFormProperties() throws PropertyManagerException {
        PropertyManager.getInstance().loadPropertyFromFile(FORM_PROPERTY_FILE_NAME);
        formProperties = PropertyManager.getInstance().getProperties();
    }

    public Map<String, String> validate(String formName, HttpServletRequest request) throws ValidatorException {
        Map<String, List<Validator>> fieldValidators = getParameterValidatorsMap(formName, request);
        Map<String, String> fieldErrors = new HashMap<>();
        for (Map.Entry<String, List<Validator>> entry : fieldValidators.entrySet()) {
            String key = entry.getKey();
            String value = request.getParameter(key);
            for (Validator validator : entry.getValue()) {
                if (!validator.isValid(value)) {
                    logger.debug("Warning! Try to validate parameter \"{}\" with value \"{}\" use validator {}. Result: {}", key, value, validator.getClass().getSimpleName(), validator.isValid(value));
                    fieldErrors.put(key, validator.getMessage());
                }
            }
        }
        return fieldErrors;
    }

    private Map<String, List<Validator>> getParameterValidatorsMap(String formName, HttpServletRequest request) throws ValidatorException {
        Map<String, List<Validator>> fieldValidators = new HashMap<>();
        List<Validator> validators;
        Enumeration<String> attributeNames = request.getParameterNames();
        while (attributeNames.hasMoreElements()) {
            String fieldName = attributeNames.nextElement();
            String value = request.getParameter(fieldName);
            String formFieldName = formName + PROPERTY_KEY_DOT + fieldName;
            logger.debug("From form received parameter \"{}\" with value \"{}\"", fieldName, value);
            validators = getValidators(formFieldName);
            if (!validators.isEmpty()) fieldValidators.put(fieldName, validators);
        }
        return fieldValidators;
    }

    private List<Validator> getValidators(String formFieldName) throws ValidatorException {
        final int DEFINE_VALIDATOR_NUMBER = 1;
        List<Validator> validators = new ArrayList<>();
        Validator validator;
        for (Map.Entry<?, ?> property : formProperties.entrySet()) {
            String key = (String) property.getKey();
            String value = (String) property.getValue();
            String validatorNameNumber = key.substring(key.length() - DEFINE_VALIDATOR_NUMBER, key.length());
            if ((key.startsWith(formFieldName)) && (validatorNameNumber.matches(REGEX_NUMBER))) {
                validator = getValidator(validatorNameNumber, formFieldName, value);
                validators.add(validator);
            }
        }
        return validators;
    }

    private Validator getValidator(String validatorNumberName, String formFiledName, String validatorName) throws ValidatorException {
        Class validatorClass;
        Validator validator;
        try {
            validatorClass = Class.forName(validatorName);
            validator = (Validator) validatorClass.newInstance();
            validatorSetFields(validatorClass, validator, validatorNumberName, formFiledName);
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
            throw new ValidatorException(e);
        }
        logger.debug("Will be used validator: {}", validator.toString());
        return validator;

    }

    private void validatorSetFields(Class validatorClass, Validator validator, String validatorNumberName, String formFiledName) throws ValidatorException {
        for (Map.Entry<?, ?> property : formProperties.entrySet()) {
            String key = (String) property.getKey();
            String value = (String) property.getValue();
            if (key.startsWith(formFiledName + PROPERTY_KEY_DOT + validatorNumberName + PROPERTY_KEY_DOT)) {
                try {
                    Object valueObject = parseValue(value);
                    String keyFieldName = formFiledName + PROPERTY_KEY_DOT + validatorNumberName + PROPERTY_KEY_DOT;
                    String filedValidatorName = key.substring(keyFieldName.length(), key.length());
                    BeanInfo beanInfo = Introspector.getBeanInfo(validatorClass);
                    PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();
                    boolean done = false;
                    for (int i = 0; !done && i < descriptors.length; i++) {
                        if (descriptors[i].getName().equals(filedValidatorName)) {
                            descriptors[i].getWriteMethod().invoke(validator, valueObject);
                            done = true;
                        }
                    }
                } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
                    throw new ValidatorException(e);
                }
            }
        }
    }

    private Object parseValue(String value) {
        if (value.matches(REGEX_NUMBER)) return new Integer(value);
        return value;
    }
}
