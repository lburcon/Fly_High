package com.example.yggdralisk.flyhighconference;

// sets recycler view to show questions to one speaker

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * Created by lukasz on 08.03.16.
 */
public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private Context mContext;
    private JSONArray mQuestions = new JSONArray();
    private ArrayList<Integer> prelectionIds = new ArrayList<>();
    private MainActivity mUpLayout;


    public QuestionAdapter(ArrayList<Integer> prelectionIds, Context context) {
        this.prelectionIds = prelectionIds; //here are ids of prelections given by one speaker
        mContext = context;
    }

    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_to_speaker_element, parent, false);

        ViewHolder vh = new ViewHolder(v);
        mUpLayout = (MainActivity) parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(QuestionAdapter.ViewHolder holder, int position) {
        DataPoster dataPoster = new DataPoster();
        dataPoster.get

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nick;
        TextView rating;
        TextView prelectionTitle;
        TextView question;

        public ViewHolder(View itemView) {
            super(itemView);

            nick = (TextView) itemView.findViewById(R.id.question_to_speaker_nick);
            rating = (TextView) itemView.findViewById(R.id.question_to_speaker_rating;
            prelectionTitle = (TextView) itemView.findViewById(R.id.question_to_speaker_prelection_title);
            question = (TextView) itemView.findViewById(R.id.question_to_speaker_question);
        }

        public void setData(JSONObject jsonObject) {



        }

    }
}
