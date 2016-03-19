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

    private JSONArray mDataset = new JSONArray();
    private JSONObject jsonConference = new JSONObject();
    private JSONObject jsonSpeaker = new JSONObject();
    private int[] speakerIds = null;
    @Bind(R.id.conference_rating_bar)
    RatingBar ratingBar;
    @Bind(R.id.conference_rating_bar_text_view)
    TextView warning;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.conference_details, container, false);

        try {
            mDataset = DataGetter.getPresentations(getContext());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < mDataset.length(); i++) {
            try {
                jsonConference = mDataset.getJSONObject(i);
                if (Integer.parseInt(jsonConference.getString("id")) == getArguments().getInt("conferenceId")) //NULL POINTER EXCEPTION//TODO:Co zrobić kiedy bundle jest nullem
                    break;
            } catch (JSONException e) {
                e.printStackTrace();
            }
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

        try {
            name.setText(jsonConference.getString("title"));
        } catch (JSONException e) {
            e.printStackTrace();
            name.setText("Błąd");
        }

        try {
            description.setText(jsonConference.getString("description"));
        } catch (JSONException e) {
            e.printStackTrace();
            description.setText("Błąd");
        }

        try {
            localizationInfo.setText(jsonConference.getString("place"));
        } catch (JSONException e) {
            e.printStackTrace();
            localizationInfo.setText("Błąd");
        }

        try {
            time.setText(getPresentationTime(jsonConference));
        } catch (JSONException e) {
            e.printStackTrace();
            time.setText("Błąd");
        }

        //setting speaker and checking their number in case of adding ','

        speakerIds = getSpeakerIds();
        for (int i = 0; i < speakerIds.length; i++)
            try {
                jsonSpeaker = DataGetter.getSpeakerById(speakerIds[i], getContext());
                if (speakerIds.length > 1 && i != speakerIds.length-1)
                    speaker.setText(speaker.getText() + jsonSpeaker.getString("name") + ", ");
                else
                    speaker.setText(speaker.getText() + jsonSpeaker.getString("name"));
            } catch (JSONException e) {
                e.printStackTrace();
            }


        return view;
    }

    private void setRatingBarVisibility() { //todo: check this



    }

    private String getPresentationTime(JSONObject jsonObject) throws JSONException {
        String dtStart = jsonObject.getString("start");
        String dtEnd = jsonObject.getString("end");

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
        try {
            String ids = jsonConference.getString("speakers");
            String[] strArray = ids.split(",");
            speakerIds = new int[strArray.length];
            for (int i = 0; i < strArray.length; i++) {
                speakerIds[i] = Integer.parseInt(strArray[i]);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return speakerIds;
    }

}
