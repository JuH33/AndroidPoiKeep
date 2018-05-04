package com.example.juh.poikeeper.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.net.sip.SipSession;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.example.juh.poikeeper.R;
import com.example.juh.poikeeper.model.PoiTask;
import com.example.juh.poikeeper.model.PointOfInterest;

import java.util.Date;

public final class PoiTaskAlert extends BasePoiAlert {

    Builder mBuilder;

    AlertDialog alertDialog;

    private EditText name;
    private EditText description;
    private EditText title;
    private DatePicker date;

    protected PoiTaskAlert(BaseBuilder builder) {
        super(builder);
        mBuilder = (Builder) builder;
        mContext = builder.getContext();
        mViewId = builder.getViewId();
        listener = builder.getListener();

        createView(builder.getViewId());
    }

    @Override
    protected View createView(int id) {
        LayoutInflater inflater = mContext.getSystemService(LayoutInflater.class);
        View view = inflater.inflate(id, null);

        view.findViewById(R.id.ok_button).setOnClickListener(this);
        view.findViewById(R.id.cancel_button).setOnClickListener(this);

        title = view.findViewById(R.id.alert_task_title);
        name = view.findViewById(R.id.alert_task_name);
        description = view.findViewById(R.id.alert_task_desc);
        date = view.findViewById(R.id.alert_task_date);

        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(mContext);
        alertBuilder.setView(view);

        alertDialog = alertBuilder.create();
        alertDialog.show();
        return null;
    }

    @Override
    public void onClick(View v) {
        if (listener == null)
            return;

        switch (v.getId()) {
            case OK_BUTTON_ID:
                PoiTask task = new PoiTask();

                task.checked = false;
                task.name = name.getText().toString();
                task.description = description.getText().toString();
                task.title = title.getText().toString();
                task.pointOfInterest = PointOfInterest.getById(mBuilder.getPoiId());
                task.date = new Date();

                long id = task.save();
                Log.e("ID::::", String.valueOf(id));
                listener.OnOkClickListenr(task);
                break;
            case CANCEL_BUTTON_ID:
                listener.OnCancelClickListener();
                break;
        }
        alertDialog.cancel();
    }

    ////////////////////////////////////////////////////////////////
    /// BUILDER
    ////////////////////////////////////////////////////////////////

    public static final class Builder extends BaseBuilder {

        private long poiId;

        public long getPoiId() {
            return poiId;
        }

        public Builder setPoiId(long poiId) {
            this.poiId = poiId;
            return this;
        }

        @Override
        public Builder setContext(Context context) {
            mContext = context;
            return this;
        }

        @Override
        public Builder setViewId(int id) {
            mViewId = id;
            return this;
        }

        @Override
        protected int getViewId() {
            return mViewId;
        }

        @Override
        protected Context getContext() {
            return mContext;
        }

        @Override
        public Builder setListener(@NonNull IOnClick onClick) {
            mListener = onClick;
            return this;
        }

        @Override
        protected IOnClick getListener() {
            return mListener;
        }

        @Override
        public PoiTaskAlert create() {
            return new PoiTaskAlert(this);
        }
    }
}
