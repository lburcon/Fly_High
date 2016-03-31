package com.example.yggdralisk.flyhighconference.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yggdralisk.flyhighconference.Adapters_Managers_Items.OrganisersRecyclerViewAdapter;
import com.example.yggdralisk.flyhighconference.Adapters_Managers_Items.SpeakersRecyclerViewAdapter;
import com.example.yggdralisk.flyhighconference.BackEnd.DataGetter;
import com.example.yggdralisk.flyhighconference.R;

/**
 * Created by lukasz on 31.03.16.
 */
public class OrganisersListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.organisers, container, false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.organisers_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new OrganisersRecyclerViewAdapter(DataGetter.getOrganisers(getContext()));
        mRecyclerView.setAdapter(mAdapter);


        return view;
    }
}
