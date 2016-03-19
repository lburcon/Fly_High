package com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses;

/**
 * Created by yggdralisk on 19.03.16.
 */
public class Place {
    int id;
    String name;
    double lat;
    double lon;
    String image; //Url to image

    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getLat() {
        return lat;
    }
    public double getLon() {
        return lon;
    }
    public String getImage() {
        return image;
    }
}
