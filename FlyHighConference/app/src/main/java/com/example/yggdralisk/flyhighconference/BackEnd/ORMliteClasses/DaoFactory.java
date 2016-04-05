package com.example.yggdralisk.flyhighconference.BackEnd.ORMliteClasses;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

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

    private Dao<ORMLike, Integer> ormLikes = null;
    private Dao<ORMOrganiser, Integer> ormOrganisers = null;
    private Dao<ORMPartner, Integer> ormPartners = null;
    private Dao<ORMPlace, Integer> ormPlaces = null;
    private Dao<ORMPresentation, Integer> ormPresentations = null;
    private Dao<ORMQuestion, Integer> ormQuestions = null;
    private Dao<ORMSpeaker, Integer> ormSpeakers = null;
    private Dao<ORMSpeakerPresentationPair, Integer> ormSpeakerPresentationPairs = null;
    private Dao<ORMUser, Integer> ormUsers = null;

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

    public Dao<ORMLike, Integer> getOrmLikes() throws SQLException {
        if (ormLikes == null) {
            ormLikes = databaseHelper.getDao(ORMLike.class);
        }
        return ormLikes;
    }

    public Dao<ORMOrganiser, Integer> getOrmOrganisers() throws SQLException {
        if (ormOrganisers == null) {
            ormOrganisers = databaseHelper.getDao(ORMOrganiser.class);
        }
        return ormOrganisers;
    }

    public Dao<ORMPartner, Integer> getOrmPartners() throws SQLException {
        if (ormPartners == null) {
            ormPartners = databaseHelper.getDao(ORMPartner.class);
        }
        return ormPartners;
    }

    public Dao<ORMPlace, Integer> getOrmPlaces() throws SQLException {
        if (ormPlaces == null) {
            ormPlaces = databaseHelper.getDao(ORMPlace.class);
        }
        return ormPlaces;
    }

    public Dao<ORMPresentation, Integer> getOrmPresentations() throws SQLException {
        if (ormPresentations == null) {
            ormPresentations = databaseHelper.getDao(ORMPresentation.class);
        }
        return ormPresentations;
    }

    public Dao<ORMQuestion, Integer> getOrmQuestions() throws SQLException {
        if (ormQuestions == null) {
            ormQuestions = databaseHelper.getDao(ORMQuestion.class);
        }
        return ormQuestions;
    }

    public Dao<ORMSpeaker, Integer> getOrmSpeakers() throws SQLException {
        if (ormSpeakers == null) {
            ormSpeakers = databaseHelper.getDao(ORMSpeaker.class);
        }
        return ormSpeakers;
    }

    public Dao<ORMSpeakerPresentationPair, Integer> getOrmSpeakerPresentationPairs() throws SQLException {
        if (ormSpeakerPresentationPairs == null) {
            ormSpeakerPresentationPairs = databaseHelper.getDao(ORMSpeakerPresentationPair.class);
        }
        return ormSpeakerPresentationPairs;
    }

    public Dao<ORMUser, Integer> getOrmUsers() throws SQLException {
        if (ormUsers == null) {
            ormUsers = databaseHelper.getDao(ORMUser.class);
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
