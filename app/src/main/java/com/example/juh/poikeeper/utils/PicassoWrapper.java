package com.example.juh.poikeeper.utils;

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
        mCreator.placeholder(R.drawable.ic_pin_drop_black_24dp);
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
}
