package com.example.juh.poikeeper;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.activeandroid.query.Select;
import com.example.juh.poikeeper.model.PointOfInterest;
import com.example.juh.poikeeper.utils.PicassoWrapper;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.squareup.picasso.Picasso;

public class PoiDetailActivity extends AppCompatActivity {

    /// UI ELEMENTS
    private Button mMapBtn;

    private Button mAddTaskBtn;

    private Button mListTaskBtn;

    /// MODEL ELEMENT
    private PointOfInterest mPoint;

    //////////////////////////////////////////////////////////////////////////////////////
    // ANDROID LIFECYCLE
    //////////////////////////////////////////////////////////////////////////////////////

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi_detail);

        /// GET BUTTONS
        mMapBtn = findViewById(R.id.poi_detail_map_btn);
        mAddTaskBtn = findViewById(R.id.poi_detail_add_btn);
        mListTaskBtn = findViewById(R.id.poi_detail_list_btn);

        /// ADD LISTENERS
        mMapBtn.setOnClickListener(mMapActionListener);
        mAddTaskBtn.setOnClickListener(mAddTaskActionListener);
        mListTaskBtn.setOnClickListener(mAddTaskActionListener);

        /// SET DATA
        Intent thisIntent = getIntent();
        long defaultLong = 0;
        long poiId = thisIntent.getExtras().getLong(PointOfInterest.PoiKeys.POI_ID.toString(), defaultLong);
        setDbDataToView(poiId);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // INSTANCE METHODS METHODS
    //////////////////////////////////////////////////////////////////////////////////////

    private void setDbDataToView(long poiId) {
        PointOfInterest pointOfInterest = PointOfInterest.getById(poiId);
        mPoint = pointOfInterest;

        if (mPoint == null) {
            onBackPressed();
        } else {
            ((TextView) findViewById(R.id.poi_detail_name)).setText(mPoint.name);
            ((TextView) findViewById(R.id.poi_detail_desc)).setText(mPoint.description);
            LatLng latLng = mPoint.getLatLng();
            ((TextView) findViewById(R.id.poi_detail_coords)).setText(String.format("Lat: %s / Lng: %s", latLng.getLatitude(), latLng.getLongitude()));
            ImageView viewImage = findViewById(R.id.poi_detail_img);

            PicassoWrapper wrapper = new PicassoWrapper();
            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            wrapper.loadWithOptions(mPoint.imageUrl, true, viewImage, true);
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // EVENTS LISTENER
    //////////////////////////////////////////////////////////////////////////////////////

    View.OnClickListener mMapActionListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.e("Imp", "not imp");
        }
    };

    View.OnClickListener mAddTaskActionListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.e("imp", "not imp");
        }
    };

    View.OnClickListener mTaskListActionListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.e("imp", "not imp");
        }
    };
}
