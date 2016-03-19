package com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses;

/**
 * Created by yggdralisk on 19.03.16.
 */
public class Partner {
    int id;
    String type;
    String name;
    String url; //Homepage url
    String logo; //Url to logo img

    public int getId() {
        return id;
    }
    public String getType() {
        return type;
    }
    public String getName() {
        return name;
    }
    public String getUrl() {
        return url;
    }
    public String getLogo() {
        return logo;
    }
}
