package com.example.yggdralisk.flyhighconference.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.yggdralisk.flyhighconference.Adapters_Managers_Items.OrganisersRecyclerViewAdapter;
import com.example.yggdralisk.flyhighconference.Adapters_Managers_Items.SpeakersRecyclerViewAdapter;
import com.example.yggdralisk.flyhighconference.BackEnd.AnalyticsApplication;
import com.example.yggdralisk.flyhighconference.BackEnd.DataGetter;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Organiser;
import com.example.yggdralisk.flyhighconference.R;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;

import butterknife.Bind;

/**
 * Created by lukasz on 31.03.16.
 */
public class OrganisersListFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    Organiser[] organisers;
    private Tracker mTracker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.organisers, container, false);


        mRecyclerView = (RecyclerView) view.findViewById(R.id.organisers_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        organisers = new Organiser[new DataGetter(getActivity().getApplication()).getOrganisers().length + 2];
        addDevelopers();
        mAdapter = new OrganisersRecyclerViewAdapter(organisers, getContext());
        mRecyclerView.setAdapter(mAdapter);


        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();


        mTracker.setScreenName("Organisers List Fragment");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());


        return view;
    }

    private void addDevelopers() {
        Organiser[] temp = new DataGetter(getActivity().getApplication()).getOrganisers();
        int count = temp.length;

        for (int i = 0 ; i < count ; i++){
            organisers[i] = temp[i];
        }

        temp = makeDevelopers();

        for (int i = count ; i < organisers.length  ; i++) {
            organisers[i] = temp[organisers.length - i - 1];
        }
    }

    private Organiser[] makeDevelopers() {
        Organiser[] devArray = new Organiser[2];
        int place = new DataGetter(getActivity().getApplication()).getOrganisers().length;

        Organiser Lukas = new Organiser();
        Lukas.setId(place + 1);
        Lukas.setName("Łukasz Burcon");
        Lukas.setTitle("FlyHigh App Developer");
        Lukas.setEmail("lukasburcon@gmail.com");
        Lukas.setDescription("Student on Politechnika Wrocławska");
        Lukas.setImage("photo_lukas");

        Organiser John = new Organiser();
        John.setId(place + 2);
        John.setName("Jan Stoltman");
        John.setTitle("FlyHigh App Developer");
        John.setEmail("stoltmanjan@gmail.com");
        John.setDescription("Student on Politechnika Wrocławska");
        John.setImage("photo_john");

        devArray[0] = John;
        devArray[1] = Lukas;

        return devArray;
    }
}
