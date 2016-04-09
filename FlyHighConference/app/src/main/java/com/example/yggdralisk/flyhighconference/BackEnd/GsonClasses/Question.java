package com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by yggdralisk on 19.03.16.
 */
@DatabaseTable(tableName = "questions")
public class Question {
    @DatabaseField(columnName = "id",id = true)
    int id;
    @DatabaseField(columnName = "presentationId") int presentation; //Presentation's id
    @DatabaseField String content;
    @DatabaseField int user; //Asking user's id
    @DatabaseField int rating;

    public int getId() {
        return id;
    }

    public int getPresentation() {
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

    public Question() {
    }
}
