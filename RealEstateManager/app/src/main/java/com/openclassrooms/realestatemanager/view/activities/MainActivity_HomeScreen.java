package com.openclassrooms.realestatemanager.view.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.injection.Injection;
import com.openclassrooms.realestatemanager.injection.ViewModelFactory;
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModel;

public class MainActivity_HomeScreen extends AppCompatActivity {

    Toolbar fToolbar;

    @SuppressLint({"UseSupportActionBar", "ResourceAsColor"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_property_layout);

        //--:: 1 -- Configure Room Database View Model ::-->
        configureLocalViewModel();

        //--:: 2 -- Toolbar ::-->
        fToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(fToolbar);
    }

    //------------
    // VIEW MODELS
    //-------------
    private void configureLocalViewModel() {
        ViewModelFactory viewModelFactory = Injection.provideViewModelFactory(getApplicationContext());
        PropertyViewModel varPropertyViewModel = viewModelFactory.create(PropertyViewModel.class);
        varPropertyViewModel.initUsersList();
    }
}
