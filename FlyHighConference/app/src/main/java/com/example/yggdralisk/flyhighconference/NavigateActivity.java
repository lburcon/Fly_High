package com.example.yggdralisk.flyhighconference;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by yggdralisk on 26.03.16.
 */
public class NavigateActivity extends AppCompatActivity {
    private GoogleMap map;

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigate_layout);

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.navigate_map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    map = googleMap;
                    if (getIntent() != null)
                        succ(getIntent().getDoubleExtra("placeLAT", 51.108636), getIntent().getDoubleExtra("placeLNG", 17.060155), getIntent().getStringExtra("placeTitle"));
                    else
                        succ(51.108636, 17.060155, getString(R.string.pwr_map_title));
                }
            });
        }
    }

    //   LatLng loc = new LatLng(51.108636, 17.060155);
    public void succ(double lat, double lng, String title) {
        LatLng loc = new LatLng(lat, lng);
        map.addMarker(new MarkerOptions().position(loc)
                .title(title != null ? title : getString(R.string.pwr_map_title)));

        // Move the camera instantly to hamburg with a zoom
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, 10));

        // Zoom in, animating the camera.
        map.animateCamera(CameraUpdateFactory.zoomTo(13), 2000, null);
    }
}