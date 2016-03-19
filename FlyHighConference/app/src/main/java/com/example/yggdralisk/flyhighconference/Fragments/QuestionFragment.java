package com.example.yggdralisk.flyhighconference.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.yggdralisk.flyhighconference.BackEnd.ConnectorResultInterface;
import com.example.yggdralisk.flyhighconference.BackEnd.DataGetter;
import com.example.yggdralisk.flyhighconference.BackEnd.ServerConnector;
import com.example.yggdralisk.flyhighconference.Adapters_Managers_Items.QuestionAdapter;
import com.example.yggdralisk.flyhighconference.R;
import com.example.yggdralisk.flyhighconference.Adapters_Managers_Items.WrappingLinearLayoutManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lukasz on 03.03.16.
 */
public class QuestionFragment extends Fragment {

    private JSONArray mQuestions = new JSONArray();
    private JSONObject presentation = new JSONObject();
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
            presentation = DataGetter.getPresentationById(speakerId, getContext());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        CollapsingToolbarLayout toolbar = ButterKnife.findById(view, R.id.question_details_collapsing_toolbar);
        ImageView imageTop = ButterKnife.findById(view, R.id.question_details_image);

        try {
            toolbar.setTitle(presentation.getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
            toolbar.setTitle("Błąd");
        }

        try {
            Glide.with(getContext())
                    .load(presentation.getString("image"))
                    .placeholder(R.drawable.fly_high_logotype)
                    .into(imageTop);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return view;
    }

    private ArrayList<Integer> getPrelectionsId(int speakerId) { //returns ids of prelections given by the speaker
        Set<Integer> prelectionIds = new HashSet<>();
        JSONArray presentations;

        try {
            presentations = DataGetter.getPresentations(getContext());

            for (int i = 0; i < presentations.length(); i++) {
                JSONObject presentation = presentations.getJSONObject(i);
                int[] speakersIds = getSpeakerIds(presentation);

                for (int j = 0; j < speakersIds.length; j++)
                { if (speakerId == speakersIds[j])
                    prelectionIds.add(Integer.parseInt(presentation.getString("id")));}
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        ArrayList<Integer> listOfIds = new ArrayList<>();
        listOfIds.addAll(prelectionIds);
        return listOfIds;
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
            serverConnector.getQuestionsToPresentation(getContext(), prelectionIds.get(i), new ConnectorResultInterface() {
                @Override
                public void onDownloadFinished(boolean succeeded) {

                }
            });
        }

        try {
            for (int i = 0; i < prelectionIds.size(); i++)
                mQuestions.put(DataGetter.getQuestionsToPresentation(getContext(), prelectionIds.get(i)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}