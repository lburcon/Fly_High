package com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses;

/**
 * Created by yggdralisk on 19.03.16.
 */

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "users")
public class SpeakerPresentationPair { //This class stores a pair of speaker and his presentation
    @DatabaseField(columnName = "id",generatedId = true)
    int id;
    @DatabaseField(columnName = "speaker") int speaker;//ORMSpeaker's id
    @DatabaseField(columnName = "presentation") int presentation;//ORMPresentation's id

    public int getSpeaker() {
        return speaker;
    }

    public int getPresentation() {
        return presentation;
    }
    public SpeakerPresentationPair()
    {

    }
}
