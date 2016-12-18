package com.epam.as.bookhotel.model;

import java.math.BigDecimal;
import java.sql.Date;

public class Order extends BaseEntity {

    private User user;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Date checkIn;
    private Date checkOut;
    private Bed bed;
    private RoomType roomType;
    private OrderStatus status;
    private Room room;
    private BigDecimal fullCost;

    public Order(User user, String firstName, String lastName, String email, String phone, Date checkIn, Date checkOut, Bed bed, RoomType roomType) {
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.bed = bed;
        this.roomType = roomType;

    }

    public Order(int id, User user, String firstName, String lastName, String email, String phone, Date checkIn, Date checkOut, Bed bed, RoomType roomType, OrderStatus status, Room room, BigDecimal fullCost) {
        super(id);
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.bed = bed;
        this.roomType = roomType;
        this.status = status;
        this.room = room;
        this.fullCost = fullCost;
    }

    public Order() {
    }

    public Order(User user, String firstName, String lastName, String email, String phone, Date checkIn, Date checkOut, Bed bed, RoomType roomType, Room room, BigDecimal fullCost) {
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.bed = bed;
        this.roomType = roomType;
        this.room = room;
        this.fullCost = fullCost;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public Bed getBed() {
        return bed;
    }

    public void setBed(Bed bed) {
        this.bed = bed;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public BigDecimal getFullCost() {
        return fullCost;
    }

    public void setFullCost(BigDecimal fullCost) {
        this.fullCost = fullCost;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + this.getId() +
                ", user=" + user +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                ", bed=" + bed +
                ", roomType='" + roomType + '\'' +
                ", status='" + status + '\'' +
                ", room=" + room +
                ", fullCost=" + fullCost +
                '}';
    }
}
