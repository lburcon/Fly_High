package com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by yggdralisk on 19.03.16.
 */@DatabaseTable(tableName = "users")
public class User {
    @DatabaseField(columnName = "id",id = true)
    int id;
    @DatabaseField String mail;
    @DatabaseField String pass;

    public int getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public String getPass() {
        return pass;
    }
    public User()
    {

    }
}
