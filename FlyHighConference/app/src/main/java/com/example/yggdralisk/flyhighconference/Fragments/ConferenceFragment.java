package com.example.yggdralisk.flyhighconference.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.yggdralisk.flyhighconference.BackEnd.DataGetter;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Place;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Presentation;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Speaker;
import com.example.yggdralisk.flyhighconference.BackEnd.MainActivity;
import com.example.yggdralisk.flyhighconference.NavigateActivity;
import com.example.yggdralisk.flyhighconference.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lukasz on 23.02.16.
 */
public class ConferenceFragment extends Fragment {

    private static View view;
    private Presentation presentation = new Presentation();
    private Speaker speakerObject = new Speaker();
    private int[] speakerIds = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.conference_details, container, false);
            setData();
            setMap(view);
        } catch (InflateException e) {
        }

        return view;
    }

    private void setData() {

        presentation = DataGetter.getPresentationById(getContext(), getArguments().getInt("conferenceId")); //NULL POINTER EXCEPTION//TODO:Co zrobić kiedy bundle jest nullem

        ButterKnife.bind(this, view);

        TextView time = ButterKnife.findById(view, R.id.conference_time);
        TextView speaker = ButterKnife.findById(view, R.id.conference_speaker);
        TextView name = ButterKnife.findById(view, R.id.conference_name);
        TextView description = ButterKnife.findById(view, R.id.conference_description);

        name.setText(presentation.getTitle());
        description.setText(presentation.getDescription());

        time.setText(getPresentationTime(presentation));

        //setting speaker and checking their number in case of adding ','

        speakerIds = getSpeakerIds();
        for (int i = 0; i < speakerIds.length; i++) {
            speakerObject = DataGetter.getSpeakerById(getContext(), speakerIds[i]);
            if (speakerIds.length > 1 && i != speakerIds.length - 1)
                speaker.setText(speaker.getText() + speakerObject.getName() + ", ");
            else
                speaker.setText(speaker.getText() + speakerObject.getName());
        }

        setToolbarData();
    }

    private void setToolbarData() {
        OnDataPass activity = ((OnDataPass) getContext());
        Bundle args = new Bundle();
        if (presentation.getSpeakers().length > 1) {
            args.putIntArray("speakersIds", presentation.getSpeakers());
            activity.dataPass(args);
        } else {
            args.putInt("speakerId", presentation.getSpeakers()[0]);
            activity.dataPass(args);
        }
    }


    private void setMap(View view) {
        final Place myPlace = DataGetter.getPlaceById(getContext(), presentation.getPlace());

        if (myPlace != null) {

            TextView localizationInfo = ButterKnife.findById(view, R.id.conference_localization_info);
            localizationInfo.setText(myPlace.getName());

            final SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.conference_map);

            if (mapFragment != null) {
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        final LatLng loc = new LatLng(myPlace.getLat(), myPlace.getLon());
                        googleMap.addMarker(new MarkerOptions().position(loc)
                                .title(presentation.getTitle()));

                        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 12));

                        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

                        googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                            @Override
                            public void onMapClick(LatLng latLng) {
                                Intent in = new Intent(getContext(), NavigateActivity.class);
                                in.putExtra("placeLAT", loc.latitude);
                                in.putExtra("placeLNG", loc.longitude);
                                in.putExtra("placeTitle", myPlace.getName());

                                getActivity().startActivity(in);
                            }
                        });
                    }
                });
            }
        }
    }

    private String getPresentationTime(Presentation pres) {
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

        speakerIds = presentation.getSpeakers();

        return speakerIds;
    }

    public interface OnDataPass {
        public void dataPass(Bundle data);
    }
}
