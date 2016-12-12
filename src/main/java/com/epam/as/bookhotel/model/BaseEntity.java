package com.epam.as.bookhotel.model;


public class BaseEntity {

    private int id;

    BaseEntity() {
    }

    BaseEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
