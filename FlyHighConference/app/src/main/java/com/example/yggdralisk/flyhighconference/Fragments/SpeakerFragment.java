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
import com.example.yggdralisk.flyhighconference.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lukasz on 01.03.16.
 */
public class SpeakerFragment extends Fragment {

    private JSONArray mDataset = new JSONArray();
    private JSONObject speaker = new JSONObject();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.speaker_details, container, false);

        try {
            mDataset = getSpeakers();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            speaker = DataGetter.getSpeakerById(getArguments().getInt("speakerId"), getContext());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        TextView name = (TextView) view.findViewById(R.id.speaker_name);
        TextView description = (TextView) view.findViewById(R.id.speaker_description);
        ImageView image = (ImageView) view.findViewById(R.id.speaker_image);

        try {
            name.setText(speaker.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
            name.setText("Błąd");
        }

        try {
            description.setText(speaker.getString("description") +
                                "\n Kraj pochodzenia: " + speaker.getString("country") +
                                "\n URL: " + speaker.getString("url"));
        } catch (JSONException e) {
            e.printStackTrace();
        description.setText("Błąd");
        }

        try {
            Glide.with(this)
                    .load(speaker.getString("image"))
                    .placeholder(R.drawable.fly_high_logotype)
                    .into(image);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return view;
    }

    private JSONArray getSpeakers() throws JSONException {
        return DataGetter.getSpeakers(getContext());
    }
}
