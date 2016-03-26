package com.example.yggdralisk.flyhighconference.BackEnd;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Import;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Like;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Question;
import com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces.ImportInterface;
import com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces.LikeInterface;
import com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces.QuestionToPresentationsInterface;
import com.example.yggdralisk.flyhighconference.R;
import com.google.gson.Gson;

import okhttp3.*;
import okhttp3.Response;
import retrofit2.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yggdralisk on 03.03.16.
 */

//Class to make both get and post request to API server. On get, results are loaded into magazine (SharedPreferences/sql database)
//One can retrieve them using according methods from DataGetter class
public class ServerConnector {
    private final String DATA_HOST_URL = "http://flyhigh.pwr.edu.pl/";
    private Context context;
    private ConnectorResultInterface callback;
    private int presentationID;

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
    public void refreshLikes(Context context, final ConnectorResultInterface callback) {
        this.context = context;
        this.callback = callback;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DATA_HOST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LikeInterface importInterface = retrofit.create(LikeInterface.class);
        Call<Like[]> call = importInterface.load();
        call.enqueue(new Callback<Like[]>() {
            @Override
            public void onResponse(retrofit2.Call<Like[]> call, retrofit2.Response<Like[]> response) {
                if (response.code() >= 200 && response.code() < 300)
                    saveData(response.body());
                if (callback != null)
                    callback.onDownloadFinished(true);
            }

            @Override
            public void onFailure(retrofit2.Call<Like[]> call, Throwable t) {
                if (callback != null)
                    callback.onDownloadFinished(false);
            }
        });
    }

    public void refreshData(Context context, final ConnectorResultInterface callback) {
        this.context = context;
        this.callback = callback;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DATA_HOST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ImportInterface importInterface = retrofit.create(ImportInterface.class);
        Call<Import> call = importInterface.load();
        call.enqueue(new Callback<Import>() {
            @Override
            public void onResponse(retrofit2.Call<Import> call, retrofit2.Response<Import> response) {
                if (response.code() >= 200 && response.code() < 300)
                    saveData(response.body());
                if (callback != null)
                    callback.onDownloadFinished(true);
            }

            @Override
            public void onFailure(retrofit2.Call<Import> call, Throwable t) {
                if (callback != null)
                    callback.onDownloadFinished(false);
            }
        });
    }

    public void getQuestionsToPresentation(Context context, int presentationID, final ConnectorResultInterface callback) {
        this.context = context;
        this.callback = callback;
        this.presentationID = presentationID;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DATA_HOST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        QuestionToPresentationsInterface importInterface = retrofit.create(QuestionToPresentationsInterface.class);
        Call<Question[]> call = importInterface.load(presentationID);
        call.enqueue(new Callback<Question[]>() {
            @Override
            public void onResponse(retrofit2.Call<Question[]> call, retrofit2.Response<Question[]> response) {
                if (response.code() >= 200 && response.code() < 300){
                    saveData(response.body());
                if (callback != null)
                    callback.onDownloadFinished(true);
                }
                else {
                    if (callback != null)
                        callback.onDownloadFinished(false);
                }
            }

            @Override
            public void onFailure(retrofit2.Call<Question[]> call, Throwable t) {
                if (callback != null)
                    callback.onDownloadFinished(false);
            }
        });
    }

    private void saveData(Object data) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Gson gson = new Gson();
        if (data instanceof Import) {
            editor.putString(context.getString(R.string.shared_preferences_organisers), gson.toJson(((Import) data).getOrganisers()));
            editor.putString(context.getString(R.string.shared_preferences_partners), gson.toJson(((Import) data).getPartners()));
            editor.putString(context.getString(R.string.shared_preferences_places), gson.toJson(((Import) data).getPlaces()));
            editor.putString(context.getString(R.string.shared_preferences_presentations), gson.toJson(((Import) data).getPresentations()));
            editor.putString(context.getString(R.string.shared_preferences_speakers), gson.toJson(((Import) data).getSpeakers()));
            editor.putString(context.getString(R.string.shared_preferences_users), gson.toJson(((Import) data).getUsers()));
            editor.putString(context.getString(R.string.shared_preferences_likes), gson.toJson(((Import) data).getLikes()));
            editor.putString(context.getString(R.string.shared_preferences_speaker_has_presentations), gson.toJson(((Import) data).getSpeakerPresentationPairs()));
        } else if (data instanceof Like[]) {
            editor.putString(context.getString(R.string.shared_preferences_likes), gson.toJson(data));
        } else if (data instanceof Question[]) {
            editor.putString(context.getString(R.string.shared_preferences_presentation_questions_prefix) + presentationID, gson.toJson(data));
         } else {
        }

        editor.apply();


    }
}


