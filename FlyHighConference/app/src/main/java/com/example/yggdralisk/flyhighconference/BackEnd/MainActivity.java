package com.example.yggdralisk.flyhighconference.BackEnd;

import android.content.res.TypedArray;
import android.os.Handler;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yggdralisk.flyhighconference.Adapters_Managers_Items.DrawerAdapter;
import com.example.yggdralisk.flyhighconference.Adapters_Managers_Items.DrawerItem;
import com.example.yggdralisk.flyhighconference.Fragments.ConferenceFavouriteList;
import com.example.yggdralisk.flyhighconference.Fragments.ConferenceFragment;
import com.example.yggdralisk.flyhighconference.Fragments.ConferenceListFragment;
import com.example.yggdralisk.flyhighconference.Fragments.InfoFragment;
import com.example.yggdralisk.flyhighconference.Fragments.LoginFragment;
import com.example.yggdralisk.flyhighconference.Fragments.LoginOutFragment;
import com.example.yggdralisk.flyhighconference.Fragments.OrganisersListFragment;
import com.example.yggdralisk.flyhighconference.Fragments.QuestionFragment;
import com.example.yggdralisk.flyhighconference.Fragments.SpeakerFragment;
import com.example.yggdralisk.flyhighconference.Fragments.SpeakersConferenceListFragment;
import com.example.yggdralisk.flyhighconference.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ConferenceFragment.OnDataPass {

    @Bind(R.id.main_drawer)
    DrawerLayout mDrawerLayout;
    @Bind(R.id.left_drawer_list_view)
    ListView mDrawerList;
    @Bind(R.id.left_drawer_logged_name)
    TextView loggedName;
    @Bind(R.id.activity_main_toolbar)
    Toolbar mToolbar;


    private ActionBarDrawerToggle mDrawerToggle;

    private Bundle toolbarData;
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

        if (DataGetter.checkUserLogged(getApplicationContext()))
            setLoggedNameOnDrawer(DataGetter.getLoggedUserName(getApplicationContext()));

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

    //Jeżeli nie wiesz co wysłać w jako savedInstanceState wyślij null
    public void setFragment(Bundle savedInstanceState, Fragment fragmentActivity, Bundle args) {
        try {
            if (findViewById(R.id.fragment_container_main) != null) {
                if (savedInstanceState != null) {
                    return;
                }

                if (getSupportFragmentManager().findFragmentById(R.id.fragment_container_main) != null)
                    if ((getSupportFragmentManager().findFragmentById(R.id.fragment_container_main).getClass() == fragmentActivity.getClass()))
                        return;

                fragmentActivity.setArguments(getIntent().getExtras());
                if (args != null) fragmentActivity.setArguments(args);
                FragmentManager fragmentManager = getSupportFragmentManager();

                boolean isLog = (fragmentManager.findFragmentById(R.id.fragment_container_main) instanceof LoginFragment
                        || fragmentManager.findFragmentById(R.id.fragment_container_main) instanceof LoginOutFragment);

                android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                fragmentTransaction.setCustomAnimations(R.anim.slide_in_top, R.anim.slide_out_bottom, R.anim.slide_in_bottom, R.anim.slide_out_top);

                fragmentTransaction.replace(R.id.fragment_container_main, fragmentActivity);

                if (!isLog)
                    fragmentTransaction.addToBackStack(null);

                setupToolbar(fragmentActivity);
                fragmentTransaction.commit();
                fragmentManager.executePendingTransactions();
                invalidateOptionsMenu();
            }
        } catch (IllegalStateException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void dataPass(Bundle data) {
        toolbarData = data;
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
            }, 350);
        }
    }

    public void setLoggedNameOnDrawer(String name) {
        if (name != null) {
            if (name.equals(""))
                loggedName.setText("");
            else
                loggedName.setText(getString(R.string.left_drawer_logged_name) + " " + name);

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
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START))
            toggleDrawer();

        if (!setPreviousFragment()) {
            this.finish();
            System.exit(0);
            //android.os.Process.killProcess(android.os.Process.myPid()); // mozna tez uzyć tego do killowania
        }
    }

    public boolean setPreviousFragment() {
        if (getSupportFragmentManager().getFragments().get(0) instanceof ConferenceListFragment)
            android.os.Process.killProcess(android.os.Process.myPid());
        if (getSupportActionBar() != null) {
            if (getSupportFragmentManager().getBackStackEntryCount() > 2) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                mDrawerToggle.setDrawerIndicatorEnabled(false);
                getSupportFragmentManager().popBackStack();
                setupToolbar(true);
                ifLast = false;
                getSupportFragmentManager().executePendingTransactions();
                invalidateOptionsMenu();
            } else if (getSupportFragmentManager().getBackStackEntryCount() == 2) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                mDrawerToggle.setDrawerIndicatorEnabled(true);
                getSupportFragmentManager().popBackStack();
                setupToolbar(false);
                ifLast = getSupportFragmentManager().getFragments().get(0) instanceof ConferenceListFragment;
                getSupportFragmentManager().executePendingTransactions();
                invalidateOptionsMenu();
            } else if (getSupportFragmentManager().getBackStackEntryCount() == 1 && !ifLast) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                mDrawerToggle.setDrawerIndicatorEnabled(true);
                setFragment(null, new ConferenceListFragment(), null);
                ifLast = true;
                getSupportFragmentManager().executePendingTransactions();
                invalidateOptionsMenu();
            } else {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        } else {
            getSupportFragmentManager().popBackStack();
        }

        return getSupportFragmentManager().getBackStackEntryCount() != 0
                || !(getSupportFragmentManager().getFragments().get(0) instanceof LoginFragment)
                || !(getSupportFragmentManager().getFragments().get(0) instanceof LoginOutFragment);
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
        if (getSupportActionBar() == null) return;
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        mDrawerToggle.setDrawerIndicatorEnabled(true);

        if (getSupportActionBar() != null) {
            if (fragment instanceof ConferenceFragment ||
                    fragment instanceof SpeakerFragment || fragment instanceof QuestionFragment ||
                    fragment instanceof InfoFragment || fragment instanceof SpeakersConferenceListFragment ||
                    fragment instanceof OrganisersListFragment || fragment instanceof ConferenceFavouriteList) {
                mDrawerToggle.setDrawerIndicatorEnabled(false);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setDisplayShowTitleEnabled(false);
            } else {
                ifLast = fragment instanceof ConferenceListFragment;
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

/*    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {


        if ((getSupportFragmentManager().findFragmentById(R.id.fragment_container_main) instanceof ConferenceFragment)) {
            menu.clear();
            getMenuInflater().inflate(R.menu.menu_with_prelegents, menu);
        }

        return super.onPrepareOptionsMenu(menu);
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_general, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            /*case R.id.info:
                if (!(getSupportFragmentManager().findFragmentById(R.id.fragment_container_main) instanceof InfoFragment))
                    setFragment(null, new InfoFragment(), null);
                return true;*/
            case R.id.organisers:
                if (!(getSupportFragmentManager().findFragmentById(R.id.fragment_container_main) instanceof OrganisersListFragment))
                    setFragment(null, new OrganisersListFragment(), null);
                return true;
            case R.id.favourites:
                if (!(getSupportFragmentManager().findFragmentById(R.id.fragment_container_main) instanceof ConferenceFavouriteList) && DataGetter.checkUserLogged(this))
                    setFragment(null, new ConferenceFavouriteList(), null);
                else
                    Toast.makeText(this, R.string.not_logged_fav, Toast.LENGTH_SHORT).show();
                return true;
            /*case R.id.prelegents:
                if(toolbarData == null){
                    return false;
                } else if (toolbarData.getIntArray("speakersIds") != null)
                    {setFragment(null, new SpeakersConferenceListFragment(), toolbarData);
                    return true;}
                else
                    {setFragment(null, new SpeakerFragment(), toolbarData);
                    return true;}*/
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
