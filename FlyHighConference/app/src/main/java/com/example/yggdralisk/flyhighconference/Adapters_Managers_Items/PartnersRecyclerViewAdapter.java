package com.example.yggdralisk.flyhighconference.Adapters_Managers_Items;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yggdralisk.flyhighconference.BackEnd.MainActivity;
import com.example.yggdralisk.flyhighconference.Fragments.PartnerFragment;
import com.example.yggdralisk.flyhighconference.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lukasz on 25.02.16.
 */
public class PartnersRecyclerViewAdapter extends RecyclerView.Adapter<PartnersRecyclerViewAdapter.ViewHolder> {

    JSONArray mDataset = new JSONArray();
    public MainActivity mUpLayout;

    public PartnersRecyclerViewAdapter(JSONArray myDataset) {
        mDataset = myDataset;
    }

    @Override
    public PartnersRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.partners_list_element, parent, false);

        ViewHolder vh = new ViewHolder(v);
        mUpLayout = (MainActivity) parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(PartnersRecyclerViewAdapter.ViewHolder holder, int position) {
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
        public TextView title;
        public ImageView image;
        public int id = -1;
        PartnerRecyclerListener nListener = new PartnerRecyclerListener();

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(nListener);

            name = (TextView) itemView.findViewById(R.id.partners_name);
            title = (TextView) itemView.findViewById(R.id.partners_title);
            image = (ImageView) itemView.findViewById(R.id.partners_image);
        }

        public void setData(JSONObject jsonObject) {

            try {
                name.setText(jsonObject.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
                name.setText("Błąd");
            }

            try {
                title.setText(jsonObject.getString("type"));
            } catch (JSONException e) {
                e.printStackTrace();
                title.setText("Błąd");
            }

            try {
                Glide.with(itemView.getContext())
                        .load(jsonObject.getString("logo"))
                        .placeholder(R.drawable.fly_high_logotype)
                        .into(image);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            try {
                id = jsonObject.getInt("id");
                nListener.setId(id);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        private class PartnerRecyclerListener implements View.OnClickListener {
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

    }
}
