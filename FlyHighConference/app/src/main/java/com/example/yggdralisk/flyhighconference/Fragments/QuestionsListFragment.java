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
import com.example.yggdralisk.flyhighconference.Adapters_Managers_Items.QuestionsRecyclerViewAdapter;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Presentation;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Speaker;
import com.example.yggdralisk.flyhighconference.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by lukasz on 03.03.16.
 */
public class QuestionsListFragment extends Fragment {

    Speaker[] speakerArray;
    Presentation[] presentationArray;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.questions, container, false);

        speakerArray = getSpeakers();
        if (presentationArray == null)
        presentationArray = getPresentations();
        ArrayList<Presentation> presList = new ArrayList<>();

        for (Presentation p : presentationArray) {

            presList.add(p);

        }

        for (int i = 0; i < presList.size() ; i++) {

            switch (presList.get(i).getTitle()){

                case "Breakfast":
                case "Supper":
                case "Coffee break":
                case "Coffee lunch":
                case "Dinner":
                case "Lunch":
                    presList.remove(i);

            }

        }



        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.questions_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);


        RecyclerView.Adapter mAdapter = new QuestionsRecyclerViewAdapter(speakerArray, presList);

            mRecyclerView.setAdapter(mAdapter);

        return view;
    }

    private Speaker[] getSpeakers(){
        return new DataGetter(getActivity().getApplication()).getSpeakers();
    }

    private Presentation[] getPresentations(){
        return new DataGetter(getActivity().getApplication()).getPresentations();
    }

}

