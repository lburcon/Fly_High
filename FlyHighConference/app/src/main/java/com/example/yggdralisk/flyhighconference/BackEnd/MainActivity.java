package com.example.yggdralisk.flyhighconference.BackEnd;

import android.content.res.TypedArray;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yggdralisk.flyhighconference.Adapters_Managers_Items.DrawerAdapter;
import com.example.yggdralisk.flyhighconference.Adapters_Managers_Items.DrawerItem;
import com.example.yggdralisk.flyhighconference.Fragments.ConferenceListFragment;
import com.example.yggdralisk.flyhighconference.Fragments.LoginFragment;
import com.example.yggdralisk.flyhighconference.Fragments.LoginOutFragment;
import com.example.yggdralisk.flyhighconference.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.main_drawer) DrawerLayout mDrawerLayout;
    @Bind(R.id.left_drawer_list_view) ListView mDrawerList;
    @Bind(R.id.left_drawer_logged_name) TextView loggedName;

    private String[] navMenuTitles;
    private List<Integer> navMenuIcons = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setFragment(savedInstanceState, new ConferenceListFragment());
        setDrawer();
    }

    public void toggleDrawer() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
            mDrawerLayout.closeDrawer(GravityCompat.START);
        else
            mDrawerLayout.openDrawer(GravityCompat.START);
    }

    public void setDrawer() {
        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        TypedArray ids = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        for (int i = 0; i < ids.length(); i++)
            navMenuIcons.add(ids.getResourceId(i, -1));
        changeLoginLogoutDrawer();
        ids.recycle();
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

                boolean isLog = (fragmentManager.findFragmentById(R.id.fragment_container_main) instanceof LoginFragment
                        || fragmentManager.findFragmentById(R.id.fragment_container_main) instanceof LoginOutFragment);

                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.fragment_container_main, fragmentActivity);

                if(!isLog)
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
                boolean isLog = (fragmentManager.findFragmentById(R.id.fragment_container_main) instanceof LoginFragment
                        || fragmentManager.findFragmentById(R.id.fragment_container_main) instanceof LoginOutFragment);
                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.replace(R.id.fragment_container_main, fragmentActivity);

                if(!isLog)
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

            setFragment(null, ((DrawerItem) mDrawerList.getItemAtPosition(position)).getFragment());
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

    public boolean changeLoginLogoutDrawer() //Returns true if drawer has been changed to zaloguj
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

    public void buttonBackPressed(View v) {
        MainActivity mainActivity = this;
        mainActivity.setPreviousFragment();
    }

}
