package com.example.yggdralisk.flyhighconference.BackEnd.ORMliteClasses;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by yggdralisk on 19.03.16.
 */
@DatabaseTable(tableName = "ORMUser")
public class ORMUser {
    @DatabaseField(id = true, generatedId = false)
    int id;
    @DatabaseField
    String mail;
    @DatabaseField
    String pass;

    public int getId() {
        return id;
    }

    public String getMail() {
        return mail;
    }

    public String getPass() {
        return pass;
    }

    public ORMUser() {
    }
}
