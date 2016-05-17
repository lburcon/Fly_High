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
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces.ConnectorResultInterface;
import com.example.yggdralisk.flyhighconference.BackEnd.DataGetter;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Presentation;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Question;
import com.example.yggdralisk.flyhighconference.BackEnd.RetrofitInterfaces.GetQuestionsResultInterface;
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
    @Bind(R.id.question_details_name)
    TextView conferenceName;

    private Question[] questionArray;
    private Presentation presentation;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private int conferenceId;
    private int prelectionId;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.question_details, container, false);
        conferenceId = getArguments().getInt("conferenceId");

        ButterKnife.bind(this, view);

        getArrayOfIds(conferenceId, view);

        return view;
    }

    private void getArrayOfIds(final int prelectionId, final View view) { //adds questions to mQuestion JSONArray list
        this.prelectionId = prelectionId;
        ServerConnector serverConnector = new ServerConnector();

        serverConnector.getQuestionsToPresentation(getActivity().getApplication(),getContext(), prelectionId, new GetQuestionsResultInterface() {
            @Override
            public void onDownloadFinished(Question[] res) {
                    questionArray = res;
                    setRecycler(view);
            }
        });
    }


    private void setRecycler(View view) {
        mRecyclerView = (RecyclerView) view.findViewById(R.id.question_details_recycler_view);
        mRecyclerView.setHasFixedSize(false);
        mRecyclerView.setNestedScrollingEnabled(false);
        mLayoutManager = new WrappingLinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new QuestionAdapter(questionArray, getActivity().getApplication(), getContext());

        mRecyclerView.setAdapter(mAdapter);

        presentation = new DataGetter(getActivity().getApplication()).getPresentationById(conferenceId);

        conferenceName.setText(presentation.getTitle());

        CollapsingToolbarLayout toolbar = ButterKnife.findById(view, R.id.question_details_collapsing_toolbar);
        ImageView imageTop = ButterKnife.findById(view, R.id.question_details_image);

        toolbar.setTitle(presentation.getTitle());

        Glide.with(getContext())
                .load(presentation.getImage())
                .placeholder(R.drawable.fly_high_temp)
                .into(imageTop);

    }

    @OnClick(R.id.question_fab)
    public void onFabClicked(View view) {
        if (DataGetter.checkUserLogged(getContext())) {
            if (editText.getVisibility() == View.GONE) {
                Toast.makeText(getContext(), getString(R.string.question_available), Toast.LENGTH_SHORT).show();
                editText.setVisibility(View.VISIBLE);
                editText.setHint(getString(R.string.question_write));
            } else {
                ServerConnector serverConnector = new ServerConnector();
                editText.setVisibility(View.GONE);
                String question = editText.getText().toString();

                if (!question.equals("")){
                serverConnector.postQuestionToPresentation(getActivity().getApplication(),getContext(), conferenceId, DataGetter.getLoggedUserId(getContext()), question, new ConnectorResultInterface() {
                    @Override
                    public void onDownloadFinished(boolean succeeded) {
                        if (succeeded) {
                            questionArray = new DataGetter(getActivity().getApplication()).getQuestionsToPresentation(prelectionId);
                            Toast.makeText(getContext(), getString(R.string.question_done), Toast.LENGTH_SHORT).show();
                        }
                        else
                            Toast.makeText(getContext(), getString(R.string.question_error), Toast.LENGTH_SHORT).show();

                        editText.getText().clear();
                    }
                });}
                else Toast.makeText(getContext(), getString(R.string.question_error_second), Toast.LENGTH_SHORT).show();


            }
        } else
            Toast.makeText(getContext(), getString(R.string.question_error_logged), Toast.LENGTH_SHORT).show();
    }
}