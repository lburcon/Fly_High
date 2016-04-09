package com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by yggdralisk on 19.03.16.
 */@DatabaseTable(tableName = "Speakers")
public class Speaker {
    @DatabaseField(columnName = "id",id = true)
    int id;
    @DatabaseField String name;
    @DatabaseField String country;
    @DatabaseField String description;
    @DatabaseField String url; //Homepage url
    @DatabaseField int partner; //Patner's id
    @DatabaseField String image; //Url to image

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

    public String getDescription() {
        return description;
    }

    public int getPartner() {
        return partner;
    }

    public Speaker()
    {

    }
}
