package com.example.yggdralisk.flyhighconference;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lukasz on 27.02.16.
 */
public class PartnerFragment extends Fragment {

    private JSONArray mDataset = new JSONArray();
    private JSONObject partner = new JSONObject();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.partners_details, container, false);

        try {
            mDataset = getPartners();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0 ; i < mDataset.length() ; i++) {
            try {
                partner = mDataset.getJSONObject(i);
                if (Integer.parseInt(partner.getString("id")) == getArguments().getInt("partnerId"))
                    break;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        TextView name = (TextView) view.findViewById(R.id.partners_details_name);
        TextView title = (TextView) view.findViewById(R.id.partners_details_title); //TODO: add speaker
        TextView description = (TextView) view.findViewById(R.id.partners_details_description);
        ImageView image = (ImageView) view.findViewById(R.id.partners_details_image);


        try {
            title.setText(partner.getString("type"));
        } catch (JSONException e) {
            e.printStackTrace();
            title.setText("Błąd");
        }

        try {
            name.setText(partner.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
            name.setText("Błąd");
        }

        try {
            description.setText(partner.getString("url"));
        } catch (JSONException e) {
            e.printStackTrace();
            description.setText("Błąd");
        }

        try {
            Glide.with(getContext())
                    .load(partner.getString("logo"))
                    .placeholder(R.drawable.fly_high_logotype)
                    .into(image);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }

    private JSONArray getPartners() throws JSONException {
        return new JSONArray(getContext()
                .getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE)
                .getString(getString(R.string.shared_preferences_partners), ""));
    }

}
