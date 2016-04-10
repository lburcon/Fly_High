package com.example.yggdralisk.flyhighconference.Adapters_Managers_Items;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yggdralisk.flyhighconference.BackEnd.DataGetter;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Presentation;
import com.example.yggdralisk.flyhighconference.BackEnd.MainActivity;
import com.example.yggdralisk.flyhighconference.Fragments.ConferenceFragment;
import com.example.yggdralisk.flyhighconference.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by yggdralisk on 20.02.16.
 */
public class ConferenceRecyclerViewAdapter extends RecyclerView.Adapter<ConferenceRecyclerViewAdapter.ViewHolder> {

    Presentation[] mConferences;
    public Context mContext;
    public MainActivity mUpLayout;


    public ConferenceRecyclerViewAdapter(Presentation[] myDataset, Context context) {
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
        holder.setData(mConferences[position]);
    }

    @Override
    public int getItemCount() {
        return mConferences.length;
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
        @Bind(R.id.conference_favourite)
        ImageButton favourite;

        public boolean isFavourite;
        public int id = -1;
        ConferenceRecyclerListner nListner = new ConferenceRecyclerListner();


        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(nListner);

            ButterKnife.bind(this, itemView);
        }

        public void setData(Presentation presentation) {
            id = presentation.getId();
            nListner.setId(id);

            title.setText(presentation.getTitle());

            descr.setText(presentation.getDescription());

            time.setText(getPresentationTime(presentation));

            auth.setText(getPresentationAuth());

            Glide.with(itemView.getContext())
                    .load("")
                    .placeholder(R.drawable.ic_favorite_border_black_24dp)
                    .into(favourite);
            isFavourite = false;

            favourite.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (isFavourite) {
                        Glide.with(itemView.getContext())
                                .load("")
                                .placeholder(R.drawable.ic_favorite_border_black_24dp)
                                .into(favourite);
                        isFavourite = false;
                    }
                        else {
                        Glide.with(itemView.getContext())
                                .load("")
                                .placeholder(R.drawable.ic_favorite_black_24dp)
                                .into(favourite);
                        isFavourite = true;
                    }
                    }
            });

        }

        private String getPresentationAuth() {
            if (id != -1)
                try {
                    String[] names = DataGetter.getPresentationSpeakersNames(mContext, id);
                    String temp = "";
                    for (int i = 0; i < names.length; i++) {
                        temp += names[i] + " ";
                        if (i % 3 == 0) temp += '\n';
                    }

                    return temp;
                } catch (NullPointerException e) {
                    e.printStackTrace();
                }

            return "";
        }

        private String getPresentationTime(Presentation presentation) {
            String dtStart = presentation.getStart();
            String dtEnd = presentation.getEnd();

            String day = getDay(dtStart, dtEnd);
            String startTime = getTime(dtStart);
            String endTime = getTime(dtEnd);

            return String.format("%s  \n%s - %s", day, startTime, endTime);
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
