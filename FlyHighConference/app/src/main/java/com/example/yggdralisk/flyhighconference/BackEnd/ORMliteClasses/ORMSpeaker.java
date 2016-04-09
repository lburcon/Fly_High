package com.example.yggdralisk.flyhighconference.BackEnd.ORMliteClasses;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by yggdralisk on 19.03.16.
 */

@DatabaseTable(tableName = "speaker")
public class ORMSpeaker {
    @DatabaseField(id = true, generatedId = false)
    int id;
    @DatabaseField
    String name;
    @DatabaseField
    String country;
    @DatabaseField
    String description;
    @DatabaseField
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

    public ORMSpeaker() {
    }
}
