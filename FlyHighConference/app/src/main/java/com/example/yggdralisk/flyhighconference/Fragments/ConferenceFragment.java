package com.example.yggdralisk.flyhighconference.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.yggdralisk.flyhighconference.BackEnd.DataGetter;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Presentation;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Speaker;
import com.example.yggdralisk.flyhighconference.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lukasz on 23.02.16.
 */
public class ConferenceFragment extends Fragment {

    private Presentation[] mDataset ;
    private Presentation conference = new Presentation();
    private Speaker speakerObject = new Speaker();
    private int[] speakerIds = null;
    @Bind(R.id.conference_rating_bar)
    RatingBar ratingBar;
    @Bind(R.id.conference_rating_bar_text_view)
    TextView warning;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.conference_details, container, false);

            mDataset = DataGetter.getPresentations(getContext());


        for (int i = 0; i < mDataset.length; i++) {
                conference = mDataset[i];
                if (conference.getId() == getArguments().getInt("conferenceId")) //NULL POINTER EXCEPTION//TODO:Co zrobić kiedy bundle jest nullem
                    break;
        }

        ButterKnife.bind(this, view);

        if (DataGetter.checkUserLogged(getContext())) {
            warning.setVisibility(View.INVISIBLE);
            ratingBar.setVisibility(View.VISIBLE);
        }

        TextView time = ButterKnife.findById(view, R.id.conference_time);
        TextView speaker = ButterKnife.findById(view, R.id.conference_speaker);
        TextView name = ButterKnife.findById(view, R.id.conference_name);
        TextView description = ButterKnife.findById(view, R.id.conference_description);
        TextView localizationInfo = ButterKnife.findById(view, R.id.conference_localization_info);


            name.setText(conference.getTitle());

 
            description.setText(conference.getDescription());



            localizationInfo.setText(Integer.toString(conference.getPlace()));


            time.setText(getPresentationTime(conference));



        //setting speaker and checking their number in case of adding ','

        speakerIds = getSpeakerIds();
        for (int i = 0; i < speakerIds.length; i++) {
            speakerObject = DataGetter.getSpeakerById(speakerIds[i], getContext());
            if (speakerIds.length > 1 && i != speakerIds.length - 1)
                speaker.setText(speaker.getText() + speakerObject.getName() + ", ");
            else
                speaker.setText(speaker.getText() + speakerObject.getName());

        }

        return view;
    }

    private String getPresentationTime(Presentation pres){
        String dtStart = pres.getStart();
        String dtEnd = pres.getEnd();

        String day = getDay(dtStart, dtEnd);
        String startTime = getTime(dtStart);
        String endTime = getTime(dtEnd);

        return String.format("%s    %s - %s", day, startTime, endTime);
    }

    private String getDay(String dtDate, String dtEnd)//TODO:Ogarnąć jakiś system sprawdzania
    {
        return dtDate.substring(0, dtDate.indexOf(' '));

    }

    private String getTime(String dtDate) {
        return dtDate.substring(dtDate.indexOf(' '), dtDate.lastIndexOf(":"));
    }

    private int[] getSpeakerIds() {
        int[] speakerIds = null;

           speakerIds = conference.getSpeakers();

        return speakerIds;
    }

}
