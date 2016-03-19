package com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses;

/**
 * Created by yggdralisk on 19.03.16.
 */
public class Presentation {
    int id;
    String title;
    String description;
    String start;
    String end;
    String image; //Url to image
    String type;
    int place; //Place id
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
    public int getPlace() {
        return place;
    }
    public int[] getSpeakers() {
        String[] numberStrs = speakers.split(",");
        int[] speakersIds = new int[numberStrs.length];
        for(int i = 0;i < numberStrs.length;i++)
            speakersIds[i] = Integer.parseInt(numberStrs[i]);
        return speakersIds;
    }
}
