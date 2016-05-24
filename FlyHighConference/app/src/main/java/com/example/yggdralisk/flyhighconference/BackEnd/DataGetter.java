package com.example.yggdralisk.flyhighconference.BackEnd;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.LinearLayout;

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
import com.example.yggdralisk.flyhighconference.R;
import com.google.gson.Gson;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;

import org.json.JSONException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by yggdralisk on 29.02.16.
 */
public class DataGetter {
    Application application;
    DaoFactory daoFactory;
    Dao<Like, Integer> ormLikes;
    Dao<User, Integer> ormUsers;
    Dao<Organiser, Integer> ormOrganisers;
    Dao<Presentation, Integer> ormPresentations;
    Dao<SpeakerPresentationPair, Integer> ormSpeakerPresentationPairs;
    Dao<Partner, Integer> ormPartners;
    Dao<Place, Integer> ormPlaces;
    Dao<Question, Integer> ormQuestions;
    Dao<Speaker, Integer> ormSpeakers;

    public DataGetter(Application application) {
        this.application = application;
        daoFactory = (DaoFactory) application;

        try {
            ormLikes = daoFactory.getOrmLikes();
            ormUsers = daoFactory.getOrmUsers();
            ormOrganisers = daoFactory.getOrmOrganisers();
            ormPresentations = daoFactory.getOrmPresentations();
            ormSpeakerPresentationPairs = daoFactory.getOrmSpeakerPresentationPairs();
            ormPartners = daoFactory.getOrmPartners();
            ormPlaces = daoFactory.getOrmPlaces();
            ormQuestions = daoFactory.getOrmQuestions();
            ormSpeakers = daoFactory.getOrmSpeakers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public SpeakerPresentationPair[] getSpeakerHasPresentations() {
        QueryBuilder<SpeakerPresentationPair, Integer> builder = ormSpeakerPresentationPairs.queryBuilder();
        builder.orderBy("id", true);
        try {
            return (SpeakerPresentationPair[]) ormSpeakerPresentationPairs.query(builder.prepare()).toArray();
        } catch (SQLException e) {
            return null;
        }
    }

    public Presentation[] getPresentations() {
        QueryBuilder<Presentation, Integer> builder = ormPresentations.queryBuilder();
        builder.orderBy("start", true);
        try {
            Object[] x = ormPresentations.query(builder.prepare()).toArray();
            return Arrays.copyOf(x, x.length, Presentation[].class);
        } catch (SQLException e) {
            return null;
        }
    }

    public Presentation[] getLectures() {
        QueryBuilder<Presentation, Integer> builder = ormPresentations.queryBuilder();
        try {
            builder.where().eq("presentationType", "lecture").or().eq("presentationType", "Lecture");
            builder.orderBy("start", true);

            Object[] x = ormPresentations.query(builder.prepare()).toArray();
            return Arrays.copyOf(x, x.length, Presentation[].class);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Question[] getQuestionsToPresentation(int presentationId) {
        QueryBuilder<Question, Integer> builder = ormQuestions.queryBuilder();
        builder.orderBy("id", true);
        try {
            builder.where().eq("presentationId", presentationId);
            Object[] x = ormQuestions.query(builder.prepare()).toArray();
            return Arrays.copyOf(x, x.length, Question[].class);
        } catch (SQLException e) {
            return new Question[]{};
        }
    }

    public Speaker[] getSpeakers() {
        QueryBuilder<Speaker, Integer> builder = ormSpeakers.queryBuilder();
        try {
            Object[] x = ormSpeakers.query(builder.prepare()).toArray();
            return Arrays.copyOf(x, x.length, Speaker[].class);
        } catch (SQLException e) {
            return null;
        }
    }

    public Partner[] getPartners() {
        QueryBuilder<Partner, Integer> builder = ormPartners.queryBuilder();
        try {
            Object[] x = ormPartners.query(builder.prepare()).toArray();
            return Arrays.copyOf(x, x.length, Partner[].class);
        } catch (SQLException e) {
            return null;
        }
    }

    public User[] getUsers() {
        QueryBuilder<User, Integer> builder = ormUsers.queryBuilder();
        try {
            Object[] x = ormUsers.query(builder.prepare()).toArray();
            return Arrays.copyOf(x, x.length, User[].class);
        } catch (SQLException e) {
            return null;
        }
    }

    public Place[] getPlaces() {
        QueryBuilder<Place, Integer> builder = ormPlaces.queryBuilder();
        try {
            Object[] x = ormPlaces.query(builder.prepare()).toArray();
            return Arrays.copyOf(x, x.length, Place[].class);
        } catch (SQLException e) {
            return null;
        }
    }

    public Like[] getLikes() {
        QueryBuilder<Like, Integer> builder = ormLikes.queryBuilder();
        try {
            Object[] x = ormLikes.query(builder.prepare()).toArray();
            return Arrays.copyOf(x, x.length, Like[].class);
        } catch (SQLException e) {
            return null;
        }
    }

    public Like[] getLikesByQuestionId(int questionId){
        QueryBuilder<Like, Integer> builder = ormLikes.queryBuilder();
        try {
            builder.where().eq("questionId", questionId);
            return ormLikes.query(builder.prepare()).toArray(new Like[]{});
        } catch (SQLException | IndexOutOfBoundsException e) {
            return null;
        }
    }

    public Organiser[] getOrganisers() {
        QueryBuilder<Organiser, Integer> builder = ormOrganisers.queryBuilder();
        try {
            Object[] x = ormOrganisers.query(builder.prepare()).toArray();
            return Arrays.copyOf(x, x.length, Organiser[].class);
        } catch (SQLException e) {
            return null;
        }
    }

    public Speaker getSpeakerById(int speakerId) {
        QueryBuilder<Speaker, Integer> builder = ormSpeakers.queryBuilder();
        try {
            builder.where().eq("id", speakerId);
            return ormSpeakers.query(builder.prepare()).get(0);
        } catch (SQLException | IndexOutOfBoundsException e) {
            return null;
        }
    }

    public String[] getPresentationSpeakersNames(int presentationId) {
        String[] names;
        try {
            int[] ids = getPresentationById(presentationId).getSpeakers();
            names = new String[ids.length];
            for (int i = 0; i < ids.length; i++)
                names[i] = getSpeakerById(ids[i]).getName();

            return names;
        } catch (NullPointerException | IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        return new String[]{""};
    }

    public Presentation getPresentationById(int presentationId) {
        QueryBuilder<Presentation, Integer> builder = ormPresentations.queryBuilder();
        try {
            builder.where().eq("id", presentationId);
            return ormPresentations.query(builder.prepare()).get(0);
        } catch (SQLException | IndexOutOfBoundsException e) {
            return null;
        }
    }

    public Place getPlaceById(int placeId) {
        QueryBuilder<Place, Integer> builder = ormPlaces.queryBuilder();
        try {
            builder.where().eq("id", placeId);
            return ormPlaces.query(builder.prepare()).get(0);
        } catch (SQLException | IndexOutOfBoundsException e) {
            return null;
        }
    }

    public User getUserById(int userID) {
        QueryBuilder<User, Integer> builder = ormUsers.queryBuilder();
        try {
            builder.where().eq("id", userID);
            return ormUsers.query(builder.prepare()).get(0);
        } catch (SQLException e) {
            return null;
        }
    }

    public static Boolean checkUserLogged(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(context.getString(R.string.shared_preferences_user_logged), false);
    }

    public static String getLoggedUserName(Context context) {
        if (checkUserLogged(context)) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
            return sharedPreferences.getString(context.getString(R.string.shared_preferences_logged_user_name), "");
        } else {
            return "";
        }
    }

    public static int getLoggedUserId(Context context) {
        if (checkUserLogged(context)) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
            return sharedPreferences.getInt(context.getString(R.string.shared_preferences_logged_user_id), -1);
        } else {
            return -1;
        }
    }

    public static Boolean toggleUserLogged(Context context, String userName, int userId)//Returns true if user is logged in or false otherwise.
    {
        if (checkUserLogged(context)) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(context.getString(R.string.shared_preferences_user_logged), false);
            editor.putString(context.getString(R.string.shared_preferences_logged_user_name), "");
            editor.putInt(context.getString(R.string.shared_preferences_logged_user_id), -1);
            editor.apply();
            return false;
        } else {
            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(context.getString(R.string.shared_preferences_user_logged), true);
            editor.putString(context.getString(R.string.shared_preferences_logged_user_name), userName);
            editor.putInt(context.getString(R.string.shared_preferences_logged_user_id), userId);
            editor.apply();
            return true;
        }
    }

    public static ArrayList<Integer> getLoggedUserFavs(Context context) {
        if (checkUserLogged(context)) {
            Set<Integer> temp = new HashSet<>();
            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
            for (String s :
                    sharedPreferences.getString(context.getString(R.string.favs_of) + getLoggedUserId(context), "").split("\\s+")) {
                if (s != "")
                    temp.add(Integer.parseInt(s));
            }
            return new ArrayList<>(temp);
        } else {

            return new ArrayList<>();
        }
    }

    public static boolean addLoggedUserFav(Context context, int presentationId) {
        if (checkUserLogged(context) && !getLoggedUserFavs(context).contains(presentationId)) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String ts = "";
            for (int i :
                    getLoggedUserFavs(context)) {
                ts += Integer.toString(i) + " ";
            }

            ts += Integer.toString(presentationId);

            editor.putString(context.getString(R.string.favs_of) + getLoggedUserId(context), ts);
            editor.apply();
            return true;
        } else {
            return false;
        }
    }

    public static boolean removeLoggedUserFav(Context context, int presentationId) {
        if (checkUserLogged(context) && getLoggedUserFavs(context).contains(presentationId)) {
            SharedPreferences sharedPreferences = context.getSharedPreferences(context.getString(R.string.shared_preferences), Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            String ts = "";
            for (int i :
                    getLoggedUserFavs(context)) {
                if (i != presentationId)
                    ts += Integer.toString(i) + " ";
            }

            editor.putString(context.getString(R.string.favs_of) + getLoggedUserId(context), ts);
            editor.apply();
            return true;
        } else {
            return false;
        }
    }
}
