package com.epam.as.bookhotel.model;

public class ConfirmationOrder extends BaseEntity {

    private Order order;
    private Room room;

    public ConfirmationOrder(Order order, Room room) {
        this.order = order;
        this.room = room;
    }

    public ConfirmationOrder(int id, Order order, Room room) {
        super(id);
        this.order = order;
        this.room = room;
    }

    public ConfirmationOrder() {
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "ConfirmationOrder{" +
                "confirmationId= " + this.getId() +
                ", order=" + order +
                ", room=" + room +
                '}';
    }
}
