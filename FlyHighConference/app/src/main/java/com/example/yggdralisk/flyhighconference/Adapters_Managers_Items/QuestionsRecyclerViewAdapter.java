package com.example.yggdralisk.flyhighconference.Adapters_Managers_Items;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Presentation;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Speaker;
import com.example.yggdralisk.flyhighconference.BackEnd.MainActivity;
import com.example.yggdralisk.flyhighconference.Fragments.QuestionFragment;
import com.example.yggdralisk.flyhighconference.R;
import com.google.android.gms.analytics.Tracker;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lukasz on 03.03.16.
 */
public class QuestionsRecyclerViewAdapter extends RecyclerView.Adapter<QuestionsRecyclerViewAdapter.ViewHolder> {
        private Speaker[] mDataset;
        private ArrayList<Presentation> mPresentations;
        public MainActivity mUpLayout;
        private final int TYPE_0 = 0;
        private final int TYPE_1 = 1;

        public QuestionsRecyclerViewAdapter(Speaker[] myDataset, ArrayList<Presentation> myDatasetPresentations) {
            mDataset = myDataset;
            mPresentations = myDatasetPresentations;
        }

        @Override
        public QuestionsRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = null;


            switch (viewType) {
                case TYPE_0:
                    v = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.speakers_list_element_opposite, parent, false);
                    break;
                case TYPE_1:
                    v = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.speakers_list_element, parent, false);
                    break;
            }

            ViewHolder vh = new ViewHolder(v);
            mUpLayout = (MainActivity) parent.getContext();
            return vh;
        }

        @Override
        public void onBindViewHolder(QuestionsRecyclerViewAdapter.ViewHolder holder, int position) {
                holder.setData(mPresentations.get(position));
        }

        @Override
        public int getItemViewType(int position) {
            int id = -1;

                id = position;

            return id % 2;
        }

        @Override
        public int getItemCount() {
            return mPresentations.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            @Bind(R.id.speakers_name)
            public TextView name;
            @Bind(R.id.speakers_description)
            public TextView description;
            @Bind(R.id.speakers_image)
            public ImageView image;

            public int id = -1;
            SpeakerRecyclerListener nListener = new SpeakerRecyclerListener();

            public ViewHolder(View itemView) {
                super(itemView);

                itemView.setOnClickListener(nListener);

                ButterKnife.bind(this, itemView);
            }

            public void setData(Presentation presentationObject) {

                    name.setText(presentationObject.getTitle());

                    description.setText(presentationObject.getDescription());

                Glide.with(itemView.getContext())
                        .load(presentationObject.getImage())
                        .placeholder(R.drawable.fly_high_temp)
                        .dontAnimate()
                        .fitCenter()
                        .into(image);

                    id = presentationObject.getId();
                    nListener.setId(id);

            }
        }


        private class SpeakerRecyclerListener implements View.OnClickListener {
            int id = 0;

            public void setId(int nId) {
                id = nId;
            }

            @Override
            public void onClick(View v) {
                if (id > 0) {
                    Bundle args = new Bundle();
                    args.putInt("conferenceId", id);
                    mUpLayout.setFragment(null, new QuestionFragment(), args);
                }

            }
        }


    }



