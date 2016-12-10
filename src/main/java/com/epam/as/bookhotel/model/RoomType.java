package com.epam.as.bookhotel.model;

public class RoomType extends BaseEntity {

    private String roomType;

    public RoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return roomType;
    }
}

