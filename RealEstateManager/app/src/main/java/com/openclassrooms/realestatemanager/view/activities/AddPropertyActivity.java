package com.openclassrooms.realestatemanager.view.activities;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.openclassrooms.realestatemanager.MVVM.injection.room.RoomInjection;
import com.openclassrooms.realestatemanager.MVVM.injection.room.RoomViewModelFactory;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.PropertyModel;
import com.openclassrooms.realestatemanager.view.viewmodel.FirebaseViewModel;
import com.openclassrooms.realestatemanager.view.viewmodel.RoomViewModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class AddPropertyActivity extends AppCompatActivity {

    //---------------
    // DATA - FIELDS
    //---------------
    /**
     * Graphics
     */
    TextInputLayout propSellerLayout;
    TextInputEditText propTitleInput;
    TextInputEditText propTypeInput;
    TextInputEditText propTotalAreaInput;
    TextInputEditText propAddressInput;
    TextInputEditText forSaleSinceInput;
    TextInputEditText soldSinceInput;
    TextInputEditText descInput;
    TextInputEditText priceInput;

    TextView totalRooms;
    Button moreBtn;
    Button lessBtn;
    int rooms = 0;

    int sellerId;
    String title;
    String type;
    String totalArea;
    String address;
    String saleSince;
    String soldSince;
    String description;
    String allRooms;
    ImageButton confirmBtn;
    ImageButton cancelBtn;

    int userId;
    String price;
    String status;
    String sellerStringId;
    List<String> photoProperty;
    List<String> interestProperty;

    /**
     * LIVE DATA - VIEW MODELS
     */
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
        setOnClickConfirmAndCancelBtn();
        setMoreAndLessButtonLogic();
        verifyInputData();
    }

    //----------------------
    // BINDING VIEWS & DATA
    //----------------------
    // 1 -- Reference views to graphics data in activity -->
    private void defineView() {
        propSellerLayout = findViewById(R.id.form_property_seller_layout);
        propTitleInput = findViewById(R.id.form_property_name_input);
        propTypeInput = findViewById(R.id.form_property_type_input);
        priceInput = findViewById(R.id.form_property_price_input);
        propTotalAreaInput = findViewById(R.id.form_property_land_area_input);
        propAddressInput = findViewById(R.id.form_address_input);
        forSaleSinceInput = findViewById(R.id.form_for_sale_since_input);
        soldSinceInput = findViewById(R.id.form_sold_out_since_input);
        descInput = findViewById(R.id.form_property_description_input);
        totalRooms = findViewById(R.id.form_total_rooms_number);
        totalRooms.setText(String.valueOf(0));
        moreBtn = findViewById(R.id.button_more_total_rooms);
        lessBtn = findViewById(R.id.button_less_total_rooms);
        confirmBtn = findViewById(R.id.form_confirm);
        cancelBtn = findViewById(R.id.form_cancel);
    }

    //--------------------
    // MINI LOGIC METHODS
    //--------------------
    private void setOnClickConfirmAndCancelBtn() {
        confirmBtn.setOnClickListener(pView -> {
            collectDataFromNewProperty();
        });
        cancelBtn.setOnClickListener(v -> startActivity(new Intent(this, MainActivity_HomeScreen.class)));
    }

    private void setMoreAndLessButtonLogic() {
        moreBtn.setOnClickListener(v -> {
            rooms += 1;
            totalRooms.setText(String.valueOf(rooms));
        });
        lessBtn.setOnClickListener(v -> {
            if (rooms > 0) {
                rooms -= 1;
            }
            totalRooms.setText(String.valueOf(rooms));
        });
    }

    public void verifyInputData() {
        sellerStringId= Objects.requireNonNull(propSellerLayout.getEditText().getText()).toString();
        title = Objects.requireNonNull(propTitleInput.getText()).toString();
        type = Objects.requireNonNull(propTypeInput.getText()).toString();
        totalArea = Objects.requireNonNull(propTotalAreaInput.getText()).toString();
        price = Objects.requireNonNull(priceInput.getText()).toString();
        address = Objects.requireNonNull(propAddressInput.getText()).toString();
        saleSince = Objects.requireNonNull(forSaleSinceInput.getText()).toString();
        soldSince = Objects.requireNonNull(soldSinceInput.getText()).toString();
        description = Objects.requireNonNull(descInput.getText()).toString();
        allRooms = totalRooms.getText().toString();
        if (soldSince.equals("")){
            status = "Unavailable";
        } else {
            status = "Available";
        }

        // String myString = NumberFormat.getInstance().format(myNumber);

        // /!\ TO CONFIGURE !!!!!!!!!!!!
        photoProperty = new ArrayList<>();
        interestProperty = new ArrayList<>();

        verifyTotalAreaInput();
        verifyPriceInput();
        initDatePicker();
        initListOfUsers();
    }

    // 2 -- Connect graphics data to real data in fragment -->
    @SuppressLint("SetTextI18n")
    private void collectDataFromNewProperty() {
        String pattern ="-?\\d+";
        if(sellerStringId.matches(pattern)){
            sellerId = Integer.parseInt(sellerStringId);
        }
        PropertyModel varPropertyModel = new PropertyModel(
                sellerId, title, type, address,
                description, totalArea, allRooms,
                price, status, photoProperty,
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


    //-------------------
    // INIT DATA METHODS
    //-------------------
    private void initListOfUsers(){
        List<String> usersId = Arrays.asList("1","2","3");
        AutoCompleteTextView atvUserInput = findViewById(R.id.form_property_seller_input);

        //-- Find user position in Drop down list --
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_item, usersId);
        atvUserInput.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
        atvUserInput.setOnItemClickListener((adapterView, view, position, l) -> {

            sellerStringId = arrayAdapter.getItem(position);});}



    //-- Init & get Time Picker Dialog Value -- /!\ Correct all setText
    private void initDatePicker() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);

        forSaleSinceInput.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AddPropertyActivity.this, (datePicker, year1, month1, day1) -> {
                month1 += 1;
                String date = day1 +"/"+ month1 +"/"+ year1;
                forSaleSinceInput.setText(date);
            }, year, month, day);
            datePickerDialog.show();
        });
        soldSinceInput.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AddPropertyActivity.this, (datePicker, year1, month1, day1) -> {
                month1 += 1;
                String date = day1 +"/"+ month1 +"/"+ year1;
                soldSinceInput.setText(date);
            }, year, month, day);
            datePickerDialog.show();
        });
    }

    //----------------------
    // VERIFY INPUT METHODS
    //----------------------
    private void verifyTotalAreaInput(){
        propTotalAreaInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence pCharSequence, int pI, int pI1, int pI2) {
            }

            @Override
            public void onTextChanged(CharSequence pCharSequence, int pI, int pI1, int pI2) {
            }

            @Override
            public void afterTextChanged(Editable pEditable) {
                TextInputLayout propTotalAreaLayout = findViewById(R.id.form_property_land_area_layout);
                Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
                if (!pattern.matcher(pEditable).matches()) {
                    propTotalAreaInput.setError("Use only numbers in that section");
                }
                if (pattern.matcher(pEditable).matches()) {
                    propTotalAreaLayout.setErrorEnabled(false);
                }
            }
        });
    }

    private void verifyPriceInput(){
        priceInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence pCharSequence, int pI, int pI1, int pI2) {
            }
            @Override
            public void onTextChanged(CharSequence pCharSequence, int pI, int pI1, int pI2) {
            }
            @Override
            public void afterTextChanged(Editable pEditable) {
                TextInputLayout propPriceLayout = findViewById(R.id.form_property_price_layout);
                Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");
                if (!pattern.matcher(pEditable).matches()) {
                    priceInput.setError("Use only numbers in that section");
                }
                if (pattern.matcher(pEditable).matches()) {
                    propPriceLayout.setErrorEnabled(false);
                }
            }
        });
    }

    //-----------------------
    // LOCAL ROOM VIEW MODEL
    //-----------------------
    // 1 -- Configure ROOM View Model & Retrieve all properties stuck on phone and last connected User -->
    private void configureLocalRoomViewModel(){
        RoomViewModelFactory roomViewModelFactory = RoomInjection.provideViewModelFactory(this);
        roomViewModel = roomViewModelFactory.create(RoomViewModel.class);
        roomViewModel.initPropertiesList();
    }
}
