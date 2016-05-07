package com.example.yggdralisk.flyhighconference.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.method.ScrollingMovementMethod;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by lukasz on 23.02.16.
 */
public class ConferenceFragment extends Fragment {

    private static View view;
    private Presentation presentation = new Presentation();
    private Speaker speakerObject = new Speaker();
    private int[] speakerIds = null;

    @Bind(R.id.conference_favourite)
    ImageButton favourite;
    @Bind(R.id.move_to_speakers)
    ImageButton moveToSpeakers;
    @Bind(R.id.move_to_questions)
    ImageButton moveToQuestions;

    boolean isFavourite = false;
    private DataGetter dataGetter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        dataGetter = new DataGetter(getActivity().getApplication());
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

        presentation = dataGetter.getPresentationById(getArguments().getInt("conferenceId")); //NULL POINTER EXCEPTION//TODO:Co zrobić kiedy bundle jest nullem

        ButterKnife.bind(this, view);

        TextView time = ButterKnife.findById(view, R.id.conference_time);
        TextView speakerName = ButterKnife.findById(view, R.id.conference_speaker);
        TextView name = ButterKnife.findById(view, R.id.conference_name);
        TextView description = ButterKnife.findById(view, R.id.conference_description);

        name.setText(presentation.getTitle());

        description.setText(presentation.getDescription());

        description.setMovementMethod(new ScrollingMovementMethod());

        time.setText(getPresentationTime(presentation));

        //setting speaker and checking their number in case of adding ','

        speakerIds = getSpeakerIds();
        if (speakerIds != null) {
            for (int i = 0; i < speakerIds.length; i++) {
                speakerObject = dataGetter.getSpeakerById(speakerIds[i]);
                if (speakerIds.length > 1 && i != speakerIds.length - 1)
                    speakerName.setText(speakerName.getText() + speakerObject.getName() + ", ");
                else
                    speakerName.setText(speakerName.getText() + speakerObject.getName());
            }
        }
        else
            speakerName.setVisibility(View.GONE);

        ArrayList<Integer> favList = DataGetter.getLoggedUserFavs(getContext());

        if (favList.size() > 0)
            for (int id : favList) {
                if (id == presentation.getId()) {
                    isFavourite = true;
                    break;
                }
            }

        if (isFavourite)
            Glide.with(this)
                    .load("")
                    .placeholder(R.drawable.ic_favorite_white_24dp)
                    .into(favourite);
        else
            Glide.with(this)
                    .load("")
                    .placeholder(R.drawable.ic_favorite_border_white_24dp)
                    .into(favourite);

        if (!(presentation.getTitle().equals("Breakfast") || presentation.getTitle().equals("Supper") ||
                presentation.getTitle().equals("Dinner") || presentation.getTitle().equals("Coffee break") ||
                presentation.getTitle().equals("Lunch"))) {

            favourite.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (DataGetter.checkUserLogged(getContext()))
                    if (isFavourite) {
                        Glide.with(v.getContext())
                                .load("")
                                .placeholder(R.drawable.ic_favorite_border_white_24dp)
                                .into(favourite);
                        isFavourite = false;
                        DataGetter.removeLoggedUserFav(getContext(), presentation.getId());
                        Toast.makeText(getContext(), R.string.question_removed_from_favs, Toast.LENGTH_SHORT).show();
                    } else {
                        Glide.with(v.getContext())
                                .load("")
                                .placeholder(R.drawable.ic_favorite_white_24dp)
                                .into(favourite);
                        isFavourite = true;
                        DataGetter.addLoggedUserFav(getContext(), presentation.getId());
                        Toast.makeText(getContext(), R.string.question_added_to_favs, Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(getContext(), R.string.not_logged_fav, Toast.LENGTH_SHORT).show();
                }
            });
        } else{
            favourite.setVisibility(View.GONE);
            speakerName.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.move_to_speakers)
    public void setMoveToSpeakers(){
        Bundle args = new Bundle();
        MainActivity mainActivity = (MainActivity) getContext();

        if (presentation.getSpeakers() == null) {

        } else if (presentation.getSpeakers().length > 1) {
            args.putIntArray("speakersIds", presentation.getSpeakers());
            mainActivity.setFragment(null, new SpeakersConferenceListFragment(), args);
        } else {
            args.putInt("speakerId", presentation.getSpeakers()[0]);
            mainActivity.setFragment(null, new SpeakerFragment(), args);
        }
    }

    @OnClick(R.id.move_to_questions)
    public void setMoveToQuestions() {

        Bundle args = new Bundle();
        MainActivity mainActivity = (MainActivity) getContext();
        args.putInt("conferenceId", presentation.getId());
        mainActivity.setFragment(null, new QuestionFragment(), args);

    }




    private void setMap(View view) {
        final Place myPlace = dataGetter.getPlaceById(presentation.getPlace());

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

        return String.format("%s  \n%s - %s", day, startTime, endTime);
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

}
