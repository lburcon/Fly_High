package com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses;

/**
 * Created by yggdralisk on 19.03.16.
 */
public class Speaker {
    int id;
    String name;
    String country;
    String description;

    String url; //Homepage url

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

    String image; //Url to image

    public String getDescription() {
        return description;
    }

    public int getPartner() {
        return partner;
    }
}
