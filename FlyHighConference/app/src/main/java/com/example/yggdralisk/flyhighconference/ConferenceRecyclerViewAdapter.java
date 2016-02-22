package com.example.yggdralisk.flyhighconference;

import android.database.DataSetObserver;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by yggdralisk on 20.02.16.
 */
public class ConferenceRecyclerViewAdapter extends RecyclerView.Adapter<ConferenceRecyclerViewAdapter.ViewHolder> {

    JSONArray mDataset = new JSONArray();

    public ConferenceRecyclerViewAdapter(JSONArray myDataset) {
        mDataset = myDataset;
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
            holder.setData(mDataset.getJSONObject(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void setData(JSONObject jsonObject)
        {

        }
    }
}
