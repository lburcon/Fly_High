package com.example.yggdralisk.flyhighconference.Adapters_Managers_Items;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Partner;
import com.example.yggdralisk.flyhighconference.BackEnd.MainActivity;
import com.example.yggdralisk.flyhighconference.Fragments.PartnerFragment;
import com.example.yggdralisk.flyhighconference.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lukasz on 25.02.16.
 */
public class PartnersRecyclerViewAdapter extends RecyclerView.Adapter<PartnersRecyclerViewAdapter.ViewHolder> {

    Partner[] mDataset;
    public MainActivity mUpLayout;

    public PartnersRecyclerViewAdapter(Partner[] myDataset) {
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

        holder.setData(mDataset[position]);

    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.partners_name)
        public TextView name;
        @Bind(R.id.partners_title)
        public TextView title;
        @Bind(R.id.partners_image)
        public ImageView image;

        public int id = -1;
        PartnerRecyclerListener nListener = new PartnerRecyclerListener();

        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnClickListener(nListener);

            ButterKnife.bind(this, itemView);
        }

        public void setData(Partner partner) {


            name.setText(partner.getName());

            if (partner.getType().equals("patronage"))
                title.setText("patron");
            else title.setText("sponsor");


            Glide.with(itemView.getContext())
                    .load(partner.getLogo())
                    .placeholder(R.drawable.fly_high_logotype)
                    .fitCenter()
                    .crossFade()
                    .into(image);


            id = partner.getId();
            nListener.setId(id);


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
                    mUpLayout.setFragment(null, new PartnerFragment(), args);
                }

            }
        }

    }
}
