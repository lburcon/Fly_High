package com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by yggdralisk on 19.03.16.
 */
@DatabaseTable(tableName = "places")
public class Place {
    @DatabaseField(columnName = "id",id = true)
    int id;
    @DatabaseField  String name;
    @DatabaseField double lat;
    @DatabaseField double lon;
    @DatabaseField String image; //Url to image

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

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Place()
    {

    }
}
