package com.example.yggdralisk.flyhighconference.Fragments;

import android.location.Location;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.yggdralisk.flyhighconference.BackEnd.DataGetter;
import com.example.yggdralisk.flyhighconference.BackEnd.GsonClasses.Place;
import com.example.yggdralisk.flyhighconference.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by lukasz on 16.03.16.
 * Hijacked by yggdralisk on 18.03.16
 */

public class NavigationFragment extends Fragment {
    private GoogleMap map;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.navigation_layout, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.navigation_map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    map = googleMap;
                    succ(getArguments().getInt("placeID"));

                }
            });
        }


        return view;
    }

    public void succ(int placeID) {
        Place place;
        LatLng loc = new LatLng(51.108636, 17.060155);

        if (placeID !=0) {
            place = DataGetter.getPlaceById(getContext(), placeID);
            if (place != null)
                loc = new LatLng(place.getLat(), place.getLon());
        } else {
            place = new Place();
            place.setName("Politechnika wrocławska");
        }

        Marker locMark = map.addMarker(new MarkerOptions().position(loc)
                .title(place.getName()));

        // Move the camera instantly to hamburg with a zoom of 50.
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 10));

        // Zoom in, animating the camera.
        map.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);
    }
}

