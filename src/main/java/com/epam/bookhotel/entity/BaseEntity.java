package com.epam.bookhotel.entity;

/**
 * Parent entity for other entities
 */

public class BaseEntity {

    private Integer id;

    BaseEntity() {
    }

    BaseEntity(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
