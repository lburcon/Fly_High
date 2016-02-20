package com.example.yggdralisk.flyhighconference;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SplashScren extends Activity {

    private final int SPLASH_DISPLAY_LENGTH = 10000;
    private final String DATA_HOST_URL = "http://flyhigh.pwr.edu.pl/api/import";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_scren);

        new getDataTask().execute();
    }

    protected void startMain()
    {
        Intent in = new Intent(this,ConferenceListActivity.class);
        this.startActivity(in);
    }

         private class getDataTask extends AsyncTask<Void,Void,Void> {
             JSONObject js = new JSONObject();

             @Override
             protected void onPreExecute() {

             }

             @Override
             protected Void doInBackground(Void... params) {
                 OkHttpClient client = new OkHttpClient();

                 Request request = new Request.Builder()
                         .url(DATA_HOST_URL)
                         .build();
                 try {
                     Response res = client.newCall(request).execute();
                     String str = res.body().string();
                      js = new JSONObject(str);
                 } catch (IOException e) {
                     e.printStackTrace();                 } catch (JSONException e) {

                     e.printStackTrace();
                 }

                 return null;
             }

             @Override
             protected void onPostExecute(Void result) {

                 try {
                     JSONArray organisersArray = new JSONArray(js.get("organisers").toString());
                     JSONArray partnersArray = new JSONArray(js.get("partners").toString());
                     JSONArray placesArray = new JSONArray(js.get("places").toString());
                     JSONArray presentationsArray = new JSONArray(js.get("presentations").toString());
                     JSONArray speakersArray = new JSONArray(js.get("speakers").toString());
                     JSONArray usersArray = new JSONArray(js.get("users").toString());

                     startMain();
                 }catch (JSONException e) {
                     e.printStackTrace();
                 }
             }
         }
}
