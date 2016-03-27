package com.example.yggdralisk.flyhighconference.Fragments;

import android.content.Intent;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

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

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by lukasz on 16.03.16.
 * Hijacked by yggdralisk on 18.03.16
 */

public class NavigationFragment extends Fragment {
    private static View view;
    private GoogleMap map;
    @Bind(R.id.navigation_jakdojad_ic)ImageButton jakdojad;
   @Bind(R.id.navigation_mpk_ic) ImageButton mpk;
    SupportMapFragment mapFragment;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        if (view != null) {
            ViewGroup parent = (ViewGroup) view.getParent();
            if (parent != null)
                parent.removeView(view);
        }
        try {
            view = inflater.inflate(R.layout.navigation_layout, container, false);

            ButterKnife.bind(this,view);

            mapFragment = SupportMapFragment.newInstance();

            setOnClickListners();

            FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
            fragmentTransaction.add(R.id.navigation_map, mapFragment);
            fragmentTransaction.commit();

            if (mapFragment != null) {
                mapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        map = googleMap;
                        succ(0);
                    }
                });
            }
        } catch (InflateException e) {
        }
        return view;
    }

    private void setOnClickListners() {
        jakdojad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.http://wroclaw.jakdojade.pl/"));
                startActivity(browserIntent);
            }
        });

        mpk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.wroclaw.pl/linie-na-przystanku-pl-grunwaldzki-wroclaw"));
                startActivity(browserIntent);
            }
        });
    }

    public void succ(int placeID) {
        Place place;
        LatLng loc = new LatLng(51.108636, 17.060155);

        if (placeID != 0) {
            place = DataGetter.getPlaceById(getContext(), placeID);
            if (place != null)
                loc = new LatLng(place.getLat(), place.getLon());
        } else {
            place = new Place();
            place.setName(getString(R.string.pwr_map_title));
        }

        Marker locMark = map.addMarker(new MarkerOptions().position(loc)
                .title(place.getName()));

        // Move the camera instantly to hamburg with a zoom
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 10));

        // Zoom in, animating the camera.
        map.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}


