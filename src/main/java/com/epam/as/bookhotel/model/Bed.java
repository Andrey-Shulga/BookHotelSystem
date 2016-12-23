package com.epam.as.bookhotel.model;

public class Bed extends BaseEntity {

    private int bed;

    public Bed(int bed) {
        this.bed = bed;
    }

    public Bed() {

    }

    public int getBed() {
        return bed;
    }

    public void setBed(int bed) {
        this.bed = bed;
    }

    @Override
    public String toString() {
        return String.valueOf(bed);
    }
}
