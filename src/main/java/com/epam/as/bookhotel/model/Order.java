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
    private int bedId;
    private int bed;
    private int roomTypeId;
    private String roomType;
    private int statusId;
    private String status;


    public Order(Integer id, int userId, String firstName, String lastName, String email, String phone, LocalDate checkIn, LocalDate checkOut, int bedId, int bed, int roomTypeId, String roomType, int statusId, String status) {
        super(id);
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.bedId = bedId;
        this.bed = bed;
        this.roomTypeId = roomTypeId;
        this.roomType = roomType;
        this.statusId = statusId;
        this.status = status;
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

    public int getStatusId() {
        return statusId;
    }

    public void setStatusId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getBedId() {
        return bedId;
    }

    public void setBedId(int bedId) {
        this.bedId = bedId;
    }

    public int getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(int roomTypeId) {
        this.roomTypeId = roomTypeId;
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
        return "Order{" +
                "userId=" + userId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", bed=" + bed +
                ", roomType='" + roomType + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
