package com.example.yggdralisk.flyhighconference.BackEnd;

import android.content.res.TypedArray;
import android.os.Handler;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.yggdralisk.flyhighconference.Adapters_Managers_Items.DrawerAdapter;
import com.example.yggdralisk.flyhighconference.Adapters_Managers_Items.DrawerItem;
import com.example.yggdralisk.flyhighconference.Fragments.ConferenceFragment;
import com.example.yggdralisk.flyhighconference.Fragments.ConferenceListFragment;
import com.example.yggdralisk.flyhighconference.Fragments.InfoFragment;
import com.example.yggdralisk.flyhighconference.Fragments.LoginFragment;
import com.example.yggdralisk.flyhighconference.Fragments.LoginOutFragment;
import com.example.yggdralisk.flyhighconference.Fragments.PartnerFragment;
import com.example.yggdralisk.flyhighconference.Fragments.QuestionFragment;
import com.example.yggdralisk.flyhighconference.Fragments.SpeakerFragment;
import com.example.yggdralisk.flyhighconference.Fragments.SpeakersConferenceListFragment;
import com.example.yggdralisk.flyhighconference.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.main_drawer)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.left_drawer_list_view)
    ListView mDrawerList;
    @Bind(R.id.left_drawer_logged_name)
    TextView loggedName;
    @Bind(R.id.activity_main_toolbar)
    Toolbar mToolbar;



    private ActionBarDrawerToggle mDrawerToggle;

    private String[] navMenuTitles;
    boolean ifLast = false;
    private List<Integer> navMenuIcons = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        setDrawer();

        setFragment(savedInstanceState, new ConferenceListFragment(), null);

        toolbarOnclick();


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

    public void setFragment(Bundle savedInstanceState, Fragment fragmentActivity, Bundle args) {
        try {
            if (findViewById(R.id.fragment_container_main) != null) {
                if (savedInstanceState != null) {
                    return;
                }

                fragmentActivity.setArguments(getIntent().getExtras());
                if (args != null) fragmentActivity.setArguments(args);
                FragmentManager fragmentManager = getSupportFragmentManager();
                boolean isLog = (fragmentManager.findFragmentById(R.id.fragment_container_main) instanceof LoginFragment
                        || fragmentManager.findFragmentById(R.id.fragment_container_main) instanceof LoginOutFragment);

                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_bottom, R.anim.slide_in_bottom, R.anim.slide_out_top);
                setupToolbar(fragmentActivity);
                fragmentTransaction.replace(R.id.fragment_container_main, fragmentActivity);

                if (!isLog)
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

            setFragment(null, ((DrawerItem) mDrawerList.getItemAtPosition(position)).getFragment(), null);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    toggleDrawer();
                }
            }, 300);
        }
    }

    public void setLoggedNameOnDrawer(String name) {
        if (name != null) {
            if (name == "")
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
            drawerElements.add(new DrawerItem(navMenuTitles[temp + 1], navMenuIcons.get(temp + 1)));
            ifChanged = true;
        } else {
            // temp+1 if so it gets "wyloguj" String and icon
            drawerElements.add(new DrawerItem(navMenuTitles[temp], navMenuIcons.get(temp)));
        }

        // Set the adapter for the list view
        mDrawerList.setAdapter(new DrawerAdapter(getApplicationContext(), R.layout.drawer_item, drawerElements));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        return ifChanged;
    }

    private void toolbarOnclick() {
        mDrawerToggle.setToolbarNavigationClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                setPreviousFragment();
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (!setPreviousFragment()) {
            this.finish();
            System.exit(0);
        }
    }

    public boolean setPreviousFragment() {


        if (getSupportFragmentManager().getBackStackEntryCount() > 2) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            mDrawerToggle.setDrawerIndicatorEnabled(false);
            getSupportFragmentManager().popBackStack();
            setupToolbar(true);
            ifLast = false;
            return true;
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 2) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            mDrawerToggle.setDrawerIndicatorEnabled(true);
            getSupportFragmentManager().popBackStack();
            setupToolbar(false);
            if (getSupportFragmentManager().getFragments().get(0) instanceof ConferenceListFragment)
                ifLast = true;
            else
                ifLast = false;
            return true;
        } else if (getSupportFragmentManager().getBackStackEntryCount() == 1 && !ifLast) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            mDrawerToggle.setDrawerIndicatorEnabled(true);
            setFragment(null, new ConferenceListFragment(), null);
            ifLast = true;
            return true;
        } else {
            return false;
        }
    }

    private void setupToolbar(boolean count) { //only used when back arrow clicked
        if (getSupportActionBar() != null)
            if (count) {
                mDrawerToggle.setDrawerIndicatorEnabled(false);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
                getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            } else {
                getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
                mDrawerToggle.setDrawerIndicatorEnabled(true);
            }
    }

    private void setupToolbar(Fragment fragment) {
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mDrawerToggle.setDrawerIndicatorEnabled(true);

        if (getSupportActionBar() != null) {
            if (fragment instanceof ConferenceFragment || fragment instanceof PartnerFragment ||
                    fragment instanceof SpeakerFragment || fragment instanceof QuestionFragment ||
                    fragment instanceof InfoFragment || fragment instanceof SpeakersConferenceListFragment) {
                mDrawerToggle.setDrawerIndicatorEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            } else {
                if (fragment instanceof ConferenceListFragment)
                    ifLast = true;
                else
                    ifLast = false;

                mDrawerToggle.setDrawerIndicatorEnabled(true);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
                clearBackStack();
            }
        }
    }

    @Override
    protected void onPostCreate(final Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_general, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.info:
                setFragment(null, new InfoFragment(), null);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void clearBackStack() {
        FragmentManager manager = getSupportFragmentManager();
        if (manager.getBackStackEntryCount() > 0) {
            FragmentManager.BackStackEntry first = manager.getBackStackEntryAt(0);
            manager.popBackStack(first.getId(), FragmentManager.POP_BACK_STACK_INCLUSIVE);
        }
    }
}
