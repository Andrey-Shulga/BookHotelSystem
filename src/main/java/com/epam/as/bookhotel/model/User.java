package com.epam.as.bookhotel.model;


public class User extends BaseEntity {

    private String login;
    private String password;
    private UserRole role;

    public User() {
    }

    public User(String login) {

        this.login = login;
    }

    public User(int id, String login, String password, UserRole role) {
        super(id);
        this.login = login;
        this.password = password;
        this.role = role;
    }

    public User(String login, String password, UserRole role) {
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

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + this.getId() +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
