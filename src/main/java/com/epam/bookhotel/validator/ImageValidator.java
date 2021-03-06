package com.epam.bookhotel.validator;

import com.epam.bookhotel.exception.PropertyManagerException;
import com.epam.bookhotel.exception.ValidatorException;
import com.epam.bookhotel.util.PropertyManager;

/**
 * Validator on content type of uploading file
 */

public class ImageValidator extends ParentValidator implements Validator {

    private static final String FORM_PROPERTY_FILE_NAME = "forms.properties";
    private static final String IMAGE_CONTENT_TYPE_KEY = "image.upload.content.type";

    @Override
    public Boolean isValid(String parameter) throws ValidatorException {

        boolean checkResult = false;
        String fileContentType;
        try {
            PropertyManager pm = new PropertyManager(FORM_PROPERTY_FILE_NAME);
            fileContentType = pm.getPropertyKey(IMAGE_CONTENT_TYPE_KEY);
        } catch (PropertyManagerException e) {
            throw new ValidatorException(e);
        }
        if (fileContentType.equals(parameter)) {
            checkResult = true;
        }
        return checkResult;
    }

}
