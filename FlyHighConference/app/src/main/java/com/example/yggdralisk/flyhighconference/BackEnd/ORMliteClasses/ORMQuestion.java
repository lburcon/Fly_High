package com.example.yggdralisk.flyhighconference.BackEnd.ORMliteClasses;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by yggdralisk on 19.03.16.
 */
@DatabaseTable(tableName = "ORMQuestion")
public class ORMQuestion {
    @DatabaseField(id = true, generatedId = false)
    int id;
    @DatabaseField
    int presentation; //ORMPresentation's id
    @DatabaseField
    String content;
    @DatabaseField
    int user; //Asking ORMUser's id
    @DatabaseField
    int rating;

    public int getId() {
        return id;
    }

    public int presentation() {
        return presentation;
    }

    public String getContent() {
        return content;
    }

    public int getUser() {
        return user;
    }

    public int getRating() {
        return rating;
    }

    public ORMQuestion() {
    }
}
