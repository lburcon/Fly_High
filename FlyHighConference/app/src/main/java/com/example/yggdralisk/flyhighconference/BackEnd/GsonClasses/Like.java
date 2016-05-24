package com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.types.IntegerObjectType;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by yggdralisk on 19.03.16.
 */
@DatabaseTable(tableName = "likes")
public class Like {
    @DatabaseField(generatedId = true)
    int id;
    @DatabaseField(columnName = "questionId")
    int question;//question's id
    @DatabaseField
    int user;//user's id

    public int getQuestion() {
        return question;
    }

    public int getUser() {
        return user;
    }

    public Like() {
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Integer) return id == (int) o;
        else return super.equals(o);
    }
}
