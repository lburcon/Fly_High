package com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by yggdralisk on 19.03.16.
 */
@DatabaseTable(tableName = "likes")
public class Like {
    @DatabaseField(columnName = "likeId", generatedId = true)
    int id;
    @DatabaseField
    int question;//question's id
    @DatabaseField
    int user;//user's id

    public int getQuestion() {
        return question;
    }

    public int getUser() {
        return user;
    }

    public Like(){}
}
