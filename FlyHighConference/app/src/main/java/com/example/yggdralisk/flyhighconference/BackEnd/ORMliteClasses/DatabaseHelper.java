package com.example.yggdralisk.flyhighconference.BackEnd.ORMliteClasses;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

/**
 * Created by yggdralisk on 05.04.16.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "fly_high.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onCreate");
            TableUtils.createTable(connectionSource, ORMLike.class);
            TableUtils.createTable(connectionSource, ORMOrganiser.class);
            TableUtils.createTable(connectionSource, ORMPartner.class);
            TableUtils.createTable(connectionSource, ORMPlace.class);
            TableUtils.createTable(connectionSource, ORMPresentation.class);
            TableUtils.createTable(connectionSource, ORMQuestion.class);
            TableUtils.createTable(connectionSource, ORMSpeaker.class);
            TableUtils.createTable(connectionSource, ORMSpeakerPresentationPair.class);
            TableUtils.createTable(connectionSource, ORMUser.class);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't create database", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            Log.i(DatabaseHelper.class.getName(), "onUpgrade");
            TableUtils.createTable(connectionSource, ORMLike.class);
            TableUtils.createTable(connectionSource, ORMOrganiser.class);
            TableUtils.createTable(connectionSource, ORMPartner.class);
            TableUtils.createTable(connectionSource, ORMPlace.class);
            TableUtils.createTable(connectionSource, ORMPresentation.class);
            TableUtils.createTable(connectionSource, ORMQuestion.class);
            TableUtils.createTable(connectionSource, ORMSpeaker.class);
            TableUtils.createTable(connectionSource, ORMSpeakerPresentationPair.class);
            TableUtils.createTable(connectionSource, ORMUser.class);
            onCreate(database, connectionSource);
        } catch (SQLException e) {
            Log.e(DatabaseHelper.class.getName(), "Can't drop databases", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        super.close();
    }
}
