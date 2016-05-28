package com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses;

import com.j256.ormlite.android.compat.ApiCompatibility;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yggdralisk on 19.03.16.
 */
@DatabaseTable(tableName = "presentatnions")
public class Presentation implements Serializable {
    @DatabaseField(id = true)
    int id;
    @DatabaseField String title;
    @DatabaseField  String description;
    @DatabaseField(columnName = "start")  String start;
    @DatabaseField String end;
    @DatabaseField String image; //Url to image
    @DatabaseField(columnName = "presentationType") String type;
    @DatabaseField int place; //place id
    @DatabaseField String speakers; //Array of speaker's ids
    @DatabaseField(columnName = "presentationGroup") String group;

    public static Calendar c = Calendar.getInstance();
    public static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

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
    public int getPlace() {
        return place;
    }
    public int[] getSpeakers() {
        String[] numberStrs = speakers.split(",");
        int[] speakersIds = new int[numberStrs.length];
        for(int i = 0;i < numberStrs.length;i++)
            try {
                speakersIds[i] = Integer.parseInt(numberStrs[i]);
            }catch(NumberFormatException ex){ return null;}
        return speakersIds;
    }
    public String getGroup(){
        return group;
    }

    public int getStartDay() throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
        c.setTime(formatter.parse(start));
        return c.get(Calendar.DAY_OF_MONTH);
    }

    public Date getStartTime() throws ParseException {
        return new SimpleDateFormat(DATE_FORMAT).parse(start);
    }

    public Date getEndTime() throws ParseException {
        return new SimpleDateFormat(DATE_FORMAT).parse(end);
    }

    public Presentation() {
    }
}
