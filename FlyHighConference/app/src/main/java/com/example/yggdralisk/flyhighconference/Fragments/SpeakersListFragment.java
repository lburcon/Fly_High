package com.example.yggdralisk.flyhighconference.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yggdralisk.flyhighconference.BackEnd.DataGetter;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Speaker;
import com.example.yggdralisk.flyhighconference.R;
import com.example.yggdralisk.flyhighconference.Adapters_Managers_Items.SpeakersRecyclerViewAdapter;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by lukasz on 01.03.16.
 */
public class SpeakersListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Tracker mTracker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.speakers, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.speakers_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        if(getArguments() == null || getArguments().getIntArray("speakersIds") == null) {
            mAdapter = new SpeakersRecyclerViewAdapter(getSpeakers());
            mRecyclerView.setAdapter(mAdapter);
        }
        else
        {
            DataGetter dataGetter = new DataGetter(getActivity().getApplication());
            int[] tempSpeakersArr =  getArguments().getIntArray("speakersIds");
            ArrayList<Speaker> temp = new ArrayList<>();
            for(int i = 0; i < tempSpeakersArr.length; i++)
               temp.add(dataGetter.getSpeakerById(tempSpeakersArr[i]));

            mAdapter = new SpeakersRecyclerViewAdapter(temp.toArray(new Speaker[1]));
            mRecyclerView.setAdapter(mAdapter);
        }

        return view;
    }

    private Speaker[] getSpeakers(){
        return new DataGetter(getActivity().getApplication()).getSpeakers();
    }

}
