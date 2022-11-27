package com.openclassrooms.realestatemanager.utils.WifiListener;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.openclassrooms.realestatemanager.R;

public class NetworkChangeListener extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        // - If connection is off, set dialog --
        if(!CheckConnectionWifi.isConnectedToInternet(context)){
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            View layoutDialog = LayoutInflater.from(context).inflate(R.layout.connection_is_off, null);
            builder.setView(layoutDialog);
            // - Show dialog --
            AlertDialog dialog = builder.create();
            dialog.show();
            dialog.setCancelable(false);
            dialog.getWindow().setGravity(Gravity.CENTER);

            // - Configure retry button --
            Button retryBtn = layoutDialog.findViewById(R.id.retry);
            retryBtn.setOnClickListener(pView -> {
                    dialog.dismiss();
                    onReceive(context, intent);
            });
        }
    }
}
