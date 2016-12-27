package com.epam.as.bookhotel.model;

import java.io.InputStream;

public class Photo extends BaseEntity {

    private InputStream imageStream;

    public Photo() {
    }

    public Photo(InputStream in) {

        this.imageStream = in;
    }

    public Photo(Integer id) {

        super(id);
    }

    public InputStream getImageStream() {

        return imageStream;
    }

    public void setImageStream(InputStream imageStream) {

        this.imageStream = imageStream;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + this.getId() +
                '}';
    }
}
