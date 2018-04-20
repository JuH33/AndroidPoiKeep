package com.example.juh.poikeeper.utils;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.View;
import com.example.juh.poikeeper.R;
import com.example.juh.poikeeper.model.PointOfInterest;

public abstract class BasePoiAlert implements View.OnClickListener {

    protected final int OK_BUTTON_ID = R.id.ok_button;

    protected final int CANCEL_BUTTON_ID = R.id.cancel_button;

    protected Context mContext;

    protected abstract View createView(@LayoutRes int id);

    protected int mViewId;

    protected IOnClick listener;

    protected BasePoiAlert(BaseBuilder builder) {

    }

    @Override
    abstract public void onClick(View v);

    public abstract static class BaseBuilder {

        protected Context mContext;
        protected int mViewId;
        protected IOnClick mListener;

        protected abstract <T extends BaseBuilder>T setContext(Context context);
        protected abstract <T extends BaseBuilder>T setViewId(@LayoutRes int id);

        protected abstract int getViewId();
        protected abstract Context getContext();

        protected abstract <T extends BaseBuilder>T setListener(@NonNull IOnClick onClick);
        protected abstract IOnClick getListener();

        protected abstract <T extends BasePoiAlert>T create();
    }

    public interface IOnClick {
        void OnOkClickListenr(@Nullable PointOfInterest pointOfInterest);
        void OnCancelClickListener();
    }
}
