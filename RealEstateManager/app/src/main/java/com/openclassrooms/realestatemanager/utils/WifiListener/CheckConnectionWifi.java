package com.openclassrooms.realestatemanager.utils.WifiListener;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckConnectionWifi {
    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager varConnectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (varConnectivityManager != null) {
            @SuppressLint("MissingPermission")
            NetworkInfo[] info = varConnectivityManager.getAllNetworkInfo();
            if (info != null) {
                for (NetworkInfo pNetworkInfo : info) {
                    if (pNetworkInfo.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
