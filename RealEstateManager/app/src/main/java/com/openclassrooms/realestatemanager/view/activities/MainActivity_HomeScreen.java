package com.openclassrooms.realestatemanager.view.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;

import com.google.firebase.FirebaseApp;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.firebase.injection.FirebaseInjection;
import com.openclassrooms.realestatemanager.firebase.viewmodel.FirebaseViewModel;
import com.openclassrooms.realestatemanager.local.injection.LocalInjection;
import com.openclassrooms.realestatemanager.local.factory.LocalViewModelFactory;
import com.openclassrooms.realestatemanager.local.viewmodel.LocalPropertyViewModel;
import com.openclassrooms.realestatemanager.model.PropertyModel;

import java.util.List;

public class MainActivity_HomeScreen extends AppCompatActivity {

    Toolbar fToolbar;
    TextView fTextView;
    LocalPropertyViewModel varLocalPropertyViewModel;

    @SuppressLint({"UseSupportActionBar", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.real_estate_sheet_layout);

        //--:: 1 -- Configure View Models ::-->
        configureLocalViewModel();
        observeLocalChange();
        configureFirebaseViewModel();


        //--:: 2 -- Toolbar ::-->
        fToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(fToolbar);

        fTextView = findViewById(R.id.sheet_prop_cost);
    }


    //------------
    // VIEW MODELS
    //-------------
    private void configureLocalViewModel() {
        LocalViewModelFactory varLocalViewModelFactory = LocalInjection.provideViewModelFactory(getApplicationContext());
        varLocalPropertyViewModel = varLocalViewModelFactory.create(LocalPropertyViewModel.class);
        varLocalPropertyViewModel.initUsersList();
    }

    private void observeLocalChange(){
        varLocalPropertyViewModel.getAllProperties().observe(this, new Observer<List<PropertyModel>>() {
            @Override
            public void onChanged(List<PropertyModel> pPropertyModels) {
                if (pPropertyModels != null){
                    for (PropertyModel property: pPropertyModels){
                        // Get the data in log
                        Log.v("Tag", "onChanged: "+ property.getName());
                    }
                }
            }
        });
    }
    private void configureFirebaseViewModel() {
        FirebaseApp.initializeApp(getApplicationContext());
        FirebaseViewModel varFirebaseViewModel = FirebaseInjection.provideFirebaseViewModelFactory().create(FirebaseViewModel.class);
        varFirebaseViewModel.initPropertiesList();
    }

}
