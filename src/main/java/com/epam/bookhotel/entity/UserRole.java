package com.epam.bookhotel.entity;

public class UserRole extends BaseEntity {

    private UserType role;

    public UserRole(UserType role) {
        this.role = role;
    }

    public UserType getRole() {
        return role;
    }

    public void setRole(UserType role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role.toString();
    }
}
