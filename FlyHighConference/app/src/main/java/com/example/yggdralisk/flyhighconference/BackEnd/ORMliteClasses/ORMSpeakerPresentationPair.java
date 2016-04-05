package com.example.yggdralisk.flyhighconference.BackEnd.ORMliteClasses;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by yggdralisk on 19.03.16.
 */
@DatabaseTable(tableName = "speakerpresentationpair")
public class ORMSpeakerPresentationPair { //This class stores a pair of speaker and his ORMPresentation
    @DatabaseField(id = true, generatedId = true)
    int id;
    @DatabaseField
    int speaker;//ORMSpeaker's id
    @DatabaseField
    int presentation;//ORMPresentation's id

    public int getSpeaker() {
        return speaker;
    }

    public int getpresentation() {
        return presentation;
    }
}
