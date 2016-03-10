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

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SplashScren extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 10000;
    private final String DATA_HOST_URL = "http://flyhigh.pwr.edu.pl/api/";
    private final String[] DATA_BASIC = {"organisers", "partners", "places", "presentations", "speakers", "users","likes","speaker_has_presentation"};
    private TextView splashMessege;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_scren);
        splashMessege = (TextView) findViewById(R.id.splash_messege);
        new getDataTask().execute();
    }

    protected void startMain() {
        Intent in = new Intent(this, MainActivity.class);
        this.startActivity(in);
        this.finish();
    }

    private class getDataTask extends AsyncTask<Void, Void, Void> {
        JSONObject js = new JSONObject();
        JSONArray organisersArray = new JSONArray();
        JSONArray partnersArray = new JSONArray();
        JSONArray placesArray = new JSONArray();
        JSONArray presentationsArray = new JSONArray();
        JSONArray speakersArray = new JSONArray();
        JSONArray usersArray = new JSONArray();
        JSONArray likesArray = new JSONArray();
        JSONArray speakerHasPresentation = new JSONArray();

        Boolean connectionFailed = false;

        @Override
        protected void onPreExecute() {

        }

        @Override
        protected Void doInBackground(Void... params) {

            try {
                js = new JSONObject(getData("import"));

                organisersArray = new JSONArray(js.get(DATA_BASIC[0]).toString());
                partnersArray = new JSONArray(js.get(DATA_BASIC[1]).toString());
                placesArray = new JSONArray(js.get(DATA_BASIC[2]).toString());
                presentationsArray = new JSONArray(js.get(DATA_BASIC[3]).toString());
                speakersArray = new JSONArray(js.get(DATA_BASIC[4]).toString());
                usersArray = new JSONArray(js.get(DATA_BASIC[5]).toString());
                likesArray = new JSONArray(js.get(DATA_BASIC[6]).toString());
                speakerHasPresentation = new JSONArray(js.get(DATA_BASIC[7]).toString());
            } catch (JSONException e) {//TODO:Zapytać się Wojtka o catch in trycatch w androidzie
                try {
                    organisersArray = new JSONArray(getData(DATA_BASIC[0]));
                    partnersArray = new JSONArray(getData(DATA_BASIC[1]));
                    placesArray = new JSONArray(getData(DATA_BASIC[2]));
                    presentationsArray = new JSONArray(getData(DATA_BASIC[3]));
                    speakersArray = new JSONArray(getData(DATA_BASIC[4]));
                    usersArray = new JSONArray(getData(DATA_BASIC[5]));
                    likesArray = new JSONArray(getData(DATA_BASIC[6]));
                    speakerHasPresentation = new JSONArray(getData(DATA_BASIC[7]));

                } catch (JSONException e1) { //TODO:Dopracować co zrobić w razie braku połączenia
                    connectionFailed = true;
                }

            } return null;
        }


        @Override
        protected void onPostExecute(Void result) {
            if(connectionFailed)
            {
                    splashMessege.setText(R.string.splash_internet_error);
                    splashMessege.invalidate();
            }
            else {
                SharedPreferences sharedPreferences = getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString(getString(R.string.shared_preferences_organisers), organisersArray.toString());
                editor.putString(getString(R.string.shared_preferences_partners), partnersArray.toString());
                editor.putString(getString(R.string.shared_preferences_places), placesArray.toString());
                editor.putString(getString(R.string.shared_preferences_presentations), presentationsArray.toString());
                editor.putString(getString(R.string.shared_preferences_speakers), speakersArray.toString());
                editor.putString(getString(R.string.shared_preferences_users), usersArray.toString());
                editor.putString(getString(R.string.shared_preferences_likes), likesArray.toString());
                editor.putString(getString(R.string.shared_preferences_speaker_has_presentations), speakerHasPresentation.toString());

                editor.apply();
            }
            startMain();
            }
        }

        private String getData(String urlAppend) throws JSONException {
            OkHttpClient client = new OkHttpClient();
            String str = new String();
            Request request = new Request.Builder()
                    .url(DATA_HOST_URL + urlAppend)
                    .build();
            try {
                Response res = client.newCall(request).execute();
                str = res.body().string();
                return str;
            } catch (IOException e) {
                e.printStackTrace();
                throw new JSONException("Brak polączenia");
            }
        }


    }

