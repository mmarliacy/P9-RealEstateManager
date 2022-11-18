package com.openclassrooms.realestatemanager.view.activities;

import static com.openclassrooms.realestatemanager.model.DummyListCallback.getInterestList;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.openclassrooms.realestatemanager.MVVM.injection.firebase.FirebaseInjection;
import com.openclassrooms.realestatemanager.MVVM.injection.firebase.FirebaseViewModelFactory;
import com.openclassrooms.realestatemanager.MVVM.injection.room.RoomInjection;
import com.openclassrooms.realestatemanager.MVVM.injection.room.RoomViewModelFactory;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.adapters.AddPhotoAdapter;
import com.openclassrooms.realestatemanager.model.InterestModel;
import com.openclassrooms.realestatemanager.model.PropertyModel;
import com.openclassrooms.realestatemanager.model.UserModel;
import com.openclassrooms.realestatemanager.view.viewmodel.FirebaseViewModel;
import com.openclassrooms.realestatemanager.view.viewmodel.RoomViewModel;

import java.io.IOException;
import java.util.ArrayList;
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
    AutoCompleteTextView atvUserInput;
    LinearLayout linearLayout;
    LayoutInflater inflater;
    ImageButton addPhotoBtn;
    ImageButton addNextPhotoBtn;

    TextView totalRooms;
    Button moreBtn;
    Button lessBtn;
    int rooms;
    String sellerIdForPropertyToUpdate;
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

    String price;
    String status;
    String sellerName;
    List<String> photoProperty = new ArrayList<>();
    String sellerId;
    List<UserModel> allUsers = new ArrayList<>();
    List<String> selectedInterest = new ArrayList<>();
    List<String> usersNameList = new ArrayList<>();
    List<PropertyModel> properties = new ArrayList<>();
    List<InterestModel> interestList = getInterestList();
    PropertyModel propertyToUpdate;
    PropertyModel propertyModel;
    AddPhotoAdapter fAddPhotoAdapter = new AddPhotoAdapter(photoProperty);
    private static final int read_permission_code = 101;
    private static final int pick_image_code = 1;

    /**
     * LIVE DATA - VIEW MODELS
     */
    RoomViewModel roomViewModel;
    FirebaseViewModel firebaseViewModel;


    //-----------
    // LIFECYCLE
    //-----------
    // 1 -- ON CREATE -->
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_property_layout);
        defineView();
        if (getIntent().hasExtra("propertyToUpdate")) {
            propertyToUpdate = getIntent().getParcelableExtra("propertyToUpdate");
            definePropertyToUpdate();
        }
        setPhotosList();
        configureLocalRoomViewModel();
        //getAndObserveUsers();

        configureFirebaseViewModel();
        getAndObserveFirebaseUsers();
        setOnClickConfirmAndCancelBtn();
        setMoreAndLessButtonLogic();
        verifyInputData();
        connectChip();
    }

    private void definePropertyToUpdate() {
        if (propertyToUpdate != null) {
            atvUserInput.setText(sellerName);
            propTitleInput.setText(propertyToUpdate.getName());
            propTypeInput.setText(propertyToUpdate.getType());
            propTotalAreaInput.setText(propertyToUpdate.getTotalLeavingArea());
            priceInput.setText(propertyToUpdate.getPrice());
            propAddressInput.setText(propertyToUpdate.getAddress());
            forSaleSinceInput.setText(propertyToUpdate.getOnSaleDate());
            soldSinceInput.setText(propertyToUpdate.getSoldDate());
            descInput.setText(propertyToUpdate.getDescription());
            totalRooms.setText(propertyToUpdate.getRooms());
        }
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
        atvUserInput = findViewById(R.id.form_property_seller_input);
        addNextPhotoBtn = findViewById(R.id.next_item);
    }

    //--------------------
    // MINI LOGIC METHODS
    //--------------------
    private void setOnClickConfirmAndCancelBtn() {
        confirmBtn.setOnClickListener(pView -> collectDataFromNewProperty());
        cancelBtn.setOnClickListener(v -> startActivity(new Intent(this, MainActivity_HomeScreen.class)));
    }

    private void setMoreAndLessButtonLogic() {
        if (propertyToUpdate != null) {
            rooms = Integer.parseInt(propertyToUpdate.getRooms());
        }
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
        verifyTotalAreaInput();
        verifyPriceInput();
        verifyInputTitleProperty();
        initDatePicker();
    }

    public void setPhotosList() {
        linearLayout = findViewById(R.id.form_property_photos_linearLayout);
        inflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.photo_add_first_item, linearLayout, true);
        addPhotoBtn = view.findViewById(R.id.first_item);
        if (photoProperty.size() == 0) {
            addNextPhotoBtn.setVisibility(View.GONE);
            addPhotoBtn.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                addPhotoBtn.setOnClickListener(v -> {
                    try {
                        addPhoto();
                    } catch (IOException pE) {
                        pE.printStackTrace();
                    }
                });
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void addPhoto() throws IOException {
        if (ContextCompat.checkSelfPermission(AddPropertyActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddPropertyActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, read_permission_code);
        }
        /*
        //First method with MediaCollection
        String[] projection = new String[] {
        };
        String selection = sql-where-clause-with-placeholder-variables;
        String[] selectionArgs = new String[] {

        };
        String sortOrder = "ORDER BY NAME";

        Cursor cursor = getApplicationContext().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                sortOrder
        );

        while (cursor.moveToNext()) {
            // Use an ID column from the projection to get
            // a URI representing the media item itself.
        }
        Uri photoUri = Uri.withAppendedPath(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                cursor.getString(idColumnIndex));

        final double[] latLong;

// Get location data using the Exif interface library.
// Exception occurs if ACCESS_MEDIA_LOCATION permission isn't granted.
        photoUri = MediaStore.setRequireOriginal(photoUri);
        InputStream stream = getContentResolver().openInputStream(photoUri);
        if (stream != null) {
            ExifInterface exifInterface = new ExifInterface(stream);
            double[] returnedLatLong = exifInterface.getLatLong();

            // If lat/long is null, fall back to the coordinates (0, 0).
            latLong = returnedLatLong != null ? returnedLatLong : new double[2];

            // Don't reuse the stream associated with
            // the instance of "ExifInterface".
            stream.close();
        } else {
            // Failed to load the stream, so return the coordinates (0, 0).
            latLong = new double[2];
        }

        ActivityResultLauncher<PickVisualMediaRequest> pickMedia =
                registerForActivityResult(new ActivityResultContracts.PickVisualMedia(), uri -> {
                    // Callback is invoked after the user selects a media item or closes the
                    // photo picker.
                    if (uri != null) {
                        Log.d("PhotoPicker", "Selected URI: " + uri);
                    } else {
                        Log.d("PhotoPicker", "No media selected");
                    }
                });

        // Launch the photo picker and allow the user to choose only images.
        pickMedia.launch(new PickVisualMediaRequest.Builder()
                .setMediaType(PickVisualMedia.ImageOnly.INSTANCE)
                .build());     */

        // Second method with Intent
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        startActivityForResult(Intent.createChooser(intent, "Select picture"), pick_image_code);


    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == pick_image_code && resultCode == RESULT_OK && data != null) {
            if (data.getClipData() != null) {
                int countOfImages = data.getClipData().getItemCount();
                for (int i = 0; i < countOfImages; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    String imageString = imageUri.toString();
                    photoProperty.add(imageString);
                    fAddPhotoAdapter.notifyDataSetChanged();
                }
                addPhotoBtn.setVisibility(View.GONE);
                displayPhotoSelectedInRecyclerView();
            } else {
                Uri imageUri = data.getData();
                String imageString = imageUri.toString();
                photoProperty.add(imageString);
                fAddPhotoAdapter.notifyDataSetChanged();
            }
        } else {
            Toast.makeText(this, "You haven't picked any images !", Toast.LENGTH_SHORT).show();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @SuppressLint("NotifyDataSetChanged")
    private void displayPhotoSelectedInRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.form_recyclerView_photo);
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(fAddPhotoAdapter);
        fAddPhotoAdapter.notifyDataSetChanged();
        //ConnectBtn
        if (photoProperty.size() > 0) {
            addNextPhotoBtn.setVisibility(View.VISIBLE);
            addNextPhotoBtn.setOnClickListener(v -> {
                try {
                    addPhoto();
                } catch (IOException pE) {
                    pE.printStackTrace();
                }
            });
        } else {
            addNextPhotoBtn.setVisibility(View.GONE);
        }
    }

    public void connectChip() {
        ChipGroup chipGroup = findViewById(R.id.chip_group_form_list_interest);
        String[] tags;
        for (int i = 0; i < interestList.size(); i++) {
            tags = interestList.get(i).getName().split(" ");
            int interestIcon = interestList.get(i).getIcon();
            @SuppressLint("UseCompatLoadingForDrawables")
            Drawable drawableIcon = getApplicationContext().getDrawable(interestIcon);

            LayoutInflater layoutInflater = LayoutInflater.from(AddPropertyActivity.this);
            //-- For each new tag associate Chip to a text --
            for (String text : tags) {
                @SuppressLint("InflateParams")
                Chip chip = (Chip) layoutInflater.inflate(R.layout.chip_item, null, true);
                chip.setText(text);

                //-- On-click add new Chip --
                chipGroup.addView(chip);
                chip.setCheckable(true);
                chip.setCloseIconVisible(false);
                chip.setChipIcon(drawableIcon);
                chipGroup.setVisibility(View.VISIBLE);

                if (propertyToUpdate != null) {
                    List<String> propertyInterestList = propertyToUpdate.getPropertyInterest();
                    for (int j = 0; j < propertyInterestList.size(); j++) {
                        if (propertyInterestList.get(j).equals(text)) {
                            chip.setChecked(true);
                            if (chip.isChecked()) {
                                selectedInterest.add(text);
                            }
                        }
                    }
                }
                getCheckedChip(chip, text);
            }
        }

    }

    public void getCheckedChip(Chip chip, String text) {
        chip.setOnClickListener(pView -> {
            if (chip.isChecked()) {
                selectedInterest.add(text);
            } else {
                selectedInterest.remove(text);
            }
            for (int i = 0; i < selectedInterest.size(); i++) {
                Log.d("Chip count :", selectedInterest.size() + " items : " + selectedInterest.get(i));
            }
        });

    }

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
                String date = day1 + "/" + month1 + "/" + year1;
                forSaleSinceInput.setText(date);
            }, year, month, day);
            datePickerDialog.show();
        });
        soldSinceInput.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AddPropertyActivity.this, (datePicker, year1, month1, day1) -> {
                month1 += 1;
                String date = day1 + "/" + month1 + "/" + year1;
                soldSinceInput.setText(date);
            }, year, month, day);
            datePickerDialog.show();
        });
    }

    //----------------------
    // VERIFY INPUT METHODS
    //----------------------
    private void verifyTotalAreaInput() {
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

    private void verifyPriceInput() {
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

    private void verifyInputTitleProperty() {
        observeFirebaseProperties();
        propTitleInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence pCharSequence, int pI, int pI1, int pI2) {
            }

            @Override
            public void onTextChanged(CharSequence pCharSequence, int pI, int pI1, int pI2) {
            }

            @Override
            public void afterTextChanged(Editable pEditable) {
                for (PropertyModel property : properties) {
                    Pattern pattern = Pattern.compile(property.getName());
                    if (pattern.matcher(pEditable).matches()) {
                        propTitleInput.setError("This name already exists in database, choose another one");
                        return;
                    }
                }
            }
        });

    }



    // 2 -- Connect graphics data to real data in fragment -->
    @SuppressLint("SetTextI18n")
    private void collectDataFromNewProperty() {
        for (UserModel user : allUsers) {
            if (sellerName.equals(user.getName())) {
                sellerId = user.getId();
            }
            title = Objects.requireNonNull(propTitleInput.getText()).toString();
            type = Objects.requireNonNull(propTypeInput.getText()).toString();
            totalArea = Objects.requireNonNull(propTotalAreaInput.getText()).toString();
            price = Objects.requireNonNull(priceInput.getText()).toString();
            address = Objects.requireNonNull(propAddressInput.getText()).toString();
            saleSince = Objects.requireNonNull(forSaleSinceInput.getText()).toString();
            soldSince = Objects.requireNonNull(soldSinceInput.getText()).toString();
            description = Objects.requireNonNull(descInput.getText()).toString();
            allRooms = totalRooms.getText().toString();
            if (soldSince.equals("")) {
                status = "Available";
            } else {
                status = "Unavailable";
            }

            if (propertyToUpdate == null) {
                propertyModel = new PropertyModel(
                        sellerId, title, type, address,
                        description, totalArea, allRooms,
                        price, status, photoProperty,
                        selectedInterest, saleSince, soldSince);
                observeFirebaseProperties();
                for (PropertyModel property : properties) {
                    if (!property.getName().equals(title)) {
                        addRoomProperty(propertyModel);
                        addFirebaseProperty(propertyModel.getName(), propertyModel);
                    } else {
                        updateRoomProperty(propertyModel);
                        updateFirebaseProperty(propertyModel.getName(), propertyModel);
                    }
                }

                //updateUserAccordingToProperty(sellerName, String.valueOf(propertyModel.getId()));
            } else {
                propertyToUpdate.setUserId(sellerId);
                propertyToUpdate.setName(title);
                propertyToUpdate.setType(type);
                propertyToUpdate.setAddress(address);
                propertyToUpdate.setDescription(description);
                propertyToUpdate.setTotalLeavingArea(totalArea);
                propertyToUpdate.setRooms(allRooms);
                propertyToUpdate.setPrice(price);
                propertyToUpdate.setStatus(status);
                propertyToUpdate.setPhotoProperty(photoProperty);
                propertyToUpdate.setPropertyInterest(selectedInterest);
                propertyToUpdate.setOnSaleDate(saleSince);
                propertyToUpdate.setSoldDate(soldSince);
                updateRoomProperty(propertyToUpdate);
                //updateFirebaseProperty(propertyToUpdate.getName(), propertyToUpdate);
            }
        }
        Intent varIntent = new Intent(this, MainActivity_HomeScreen.class);
        startActivity(varIntent);
    }

    //-----------------------
    // LOCAL ROOM VIEW MODEL
    //-----------------------
    // 1 -- Configure ROOM View Model & Retrieve all properties stuck on phone and last connected User -->
    private void configureLocalRoomViewModel() {
        RoomViewModelFactory roomViewModelFactory = RoomInjection.provideViewModelFactory(this);
        roomViewModel = roomViewModelFactory.create(RoomViewModel.class);
        roomViewModel.initAllUsers();
    }

    private void getAndObserveUsers() {
        this.roomViewModel.getAllUsers().observe(this, this::getRoomUsers);
    }

    public void getRoomUsers(List<UserModel> users) {
        allUsers = new ArrayList<>();
        allUsers.addAll(users);
        initListOfUsers();
    }

    /**
     * Property methods for RoomViewModel.
     */
    private void addRoomProperty(@NonNull PropertyModel pPropertyModel) {
        this.roomViewModel.addProperty(pPropertyModel);
    }

    private void updateRoomProperty(PropertyModel pPropertyModel) {
        this.roomViewModel.updateProperty(pPropertyModel);
    }

    //---------------------
    // FIREBASE VIEW MODEL
    //---------------------
    // 1 -- Configure Firebase View Model & Retrieve all properties and connected User -->
    private void configureFirebaseViewModel() {
        FirebaseViewModelFactory varFirebaseViewModelFactory = FirebaseInjection.provideFirebaseViewModelFactory();
        firebaseViewModel = varFirebaseViewModelFactory.create(FirebaseViewModel.class);
        firebaseViewModel.retrieveAllUsers();
        firebaseViewModel.retrieveAllProperties();
    }

    // 2 -- Get, update & observe users changes -->
    private void getAndObserveFirebaseUsers() {
        this.firebaseViewModel.getAllUsers().observe(this, this::getFirebaseUsers);
    }

    // 2a -- Get & update users changes -->
    private void getFirebaseUsers(List<UserModel> users) {
        allUsers.addAll(users);
        initListOfUsers();
    }

    // 2 -- Get, update & observe properties changes, by paying attention to the adapter -->
    private void observeFirebaseProperties() {
        firebaseViewModel.getAllProperties().observe(this, pPropertyModels -> {
            //--:: Attach the adapter to the recycler view to inflate items ::--
            properties.addAll(pPropertyModels);
        });
    }

    //Get firebase properties
    //Add all in a list
    // if one of the property as document has the same name then one
    // that is in the collection so update property, if else create

    /**
     * Property methods for FirebaseViewModel.
     */
    private void addFirebaseProperty(String propertyName, PropertyModel pPropertyModel) {
        this.firebaseViewModel.createProperty(propertyName, pPropertyModel);
    }

    private void updateFirebaseProperty(String propertyName, PropertyModel pPropertyModel) {
        this.firebaseViewModel.updateProperty(propertyName, pPropertyModel);
    }

    private void updateUserAccordingToProperty(String userId, String propertyId) {
        this.firebaseViewModel.updateUser(userId, propertyId);
    }

    //-------------------
    // INIT DATA METHODS
    //-------------------
    private void initListOfUsers() {
        for (UserModel user : allUsers) {
            String userName = user.getName();
            usersNameList.add(userName);

            //-- Find user position in Drop down list --
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_item, usersNameList);
            atvUserInput.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();

            atvUserInput.setOnItemClickListener((adapterView, view, position, l) -> {
                sellerName = arrayAdapter.getItem(position);
                //-- get user for propertyToUpdate --
            });
        }
        if (propertyToUpdate != null) {
            sellerIdForPropertyToUpdate = propertyToUpdate.getUserId();
            for (UserModel user : allUsers) {
                if (user.getId().equals(sellerIdForPropertyToUpdate)) {
                    sellerName = user.getName();
                    atvUserInput.setText(sellerName);
                }
            }
        }
    }
}
