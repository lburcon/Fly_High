package com.example.yggdralisk.flyhighconference;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SplashScren extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_scren);

        try {
            getConferenceList();//Get conferences list for the next screen and block view on splash till then
        } catch (IOException e) {}

        getRest();//Start downloading rest of the content

        Intent in = new Intent(this,conferenceListActivity.class);
        this.startActivity(in);

    }

    private void getConferenceList() throws IOException {
        String url = "http://flyhigh.pwr.edu.pl/api/presentations";
        OkHttpClient client = new OkHttpClient();


            Request request = new Request.Builder()
                    .url(url)
                    .build();

            Response response = client.newCall(request).execute();
    }

    private void getRest() {
        
    }
}
