package com.epam.as.bookhotel.exception;


import java.io.IOException;

/**
 * Exception for wrapping any exceptions in Property Manager
 */

public class PropertyManagerException extends Exception {

    public PropertyManagerException(IOException e) {

        super(e);
    }


    public PropertyManagerException(String message) {
        super(message);
    }
}
