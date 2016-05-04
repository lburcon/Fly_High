package com.example.yggdralisk.flyhighconference.Adapters_Managers_Items;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.yggdralisk.flyhighconference.BackEnd.DataGetter;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Organiser;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Speaker;
import com.example.yggdralisk.flyhighconference.BackEnd.MainActivity;
import com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces.ConnectorResultInterface;
import com.example.yggdralisk.flyhighconference.BackEnd.ServerConnector;
import com.example.yggdralisk.flyhighconference.Fragments.SpeakerFragment;
import com.example.yggdralisk.flyhighconference.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lukasz on 31.03.16.
 */
public class OrganisersRecyclerViewAdapter extends RecyclerView.Adapter<OrganisersRecyclerViewAdapter.ViewHolder> {

    private Organiser[] mDataset;
    public MainActivity mUpLayout;
    public Context mContext;
    private final int TYPE_0 = 0;
    private final int TYPE_1 = 1;

    public OrganisersRecyclerViewAdapter(Organiser[] myDataset, Context context) {
        mDataset = myDataset;
        mContext = context;
    }

    @Override
    public OrganisersRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;


        switch (viewType) {
            case TYPE_0:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.organisers_list_element_opposite, parent, false);
                break;
            case TYPE_1:
                v = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.organisers_list_element, parent, false);
                break;
        }

        ViewHolder vh = new ViewHolder(v);
        mUpLayout = (MainActivity) parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(OrganisersRecyclerViewAdapter.ViewHolder holder, int position) {

        holder.setData(mDataset[position]);

    }

    @Override
    public int getItemViewType(int position) {
        int id = -1;

        id = mDataset[position].getId();

        return id % 2;
    }

    @Override
    public int getItemCount() {
        return mDataset.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.organisers_name)
        public TextView name;
        @Bind(R.id.organisers_description)
        public TextView description;
        @Bind(R.id.organisers_image)
        public ImageView image;
        @Bind(R.id.organisers_email)
        public TextView email;
        @Bind(R.id.organisers_title)
        public TextView title;

        public int id = -1;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void setData(final Organiser speakerObject) {


            name.setText(speakerObject.getName());

            //description.setText(speakerObject.getDescription());
            description.setVisibility(View.GONE);

            title.setText(speakerObject.getTitle());

            email.setText(speakerObject.getEmail());

            //A MOZE BY TAK UZYC SWITCHA?

            if (speakerObject.getImage().equals("photo_john") || speakerObject.getImage().equals("photo_lukas")) {
                if (speakerObject.getImage().equals("photo_john"))
                    Glide.with(itemView.getContext())
                            .load("")
                            .placeholder(R.drawable.photo_john)
                            .dontAnimate()
                            .into(image);
                else
                    Glide.with(itemView.getContext())
                            .load("")
                            .placeholder(R.drawable.photo_lukas)
                            .dontAnimate()
                            .into(image);


            } else
                Glide.with(itemView.getContext())
                        .load(speakerObject.getImage())
                        .placeholder(R.drawable.fly_high_temp)
                        .fitCenter()
                        .dontAnimate()
                        .into(image);

            id = speakerObject.getId();

            email.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent i = new Intent(Intent.ACTION_SEND);
                    i.setType("message/rfc822");
                    i.putExtra(Intent.EXTRA_EMAIL, new String[]{speakerObject.getEmail()});
                    try {
                        mContext.startActivity(Intent.createChooser(i, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(mContext, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

    }
}


