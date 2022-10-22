package com.openclassrooms.realestatemanager.view.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.openclassrooms.realestatemanager.MVVM.injection.firebase.FirebaseInjection;
import com.openclassrooms.realestatemanager.MVVM.injection.firebase.FirebaseViewModelFactory;
import com.openclassrooms.realestatemanager.MVVM.injection.room.RoomInjection;
import com.openclassrooms.realestatemanager.MVVM.injection.room.RoomViewModelFactory;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.adapters.PropertyListAdapter;
import com.openclassrooms.realestatemanager.model.PropertyModel;
import com.openclassrooms.realestatemanager.view.viewmodel.FirebaseViewModel;
import com.openclassrooms.realestatemanager.view.viewmodel.RoomViewModel;

import java.util.ArrayList;
import java.util.List;

public class AddPropertyActivity extends AppCompatActivity {

    //---------------
    // DATA - FIELDS
    //---------------
    /** Graphics */
    TextInputLayout propTitleInput;
    TextInputLayout propTypeInput;
    TextInputLayout propTotalAreaInput;
    TextInputLayout propAddressInput;
    TextInputLayout forSaleSinceInput;
    TextInputLayout soldSinceInput;
    TextInputLayout descInput;
    TextView totalRooms;
    Button moreBtn;
    Button lessBtn;
    ImageButton confirmBtn;
    ImageButton cancelBtn;

    /** LIVE DATA - VIEW MODELS */
    FirebaseViewModel varFirebaseViewModel;
    RoomViewModel roomViewModel;


    //-----------
    // LIFECYCLE
    //-----------
    // 1 -- ON CREATE -->
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_property_layout);
        configureLocalRoomViewModel();
        defineView();
        confirmBtn.setOnClickListener(v-> collectDataFromNewProperty());
    }

    //----------------------
    // BINDING VIEWS & DATA
    //----------------------
    // 1 -- Reference views to graphics data in activity -->
    private void defineView(){
        propTitleInput = findViewById(R.id.form_property_name_input);
        propTypeInput = findViewById(R.id.form_property_type_input);
        propTotalAreaInput = findViewById(R.id.form_property_land_area_input);
        propAddressInput = findViewById(R.id.form_address_input);
        forSaleSinceInput = findViewById(R.id.form_for_sale_since_input);
        soldSinceInput = findViewById(R.id.form_sold_out_since_input);
        descInput = findViewById(R.id.form_property_description_input);
        totalRooms = findViewById(R.id.form_total_rooms_number);

        moreBtn = findViewById(R.id.button_more_total_rooms);
        lessBtn = findViewById(R.id.button_less_total_rooms);
        confirmBtn = findViewById(R.id.form_confirm);
        cancelBtn = findViewById(R.id.form_cancel);
    }

    // 2 -- Connect graphics data to real data in fragment -->
    @SuppressLint("SetTextI18n")
    private void collectDataFromNewProperty() {

        String title = propTitleInput.getEditText().getText().toString();
        String type  = propTypeInput.getEditText().getText().toString();
        String totalArea  = propTotalAreaInput.getEditText().getText().toString();
        String address  = propAddressInput.getEditText().getText().toString();
        String saleSince  = forSaleSinceInput.getEditText().getText().toString();
        String soldSince  = soldSinceInput.getEditText().getText().toString();
        String description  = descInput.getEditText().getText().toString();
        int rooms = 0;
        if (moreBtn.callOnClick()){
            totalRooms.setText(String.valueOf(rooms + 1));
        } if (lessBtn.callOnClick()){
            totalRooms.setText(String.valueOf(rooms - 1));
        }

        // /!\ TO CONFIGURE !!!!!!!!!!!!
        String allRooms = totalRooms.getText().toString();
        int userId = 1;
        String price = "100000";
        String status = "";
        List<String> photoProperty = new ArrayList<>();

        List<String> interestProperty = new ArrayList<>();


        PropertyModel varPropertyModel = new PropertyModel(
                userId, title, type, address,
                description, totalArea, allRooms,
                price,status, photoProperty,
                interestProperty, saleSince, soldSince);
        addProperty(varPropertyModel);
        Intent varIntent = new Intent(this, MainActivity_HomeScreen.class);
        startActivity(varIntent);
    }

    /**
     * Adds the given task to the list of created tasks.
     */
    private void addProperty(@NonNull PropertyModel pPropertyModel) {
        this.roomViewModel.addProperty(pPropertyModel);
    }

    //---------------------
    // FIREBASE VIEW MODEL
    //---------------------
    // 1 -- Configure Firebase View Model & Retrieve all properties and connected User -->
    private void configureFirebaseViewModel() {
        FirebaseViewModelFactory varFirebaseViewModelFactory = FirebaseInjection.provideFirebaseViewModelFactory();
        varFirebaseViewModel = varFirebaseViewModelFactory.create(FirebaseViewModel.class);
        varFirebaseViewModel.retrieveAllProperties();
        varFirebaseViewModel.retrieveUserConnected();
    }

    //-----------------------
    // LOCAL ROOM VIEW MODEL
    //-----------------------
    // 1 -- Configure ROOM View Model & Retrieve all properties stuck on phone and last connected User -->
    private void configureLocalRoomViewModel(){
        RoomViewModelFactory roomViewModelFactory = RoomInjection.provideViewModelFactory(this);
        roomViewModel = roomViewModelFactory.create(RoomViewModel.class);
    }
}
