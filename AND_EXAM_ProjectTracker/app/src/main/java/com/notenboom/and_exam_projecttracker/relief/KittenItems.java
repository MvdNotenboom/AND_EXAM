package com.notenboom.and_exam_projecttracker.relief;

/*************************************************************************
 * Object class to hold image URL's from json data for displaying when needed
 ************************************************************************/

public class KittenItems {
    private String imgUrl;

    public KittenItems(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }
}
