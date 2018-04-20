package com.example.juh.poikeeper;

import android.app.FragmentManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mapbox.mapboxsdk.geometry.LatLng;

import fragments.map.MapFragment;

public class MapActivity extends AppCompatActivity implements MapFragment.OnFragmentInteractionListener {

    private LatLng mLatLng;

    private boolean mEditMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        if (getIntent().hasExtra("lng") && getIntent().hasExtra("lat")) {
            double lng = getIntent().getDoubleExtra("lng", 0.0);
            double lat = getIntent().getDoubleExtra("lat", 0.0d);

            mLatLng = new LatLng();
            mLatLng.setLatitude(lat);
            mLatLng.setLongitude(lng);
        }

        mEditMode = getIntent().getBooleanExtra("edit_mode", false);

        MapFragment fragment = (MapFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment_id);
        if (fragment != null) {
            fragment.setLatLng(mLatLng);
            fragment.setEditMode(mEditMode);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mLatLng != null) {
            outState.putDouble("lat", mLatLng.getLatitude());
            outState.putDouble("lng", mLatLng.getLongitude());
        }
        super.onSaveInstanceState(outState);
    }
}
