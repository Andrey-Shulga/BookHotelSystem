package com.epam.as.bookhotel.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PriceDoubleValidator extends ParentValidator implements Validator {

    private static final Logger logger = LoggerFactory.getLogger(PriceDoubleValidator.class);

    @Override
    public Boolean isValid(String parameter) {

        boolean checkResult;
        try {
            Double doubleValue = Double.parseDouble(parameter);
            checkResult = true;
        } catch (NumberFormatException e) {

            checkResult = false;
            logger.error("Price {} is not correct.", parameter);
        }

        return checkResult;
    }

    @Override
    public String toString() {
        return "PriceDoubleValidator{}";
    }
}