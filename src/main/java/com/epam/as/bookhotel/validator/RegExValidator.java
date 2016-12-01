package com.epam.as.bookhotel.validator;


public class RegExValidator extends ParentValidator implements Validator {

    private String regEx;

    public RegExValidator() {
    }

    public String getRegEx() {
        return regEx;
    }

    public void setRegEx(String regEx) {
        this.regEx = regEx;
    }

    @Override
    public Boolean isValid(String parameter) {
        return null;
    }
}
