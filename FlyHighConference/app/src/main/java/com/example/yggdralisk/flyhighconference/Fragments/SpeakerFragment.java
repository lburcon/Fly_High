package com.example.yggdralisk.flyhighconference.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yggdralisk.flyhighconference.BackEnd.DataGetter;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Speaker;
import com.example.yggdralisk.flyhighconference.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lukasz on 01.03.16.
 */
public class SpeakerFragment extends Fragment {

    private JSONArray mDataset = new JSONArray();
    private Speaker speaker = new Speaker();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.speaker_details, container, false);



            speaker = DataGetter.getSpeakerById(getArguments().getInt("speakerId"), getContext());


        TextView name = (TextView) view.findViewById(R.id.speaker_name);
        TextView description = (TextView) view.findViewById(R.id.speaker_description);
        ImageView image = (ImageView) view.findViewById(R.id.speaker_image);

            name.setText(speaker.getName());



            description.setText(speaker.getDescription() +//todo: add description
                                "\n Kraj pochodzenia: " + speaker.getCountry() +
                                "\n URL: " + speaker.getUrl());


            Glide.with(this)
                    .load(speaker.getImage())
                    .placeholder(R.drawable.fly_high_logotype)
                    .into(image);


        return view;
    }
}
