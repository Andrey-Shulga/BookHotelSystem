package com.epam.as.bookhotel.validator;


public class LengthValidator {

    private int minLength;
    private int maxLength;

    public LengthValidator() {
    }

    public int getMinLength() {
        return minLength;
    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public int getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }
}
