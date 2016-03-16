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
import com.example.yggdralisk.flyhighconference.R;

import org.json.JSONArray;
import org.json.JSONException;

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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.partners_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        //TODO: hamburger icon

       try {
            mAdapter = new PartnersRecyclerViewAdapter(getPartners());
            mRecyclerView.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }


       /*  Button drawerButton = (Button) view.findViewById(R.id.conference_drawer_button);
        drawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).toggleDrawer();
            }
        });*/

        return view;
    }

    private JSONArray getPartners() throws JSONException {
        return DataGetter.getPartners(getContext());
    }

}