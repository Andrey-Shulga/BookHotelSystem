package com.epam.as.bookhotel.validator;

public class ImageValidator extends ParentValidator implements Validator {

    private static final String JPEG_CONTENT_TYPE = "image/jpeg";

    @Override
    public Boolean isValid(String parameter) {

        boolean checkResult = false;
        if (JPEG_CONTENT_TYPE.equals(parameter)) {
            checkResult = true;
        }
        return checkResult;
    }

}
