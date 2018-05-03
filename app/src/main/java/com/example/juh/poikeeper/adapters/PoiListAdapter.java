package com.example.juh.poikeeper.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.juh.poikeeper.PoiListActivity;
import com.example.juh.poikeeper.R;
import com.example.juh.poikeeper.model.PointOfInterest;

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;

public class PoiListAdapter extends RecyclerView.Adapter<PoiListAdapter.PoiViewHolder> {

    private List<PointOfInterest> mDataSet;

    private PoiViewHolder.OnPoiViewHolderAction mViewHolderListener;

    public PoiListAdapter(@NonNull List<PointOfInterest> dataSet,
                          @NonNull PoiViewHolder.OnPoiViewHolderAction action) throws Exception {
        if (action == null) {
            throw new Exception("Action cannot be null");
        }
        mViewHolderListener = action;
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

        PoiViewHolder viewHolder = new PoiViewHolder(view, mViewHolderListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PoiViewHolder holder, int position) {
        PointOfInterest poi = mDataSet.get(position);

        holder.mPoiDesc.setText(poi.description);
        holder.mPoiName.setText(poi.name);
        holder.mPoiLng.setText(String.valueOf(Math.round(100 * poi.lat)/100.f));
        holder.mPoiLat.setText(String.valueOf(Math.round(100 * poi.lng)/100.f));
        holder.setId(poi.getId());
    }

    @Override
    public void onBindViewHolder(@NonNull PoiViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public void addData(List<PointOfInterest> list) {
        int insertAt = mDataSet.size();
        int count = 0;

        for (PointOfInterest poi : list) {
            if (mDataSet.contains(poi)) continue;

            mDataSet.add(poi);
            count++;
        }

        this.notifyItemRangeInserted(insertAt, count);
    }

    //==================================
    //           VIEW HOLDER
    //==================================
    public static class PoiViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @NonNull public LinearLayout mLayout;

        @NonNull public TextView mPoiName;

        @NonNull public TextView mPoiDesc;

        @NonNull public TextView mPoiLat;

        @NonNull public TextView mPoiLng;

        // DATA

        @NonNull private long id;

        @NonNull private OnPoiViewHolderAction mAction;

        // CONSTRUCTOR
        public PoiViewHolder(View itemView, OnPoiViewHolderAction action) {
            super(itemView);
            mLayout = itemView.findViewById(R.id.poi_item_layout);
            mAction = action;
            mLayout.setOnClickListener(this);

            mPoiName = mLayout.findViewById(R.id.poi_item_name);
            mPoiDesc = mLayout.findViewById(R.id.poi_item_desc);
            mPoiLat = mLayout.findViewById(R.id.poi_item_lat);
            mPoiLng = mLayout.findViewById(R.id.poi_item_lng);
        }

        public void setId(long id) {
            this.id = id;
        }

        @Override
        public void onClick(View v) {
            Log.e("lalalal", "clicked" + id);
            mAction.onClickGetDbID(id);
        }

        //////////
        // Public Interface as Callback
        //////////
        public interface OnPoiViewHolderAction {
            void onClickGetDbID(long id);
        }
    }

}
