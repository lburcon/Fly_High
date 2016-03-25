package com.example.yggdralisk.flyhighconference.Adapters_Managers_Items;

// sets recycler view to show questions to one speaker

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Question;
import com.example.yggdralisk.flyhighconference.BackEnd.MainActivity;
import com.example.yggdralisk.flyhighconference.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by lukasz on 08.03.16.
 */
public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.ViewHolder> {

    private Context mContext;
    private Question[] mQuestions;
    private MainActivity mUpLayout;


    public QuestionAdapter(Question[] mQuestions, Context context) {
        this.mQuestions = mQuestions;
        mContext = context;
    }

    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_to_speaker_element, parent, false);


        ViewHolder vh = new ViewHolder(v);
        mUpLayout = (MainActivity) parent.getContext();
        return vh;
    }

    @Override
    public void onBindViewHolder(QuestionAdapter.ViewHolder holder, int position) {

            holder.setData(mQuestions[position]);


    }

    @Override
    public int getItemCount() {
        return mQuestions.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.question_to_speaker_nick)
        TextView nick;
        @Bind(R.id.question_to_speaker_rating)
        TextView rating;
        @Bind(R.id.question_to_speaker_question)
        TextView questionField;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void setData(Question question) {

            if (question !=null) {
                    nick.setText(String.valueOf(question.getUser()));

                    rating.setText("Ocena: " + question.getRating());

                    questionField.setText(question.getContent());

            }

        }

    }
}
