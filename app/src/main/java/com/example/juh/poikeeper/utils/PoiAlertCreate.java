package com.example.juh.poikeeper.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.juh.poikeeper.R;
import com.example.juh.poikeeper.model.PointOfInterest;

public final class PoiAlertCreate extends BasePoiAlert {

    /////////////////////////////////////////////////////////////////
    /// PROPERTIES
    /////////////////////////////////////////////////////////////////

    private TextView name;
    private TextView desc;
    private double lat;
    private double lng;
    private View view;
    private AlertDialog alertDialog;

    private PoiAlertCreate(BaseBuilder builder) {
        super(builder);
        mContext = builder.getContext();
        listener = builder.getListener();
        lat = ((Builder) builder).getLat();
        lng = ((Builder) builder).getLng();

        view = createView(builder.getViewId());
    }

    @Override
    protected View createView(@LayoutRes int id) {
        LayoutInflater inflater = mContext.getSystemService(LayoutInflater.class);
        View view = inflater.inflate(id, null, false);
        view.findViewById(OK_BUTTON_ID).setOnClickListener(this);
        view.findViewById(CANCEL_BUTTON_ID).setOnClickListener(this);
        name = view.findViewById(R.id.alert_poi_add_name);
        desc = view.findViewById(R.id.alert_poi_add_desc);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
        alertBuilder.setView(view);
        alertDialog = alertBuilder.show();
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        return null;
    }

    @Override
    public void onClick(View v) {
        if (listener == null)
            return;

        switch (v.getId()) {
            case OK_BUTTON_ID:
                PointOfInterest point = new PointOfInterest(name.getText().toString(),
                                                            desc.getText().toString(),
                                                   null, lat, lng);
                point.save();
                listener.OnOkClickListenr(point);
                break;
            case CANCEL_BUTTON_ID:
                listener.OnCancelClickListener();
                break;
        }
        alertDialog.cancel();
    }

    /////////////////////////////////////////////////////////////////
    /// BUILDER INNER CLASS
    /////////////////////////////////////////////////////////////////

    public final static class Builder extends BaseBuilder {

        private double lat;
        private double lng;

        /////////////////////////////////////////////////////////////////
        /// SETTERS
        /////////////////////////////////////////////////////////////////

        @Override
        public Builder setViewId(int id) {
            mViewId = id;
            return this;
        }

        @Override
        public Builder setContext(@NonNull Context context) {
            mContext = context;
            return this;
        }

        @Override
        public Builder setListener(@NonNull IOnClick onClick) {
            mListener = onClick;
            return this;
        }

        public Builder setLng(double lng) {
            this.lng = lng;
            return this;
        }

        public Builder setLat(double lat) {
            this.lat = lat;
            return this;
        }


        /////////////////////////////////////////////////////////////////
        /// GETTERS
        /////////////////////////////////////////////////////////////////

        @Override
        public int getViewId() {
            return mViewId;
        }

        @Override
        public Context getContext() {
            return mContext;
        }

        @Override
        protected IOnClick getListener() {
            return mListener;
        }

        public double getLng() {
            return lng;
        }

        public double getLat() {
            return lat;
        }

        @Override
        public PoiAlertCreate create() {
            return new PoiAlertCreate(this);
        }
    }
}
