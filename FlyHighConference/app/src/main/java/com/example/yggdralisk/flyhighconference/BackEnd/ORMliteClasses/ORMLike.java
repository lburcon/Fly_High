package com.example.yggdralisk.flyhighconference.BackEnd.ORMliteClasses;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by yggdralisk on 19.03.16.
 */

@DatabaseTable(tableName = "like")
public class ORMLike {

    @DatabaseField(id = true, generatedId = true)
    int id;
    @DatabaseField
    int question;//ORMQuestion's id
    @DatabaseField
    int user;//ORMUser's id

    public int getORMQuestion() {
        return question;
    }

    public int getORMUser() {
        return user;
    }

    public ORMLike() {
    }
}
