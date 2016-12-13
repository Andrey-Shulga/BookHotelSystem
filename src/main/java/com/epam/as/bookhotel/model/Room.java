package com.epam.as.bookhotel.model;

import java.math.BigDecimal;

public class Room extends BaseEntity {

    private RoomType roomType;
    private Bed bed;
    private int number;
    private RoomStatus roomStatus;
    private BigDecimal price;

    public Room(RoomType roomType, Bed bed, int number, RoomStatus roomStatus, BigDecimal price) {
        this.roomType = roomType;
        this.bed = bed;
        this.number = number;
        this.roomStatus = roomStatus;
        this.price = price;
    }

    public Room() {
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public Bed getBed() {
        return bed;
    }

    public void setBed(Bed bed) {
        this.bed = bed;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + this.getId() +
                ", roomType=" + roomType +
                ", bed=" + bed +
                ", number=" + number +
                ", roomStatus=" + roomStatus +
                ", price=" + price +
                '}';
    }
}
