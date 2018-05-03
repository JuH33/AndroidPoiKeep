package com.example.juh.poikeeper;

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

import com.example.juh.poikeeper.model.PointOfInterest;
import com.example.juh.poikeeper.utils.PicassoWrapper;
import com.mapbox.mapboxsdk.geometry.LatLng;

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
        mListTaskBtn.setOnClickListener(mTaskListActionListener);

        /// SET DATA
        Intent thisIntent = getIntent();
        long defaultLong = 0;
        long poiId = thisIntent.getExtras().getLong(PointOfInterest.PoiKeys.POI_ID.toString(), defaultLong);
        setDbDataToView(poiId);
        getSupportActionBar().setTitle(mPoint.name);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        return super.onCreateView(parent, name, context, attrs);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
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
            ((TextView) findViewById(R.id.poi_detail_coords)).setText(String.format("Lat: %s / Lng: %s",
                    Math.round(100*latLng.getLatitude())/100.f,
                    Math.round(100*latLng.getLongitude())/100.f));
            ImageView viewImage = findViewById(R.id.poi_detail_img);

            PicassoWrapper wrapper = new PicassoWrapper();

            Display display = getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);

            mPoint.imageUrl = PicassoWrapper.formatUrl(mPoint.lat, mPoint.lng, 800, 480,
                    getResources().getString(R.string.token_access_mapboxgl));

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
            Intent intent = new Intent(PoiDetailActivity.this, MapActivity.class);
            LatLng latLng = mPoint.getLatLng();
            intent.putExtra("lat", latLng.getLatitude());
            intent.putExtra("lng", latLng.getLongitude());
            startActivity(intent);
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
            Intent intent = new Intent(PoiDetailActivity.this, TaskListActivity.class);
            intent.putExtra("poi_id", mPoint.getId());
            startActivity(intent);
        }
    };
}
