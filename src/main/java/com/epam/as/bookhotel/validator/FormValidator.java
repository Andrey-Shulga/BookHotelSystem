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
    private static final String ERROR_MESSAGE_SUFFIX = "ErrorMessages";
    private static final String FORM_PROPERTY_FILE_NAME = "forms.properties";
    private static final String PROPERTY_KEY_DOT = ".";
    private static final String REGEX_FOR_NUMBER = "[0-9]*";
    private static final String FIELDS_NOT_EQUAL_ERROR_MESSAGE = "fields.not.equal.message";
    private static final String LIST_NOT_SELECTED_ERROR_MESSAGE = "drop.down.list.item.not.select";
    private static Properties formProperties;
    private Map<String, List<String>> fieldErrors = new HashMap<>();

    public FormValidator() throws ValidatorException {

        if (formProperties == null) {
            loadFormProperties();
        }
    }

    private void loadFormProperties() throws ValidatorException {

        PropertyManager propertyManager;
        try {
            propertyManager = new PropertyManager(FORM_PROPERTY_FILE_NAME);
        } catch (PropertyManagerException e) {
            throw new ValidatorException(e);
        }
        formProperties = propertyManager.getProperties();
    }

    public boolean hasFieldsErrors(HttpServletRequest req, Map<String, List<String>> fieldErrors) {

        boolean isError = false;
        if (!fieldErrors.isEmpty()) {
            setErrorsToSession(req);
            isError = true;
        }
        return isError;
    }

    private void setErrorsToSession(HttpServletRequest req) {

        for (Map.Entry<String, List<String>> entry : fieldErrors.entrySet()) {
            req.getSession().setAttribute(entry.getKey() + ERROR_MESSAGE_SUFFIX, entry.getValue());
            for (String errorMessage : entry.getValue()) {
                logger.debug("In filed \"{}\" found error message \"{}\"", entry.getKey(), errorMessage);
            }
        }
    }

    public Map<String, List<String>> validate(String formName, HttpServletRequest request) throws ValidatorException {

        deleteErrorsFromSession(request);
        Map<String, List<Validator>> fieldValidators = getParameterValidatorsMap(formName, request);
        for (Map.Entry<String, List<Validator>> entry : fieldValidators.entrySet()) {
            String key = entry.getKey();
            String value = request.getParameter(key);
            List<String> errorMessages = new ArrayList<>();
            for (Validator validator : entry.getValue()) {
                if (!validator.isValid(value)) {
                    logger.debug("Warning! Try to validate parameter \"{}\" with value \"{}\" use validator {}. Result: {}", key, value, validator.getClass().getSimpleName(), validator.isValid(value));
                    errorMessages.add(validator.getMessage());
                }
            }
            if (!errorMessages.isEmpty()) {
                fieldErrors.put(key, errorMessages);
            }
        }
        return fieldErrors;
    }

    private void deleteErrorsFromSession(HttpServletRequest request) {

        Enumeration<String> attributeNames = request.getSession().getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String sessionAttribute = attributeNames.nextElement();
            if (sessionAttribute.endsWith(ERROR_MESSAGE_SUFFIX)) request.getSession().removeAttribute(sessionAttribute);
        }
    }

    public void checkFieldsOnEquals(String field, String otherField, HttpServletRequest request) {

        if ((request.getParameter(field) != null) && (!request.getParameter(field).equals(request.getParameter(otherField)))) {
            List<String> errorFieldMessages = new ArrayList<>();
            String errorMessage = formProperties.getProperty(FIELDS_NOT_EQUAL_ERROR_MESSAGE);
            errorFieldMessages.add(errorMessage);
            fieldErrors.put(otherField, errorFieldMessages);
        }
    }

    public void checkDropDownListOnSelect(String parameter, HttpServletRequest req) {

        if (req.getParameter(parameter) == null) {
            List<String> errorMessages = new ArrayList<>();
            String errorMessage = formProperties.getProperty(LIST_NOT_SELECTED_ERROR_MESSAGE);
            errorMessages.add(errorMessage);
            fieldErrors.put(parameter, errorMessages);
        }
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
            if ((key.startsWith(formFieldName)) && (validatorNameNumber.matches(REGEX_FOR_NUMBER))) {
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

        if (value.matches(REGEX_FOR_NUMBER)) return new Integer(value);
        return value;
    }
}
