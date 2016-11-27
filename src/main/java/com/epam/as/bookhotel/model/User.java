package com.epam.as.bookhotel.model;


public class User extends BaseEntity {

    private String login;
    private String password;
    private String role;

    public User() {
    }

    public User(Integer id, String login, String password, String role) {
        super(id);
        this.login = login;
        this.password = password;
        this.role = role;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}