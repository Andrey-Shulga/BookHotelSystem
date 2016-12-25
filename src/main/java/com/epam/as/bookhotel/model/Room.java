package com.epam.as.bookhotel.model;

import javax.servlet.http.Part;
import java.math.BigDecimal;

public class Room extends BaseEntity {

    private RoomType roomType;
    private Bed bed;
    private int number;
    private BigDecimal price;
    private Part photoPart;

    public Room(RoomType roomType, Bed bed, int number, BigDecimal price) {
        this.roomType = roomType;
        this.bed = bed;
        this.number = number;
        this.price = price;
    }

    public Room(RoomType roomType, Bed bed, int number, BigDecimal price, Part photoPart) {
        this.roomType = roomType;
        this.bed = bed;
        this.number = number;
        this.price = price;
        this.photoPart = photoPart;
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

    public Part getPhotoPart() {
        return photoPart;
    }

    public void setPhotoPart(Part photoPart) {
        this.photoPart = photoPart;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + this.getId() +
                ", roomType=" + roomType +
                ", bed=" + bed +
                ", number=" + number +
                ", price=" + price +
                ", photoPart=" + photoPart.toString() +
                '}';
    }
}
