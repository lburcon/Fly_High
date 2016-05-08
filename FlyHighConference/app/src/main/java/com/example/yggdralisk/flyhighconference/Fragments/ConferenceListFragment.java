package com.example.yggdralisk.flyhighconference.Fragments;

import android.media.audiofx.PresetReverb;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.yggdralisk.flyhighconference.Adapters_Managers_Items.ConferenceRecyclerViewAdapter;
import com.example.yggdralisk.flyhighconference.BackEnd.DataGetter;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Presentation;
import com.example.yggdralisk.flyhighconference.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.ButterKnife;

/**
 * Created by yggdralisk on 20.02.16.
 */
public class ConferenceListFragment extends Fragment {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    ArrayList<ArrayList<Presentation>> separatedDaysPresentations;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.conference_list_view, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.conference_recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        Presentation[] mDataSet = new DataGetter(getActivity().getApplication()).getPresentations();
        try {
           separatedDaysPresentations = separateByDay(mDataSet);
            LinearLayout ll = (LinearLayout)view.findViewById(R.id.conference_list_buttons_layout);
            setButtons(ll);
        } catch (ParseException e) {
            mAdapter = new ConferenceRecyclerViewAdapter(mDataSet, getContext());
            mRecyclerView.setAdapter(mAdapter);
        }

        mAdapter = new ConferenceRecyclerViewAdapter(mDataSet, getContext());
        mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    private void setButtons(LinearLayout ll) {
        final float fontSize = 7f;
        final int maxWidth = 50;
        if (separatedDaysPresentations != null && separatedDaysPresentations.size() != 0) {
            Button tempButt = new Button(getContext());
            tempButt.setText("All\npresentations");
            tempButt.setTextSize(fontSize);
            tempButt.setMaxWidth(maxWidth);

            tempButt.setOnClickListener(new MyOnClick(new DataGetter(getActivity().getApplication()).getPresentations()));
            ll.addView(tempButt);
            for (int i = 0; i < separatedDaysPresentations.size(); i++) {
                tempButt = new Button(getContext());
                tempButt.setText("Day " + (i + 1));
                tempButt.setTextSize(fontSize);
                tempButt.setMaxWidth(maxWidth);

                Presentation[] presArray = new Presentation[separatedDaysPresentations.get(i).size()];
                presArray = separatedDaysPresentations.get(i).toArray(presArray);

                tempButt.setOnClickListener(new MyOnClick(presArray));

                ll.addView(tempButt);
            }
        }
    }

    private ArrayList<ArrayList<Presentation>> separateByDay(Presentation[] mDataSet) throws ParseException {
        ArrayList<ArrayList<Presentation>> tempSeparatedPres = new ArrayList<>();
        ArrayList<Presentation> tempPresetatnions = new ArrayList<>();

        for (Presentation presentation : mDataSet) {
                if(tempPresetatnions.size() == 0 || tempPresetatnions.get(0).getStartDay() == presentation.getStartDay()) {
                    tempPresetatnions.add(presentation);
                }
                else{
                    tempSeparatedPres.add(tempPresetatnions);
                    tempPresetatnions = new ArrayList<>();
                }
        }

        tempSeparatedPres.add(tempPresetatnions);

        return tempSeparatedPres;
    }

    protected void scrollToCurrentPresentation(int index) {
        if(mLayoutManager != null)
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

        public MyOnClick(Presentation[] mDataSet){
            this.mDataSet = mDataSet;
        }

        @Override
        public void onClick(View v) {
            if(mDataSet != null) {
                mAdapter = new ConferenceRecyclerViewAdapter(mDataSet, getContext());
                mRecyclerView.setAdapter(mAdapter);
            }
        }
    }
}