package com.example.lab23vnexpress;

import android.graphics.Bitmap;

public class NewsItem {
    private String title;
    private String description;
    private String link;
    private String imageUrl;
    private Bitmap imageBitmap;

    public NewsItem(String title, String description, String link, String imageUrl) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.imageUrl = imageUrl;
    }

    public NewsItem() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }
}