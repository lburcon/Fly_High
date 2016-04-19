package com.example.yggdralisk.flyhighconference.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yggdralisk.flyhighconference.BackEnd.DataGetter;
import com.example.yggdralisk.flyhighconference.Adapters_Managers_Items.PartnersRecyclerViewAdapter;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Partner;
import com.example.yggdralisk.flyhighconference.R;

import org.json.JSONArray;
import org.json.JSONException;

import butterknife.ButterKnife;

/**
 * Created by lukasz on 25.02.16.
 */
public class PartnersListFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.partners, container, false);
        mRecyclerView = ButterKnife.findById(view, R.id.partners_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new PartnersRecyclerViewAdapter(getPartners());
        mRecyclerView.setAdapter(mAdapter);


        return view;
    }

    private Partner[] getPartners() {
        return new DataGetter(getActivity().getApplication()).getPartners();
    }

}
