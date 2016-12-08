package com.epam.as.bookhotel.model;

public class Order extends BaseEntity {

    private int userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String checkIn;
    private String checkOut;
    private int bed;
    private String roomType;

    public Order(Integer id, int userId, String firstName, String lastName, String email, String phone, String checkIn, String checkOut, int bed, String roomType) {
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

    public Order(String firstName, String lastName, String email, String phone, String checkIn, String checkOut, int bed, String roomType) {
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

    public String getCheckin() {
        return checkIn;
    }

    public void setCheckin(String checkin) {
        this.checkIn = checkin;
    }

    public String getCheckout() {
        return checkOut;
    }

    public void setCheckout(String checkout) {
        this.checkOut = checkout;
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
}
