package com.example.juh.poikeeper.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.juh.poikeeper.R;
import com.example.juh.poikeeper.model.PointOfInterest;

import org.w3c.dom.Text;

import java.util.List;

public class PoiListAdapter extends RecyclerView.Adapter<PoiListAdapter.PoiViewHolder> {

    private List<PointOfInterest> mDataSet;

    public PoiListAdapter(@NonNull List<PointOfInterest> dataSet) {
        mDataSet = dataSet;
    }

    // ==================================
    //          ADAPTER LIFECYCLE
    // ==================================

    @NonNull
    @Override
    public PoiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.poi_list_item, parent, false);

        PoiViewHolder viewHolder = new PoiViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PoiViewHolder holder, int position) {
        PointOfInterest poi = mDataSet.get(position);

        holder.mPoiDesc.setText(poi.description);
        holder.mPoiName.setText(poi.name);
        holder.mPoiLng.setText(String.valueOf(poi.lat));
        holder.mPoiLat.setText(String.valueOf(poi.lng));
    }

    @Override
    public void onBindViewHolder(@NonNull PoiViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    //==================================
    //           VIEW HOLDER
    //==================================
    public static class PoiViewHolder extends RecyclerView.ViewHolder {

        @NonNull public LinearLayout mLayout;

        @NonNull public TextView mPoiName;

        @NonNull public TextView mPoiDesc;

        @NonNull public TextView mPoiLat;

        @NonNull public TextView mPoiLng;

        // CONSTRUCTOR
        public PoiViewHolder(View itemView) {
            super(itemView);
            mLayout = itemView.findViewById(R.id.poi_item_layout);

            mPoiName = mLayout.findViewById(R.id.poi_item_name);
            mPoiDesc = mLayout.findViewById(R.id.poi_item_desc);
            mPoiLat = mLayout.findViewById(R.id.poi_item_lat);
            mPoiLng = mLayout.findViewById(R.id.poi_item_lng);
        }
    }

}
