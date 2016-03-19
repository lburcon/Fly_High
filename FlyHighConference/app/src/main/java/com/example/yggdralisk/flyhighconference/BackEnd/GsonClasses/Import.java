package com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yggdralisk on 19.03.16.
 */
public class Import {
    List<Like> likes;
    List<Organiser> organisers;
    List<Partner> partners;
    List<Place> places;
    List<Presentation> presentations;
    List<Question> questions;
    List<Speaker> speakers;
    List<SpeakerPresentationPair> speakerPresentationPairs;
    List<User> users;

    public List<Like> getLikes() {
        return likes;
    }
    public List<Organiser> getOrganisers() {
        return organisers;
    }
    public List<Partner> getPartners() {
        return partners;
    }
    public List<Place> getPlaces() {
        return places;
    }
    public List<Presentation> getPresentations() {
        return presentations;
    }
    public List<Question> getQuestions() {
        return questions;
    }
    public List<Speaker> getSpeakers() {
        return speakers;
    }
    public List<SpeakerPresentationPair> getSpeakerPresentationPairs() {
        return speakerPresentationPairs;
    }

    public List<User> getUsers() {
        return users;
    }

    public Import()
    {
        likes = new ArrayList<>();
        organisers = new ArrayList<>();
        partners  = new ArrayList<>();
        places = new ArrayList<>();
        presentations = new ArrayList<>();
        questions = new ArrayList<>();
        speakers = new ArrayList<>();
        speakerPresentationPairs = new ArrayList<>();
        users  = new ArrayList<>();
    }

    public static Import parseJSON(String response)
    {
        Gson gson = new GsonBuilder().create();
        return gson.fromJson(response, Import.class);

    }
}
