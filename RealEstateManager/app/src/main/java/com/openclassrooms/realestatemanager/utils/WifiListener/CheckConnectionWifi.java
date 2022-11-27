package com.openclassrooms.realestatemanager.utils.WifiListener;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckConnectionWifi {

    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager varConnectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (varConnectivityManager != null) {
            NetworkInfo[] info = varConnectivityManager.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
