package com.example.yggdralisk.flyhighconference;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import org.apache.http.client.protocol.ClientContextConfigurer;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.logging.Handler;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by yggdralisk on 03.03.16.
 */

//Class to make both get and post request to API server. On get, results are loaded into magazine (SharedPreferences/sql database)
//One can retrieve them using according methods from DataGetter class
//For now I have no idea how to make usefull callbacks from this class neither do I know if we do even need them TODO:ServerPoster callbacks
public class ServerConnector {
    private final String DATA_HOST_URL = "http://flyhigh.pwr.edu.pl/api/";

    //------------------------------------------------------------------------DATA_POST_PART --------------------------------------------------------------------------------
    public void postLikeToPresentation(int questionID, int userID, Context context) //Questions_to_speaker - Post like for question to presentation. Returns true on succesful post
    {
        PostRunnable postRunnable = new PostRunnable("like", "?question=" + questionID + "&user=" + userID, context);
        new Thread(postRunnable).start();

    }

    public void postQuestionToPresentation(int presentationID, int userID, String questionContent, Context context)//Questions_to_speaker - Post question for presentation
    {
        PostRunnable postRunnable = new PostRunnable("question", "?presentation=" + presentationID + "&user=" + userID + "&content=" + questionContent, context);
        new Thread(postRunnable).start();
    }

    private class PostRunnable implements Runnable {
        String appendUrl;
        String appendData;
        public boolean succes = true;
        Context context;
        public final MediaType MEDIA_TYPE_MARKDOWN = MediaType.parse("text/x-markdown; charset=utf-8");

        public PostRunnable(String _appendUrl, String _appendData, Context _context) {
            appendData = _appendData;
            appendUrl = _appendUrl;
            context = _context;
        }

        @Override
        public void run() {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(DATA_HOST_URL + appendUrl)
                    .post(RequestBody.create(MEDIA_TYPE_MARKDOWN, appendData))
                    .build();

            Response res = null;
            try {
                res = client.newCall(request).execute();

                if (!res.isSuccessful()) succes = false;
            } catch (IOException e) {
                e.printStackTrace();
                succes = false;
            }

        }
    }

    //------------------------------------------------------------------------DATA_GET_PART ------------------------------------------------------------------------
    public boolean refreshLikes(Context context) {
        GetRunnable getRunnable = new GetRunnable(context, "likes", "", context.getString(R.string.shared_preferences_likes));
        new Thread(getRunnable).start();

        return getRunnable.succes;
    }

    public boolean getQuestionsToPresentation(Context context, int presentationID) {
        GetRunnable getRunnable = new GetRunnable(context, "likes", "?presentation=" + presentationID, context.getString(R.string.shared_preferences_presentation_questions_prefix) + presentationID);
        new Thread(getRunnable).start();

        return getRunnable.succes;
    }

    private class GetRunnable implements Runnable {
        String appendUrl;
        String appendData;
        String sharedPreferencesName;
        public boolean succes = true;
        Context context;

        public GetRunnable(Context _context, String _appendUrl, String _appendData, String _sharedPreferencesName) {
            appendData = _appendData;
            appendUrl = _appendUrl;
            context = _context;
            sharedPreferencesName = _sharedPreferencesName;
        }

        @Override
        public void run() {
            OkHttpClient client = new OkHttpClient();
            String str = new String();
            Request request = new Request.Builder()
                    .url(DATA_HOST_URL + appendUrl + appendData)
                    .build();
            try {
                Response res = client.newCall(request).execute();
                str = res.body().string();
                if (!res.isSuccessful()) succes = false;

                JSONArray jsonArray = new JSONArray(str);

                SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(sharedPreferencesName, jsonArray.toString());

                editor.apply();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
                succes = false;
            }

        }
    }
}


