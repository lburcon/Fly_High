package com.example.yggdralisk.flyhighconference.BackEnd;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Import;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Like;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Organiser;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Partner;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Place;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Presentation;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Question;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Speaker;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.SpeakerPresentationPair;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.User;
import com.example.yggdralisk.flyhighconference.BackEnd.ORMliteClasses.DaoFactory;
import com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces.ConnectorResultInterface;
import com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces.ImportInterface;
import com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces.LikeInterface;
import com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces.PostLikeInterface;
import com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces.PostQuestionToPresentationInterface;
import com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces.QuestionToPresentationsInterface;
import com.example.yggdralisk.flyhighconference.R;
import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;

import okhttp3.*;
import okhttp3.Response;
import retrofit2.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.converter.gson.GsonConverterFactory;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.Callable;

/**
 * Created by yggdralisk on 03.03.16.
 */

//Class to make both get and post request to API server. On get, results are loaded into magazine (SharedPreferences/sql database)
//One can retrieve them using according methods from DataGetter class
public class ServerConnector {
    private final String DATA_HOST_URL = "http://flyhigh.pwr.edu.pl/";
    private Context context;
    private Application application;
    private int presentationID;

    //------------------------------------------------------------------------DATA_POST_PART --------------------------------------------------------------------------------
    public void postLikeToQuestion(Application application, Context context,
                                   int questionID, int userID, final ConnectorResultInterface callback) //Questions_to_speaker - Post like for question to presentation. Returns true on succesful post
    {
        this.context = context;
        this.application = application;

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

    public void postQuestionToPresentation(Application application, Context context,
                                           int presentationID, int userID, String questionContent, final ConnectorResultInterface callback)//Questions_to_speaker - Post question for presentation
    {
        this.context = context;
        this.application = application;

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
    public void refreshLikes(Application application, Context context, final ConnectorResultInterface callback) {
        this.context = context;
        this.application = application;

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

    public void refreshData(Application application, Context context, final ConnectorResultInterface callback) {
        this.context = context;
        this.application = application;

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

    public void getQuestionsToPresentation(Application application, Context context, int presentationID, final ConnectorResultInterface callback) {
        this.context = context;
        this.application = application;
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

    private void saveData(final Object data) {
        DaoFactory daoFactory = (DaoFactory) application;

        try {
            final Dao<Like, Integer> ormLikes = daoFactory.getOrmLikes();
            final Dao<User, Integer> ormUsers = daoFactory.getOrmUsers();
            final Dao<Organiser, Integer> ormOrganisers = daoFactory.getOrmOrganisers();
            final Dao<Presentation, Integer> ormPresentations = daoFactory.getOrmPresentations();
            final Dao<SpeakerPresentationPair, Integer> ormSpeakerPresentationPairs = daoFactory.getOrmSpeakerPresentationPairs();
            final Dao<Partner, Integer> ormPartners = daoFactory.getOrmPartners();
            final Dao<Place, Integer> ormPlaces = daoFactory.getOrmPlaces();
            final Dao<Question, Integer> ormQuestions = daoFactory.getOrmQuestions();
            final Dao<Speaker, Integer> ormSpeakers = daoFactory.getOrmSpeakers();
            if (data instanceof Import) {

                ormOrganisers.callBatchTasks(new Callable<Void>() {
                    public Void call() throws Exception {
                        for (Organiser o : ((Import) data).getOrganisers())
                            ormOrganisers.createOrUpdate(o);
                        return null;
                    }
                });

                ormPresentations.callBatchTasks(new Callable<Void>() {
                    public Void call() throws Exception {
                        for (Presentation o : ((Import) data).getPresentations())
                            ormPresentations.createOrUpdate(o);
                        return null;
                    }
                });


                ormQuestions.callBatchTasks(new Callable<Void>() {
                    public Void call() throws Exception {
                        for (Question o : ((Import) data).getQuestions())
                        if(o.getContent() != "")
                            ormQuestions.createOrUpdate(o);
                        return null;
                    }
                });

                ormLikes.callBatchTasks(new Callable<Void>() {
                    public Void call() throws Exception {
                        for (Like o : ((Import) data).getLikes())
                            ormLikes.createOrUpdate(o);
                        return null;
                    }
                });

                ormPartners.callBatchTasks(new Callable<Void>() {
                    public Void call() throws Exception {
                        for (Partner o : ((Import) data).getPartners())
                            ormPartners.createOrUpdate(o);
                        return null;
                    }
                });


                ormPlaces.callBatchTasks(new Callable<Void>() {
                    public Void call() throws Exception {
                        for (Place o : ((Import) data).getPlaces())
                            ormPlaces.createOrUpdate(o);
                        return null;
                    }
                });


                ormSpeakers.callBatchTasks(new Callable<Void>() {
                    public Void call() throws Exception {
                        for (Speaker o : ((Import) data).getSpeakers())
                            ormSpeakers.createOrUpdate(o);
                        return null;
                    }
                });


                ormSpeakerPresentationPairs.callBatchTasks(new Callable<Void>() {
                    public Void call() throws Exception {
                        for (SpeakerPresentationPair o : ((Import) data).getSpeakerPresentationPairs())
                            ormSpeakerPresentationPairs.createOrUpdate(o);
                        return null;
                    }
                });


                ormUsers.callBatchTasks(new Callable<Void>() {
                    public Void call() throws Exception {
                        for (User o : ((Import) data).getUsers())
                            ormUsers.createOrUpdate(o);
                        return null;
                    }
                });

            } else if (data instanceof Like[]) {
                ormLikes.callBatchTasks(new Callable<Void>() {
                    public Void call() throws Exception {
                        for (Like o : (Like[]) data)
                            ormLikes.createOrUpdate(o);
                        return null;
                    }
                });

            } else if (data instanceof Question[]) {
                ormQuestions.callBatchTasks(new Callable<Void>() {
                    public Void call() throws Exception {
                        for (Question o : (Question[]) data)
                            ormQuestions.createOrUpdate(o);
                        return null;
                    }
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


