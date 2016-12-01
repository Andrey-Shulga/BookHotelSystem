package com.epam.as.bookhotel.validator;


public class LengthValidator extends ParentValidator implements Validator {

    private Integer minLength;
    private Integer maxLength;


    public LengthValidator() {
    }

    public Integer getMinLength() {
        return minLength;
    }

    public void setMinLength(Integer minLength) {
        this.minLength = minLength;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public Boolean isValid(String parameter) {
        return null;
    }

    @Override
    public String toString() {
        return "LengthValidator{" +
                "minLength=" + minLength +
                ", maxLength=" + maxLength +
                '}';
    }
}