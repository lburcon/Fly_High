package com.example.yggdralisk.flyhighconference.BackEnd.ORMliteClasses;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Like;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Organiser;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Partner;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Place;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Presentation;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Question;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Speaker;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.SpeakerPresentationPair;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.User;
import com.j256.ormlite.android.apptools.OpenHelperManager;
import com.j256.ormlite.dao.Dao;

import java.io.File;
import java.sql.SQLException;

/**
 * Created by yggdralisk on 05.04.16.
 */
public class DaoFactory extends Application {

    private SharedPreferences preferences;
    private DatabaseHelper databaseHelper = null;

    private Dao<Like, Integer> ormLikes = null;
    private Dao<Organiser, Integer> ormOrganisers = null;
    private Dao<Partner, Integer> ormPartners = null;
    private Dao<Place, Integer> ormPlaces = null;
    private Dao<Presentation, Integer> ormPresentations = null;
    private Dao<Question, Integer> ormQuestions = null;
    private Dao<Speaker, Integer> ormSpeakers = null;
    private Dao<SpeakerPresentationPair, Integer> ormSpeakerPresentationPairs = null;
    private Dao<User, Integer> ormUsers = null;

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        databaseHelper = new DatabaseHelper(this);
    }

    public SharedPreferences getPreferences() {return preferences;}

    public DatabaseHelper getDatabaseHelper() {
        return databaseHelper;
    }

    public Dao<Like, Integer> getOrmLikes() throws SQLException {
        if (ormLikes == null) {
            ormLikes = databaseHelper.getDao(Like.class);
        }
        return ormLikes;
    }

    public Dao<Organiser, Integer> getOrmOrganisers() throws SQLException {
        if (ormOrganisers == null) {
            ormOrganisers = databaseHelper.getDao(Organiser.class);
        }
        return ormOrganisers;
    }

    public Dao<Partner, Integer> getOrmPartners() throws SQLException {
        if (ormPartners == null) {
            ormPartners = databaseHelper.getDao(Partner.class);
        }
        return ormPartners;
    }

    public Dao<Place, Integer> getOrmPlaces() throws SQLException {
        if (ormPlaces == null) {
            ormPlaces = databaseHelper.getDao(Place.class);
        }
        return ormPlaces;
    }

    public Dao<Presentation, Integer> getOrmPresentations() throws SQLException {
        if (ormPresentations == null) {
            ormPresentations = databaseHelper.getDao(Presentation.class);
        }
        return ormPresentations;
    }

    public Dao<Question, Integer> getOrmQuestions() throws SQLException {
        if (ormQuestions == null) {
            ormQuestions = databaseHelper.getDao(Question.class);
        }
        return ormQuestions;
    }

    public Dao<Speaker, Integer> getOrmSpeakers() throws SQLException {
        if (ormSpeakers == null) {
            ormSpeakers = databaseHelper.getDao(Speaker.class);
        }
        return ormSpeakers;
    }

    public Dao<SpeakerPresentationPair, Integer> getOrmSpeakerPresentationPairs() throws SQLException {
        if (ormSpeakerPresentationPairs == null) {
            ormSpeakerPresentationPairs = databaseHelper.getDao(SpeakerPresentationPair.class);
        }
        return ormSpeakerPresentationPairs;
    }

    public Dao<User, Integer> getOrmUsers() throws SQLException {
        if (ormUsers == null) {
            ormUsers = databaseHelper.getDao(User.class);
        }
        return ormUsers;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        if (databaseHelper != null) {
            OpenHelperManager.releaseHelper();
            databaseHelper = null;
        }
    }
}
