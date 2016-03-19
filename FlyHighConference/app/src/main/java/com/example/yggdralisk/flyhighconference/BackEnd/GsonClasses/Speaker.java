package com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses;

/**
 * Created by yggdralisk on 19.03.16.
 */
public class Speaker {
    int id;
    String name;
    String country;
    String url; //Homepage url
    String image; //Url to image
    int partner; //Patner's id

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public String getUrl() {
        return url;
    }

    public String getImage() {
        return image;
    }

    public int getPartner() {
        return partner;
    }
}
