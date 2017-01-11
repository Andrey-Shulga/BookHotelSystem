package com.epam.bookhotel.entity;

public class OrderStatus extends BaseEntity {

    private String status;
    private String statusEn;
    private String statusRu;

    public OrderStatus() {
    }

    public OrderStatus(String status) {
        this.status = status;
    }

    public OrderStatus(String statusEn, String statusRu) {
        this.statusEn = statusEn;
        this.statusRu = statusRu;
    }

    public String getStatusEn() {
        return statusEn;
    }

    public void setStatusEn(String statusEn) {
        this.statusEn = statusEn;
    }

    public String getStatusRu() {
        return statusRu;
    }

    public void setStatusRu(String statusRu) {
        this.statusRu = statusRu;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
