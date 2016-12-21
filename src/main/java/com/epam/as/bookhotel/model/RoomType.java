package com.epam.as.bookhotel.model;

public class RoomType extends BaseEntity {

    private String roomType;
    private String roomTypeEn;
    private String roomTypeRu;

    public RoomType(String roomType) {
        this.roomType = roomType;
    }

    public RoomType(String roomTypeEn, String roomTypeRu) {
        this.roomTypeEn = roomTypeEn;
        this.roomTypeRu = roomTypeRu;
    }

    public RoomType() {
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public String getRoomTypeEn() {
        return roomTypeEn;
    }

    public void setRoomTypeEn(String roomTypeEn) {
        this.roomTypeEn = roomTypeEn;
    }

    public String getRoomTypeRu() {
        return roomTypeRu;
    }

    public void setRoomTypeRu(String roomTypeRu) {
        this.roomTypeRu = roomTypeRu;
    }

    @Override
    public String toString() {
        return roomType;
    }
}

