package com.example.yggdralisk.flyhighconference.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces.ConnectorResultInterface;
import com.example.yggdralisk.flyhighconference.BackEnd.DataGetter;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Presentation;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Question;
import com.example.yggdralisk.flyhighconference.BackEnd.ServerConnector;
import com.example.yggdralisk.flyhighconference.Adapters_Managers_Items.QuestionAdapter;
import com.example.yggdralisk.flyhighconference.R;
import com.example.yggdralisk.flyhighconference.Adapters_Managers_Items.WrappingLinearLayoutManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lukasz on 03.03.16.
 */
public class QuestionFragment extends Fragment {

    @Bind(R.id.question_details_add_question)
    EditText editText;

    private Question[] questionArray;
    private Presentation presentation = new Presentation();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int speakerId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_details, container, false);
        speakerId = getArguments().getInt("speakerId");

        ButterKnife.bind(this, view);


        getArrayOfIds(speakerId,view);


        return view;
    }

    private void getArrayOfIds(final int prelectionId, final View view) { //adds questions to mQuestion JSONArray list

        ServerConnector serverConnector = new ServerConnector();

        serverConnector.getQuestionsToPresentation(getContext(), prelectionId, new ConnectorResultInterface() {
            @Override
            public void onDownloadFinished(boolean succeeded) {
                if(succeeded) {
                    questionArray = DataGetter.getQuestionsToPresentation(getContext(), prelectionId);
                    setRecycler(view);
                }
            }
        });
    }


    private void setRecycler(View view)
    {
        if (questionArray != null) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.question_details_recycler_view);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setNestedScrollingEnabled(false);
        mLayoutManager = new WrappingLinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new QuestionAdapter(questionArray, getContext());

        mRecyclerView.setAdapter(mAdapter);

        presentation = DataGetter.getPresentationById(getContext(), speakerId);

        CollapsingToolbarLayout toolbar = ButterKnife.findById(view, R.id.question_details_collapsing_toolbar);
        ImageView imageTop = ButterKnife.findById(view, R.id.question_details_image);

        toolbar.setTitle(presentation.getTitle());

        Glide.with(getContext())
                .load(presentation.getImage())
                .placeholder(R.drawable.fly_high_logotype)
                .into(imageTop);
    }

    }

    @OnClick(R.id.question_fab)
    public void onFabClicked(View view) {


        if (DataGetter.checkUserLogged(getContext())) {
            if (editText.getVisibility() == View.GONE) {
                Toast.makeText(getContext(), "Możesz dodać pytanie.", Toast.LENGTH_SHORT).show();
                editText.setVisibility(View.VISIBLE);
                editText.setHint("Wpisz pytanie");
            } else {
                ServerConnector serverConnector = new ServerConnector();
                editText.setVisibility(View.GONE);

                serverConnector.postQuestionToPresentation(getContext(), speakerId, DataGetter.getLoggedUserId(getContext()), editText.getText().toString(), new ConnectorResultInterface() {
                    @Override
                    public void onDownloadFinished(boolean succeeded) {
                        if (succeeded)
                            Toast.makeText(getContext(), "Pytanie zostało dodane.", Toast.LENGTH_SHORT).show();
                        else
                            Toast.makeText(getContext(), "Błąd dodawania pytania.", Toast.LENGTH_SHORT).show();

                        editText.getText().clear();
                    }
                });

            }
        } else
            Toast.makeText(getContext(), "Musisz się zalogować.", Toast.LENGTH_SHORT).show();
    }
}