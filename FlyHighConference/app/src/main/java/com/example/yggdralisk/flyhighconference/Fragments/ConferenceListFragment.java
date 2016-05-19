package com.example.yggdralisk.flyhighconference.Fragments;

import android.os.AsyncTask;
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
        final DataGetter dataGetter = new DataGetter(getActivity().getApplication());

       if(DataGetter.checkUserLogged(getContext())){
           new ServerConnector().getHarmonogramToUser(getContext(), DataGetter.getLoggedUserId(getContext()), new GetHarmonogramResultInterface() {
               @Override
               public void onDownloadFinished(Presentation[] res) {
                   if(res != null) mDataSet =res;

                   if(mDataSet == null || mDataSet.length == 0)  mDataSet = dataGetter.getPresentations();

                   try {
                       separatedDaysPresentations = separateByDay(mDataSet);
                       ll = (LinearLayout) view.findViewById(R.id.conference_list_buttons_layout);
                       setButtons(ll);
                   } catch (ParseException e) {
                       mAdapter = new ConferenceRecyclerViewAdapter(mDataSet, getContext());
                       mRecyclerView.setAdapter(mAdapter);
                   }

                   mAdapter = new ConferenceRecyclerViewAdapter(mDataSet, getContext());
                   mRecyclerView.setAdapter(mAdapter);

       /* view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (ll != null) {
                        if (activeLLChild == ll.getChildCount()-1) ll.getChildAt(0).callOnClick();
                        else ll.getChildAt(activeLLChild + 1).callOnClick();
                        return true;
                    }
                }

                return false;
            }
        });*/

               }
           });} else {
           mDataSet = dataGetter.getPresentations();

           try {
               separatedDaysPresentations = separateByDay(mDataSet);
               ll = (LinearLayout) view.findViewById(R.id.conference_list_buttons_layout);
               setButtons(ll);
           } catch (ParseException e) {
               mAdapter = new ConferenceRecyclerViewAdapter(mDataSet, getContext());
               mRecyclerView.setAdapter(mAdapter);
           }

           mAdapter = new ConferenceRecyclerViewAdapter(mDataSet, getContext());
           mRecyclerView.setAdapter(mAdapter);
       }

        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        mTracker.setScreenName("Conference List Fragment");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
        return view;
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

            tempButt.setOnClickListener(new MyOnClick(new DataGetter(getActivity().getApplication()).getPresentations(),ll));
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

                tempButt.setOnClickListener(new MyOnClick(presArray,ll));

                ll.addView(tempButt);
            }
        }
    }

    private ArrayList<ArrayList<Presentation>> separateByDay(Presentation[] mDataSet) throws ParseException {
        ArrayList<ArrayList<Presentation>> tempSeparatedPres = new ArrayList<>();
        ArrayList<Presentation> tempPresetatnions = new ArrayList<>();

        for (Presentation presentation : mDataSet) {
            if (tempPresetatnions.size() == 0 || tempPresetatnions.get(0).getStartDay() == presentation.getStartDay()) {
                tempPresetatnions.add(presentation);
            } else {
                tempSeparatedPres.add(tempPresetatnions);
                tempPresetatnions = new ArrayList<>();
            }
        }

        tempSeparatedPres.add(tempPresetatnions);

        return tempSeparatedPres;
    }

    protected void scrollToCurrentPresentation(int index) {
        if (mLayoutManager != null)
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
                    if (compareDates(mDataSet[i].getStart(), currentDate) >= 0 && compareDates(mDataSet[i].getEnd(), currentDate) <= 0) {
                        index = i;
                        break;
                    }
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