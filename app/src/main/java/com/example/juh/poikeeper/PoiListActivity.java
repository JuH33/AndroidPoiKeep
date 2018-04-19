package com.example.juh.poikeeper;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.query.Select;
import com.example.juh.poikeeper.adapters.PoiListAdapter;
import com.example.juh.poikeeper.model.PointOfInterest;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.List;

public class PoiListActivity extends AppCompatActivity {

    private Button mNavigateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poi_list);

        // INITIALIZE BUTTONS
        mNavigateButton = findViewById(R.id.poi_list_navigate_button);
        mNavigateButton.setOnClickListener(navigateListener);

        // INITIALIZE ADAPTERS / VIEW RECYCLER
        RecyclerView listView = findViewById(R.id.poilist_listview);
        listView.setHasFixedSize(false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        listView.setLayoutManager(layoutManager);

        ActiveAndroid.beginTransaction();
        try {
            for (int i = 0; i < 10; i++) {
                PointOfInterest poi = new PointOfInterest("name" + i,
                        "desc" + i,
                        new LatLng(44d, 157d));
                poi.save();
            }
            ActiveAndroid.setTransactionSuccessful();
        } finally {
            ActiveAndroid.endTransaction();
        }

        List<PointOfInterest> pois = new Select().from(PointOfInterest.class)
                .orderBy("RANDOM()")
                .execute();

        listView.setAdapter(new PoiListAdapter(pois));
        listView.addOnItemTouchListener(poiClickListener);
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // EVENTS LISTENER
    //////////////////////////////////////////////////////////////////////////////////////

    private View.OnClickListener navigateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.e("Not Implemented", String.format("Navigate button listener from {0} is not settled", this.getClass()));
        }
    };

    private RecyclerView.OnItemTouchListener poiClickListener = new RecyclerView.OnItemTouchListener() {
        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
            Log.e("Not Implemented", String.format("Navigate button listener from {0} is not settled", this.getClass()));
            Toast.makeText(PoiListActivity.this, "touched", Toast.LENGTH_SHORT).show();
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
            return false;
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    };
}
