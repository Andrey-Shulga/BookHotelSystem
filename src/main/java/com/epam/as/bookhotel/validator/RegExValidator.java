package com.epam.as.bookhotel.validator;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegExValidator extends ParentValidator implements Validator {

    private static final Logger logger = LoggerFactory.getLogger(RegExValidator.class);

    private String regex;

    public RegExValidator() {
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public Boolean isValid(String parameter) {
        if (parameter.matches(regex)) logger.debug("!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return parameter.matches(regex);
    }

    @Override
    public String toString() {
        return "RegExValidator{" +
                "regex='" + regex + '\'' +
                ", message=" + this.getMessage() +
                '}';
    }
}
