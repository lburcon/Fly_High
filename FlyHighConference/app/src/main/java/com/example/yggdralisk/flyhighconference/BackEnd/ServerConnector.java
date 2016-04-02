package com.example.yggdralisk.flyhighconference.BackEnd;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Import;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Like;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Question;
import com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces.ConnectorResultInterface;
import com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces.ImportInterface;
import com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces.LikeInterface;
import com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces.PostLikeInterface;
import com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces.PostQuestionToPresentationInterface;
import com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces.QuestionToPresentationsInterface;
import com.example.yggdralisk.flyhighconference.R;
import com.google.gson.Gson;

import okhttp3.*;
import okhttp3.Response;
import retrofit2.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;

/**
 * Created by yggdralisk on 03.03.16.
 */

//Class to make both get and post request to API server. On get, results are loaded into magazine (SharedPreferences/sql database)
//One can retrieve them using according methods from DataGetter class
public class ServerConnector {
    private final String DATA_HOST_URL = "http://flyhigh.pwr.edu.pl/";
    private Context context;
    private int presentationID;

    //------------------------------------------------------------------------DATA_POST_PART --------------------------------------------------------------------------------
    public void postLikeToQuestion(Context context, int questionID, int userID, final ConnectorResultInterface callback) //Questions_to_speaker - Post like for question to presentation. Returns true on succesful post
    {
        this.context = context;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DATA_HOST_URL)
                .build();

        PostLikeInterface importInterface = retrofit.create(PostLikeInterface.class);
        Call<ResponseBody> call = importInterface.load(questionID, userID);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.code() >= 200 && response.code() < 300) {
                    if (callback != null)
                        callback.onDownloadFinished(true);
                } else {
                    if (callback != null)
                        callback.onDownloadFinished(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (callback != null)
                    callback.onDownloadFinished(false);
            }
        });
    }

    public void postQuestionToPresentation(Context context, int presentationID, int userID, String questionContent, final ConnectorResultInterface callback)//Questions_to_speaker - Post question for presentation
    {
        this.context = context;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DATA_HOST_URL)
                .build();

        PostQuestionToPresentationInterface importInterface = retrofit.create(PostQuestionToPresentationInterface.class);
        Call<ResponseBody> call = importInterface.load(presentationID, userID, questionContent);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, retrofit2.Response<ResponseBody> response) {
                if (response.code() >= 200 && response.code() < 300) {
                    if (callback != null)
                        callback.onDownloadFinished(true);
                } else {
                    if (callback != null)
                        callback.onDownloadFinished(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                if (callback != null)
                    callback.onDownloadFinished(false);
            }
        });
    }

    //------------------------------------------------------------------------DATA_GET_PART ------------------------------------------------------------------------
    public void refreshLikes(Context context, final ConnectorResultInterface callback) {
        this.context = context;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DATA_HOST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        LikeInterface importInterface = retrofit.create(LikeInterface.class);
        Call<Like[]> call = importInterface.load();
        call.enqueue(new Callback<Like[]>() {
            @Override
            public void onResponse(retrofit2.Call<Like[]> call, retrofit2.Response<Like[]> response) {
                if (response.code() >= 200 && response.code() < 300) {
                    saveData(response.body());
                    if (callback != null)
                        callback.onDownloadFinished(true);
                } else {
                    if (callback != null)
                        callback.onDownloadFinished(false);
                }
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

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(DATA_HOST_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ImportInterface importInterface = retrofit.create(ImportInterface.class);
        Call<Import> call = importInterface.load();
        call.enqueue(new Callback<Import>() {
            @Override
            public void onResponse(retrofit2.Call<Import> call, retrofit2.Response<Import> response) {
                if (response.code() >= 200 && response.code() < 300) {
                    saveData(response.body());
                    if (callback != null)
                        callback.onDownloadFinished(true);
                } else {
                    if (callback != null)
                        callback.onDownloadFinished(false);
                }
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
                if (response.code() >= 200 && response.code() < 300) {
                    saveData(response.body());
                    if (callback != null)
                        callback.onDownloadFinished(true);
                } else {
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


