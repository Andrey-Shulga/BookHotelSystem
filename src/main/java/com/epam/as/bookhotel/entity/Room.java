package com.epam.as.bookhotel.entity;

import java.math.BigDecimal;

public class Room extends BaseEntity {

    private RoomType roomType;
    private Bed bed;
    private int number;
    private BigDecimal price;
    private Photo photo;


    public Room(RoomType roomType, Bed bed, int number, BigDecimal price) {

        this.roomType = roomType;
        this.bed = bed;
        this.number = number;
        this.price = price;
    }

    public Room(RoomType roomType, Bed bed, int number, BigDecimal price, Photo photo) {

        this.roomType = roomType;
        this.bed = bed;
        this.number = number;
        this.price = price;
        this.photo = photo;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + this.getId() +
                ", roomType=" + roomType +
                ", bed=" + bed +
                ", number=" + number +
                ", price=" + price +
                ", photo=" + photo +
                '}';
    }
}
