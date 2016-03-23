package com.example.yggdralisk.flyhighconference;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yggdralisk.flyhighconference.BackEnd.ConnectorResultInterface;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Import;
import com.example.yggdralisk.flyhighconference.BackEnd.MainActivity;
import com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces.ImportInterface;
import com.example.yggdralisk.flyhighconference.BackEnd.ServerConnector;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SplashScren extends Activity {

    private final String DATA_HOST_URL = "http://flyhigh.pwr.edu.pl/";
    @Bind(R.id.splash_messege)
    TextView splashMessege;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_scren);
        ButterKnife.bind(this);                                     //WTF THAT GIT
        //retrofitGet();
        ServerConnector serverConnector = new ServerConnector();
        serverConnector.refreshData(getApplicationContext(), new ConnectorResultInterface() {
            @Override
            public void onDownloadFinished(boolean succeeded) {
                if (!succeeded)
                splashMessege.setText(getString(R.string.splash_internet_error));

                startMain();
            }
        });
    }

    protected void startMain() {
        Intent in = new Intent(this, MainActivity.class);
        this.startActivity(in);
        this.finish();
    }
}

