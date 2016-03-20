package com.example.yggdralisk.flyhighconference.BackEnd;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Like;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Organiser;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Partner;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Place;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Presentation;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Question;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Speaker;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.SpeakerPresentationPair;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.User;
import com.example.yggdralisk.flyhighconference.R;
import com.google.gson.Gson;

import org.json.JSONException;

/**
 * Created by yggdralisk on 29.02.16.
 */
public class DataGetter {
    private final String DATA_HOST_URL = "http://flyhigh.pwr.edu.pl/api/";

    public static SpeakerPresentationPair[] getSpeakerHasPresentations(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(context.getString(R.string.shared_preferences_speaker_has_presentations), "");
        return new Gson().fromJson(str, SpeakerPresentationPair[].class);
    }

    public static Presentation[]  getPresentations(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(context.getString(R.string.shared_preferences_presentations), "");
        return new Gson().fromJson(str, Presentation[].class);
    }

    public static Question[] getQuestionsToPresentation(Context context, int presentationID) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(context.getString(R.string.shared_preferences_presentation_questions_prefix) + presentationID, "");
        return new Gson().fromJson(str, Question[].class);
    }

    public static Speaker[] getSpeakers(Context context)  {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(context.getString(R.string.shared_preferences_speakers), "");
        return new Gson().fromJson(str, Speaker[].class);
    }

    public static Partner[] getPartners(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(context.getString(R.string.shared_preferences_partners), "");
        return new Gson().fromJson(str, Partner[].class);
    }

    public static User[] getUsers(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(context.getString(R.string.shared_preferences_users), "");
        return new Gson().fromJson(str, User[].class);
    }

    public static Place[] getPlaces(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(context.getString(R.string.shared_preferences_places), "");
        return new Gson().fromJson(str, Place[].class);
    }

    public static Like[] getLikes(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(context.getString(R.string.shared_preferences_likes), "");
        return new Gson().fromJson(str, Like[].class);
    }

    public static Organiser[] getOrganisers(Context context)  {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(context.getString(R.string.shared_preferences_organisers), "");
        return new Gson().fromJson(str, Organiser[].class);
    }

    public static Speaker getSpeakerById(int speakerId,Context context) {
        Speaker[] speakers = getSpeakers(context);
        int i = 0;
        for(; i < speakers.length && (speakers[i].getId() != speakerId); i++)
        {}
        return speakers[i];
    }

    public static Presentation getPresentationById(int presentationId,Context context) {

        Presentation[] presentations = getPresentations(context);
        int i = 0;
        for(; i < presentations.length && (presentations[i].getId() != presentationId); i++)
        {}
        return presentations[i];
    }

    public static Boolean checkUserLogged(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(context.getString(R.string.shared_preferences_user_logged), false);
    }

    public static String getLoggedUserName(Context context) {
        if(checkUserLogged(context))
        {
            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
            return sharedPreferences.getString(context.getString(R.string.shared_preferences_logged_user_name), "");
        }
        else
        {
            return "";
        }
    }

    public static int getLoggedUserId(Context context) {
        if(checkUserLogged(context))
        {
            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
            return sharedPreferences.getInt(context.getString(R.string.shared_preferences_logged_user_id), -1);
        }
        else
        {
            return -1;
        }
    }

    public static Boolean toggleUserLogged(Context context,String userName, int userId)//Returns true if method logged user in or false otherwise.
    {
        if(checkUserLogged(context)  ) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(context.getString(R.string.shared_preferences_user_logged), false);
            editor.putString(context.getString(R.string.shared_preferences_logged_user_name), "");
            editor.putInt(context.getString(R.string.shared_preferences_logged_user_id), -1);
            editor.apply();
            return false;
        }
        else {
            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(context.getString(R.string.shared_preferences_user_logged), true);
            editor.putString(context.getString(R.string.shared_preferences_logged_user_name), userName);
            editor.putInt(context.getString(R.string.shared_preferences_logged_user_id),userId);
            editor.apply();
            return true;
        }
    }
}
