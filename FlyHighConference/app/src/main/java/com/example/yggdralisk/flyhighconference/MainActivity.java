package com.example.yggdralisk.flyhighconference;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.fragment_container_main) != null) {
            if (savedInstanceState != null) {
                return;
            }
            ConferenceListFragment firstFragment = new ConferenceListFragment();
            firstFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.fragment_container_main, firstFragment).commit();

        }
    }

    public void toggleDrawer()
    {
        DrawerLayout mDrawerLayout = (DrawerLayout)findViewById(R.id.activity_main_drawer);
        mDrawerLayout.openDrawer(Gravity.LEFT);
    }
}
