package com.example.yggdralisk.flyhighconference;

import android.content.Context;
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
 * Created by yggdralisk on 20.02.16.
 */
public class ConferenceRecyclerViewAdapter extends RecyclerView.Adapter<ConferenceRecyclerViewAdapter.ViewHolder> {

    JSONArray mConferences = new JSONArray();
    public Context mContext;

    public ConferenceRecyclerViewAdapter(JSONArray myDataset, Context context) {
        mConferences = myDataset;
       mContext = context;
    }

    @Override
    public ConferenceRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.conference_list_element, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ConferenceRecyclerViewAdapter.ViewHolder holder, int position) {
        try {
            holder.setData(mConferences.getJSONObject(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mConferences.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView image;
        public TextView title;
        public TextView descr;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.conference_list_item_image);
            title = (TextView) itemView.findViewById(R.id.conference_list_item_titlext);
            descr = (TextView) itemView.findViewById(R.id.conference_list_item_descrxt);
        }

        public void setData(JSONObject jsonObject) {

            try {
                title.setText(jsonObject.getString("title"));
            } catch (JSONException e) {
                e.printStackTrace();
                title.setText("Błąd");
            }
            try {
                descr.setText(jsonObject.getString("description"));
            } catch (JSONException e) {
                e.printStackTrace();
                descr.setText("Błąd");
            }
            try {
                Glide.with(mContext).load(jsonObject.getString("image").replace("\\","")).into(image);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
