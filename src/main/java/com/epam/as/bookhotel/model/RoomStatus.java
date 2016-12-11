package com.epam.as.bookhotel.model;

public class RoomStatus extends BaseEntity {

    private String roomStatus;

    public RoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }


    public String getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(String roomStatus) {
        this.roomStatus = roomStatus;
    }

    @Override
    public String toString() {
        return roomStatus;
    }
}
