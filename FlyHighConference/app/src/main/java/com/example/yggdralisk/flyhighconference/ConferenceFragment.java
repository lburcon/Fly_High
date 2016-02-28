package com.example.yggdralisk.flyhighconference;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by lukasz on 23.02.16.
 */
public class ConferenceFragment extends Fragment {

    private JSONArray mDataset = new JSONArray();
    private JSONArray mDatasetSpeakers = new JSONArray();
    private JSONObject jsonConference = new JSONObject();
    private JSONObject jsonSpeaker = new JSONObject();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.conference_details, container, false);

        try {
            mDataset = getPresentations();
            mDatasetSpeakers = getSpeakers();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int i = 0 ; i < mDataset.length() ; i++) {
            try {
                jsonConference = mDataset.getJSONObject(i);
                if (Integer.parseInt(jsonConference.getString("id")) == getArguments().getInt("presentationId"))
                break;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        TextView time = (TextView) view.findViewById(R.id.conference_time);
        TextView speaker = (TextView) view.findViewById(R.id.conference_speaker); //TODO: add speaker
        TextView name = (TextView) view.findViewById(R.id.conference_name);
        TextView description = (TextView) view.findViewById(R.id.conference_description);
        TextView localizationInfo = (TextView) view.findViewById(R.id.conference_localization_info);

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

        return view;
    }

    private JSONArray getPresentations() throws JSONException {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(getString(R.string.shared_preferences_presentations), "");
        return new JSONArray(str);
    }
    private JSONArray getSpeakers() throws JSONException {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences(getString(R.string.shared_preferences), Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(getString(R.string.shared_preferences_speakers), "");
        return new JSONArray(str);
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

}
