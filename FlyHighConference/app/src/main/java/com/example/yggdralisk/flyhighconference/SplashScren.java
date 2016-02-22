package com.example.yggdralisk.flyhighconference;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.realm.Realm;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SplashScren extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 10000;
    private final String DATA_HOST_URL = "http://flyhigh.pwr.edu.pl/api/";
    private final String[] DATA_BASIC = {"organisers","partners","places","presentations","speakers","users"};
    private TextView splashMessege;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_scren);
        splashMessege = (TextView)findViewById(R.id.splash_messege);
        new getDataTask().execute();
    }

    protected void startMain() {
        Intent in = new Intent(this, MainActivity.class);
        this.startActivity(in);
    }

    private class getDataTask extends AsyncTask<Void, Void, Void> {
        JSONObject js = new JSONObject();

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                js = new JSONObject(getData("import"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            JSONArray organisersArray = new JSONArray();
            JSONArray partnersArray = new JSONArray();
            JSONArray placesArray = new JSONArray();
            JSONArray presentationsArray = new JSONArray();
            JSONArray speakersArray = new JSONArray();
            JSONArray usersArray = new JSONArray();

            try {
                 organisersArray = new JSONArray(js.get(DATA_BASIC[0]).toString());
                 partnersArray = new JSONArray(js.get(DATA_BASIC[1]).toString());
                 placesArray = new JSONArray(js.get(DATA_BASIC[2]).toString());
                 presentationsArray = new JSONArray(js.get(DATA_BASIC[3]).toString());
                 speakersArray = new JSONArray(js.get(DATA_BASIC[4]).toString());
                 usersArray = new JSONArray(js.get(DATA_BASIC[5]).toString());
            } catch (JSONException e) {
                     try {
                         organisersArray = new JSONArray(getData(DATA_BASIC[0]));
                         partnersArray = new JSONArray(getData(DATA_BASIC[1]));
                         placesArray = new JSONArray(getData(DATA_BASIC[2]));
                         presentationsArray = new JSONArray(getData(DATA_BASIC[3]));
                         speakersArray = new JSONArray(getData(DATA_BASIC[4]));
                         usersArray = new JSONArray(getData(DATA_BASIC[5]));
                     } catch (JSONException e1) { //TODO:Dopracować co zrobić w razie braku połączenia

                        splashMessege.setText("Brak połączenia z internetem/nBłąd serwera");
                         splashMessege.invalidate();
                         try {
                             this.wait(SPLASH_DISPLAY_LENGTH);
                         } catch (InterruptedException e2) {
                             e2.printStackTrace();
                         }

                     }

            }finally {
                SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_preferences_name), Context.MODE_PRIVATE);
                sharedPreferences.edit().putString(getString(R.string.shared_preferences_organisers), organisersArray.toString());
                sharedPreferences.edit().putString(getString(R.string.shared_preferences_partners), partnersArray.toString());
                sharedPreferences.edit().putString(getString(R.string.shared_preferences_places), placesArray.toString());
                sharedPreferences.edit().putString(getString(R.string.shared_preferences_presentations), presentationsArray.toString());
                sharedPreferences.edit().putString(getString(R.string.shared_preferences_speakers), speakersArray.toString());
                sharedPreferences.edit().putString(getString(R.string.shared_preferences_users), usersArray.toString());

                sharedPreferences.edit().commit();
            }
            startMain();
        }

        private String getData(String urlAppend) {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(DATA_HOST_URL + urlAppend)
                    .build();
            try {
                Response res = client.newCall(request).execute();
                String str = res.body().string();
                return str;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }


    }
}
