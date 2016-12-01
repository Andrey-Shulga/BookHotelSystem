package com.epam.as.bookhotel.validator;


public interface Validator {


    Boolean isValid(String parameter);

    String getMessage();

    void setMessage(String msg);



}
