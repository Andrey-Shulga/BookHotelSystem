package com.epam.as.bookhotel.model;

public class UserLocale extends BaseEntity {

    private String localeName;

    public UserLocale() {
    }

    public UserLocale(String locale) {
        this.localeName = locale;
    }

    public String getLocaleName() {
        return localeName;
    }

    public void setLocaleName(String localeName) {
        this.localeName = localeName;
    }

    @Override
    public String toString() {
        return localeName;
    }
}
