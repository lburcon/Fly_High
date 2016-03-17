package com.example.yggdralisk.flyhighconference.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.yggdralisk.flyhighconference.Adapters_Managers_Items.ConferenceRecyclerViewAdapter;
import com.example.yggdralisk.flyhighconference.BackEnd.DataGetter;
import com.example.yggdralisk.flyhighconference.BackEnd.MainActivity;
import com.example.yggdralisk.flyhighconference.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yggdralisk on 20.02.16.
 */
public class ConferenceListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.conference_list_view, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.conference_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        JSONArray mDataSet = new JSONArray();
        try {
            mDataSet = DataGetter.getPresentations(getContext());
            mAdapter = new ConferenceRecyclerViewAdapter(mDataSet, getContext());
            mRecyclerView.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new TimeFinder().execute(mDataSet);

        ImageButton drawerButton = (ImageButton) view.findViewById(R.id.conference_drawer_button);
        drawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getContext()).toggleDrawer();
            }
        });

        return view;
    }

    protected void scrollToCurrentPresentation(int index) {
        mLayoutManager.scrollToPosition(index);
    }

    private class TimeFinder extends AsyncTask<JSONArray, Void, Void> {
        int index;

        private int compareDates(String dt1, String dt2) throws ParseException {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date date1 = formatter.parse(dt1);

            Date date2 = formatter.parse(dt2);

            return date1.compareTo(date2);

        }

        @Override
        protected Void doInBackground(JSONArray... params) {
            JSONArray mDataSet = params[0];

            String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            try {
                for (int i = 0; i < mDataSet.length(); i++) {
                    if (compareDates(mDataSet.getJSONObject(i).getString("start"), currentDate) >= 0 && compareDates(mDataSet.getJSONObject(i).getString("end"), currentDate) <= 0)
                        index = i;

                }
            } catch (JSONException | ParseException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            scrollToCurrentPresentation(index);
        }
    }
}