package com.example.juh.poikeeper.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;

public final class ConnectionWrapper {

    public static boolean hasConnexion(Context context) {
        ConnectivityManager c = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo n = c.getActiveNetworkInfo();

        return (n != null && n.isConnected() &&
                n.getType() == ConnectivityManager.TYPE_WIFI ||
                n.getType() == ConnectivityManager.TYPE_MOBILE);
    }
}
