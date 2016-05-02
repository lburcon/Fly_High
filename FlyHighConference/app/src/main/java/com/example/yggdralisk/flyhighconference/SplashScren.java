package com.example.yggdralisk.flyhighconference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces.ConnectorResultInterface;
import com.example.yggdralisk.flyhighconference.BackEnd.MainActivity;
import com.example.yggdralisk.flyhighconference.BackEnd.ServerConnector;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashScren extends Activity {

    @Bind(R.id.splash_messege)
    TextView splashMessege;
    @Bind(R.id.splash_image)
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_scren);
        ButterKnife.bind(this);

        Glide.with(this).load("").fitCenter().placeholder(R.drawable.fly_high).crossFade().into(image);

        ServerConnector serverConnector = new ServerConnector();
        serverConnector.refreshData(getApplication(),getApplicationContext(), new ConnectorResultInterface() {
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
