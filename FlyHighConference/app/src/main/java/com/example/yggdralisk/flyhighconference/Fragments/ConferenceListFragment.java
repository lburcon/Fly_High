package com.example.yggdralisk.flyhighconference.Fragments;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.yggdralisk.flyhighconference.Adapters_Managers_Items.ConferenceRecyclerViewAdapter;
import com.example.yggdralisk.flyhighconference.BackEnd.AnalyticsApplication;
import com.example.yggdralisk.flyhighconference.BackEnd.DataGetter;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Presentation;
import com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces.GetHarmonogramResultInterface;
import com.example.yggdralisk.flyhighconference.BackEnd.ServerConnector;
import com.example.yggdralisk.flyhighconference.R;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by yggdralisk on 20.02.16.
 */
public class ConferenceListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Tracker mTracker;
    private Presentation[] mDataSet = {};
    int dayOfMonth = 0;
    int gotoButtonNr = 0; //Number of button to go to and display it's prelections
    int gotoViewNr = 0;
    int activeLLChild = 0;
    LinearLayout ll;

    ArrayList<ArrayList<Presentation>> separatedDaysPresentations;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.conference_list_view, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.conference_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        ll = (LinearLayout) view.findViewById(R.id.conference_list_buttons_layout);


        getDataSet();
        setAnalytics();
        try {
            ll.getChildAt(gotoButtonNr).callOnClick();//Open prelections for "today"
            mRecyclerView.scrollToPosition(gotoViewNr);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        return view;
    }

    private void setAnalytics() {
        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("Conference List Fragment");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    private void getDataSet() {
        final DataGetter dataGetter = new DataGetter(getActivity().getApplication());
        if (DataGetter.checkUserLogged(getContext())) {
            new ServerConnector().getHarmonogramToUser(getContext(), DataGetter.getLoggedUserId(getContext()), new GetHarmonogramResultInterface() {
                @Override
                public void onDownloadFinished(Presentation[] res) {
                    if (res != null) mDataSet = res;

                    if (mDataSet == null || mDataSet.length == 0)
                        mDataSet = dataGetter.getPresentations();
                    try {
                        separatedDaysPresentations = separateByDay(mDataSet);
                        setButtons(ll);
                    } catch (ParseException e) {
                        mAdapter = new ConferenceRecyclerViewAdapter(mDataSet, getContext());
                        mRecyclerView.setAdapter(mAdapter);
                    }

                    mAdapter = new ConferenceRecyclerViewAdapter(mDataSet, getContext());
                    mRecyclerView.setAdapter(mAdapter);
                }
            });
        } else {
            mDataSet = dataGetter.getPresentations();

            try {
                separatedDaysPresentations = separateByDay(mDataSet);
                setButtons(ll);
            } catch (ParseException e) {
                mAdapter = new ConferenceRecyclerViewAdapter(mDataSet, getContext());
                mRecyclerView.setAdapter(mAdapter);
            }

            mAdapter = new ConferenceRecyclerViewAdapter(mDataSet, getContext());
            mRecyclerView.setAdapter(mAdapter);
        }
    }

    private void setButtons(LinearLayout ll) {
        final int fontSize = 10;

        if (separatedDaysPresentations != null && separatedDaysPresentations.size() != 0) {
            ll.removeAllViews();
            Button tempButt = new Button(getContext());
            tempButt.setText("All\npresentations");
            tempButt.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
            tempButt.setBackgroundColor(getResources().getColor(R.color.main_yellow_dark));
            tempButt.setTextColor(getResources().getColor(R.color.text_white));
            tempButt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 90 / separatedDaysPresentations.size()));

            tempButt.setOnClickListener(new MyOnClick(new DataGetter(getActivity().getApplication()).getPresentations(), ll));
            ll.addView(tempButt);
            for (int i = 0; i < separatedDaysPresentations.size(); i++) {
                tempButt = new Button(getContext());
                tempButt.setText("Day\n" + (i + 1));
                tempButt.setTextSize(TypedValue.COMPLEX_UNIT_SP, fontSize);
                tempButt.setBackgroundColor(getResources().getColor(R.color.background_main));
                tempButt.setTextColor(getResources().getColor(R.color.text_white));
                tempButt.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, (float) 100 / separatedDaysPresentations.size()));

                Presentation[] presArray = new Presentation[separatedDaysPresentations.get(i).size()];
                presArray = separatedDaysPresentations.get(i).toArray(presArray);

                tempButt.setOnClickListener(new MyOnClick(presArray, ll));

                ll.addView(tempButt);
            }
        }
    }

    private ArrayList<ArrayList<Presentation>> separateByDay(Presentation[] mDataSet) throws ParseException {
        ArrayList<ArrayList<Presentation>> tempSeparatedPres = new ArrayList<>();
        ArrayList<Presentation> tempPresetatnions = new ArrayList<>();
        dayOfMonth = Calendar.getInstance().get(Calendar.DATE);

        for (Presentation presentation : mDataSet) {
            if (tempPresetatnions.size() == 0 || tempPresetatnions.get(0).getStartDay() == presentation.getStartDay()) {
                tempPresetatnions.add(presentation);
            } else {
                tempSeparatedPres.add(tempPresetatnions);
                if (tempPresetatnions.get(0).getStartDay() == dayOfMonth) {
                    getCurrentPosition(tempPresetatnions);
                    gotoButtonNr = tempSeparatedPres.size();
                }
                tempPresetatnions = new ArrayList<>();
                tempPresetatnions.add(presentation);
            }
        }
        if (tempPresetatnions.size() != 0)
            tempSeparatedPres.add(tempPresetatnions);

        return tempSeparatedPres;
    }

    public void getCurrentPosition(ArrayList<Presentation> tempPresetatnions) {
        Date currTime = new Date(System.currentTimeMillis());
        int i = 0;
        for (Presentation p :
                tempPresetatnions) {
            try {
                if(p.getStartTime().compareTo(currTime) < 0 && p.getEndTime().compareTo(currTime) > 0) {
                    gotoViewNr = i;
                    break;
                }
                i++;
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    private class MyOnClick implements View.OnClickListener {
        Presentation[] mDataSet;
        LinearLayout ll;

        public MyOnClick(Presentation[] mDataSet, LinearLayout ll) {
            this.mDataSet = mDataSet;
            this.ll = ll;
        }

        @Override
        public void onClick(View v) {
            if (mDataSet != null) {
                mAdapter = new ConferenceRecyclerViewAdapter(mDataSet, getContext());
                mRecyclerView.setAdapter(mAdapter);
            }

            if (ll != null) {
                int childcount = ll.getChildCount();
                for (int i = 0; i < childcount; i++)
                    ll.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.background_main));

                v.setBackgroundColor(getResources().getColor(R.color.main_yellow_dark));
                activeLLChild = ll.indexOfChild(v);
            }
        }
    }
}