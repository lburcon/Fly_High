package com.example.yggdralisk.flyhighconference;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;

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

        View view = inflater.inflate(R.layout.conference_view, container, false);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.conference_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        try {
            mAdapter = new ConferenceRecyclerViewAdapter(getPresentations());
            mRecyclerView.setAdapter(mAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        Button drawerButton = (Button) view.findViewById(R.id.conference_drawer_button);
        drawerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MainActivity) getActivity()).toggleDrawer();
            }
        });

        return view;
    }

    private JSONArray getPresentations() throws JSONException {
       return new JSONArray(getContext()
               .getSharedPreferences(getString(R.string.shared_preferences_name), Context.MODE_PRIVATE)
               .getString(getString(R.string.shared_preferences_presentations), ""));
    }

}
