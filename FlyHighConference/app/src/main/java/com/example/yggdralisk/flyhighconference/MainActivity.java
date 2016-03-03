package com.example.yggdralisk.flyhighconference;

import android.app.Activity;
import android.app.Application;
import android.app.FragmentTransaction;
import android.content.res.TypedArray;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private TextView loggedName;

    private String[] navMenuTitles;
    private List<Integer> navMenuIcons = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loggedName = (TextView) findViewById(R.id.left_drawer_logged_name);
        setFragment(savedInstanceState, new ConferenceListFragment());
        setDrawer();
    }

    public void toggleDrawer() {
        DrawerLayout mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT))
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        else
            mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    public void setDrawer() {
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        TypedArray ids = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        for (int i = 0; i < ids.length(); i++)
            navMenuIcons.add(ids.getResourceId(i, -1));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);
        mDrawerList = (ListView) findViewById(R.id.left_drawer_list_view);
        changeZalogujWylogujOnDrawer();
    }

    //Jeżeli nie wiesz co wysłać w jakos aveInstanceState, wyślij null
    public void setFragment(Bundle savedInstanceState, Fragment fragmentActivity) {
        try {
            if (findViewById(R.id.fragment_container_main) != null) {
                if (savedInstanceState != null) {
                    return;
                }
                fragmentActivity.setArguments(getIntent().getExtras());
                FragmentManager fragmentManager = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_main, fragmentActivity);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }

    public void setFragment(Bundle savedInstanceState, Fragment fragmentActivity, Bundle args) {
        try {
            if (findViewById(R.id.fragment_container_main) != null) {
                if (savedInstanceState != null) {
                    return;
                }

                fragmentActivity.setArguments(getIntent().getExtras());
                fragmentActivity.setArguments(args);
                FragmentManager fragmentManager = getSupportFragmentManager();
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment_container_main, fragmentActivity);
                fragmentTransaction.addToBackStack(null);

                fragmentTransaction.commit();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }

    private class DrawerItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            setFragment(null, ((DrawerItem) mDrawerList.getItemAtPosition(position)).fragment);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    toggleDrawer();
                }
            }, 300);
        }
    }

    public boolean setPreviousFragment() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        if (!setPreviousFragment()) {
            this.finish();
            System.exit(0);
        }
    }

    public void setLoggedNameOnDrawer(String name) {
        if (name != null) {
            if(name == "")
                loggedName.setText("");
            else
            loggedName.setText(getString(R.string.left_drawer_logged_name) + name);

            loggedName.invalidate();
        }

    }

    public boolean changeZalogujWylogujOnDrawer() //Returns true if drawer has been changed to zaloguj
    {
        ArrayList<DrawerItem> drawerElements = new ArrayList<>();
        int temp;
        boolean ifChanged = false;

        if (navMenuTitles.length >= navMenuIcons.size())
            temp = navMenuTitles.length - 2;
        else
            temp = navMenuIcons.size() - 2;

        for (int i = 0; i < temp; i++)
            drawerElements.add(new DrawerItem(navMenuTitles[i], navMenuIcons.get(i)));

        if (DataGetter.checkUserLogged(getApplicationContext())) {
            drawerElements.add(new DrawerItem(navMenuTitles[temp +1 ], navMenuIcons.get(temp + 1)));
            ifChanged = true;
        }
        else {
            // temp+1 if so it gets "wyloguj" String and icon
            drawerElements.add(new DrawerItem(navMenuTitles[temp], navMenuIcons.get(temp)));
        }

        // Set the adapter for the list view
        mDrawerList.setAdapter(new DrawerAdapter(getApplicationContext(), R.layout.drawer_item, drawerElements));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        return ifChanged;
    }
}
