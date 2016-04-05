package com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses;

/**
 * Created by yggdralisk on 19.03.16.
 */
public class Question {
    int id;
    int presentation; //ORMPresentation's id
    String content;
    int user; //Asking user's id
    int rating;

    public int getId() {
        return id;
    }

    public int getPresentation() {
        return presentation;
    }

    public String getContent() {
        return content;
    }

    public int getUser() {
        return user;
    }

    public int getRating() {
        return rating;
    }
}
