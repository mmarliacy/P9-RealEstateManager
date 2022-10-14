package com.openclassrooms.realestatemanager.view.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.view.fragments.PropertyListFragment;

public class MainActivity_HomeScreen extends AppCompatActivity {

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
}
