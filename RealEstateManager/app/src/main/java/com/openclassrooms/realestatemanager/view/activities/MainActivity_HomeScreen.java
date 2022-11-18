package com.openclassrooms.realestatemanager.view.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.realestatemanager.MVVM.injection.room.RoomInjection;
import com.openclassrooms.realestatemanager.MVVM.injection.room.RoomViewModelFactory;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.adapters.PropertyListAdapter;
import com.openclassrooms.realestatemanager.model.PropertyModel;
import com.openclassrooms.realestatemanager.model.UserModel;
import com.openclassrooms.realestatemanager.view.fragments.PropertyListFragment;
import com.openclassrooms.realestatemanager.view.viewmodel.RoomViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity_HomeScreen extends AppCompatActivity{

    /** Graphics */

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
