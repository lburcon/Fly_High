package com.example.yggdralisk.flyhighconference.Adapters_Managers_Items;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yggdralisk.flyhighconference.BackEnd.DataGetter;
import com.example.yggdralisk.flyhighconference.BackEnd.MainActivity;
import com.example.yggdralisk.flyhighconference.Fragments.ConferenceFragment;
import com.example.yggdralisk.flyhighconference.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yggdralisk on 20.02.16.
 */
public class ConferenceRecyclerViewAdapter extends RecyclerView.Adapter<ConferenceRecyclerViewAdapter.ViewHolder> {

    JSONArray mConferences = new JSONArray();
    public Context mContext;
    public MainActivity mUpLayout;

    public ConferenceRecyclerViewAdapter(JSONArray myDataset, Context context) {
        mConferences = myDataset;
        mContext = context;
    }

    @Override
    public ConferenceRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.conference_list_element, parent, false);

        mUpLayout = (MainActivity) parent.getContext();
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
        @Bind(R.id.conference_list_item_titlext)
        TextView title;
        @Bind(R.id.conference_list_item_descrxt)
        TextView descr;
        @Bind(R.id.conference_list_item_auth)
        TextView auth;
        @Bind(R.id.conference_list_itemt_time)
        TextView time;
        public int id = -1;
        ConferenceRecyclerListner nListner = new ConferenceRecyclerListner();


        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(nListner);

            ButterKnife.bind(this, itemView);
        }

        public void setData(JSONObject jsonObject) {
            try {
                id = jsonObject.getInt("id");
                nListner.setId(id);
            } catch (JSONException e) {
                e.printStackTrace();
            }
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
                time.setText(getPresentationTime(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
                descr.setText("Błąd");
            }
            try {
                auth.setText(getPresentationAuth(jsonObject));
            } catch (JSONException e) {
                e.printStackTrace();
                descr.setText("Błąd");
            }
        }

        private String getPresentationAuth(JSONObject jsonObject) throws JSONException {
            if (id == -1)
                try {
                    return DataGetter.getSpeakerById(id, mContext).getString("name");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            return "";
        }

        private String getPresentationTime(JSONObject jsonObject) throws JSONException {
            String dtStart = jsonObject.getString("start");
            String dtEnd = jsonObject.getString("end");

            String day = getDay(dtStart, dtEnd);
            String startTime = getTime(dtStart);
            String endTime = getTime(dtEnd);

            return String.format("%s    %s - %s", day, startTime, endTime);
        }

        private String getDay(String dtDate, String dtEnd)//TODO:Ogarnąć jakiś system sprawdzania
        {
            return dtDate.substring(0, dtDate.indexOf(' '));

        }

        private String getTime(String dtDate) {
            return dtDate.substring(dtDate.indexOf(' '), dtDate.lastIndexOf(":"));
        }

        private class ConferenceRecyclerListner implements View.OnClickListener {
            int id = 0;

            public void setId(int nId) {
                id = nId;
            }

            @Override
            public void onClick(View v) {
                if (id > 0) {
                    Bundle args = new Bundle();
                    args.putInt("conferenceId", id);
                    mUpLayout.setFragment(null, new ConferenceFragment(), args);
                }

            }
        }
    }
}
