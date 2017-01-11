package com.epam.bookhotel.validator;

import com.epam.bookhotel.exception.ValidatorException;

/**
 * An interface for validators operations
 */
public interface Validator {

    Boolean isValid(String parameter) throws ValidatorException;

    Boolean isValid(String parameter, String otherParameter) throws ValidatorException;

    Boolean isValid(Long parameter) throws ValidatorException;

    String getMessage();

    void setMessage(String msg);

}
