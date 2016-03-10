package com.example.yggdralisk.flyhighconference;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.logging.Handler;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by yggdralisk on 29.02.16.
 */
public class DataGetter {
    private final String DATA_HOST_URL = "http://flyhigh.pwr.edu.pl/api/";

    public static JSONArray getSpeakerHasPresentations(Context context) throws JSONException {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(context.getString(R.string.shared_preferences_speaker_has_presentations), "");
        return new JSONArray(str);
    }

    public static JSONArray getPresentations(Context context) throws JSONException {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(context.getString(R.string.shared_preferences_presentations), "");
        return new JSONArray(str);
    }

    public static JSONArray getQuestionsToPresentation(Context context, int presentationID) throws JSONException {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(context.getString(R.string.shared_preferences_presentation_questions_prefix) + presentationID, "");
        return new JSONArray(str);
    }

    public static JSONArray getSpeakers(Context context) throws JSONException {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(context.getString(R.string.shared_preferences_speakers), "");
        return new JSONArray(str);
    }

    public static JSONArray getPartners(Context context) throws JSONException {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(context.getString(R.string.shared_preferences_partners), "");
        return new JSONArray(str);
    }

    public static JSONArray getUsers(Context context) throws JSONException {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(context.getString(R.string.shared_preferences_users), "");
        return new JSONArray(str);
    }

    public static JSONArray getPlaces(Context context) throws JSONException {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(context.getString(R.string.shared_preferences_places), "");
        return new JSONArray(str);
    }

    public static JSONArray getLikes(Context context) throws JSONException {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(context.getString(R.string.shared_preferences_likes), "");
        return new JSONArray(str);
    }

    public static JSONArray getOrganisers(Context context) throws JSONException {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(context.getString(R.string.shared_preferences_organisers), "");
        return new JSONArray(str);
    }

    public static JSONObject getSpeakerById(int speakerId,Context context) throws JSONException {
        JSONArray speakers = getSpeakers(context);
        int i = 0;
        for(; i < speakers.length() && (speakers.getJSONObject(i).getInt("id") != speakerId); i++)
        {}
        return speakers.getJSONObject(i);
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
