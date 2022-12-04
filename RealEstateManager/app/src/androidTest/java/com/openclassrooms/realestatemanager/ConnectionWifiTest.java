package com.openclassrooms.realestatemanager;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.net.ConnectivityManager;

import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.openclassrooms.realestatemanager.utils.Utils;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class ConnectionWifiTest {

    @Test
    public void verifyIfWifiIsConnected() {
        ConnectivityManager connectivityManager = ((ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE));
        assertNotEquals( connectivityManager.getActiveNetworkInfo(), null);
        assertTrue(connectivityManager.getActiveNetworkInfo().isConnected());
        assertTrue(Utils.isInternetAvailable(getApplicationContext()));
    }
}
