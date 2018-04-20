package com.example.juh.poikeeper;

import android.content.Intent;
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

        List<PointOfInterest> pois = new Select().from(PointOfInterest.class)
                .orderBy("RANDOM()")
                .execute();

        try {
            listView.setAdapter(new PoiListAdapter(pois, new PoiListAdapter.PoiViewHolder.OnPoiViewHolderAction() {
                @Override
                public void onClickGetDbID(long id) {
                    Intent intent = new Intent(PoiListActivity.this, PoiDetailActivity.class);
                    intent.putExtra(PointOfInterest.PoiKeys.POI_ID.toString(), id);
                    startActivity(intent);
                }
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // EVENTS LISTENER
    //////////////////////////////////////////////////////////////////////////////////////

    private View.OnClickListener navigateListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.e("Not Implemented", String.format("Navigate button listener from %s is not settled", this.getClass()));
            Intent intent = new Intent(PoiListActivity.this, MapActivity.class);
            startActivity(intent);
        }
    };
}
