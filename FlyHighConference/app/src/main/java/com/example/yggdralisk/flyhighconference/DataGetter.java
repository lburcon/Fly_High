package com.example.yggdralisk.flyhighconference;

import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yggdralisk on 29.02.16.
 */
public class DataGetter {
    public static JSONArray getPresentations(Context context) throws JSONException {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(context.getString(R.string.shared_preferences_presentations), "");
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

    public static String getLoogedUserName(Context context) {
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

    public static Boolean toggleUserLogged(Context context,String userName)//Returns true if method logged user in or false otherwise
    {
        if(userName == null || userName == "" || !checkUserLogged(context)) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(context.getString(R.string.shared_preferences_user_logged), false);
            editor.putString(context.getString(R.string.shared_preferences_logged_user_name), "");
            return false;
        }
        else {
            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(context.getString(R.string.shared_preferences_user_logged), true);
            editor.putString(context.getString(R.string.shared_preferences_logged_user_name), userName);
            editor.apply();
            return true;
        }
    }

}
