package com.epam.as.bookhotel.model;

import java.io.InputStream;

public class Photo extends BaseEntity {

    private InputStream imageStream;
    private String contentType;
    private long contentLength;

    public Photo(InputStream imageStream, String contentType, long contentLength) {

        this.imageStream = imageStream;
        this.contentType = contentType;
        this.contentLength = contentLength;
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

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public long getContentLength() {
        return contentLength;
    }

    public void setContentLength(long contentLength) {
        this.contentLength = contentLength;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "id=" + this.getId() +
                ", contentType=" + contentType +
                ", contentLength=" + contentLength +
                '}';
    }
}
