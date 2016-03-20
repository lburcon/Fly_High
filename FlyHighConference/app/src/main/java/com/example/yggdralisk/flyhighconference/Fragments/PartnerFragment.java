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
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Partner;
import com.example.yggdralisk.flyhighconference.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lukasz on 27.02.16.
 */
public class PartnerFragment extends Fragment {

    private Partner[] mDataset;
    private Partner partner = new Partner();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.partners_details, container, false);

            mDataset = getPartners();


        for (int i = 0 ; i < mDataset.length ; i++) {

                partner = mDataset[i];
                if (partner.getId() == getArguments().getInt("partnerId"))
                    break;

        }

        TextView name = (TextView) view.findViewById(R.id.partners_details_name);
        TextView title = (TextView) view.findViewById(R.id.partners_details_title); //TODO: add speaker
        TextView description = (TextView) view.findViewById(R.id.partners_details_description);
        ImageView image = (ImageView) view.findViewById(R.id.partners_details_image);


            title.setText(partner.getType());

            name.setText(partner.getName());

            description.setText(partner.getUrl());

            Glide.with(getContext())
                    .load(partner.getLogo())
                    .placeholder(R.drawable.fly_high_logotype)
                    .into(image);

        return view;
    }

    private Partner[] getPartners(){
        return DataGetter.getPartners(getContext());
    }

}
