package com.example.yggdralisk.flyhighconference.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
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
import org.w3c.dom.Text;

import butterknife.Bind;

/**
 * Created by lukasz on 01.03.16.
 */
public class SpeakerFragment extends Fragment {

    private Speaker speaker = new Speaker();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.speaker_details, container, false);

        speaker = new DataGetter(getActivity().getApplication()).getSpeakerById(getArguments().getInt("speakerId"));

        TextView name = (TextView) view.findViewById(R.id.speaker_name);
        TextView country = (TextView) view.findViewById(R.id.speaker_country);
        TextView description = (TextView) view.findViewById(R.id.speaker_description);
        ImageView image = (ImageView) view.findViewById(R.id.speaker_image);

        description.setMovementMethod(new ScrollingMovementMethod());

        name.setText(speaker.getName());


        description.setText(speaker.getDescription() +//todo: add description
                "\n URL: " + speaker.getUrl());

        country.setText("Country: " + speaker.getCountry());

        Glide.with(this)
                .load(speaker.getImage())
                .placeholder(R.drawable.fly_high_temp)
                .dontAnimate()
                .into(image);

        return view;
    }
}
