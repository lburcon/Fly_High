package com.example.yggdralisk.flyhighconference;

// sets recycler view to show questions to one speaker

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;


/**
 * Created by lukasz on 08.03.16.
 */
public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private Context mContext;
    private JSONArray mQuestions = new JSONArray();
    private MainActivity mUpLayout;


    public QuestionAdapter(JSONArray mQuestions, Context context) {
        this.mQuestions = mQuestions;
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


        try {
            holder.setData(mQuestions.getJSONObject(position));
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mQuestions.length();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nick;
        TextView rating;
        TextView prelectionTitle;
        TextView question;

        public ViewHolder(View itemView) {
            super(itemView);

            nick = (TextView) itemView.findViewById(R.id.question_to_speaker_nick);
            rating = (TextView) itemView.findViewById(R.id.question_to_speaker_rating);
            prelectionTitle = (TextView) itemView.findViewById(R.id.question_to_speaker_prelection_title);
            question = (TextView) itemView.findViewById(R.id.question_to_speaker_question);
        }

        public void setData(JSONObject jsonObject) {

            try {
                nick.setText(jsonObject.getString("user"));
            } catch (JSONException e) {
                e.printStackTrace();
                nick.setText("Błąd");
            }

            try {
                rating.setText("Ocena: " + jsonObject.getString("rating"));
            } catch (JSONException e) {
                e.printStackTrace();
                rating.setText("Błąd");
            }

            try {
                prelectionTitle.setText(jsonObject.getString("user"));
            } catch (JSONException e) {
                e.printStackTrace();
                prelectionTitle.setText("Błąd");
            }

            try {
                question.setText(jsonObject.getString("content"));
            } catch (JSONException e) {
                e.printStackTrace();
                question.setText("Błąd");
            }

        }

    }
}
