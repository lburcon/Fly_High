package com.example.yggdralisk.flyhighconference;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by lukasz on 03.03.16.
 */
public class QuestionFragment extends Fragment {

    private JSONArray mQuestions = new JSONArray();
    private JSONObject speaker = new JSONObject();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int speakerId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_details, container, false);
        speakerId = getArguments().getInt("speakerId");


        mRecyclerView = (RecyclerView) view.findViewById(R.id.question_details_recycler_view);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setNestedScrollingEnabled(false);
        mLayoutManager = new WrappingLinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        getArrayOfIds(getPrelectionsId(speakerId));
        mAdapter = new QuestionAdapter(mQuestions, getContext());
        mRecyclerView.setAdapter(mAdapter);

        try {
            speaker = DataGetter.getPresentationById(speakerId, getContext());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        CollapsingToolbarLayout toolbar = (CollapsingToolbarLayout) view.findViewById(R.id.question_details_collapsing_toolbar);

        try {
            toolbar.setTitle(speaker.getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
            toolbar.setTitle("Błąd");
        }


        return view;
    }

    private JSONArray getSpeakers() throws JSONException {
        return DataGetter.getSpeakers(getContext());
    }

    private ArrayList<Integer> getPrelectionsId(int speakerId) { //returns ids of prelections given by the speaker
        ArrayList<Integer> prelectionIds = new ArrayList<>();
        JSONArray presentations;

        try {
            presentations = DataGetter.getPresentations(getContext());

            for (int i = 0; i < presentations.length(); i++) {
                JSONObject presentation = presentations.getJSONObject(i);
                int[] speakersIds = getSpeakerIds(presentation);

                for (int j = 0; j < speakersIds.length; j++)
                    if (speakerId == speakersIds[i]) ;
                prelectionIds.add(Integer.parseInt(presentation.getString("id")));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return prelectionIds;
    }

    private int[] getSpeakerIds(JSONObject presentation) { // returns ids of prelegents for one prelection
        int[] speakerIds = null;
        try {
            String ids = presentation.getString("speakers");
            String[] strArray = ids.split(",");
            speakerIds = new int[strArray.length];
            for (int i = 0; i < strArray.length; i++) {
                speakerIds[i] = Integer.parseInt(strArray[i]);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return speakerIds;
    }

    private void getArrayOfIds(ArrayList<Integer> prelectionIds) { //adds questions to mQuestion JSONArray list

        ServerConnector serverConnector;

        for (int i = 0; i < prelectionIds.size(); i++) {
            serverConnector = new ServerConnector();
            serverConnector.getQuestionsToPresentation(getContext(), prelectionIds.get(i));
        }

        try {
            for (int i = 0; i < prelectionIds.size(); i++)
                mQuestions.put(DataGetter.getQuestionsToPresentation(getContext(), prelectionIds.get(i)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
