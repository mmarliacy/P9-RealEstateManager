package com.openclassrooms.realestatemanager.view.activities;

import android.annotation.SuppressLint;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.view.fragments.PropertyListFragment;

public class MainActivity_HomeScreen extends AppCompatActivity {

    public static String api_key;

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

        //--:: 2 -- Toolbar ::-->
        Toolbar fToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(fToolbar);

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
}
