package com.epam.as.bookhotel.model;

import java.time.LocalDate;

public class Order extends BaseEntity {

    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private LocalDate checkIn;
    private LocalDate checkOut;
    private int bed;
    private String roomType;

    public Order(Integer id, int userId, String firstName, String lastName, String email, String phone, LocalDate checkIn, LocalDate checkOut, int bed, String roomType) {
        super(id);
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.bed = bed;
        this.roomType = roomType;
    }


    public Order(int userId, String firstName, String lastName, String email, String phone, LocalDate checkIn, LocalDate checkOut, int bed, String roomType) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.bed = bed;
        this.roomType = roomType;
    }


    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDate checkIn) {
        this.checkIn = checkIn;
    }

    public LocalDate getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDate checkOut) {
        this.checkOut = checkOut;
    }

    public int getBed() {
        return bed;
    }

    public void setBed(int bed) {
        this.bed = bed;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Order{" + "id=" + this.getId() +
                ", userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", bed=" + bed +
                ", roomType='" + roomType + '\'' +
                '}';
    }
}
