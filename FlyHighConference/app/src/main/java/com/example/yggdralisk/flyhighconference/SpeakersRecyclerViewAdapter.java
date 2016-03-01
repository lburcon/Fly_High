package com.example.yggdralisk.flyhighconference;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
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
 * Created by lukasz on 25.02.16.
 */
public class SpeakersRecyclerViewAdapter extends RecyclerView.Adapter<SpeakersRecyclerViewAdapter.ViewHolder> {

    JSONArray mDataset = new JSONArray();
    public MainActivity mUpLayout;

    public SpeakersRecyclerViewAdapter(JSONArray myDataset) {
        mDataset = myDataset;
    }

    @Override
    public SpeakersRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.speakers_list_element, parent, false);

        ViewHolder vh = new ViewHolder(v);
        mUpLayout = (MainActivity) parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(SpeakersRecyclerViewAdapter.ViewHolder holder, int position) {
        try {
            holder.setData(mDataset.getJSONObject(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mDataset.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView name;
        public TextView description;
        public ImageView image;
        public int id = -1;
       // SpeakersRecyclerViewAdapter nListener = new SpeakersRecyclerViewAdapter();

        public ViewHolder(View itemView) {
            super(itemView);

      //      itemView.setOnClickListener(nListener);

            name = (TextView) itemView.findViewById(R.id.speakers_name);
            description = (TextView) itemView.findViewById(R.id.speakers_description);
            image = (ImageView) itemView.findViewById(R.id.speakers_image);
        }

        public void setData(JSONObject jsonObject) {

            try {
                name.setText(jsonObject.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                description.setText(jsonObject.getString("description"));
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                Glide.with(itemView.getContext())
                        .load(jsonObject.getString("image"))
                        .placeholder(R.drawable.fly_high_logotype)
                        .into(image);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

/*
        private class SpeakerRecyclerListener implements View.OnClickListener {
            int id = 0;

            public void setId(int nId) {
                id = nId;
            }

            @Override
            public void onClick(View v) {
                if (id > 0) {
                    Bundle args = new Bundle();
                    args.putInt("partnerId", id);
                    mUpLayout.setFragment(null,new PartnerFragment(),args);
                }

            }
        }
*/

    }
}
