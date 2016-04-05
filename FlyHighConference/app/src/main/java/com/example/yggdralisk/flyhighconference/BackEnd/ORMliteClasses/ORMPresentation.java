package com.example.yggdralisk.flyhighconference.BackEnd.ORMliteClasses;

import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Presentation;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.ArrayList;

/**
 * Created by yggdralisk on 19.03.16.
 */
@DatabaseTable(tableName = "presentatnion")
public class ORMPresentation {
    @DatabaseField(id = true, generatedId = false)
    int id;
    @DatabaseField
    String title;
    @DatabaseField
    String description;
    @DatabaseField
    String start;
    @DatabaseField
    String end;
    @DatabaseField
    String image; //Url to image
    @DatabaseField
    String type;
    @DatabaseField
    int place; //Place id
    @DatabaseField
    String speakers; //Array of speaker's ids

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getImage() {
        return image;
    }

    public String getType() {
        return type;
    }

    public int place() {
        return place;
    }

    public int[] getSpeakers() {
        String[] numberStrs = speakers.split(",");
        int[] speakersIds = new int[numberStrs.length];
        for(int i = 0;i < numberStrs.length;i++)
            speakersIds[i] = Integer.parseInt(numberStrs[i]);
        return speakersIds;
    }

    public ORMPresentation() {
    }

    public ORMPresentation(Presentation p)
    {

    }
}
