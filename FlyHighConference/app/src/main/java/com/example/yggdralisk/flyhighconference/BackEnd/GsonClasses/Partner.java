package com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by yggdralisk on 19.03.16.
 */
@DatabaseTable(tableName = "partners")
public class Partner {
    @DatabaseField(columnName = "id",id = true)
    int id;
    @DatabaseField String type;
    @DatabaseField String name;
    @DatabaseField String url; //Homepage url
    @DatabaseField  String logo; //Url to logo img

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

    public Partner()
    {

    }
}
