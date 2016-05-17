package com.example.yggdralisk.flyhighconference.Fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yggdralisk.flyhighconference.Adapters_Managers_Items.ConferenceRecyclerViewAdapter;
import com.example.yggdralisk.flyhighconference.BackEnd.AnalyticsApplication;
import com.example.yggdralisk.flyhighconference.BackEnd.DataGetter;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Presentation;
import com.example.yggdralisk.flyhighconference.R;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.nio.channels.DatagramChannel;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lukasz on 10.04.16.
 */
public class ConferenceFavouriteList extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Tracker mTracker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.conference_favourite, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.conference_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        ArrayList<Integer> favList = DataGetter.getLoggedUserFavs(getContext());

        Presentation[] mDataSet = new Presentation[favList.size()];

        for (int i = 0; i < favList.size() ; i++) {
            mDataSet[i] = new DataGetter(getActivity().getApplication()).getPresentationById(favList.get(i));
        }
        mAdapter = new ConferenceRecyclerViewAdapter(mDataSet, getContext());
        mRecyclerView.setAdapter(mAdapter);


        new TimeFinder().execute(mDataSet);

        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();


        mTracker.setScreenName("Conference Fabvourite List");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());


        return view;
    }

    protected void scrollToCurrentPresentation(int index) {
        mLayoutManager.scrollToPosition(index);
    }

    private class TimeFinder extends AsyncTask<Presentation[], Void, Void> {
        int index;

        private int compareDates(String dt1, String dt2) throws ParseException {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date date1 = formatter.parse(dt1);

            Date date2 = formatter.parse(dt2);

            return date1.compareTo(date2);

        }

        @Override
        protected Void doInBackground(Presentation[]... params) {
            Presentation[] mDataSet = params[0];

            String currentDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
            try {
                for (int i = 0; i < mDataSet.length; i++) {
                    if (compareDates(mDataSet[i].getStart(), currentDate) >= 0 && compareDates(mDataSet[i].getEnd(), currentDate) <= 0)
                        index = i;

                }
            } catch (ParseException e) {
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