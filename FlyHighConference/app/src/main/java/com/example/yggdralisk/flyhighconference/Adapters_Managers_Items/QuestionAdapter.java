package com.example.yggdralisk.flyhighconference.Adapters_Managers_Items;

// sets recycler view to show questions to one speaker

import android.app.Application;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yggdralisk.flyhighconference.BackEnd.DataGetter;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Question;
import com.example.yggdralisk.flyhighconference.BackEnd.MainActivity;
import com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces.ConnectorResultInterface;
import com.example.yggdralisk.flyhighconference.BackEnd.ServerConnector;
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

    private Question[] mQuestions;
    private Application application;
    private Context context;

    public QuestionAdapter(Question[] mQuestions, Application application, Context context) {
        this.mQuestions = mQuestions;
        this.application = application;
        this.context = context;
    }

    @Override
    public QuestionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;

        v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.question_to_speaker_element, parent, false);


        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(QuestionAdapter.ViewHolder holder, int position) {

        if (mQuestions == null || mQuestions.length == 0)
            holder.setData(null);
        else
            holder.setData(mQuestions[position]);
    }

    @Override
    public int getItemCount() {
        if (mQuestions == null || mQuestions.length == 0)
            return 1;
        else
            return mQuestions.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.question_to_speaker_nick)
        TextView nick;
        @Bind(R.id.question_to_speaker_rating)
        TextView rating;
        @Bind(R.id.question_to_speaker_question)
        TextView questionField;
        @Bind(R.id.question_plus_one)
        ImageButton plusOne;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

        public void setData(final Question question) {

            if (question != null) {
                plusOne.setVisibility(View.VISIBLE);
                try
                {
                    String mail = new DataGetter(application).getUserById( question.getUser()).getMail();
                    nick.setText(mail.substring(0, mail.indexOf('@')));
                } catch (Exception ex)
                {
                    nick.setText("");
                }
                    rating.setText("Likes: " + question.getRating());
                    questionField.setText(question.getContent()); }
            else {
                questionField.setText(R.string.no_questions_to_prelection);
                plusOne.setVisibility(View.GONE);
                rating.setVisibility(View.GONE);
                nick.setVisibility(View.GONE);
            }


        plusOne.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              if (plusOne.getVisibility() == View.VISIBLE) {
                  ServerConnector serverConnector = new ServerConnector();
                  serverConnector.postLikeToQuestion(application, context, question.getId(), DataGetter.getLoggedUserId(context), new ConnectorResultInterface() {
                      @Override
                      public void onDownloadFinished(boolean succeeded) {
                          if (succeeded)
                              Toast.makeText(context, R.string.like_added, Toast.LENGTH_SHORT).show();
                          else
                              Toast.makeText(context, R.string.like_not_added, Toast.LENGTH_SHORT).show();

                      }
                  });

                  serverConnector.refreshLikes(application, context, new ConnectorResultInterface() {
                      @Override
                      public void onDownloadFinished(boolean succeeded) {
                      }
                  });
              }
            }
        });
        }
    }
}
