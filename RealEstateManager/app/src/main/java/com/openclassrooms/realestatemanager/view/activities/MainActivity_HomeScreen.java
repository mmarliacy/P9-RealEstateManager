package com.openclassrooms.realestatemanager.view.activities;

import android.annotation.SuppressLint;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.utils.WifiListener.NetworkChangeListener;
import com.openclassrooms.realestatemanager.view.fragments.PropertyListFragment;

public class MainActivity_HomeScreen extends AppCompatActivity{

    public static String api_key;
    private final NetworkChangeListener networkChangeListener = new NetworkChangeListener();

    //-----------
    // LIFECYCLE
    //-----------
    // 1 -- ON CREATE -->
    @SuppressLint({"UseSupportActionBar", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity_home_screen);
        //--:: 1 -- PropertyListFragment ::-->
        setUpPropertyListFragment();
        getMapApiKey();
    }

    //-------------------
    // HANDLING FRAGMENT
    //-------------------
    private void setUpPropertyListFragment() {
        Fragment varFragment = PropertyListFragment.getInstance();
        FragmentTransaction varTransaction = getSupportFragmentManager().beginTransaction();
        varTransaction.replace(R.id.main_container, varFragment);
        varTransaction.commit();
    }

    //-----------------
    // MAPS STATIC API
    //-----------------
    public void getMapApiKey() {
        try {
            ApplicationInfo varInfo_key = getPackageManager().getApplicationInfo(getApplicationContext().getPackageName(), PackageManager.GET_META_DATA);
            api_key = varInfo_key.metaData.getString("com.google.android.geo.API_KEY");
        } catch (PackageManager.NameNotFoundException pE) {
            pE.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(networkChangeListener, filter);
        super.onStart();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(networkChangeListener);
        super.onStop();
    }
}
