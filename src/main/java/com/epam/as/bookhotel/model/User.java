package com.epam.as.bookhotel.model;


public class User extends BaseEntity {

    private String login;
    private String password;
    private UserType role;

    public User() {
    }

    public User(Integer id, String login, String password, UserType role) {
        super(id);
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User(String login, String password, UserType role) {
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User(String login, String password) {
        this.login = login;
        this.password = password;

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

    public UserType getRole() {
        return role;
    }

    public void setRole(UserType role) {
        this.role = role;
    }
}
