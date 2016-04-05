package com.example.yggdralisk.flyhighconference;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces.ConnectorResultInterface;
import com.example.yggdralisk.flyhighconference.BackEnd.MainActivity;
import com.example.yggdralisk.flyhighconference.BackEnd.ServerConnector;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashScren extends Activity {

    @Bind(R.id.splash_messege)
    TextView splashMessege;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_scren);
        ButterKnife.bind(this);
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
