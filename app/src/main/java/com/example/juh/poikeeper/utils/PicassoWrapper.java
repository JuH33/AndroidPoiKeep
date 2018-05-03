package com.example.juh.poikeeper.utils;

import android.support.annotation.StringRes;
import android.widget.ImageView;

import com.example.juh.poikeeper.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

public final class PicassoWrapper {

    Picasso picasso;

    RequestCreator mCreator;

    public PicassoWrapper() {
        picasso = Picasso.get();
    }

    public RequestCreator load(String url) {
        return picasso.load(url);
    }

    public void loadWithLoader() {
        mCreator.placeholder(R.drawable.ic_sync_black_24dp);
    }

    public void loadWithOptions(String url, boolean fit, ImageView target,
                                boolean placeholder) {
        mCreator = load(url);

        if (placeholder)
            loadWithLoader();

        if (fit)
            mCreator.fit().centerCrop();

        mCreator.into(target);
    }

    public static String formatUrl(double lat, double lng, int width, int height, String token) {
        String format = "https://api.mapbox.com/styles/v1/mapbox/streets-v10/static/%s,%s,10,0,0/%sx%s?access_token=%s";
        return String.format(format, lng, lat, width, height, token);
    }
}
