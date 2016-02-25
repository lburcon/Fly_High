package com.example.yggdralisk.flyhighconference;

import android.content.res.TypedArray;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;


    // slide menu items
    private String[] navMenuTitles;
    private List<Integer> navMenuIcons = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFragment(savedInstanceState, new ConferenceListFragment());
        setDrawer();
    }

    public void toggleDrawer() {
        DrawerLayout mDrawerLayout = (DrawerLayout)findViewById(R.id.main_drawer);
        if(mDrawerLayout.isDrawerOpen(Gravity.LEFT))
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        else
            mDrawerLayout.openDrawer(Gravity.LEFT);
    }

    public void setDrawer() {
       navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items);

        TypedArray ids = getResources().obtainTypedArray(R.array.nav_drawer_icons);

        for(int i = 0; i < ids.length(); i ++)
        navMenuIcons.add(ids.getResourceId(i, -1));

        mDrawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);
        mDrawerList = (ListView) findViewById(R.id.left_drawer_list_view);

        ArrayList<DrawerItem> drawerElements = new ArrayList<>();
        int temp;
        if(navMenuTitles.length>=navMenuIcons.size())
            temp = navMenuTitles.length;
        else
            temp = navMenuTitles.length;
        for(int i = 0; i < temp; i++)
            drawerElements.add(new DrawerItem(navMenuTitles[i], navMenuIcons.get(i)));

        // Set the adapter for the list view
        mDrawerList.setAdapter(new DrawerAdapter(getApplicationContext(),R.layout.drawer_item, drawerElements));
        // Set the list's click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
    }

    public void setFragment(Bundle savedInstanceState,Fragment fragmentActivity) {
        try
        {
        if (findViewById(R.id.fragment_container_main) != null) {
            if (savedInstanceState != null) {
                return;
            }
            fragmentActivity.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_main, fragmentActivity)
                    .commit();
        }
            }
        catch(IllegalStateException ex)
        {
            ex.printStackTrace();
        }
    }

    private class DrawerItemClickListener implements android.widget.AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
             setFragment(null,((DrawerItem)mDrawerList.getItemAtPosition(position)).fragment);
        }
    }
}
