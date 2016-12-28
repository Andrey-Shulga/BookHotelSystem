package com.epam.as.bookhotel.validator;

public class FieldsEqualsValidator extends ParentValidator implements Validator {


    Boolean isValid(String field, String otherField) {

        boolean checkResult = false;
        if (field.equals(otherField)) checkResult = true;
        return checkResult;
    }

    @Override
    public Boolean isValid(String parameter) {
        return null;
    }
}
