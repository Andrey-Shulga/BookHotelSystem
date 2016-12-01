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
    private static final String formPropertyFileName = "forms.properties";
    private static final String propertyKeyDot = ".";
    private static final String formActionName = "command";
    private static final String regexNumber = "[0-9]*";
    private static Map<String, List<Validator>> fieldValidators = new HashMap<>();
    private static Properties formProperties;

    public FormValidator() throws PropertyManagerException {
        if (formProperties == null) {
            loadFormProperties();
        }
    }

    private void loadFormProperties() throws PropertyManagerException {
        PropertyManager.getInstance().loadPropertyFromFile(formPropertyFileName);
        formProperties = PropertyManager.getInstance().getProperties();
    }

    public String validate(String formName, HttpServletRequest request) throws ValidatorException {
        List<String> message = new ArrayList<>();
        List<Validator> validators = new ArrayList<>();
        Enumeration<String> attributeNames = request.getParameterNames();
        while (attributeNames.hasMoreElements()) {
            String fieldName = attributeNames.nextElement();
            String value = request.getParameter(fieldName);
            String formFieldName = formName + propertyKeyDot + fieldName;
            if (!fieldName.equals(formActionName)) {
                logger.debug("From form \"{}\" received parameter \"{}\" with value \"{}\"", formName, fieldName, value);
                validators = getValidators(formFieldName);
                fieldValidators.put(fieldName, validators);
            }

        }
        return null;
    }

    private List<Validator> getValidators(String formFieldName) throws ValidatorException {
        final int defineValidatorNumber = 1;
        List<Validator> validators = new ArrayList<>();
        Validator validator;
        for (Map.Entry<?, ?> property : formProperties.entrySet()) {
            String key = (String) property.getKey();
            String value = (String) property.getValue();
            String validatorNameNumber = key.substring(key.length() - defineValidatorNumber, key.length());
            if (validatorNameNumber.matches(regexNumber)) {
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
        logger.debug("Validate using validator: {}", validator.toString());
        return validator;

    }

    private void validatorSetFields(Class validatorClass, Validator validator, String validatorNumberName, String formFiledName) throws ValidatorException {
        for (Map.Entry<?, ?> property : formProperties.entrySet()) {
            String key = (String) property.getKey();
            String value = (String) property.getValue();
            if (key.startsWith(formFiledName + propertyKeyDot + validatorNumberName)) {
                try {
                    Object valueObject = parseValue(value);
                    String keyFieldName = formFiledName + propertyKeyDot + validatorNumberName + propertyKeyDot;
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

        if (value.matches(regexNumber)) return new Integer(value);
        return value;
    }
}
