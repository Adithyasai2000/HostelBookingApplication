package com.example.innfystays;

import java.net.URL;

public class CarouselItem {

    String imageUrl;
    String caption;

    public CarouselItem() {
    }

    public CarouselItem(String imageUrl, String caption) {
        this.imageUrl = imageUrl;
        this.caption = caption;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }
}
