package com.openclassrooms.realestatemanager.view.activities;

import static com.openclassrooms.realestatemanager.model.DummyListCallback.getInterestList;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
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

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    private TextInputEditText propTitleInput;
    private TextInputEditText propTotalAreaInput;
    private TextInputEditText propAddressInput;
    private TextInputEditText forSaleSinceInput;
    private TextInputEditText soldSinceInput;
    private TextInputEditText descInput;
    private TextInputEditText priceInput;
    private TextView totalRooms;
    private AutoCompleteTextView propTypeInput;
    private AutoCompleteTextView atvUserInput;
    private ImageButton confirmBtn;
    private ImageButton cancelBtn;
    private ImageButton addPhotoBtn;
    private ImageButton addNextPhotoBtn;
    private Button moreBtn;
    private Button lessBtn;
    RecyclerView recyclerView;

    /**
     * Data
     */
    // -- List -->
    private final List<UserModel> users = new ArrayList<>();
    private final List<PropertyModel> properties = new ArrayList<>();
    private final List<String> usersNameList = new ArrayList<>();
    private final List<String> photoProperty = new ArrayList<>();
    private final List<InterestModel> interestList = getInterestList();
    private final List<String> selectedInterest = new ArrayList<>();

    // -- Variables -->
    private int rooms;
    private String sellerId;
    private String sellerName;
    private String propertyName;
    private String type;
    private String totalArea;
    private String address;
    private String saleSince;
    private String soldSince;
    private String description;
    private String allRooms;
    private String price;
    private String status;
    private String propertyNameToUpdate;
    private PropertyModel propertyToUpdate;
    private PropertyModel propertyModel;
    private final AddPhotoAdapter fAddPhotoAdapter = new AddPhotoAdapter(photoProperty);
    private ActivityResultLauncher<String> resultPhoto;
    private ActivityResultLauncher<Intent> resultTakePhoto;
    private static final int read_permission_code = 101;

    /**
     * LIVE DATA - VIEW MODELS
     */
    RoomViewModel roomViewModel;
    FirebaseViewModel firebaseViewModel;

    //-----------
    // LIFECYCLE
    //-----------
    // 1 -- ON CREATE -->
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_property_layout);
        // -- Configure View Models -->
        configureViewModels();
        if (roomViewModel != null) {
            getRoomPropertiesAndUsers();
        } else {
            getFirebasePropertiesAndUsers();
        }
        defineViews();
        setAddPhotoLogic();
        takePhotos();
        getPhotos();
        // -- Configure Other methods -->
        verifyIfPropertyToUpdateExist();
        setConfirmAndCancelBtn();
        setMoreAndLessBtn();
        verifyInputData();
        initChip();
        initTypeList();
    }

    //----------------------------------
    // LOCAL ROOM & FIREBASE VIEW MODEL
    //----------------------------------
    // 1 -- Configure ROOM & FIREBASE View Model  -->
    private void configureViewModels() {
        // -- ROOM View Model  --
        RoomViewModelFactory roomViewModelFactory = RoomInjection.provideViewModelFactory(this);
        roomViewModel = roomViewModelFactory.create(RoomViewModel.class);
        roomViewModel.initAllUsers();
        roomViewModel.initAllProperties();
        // -- FIREBASE View Model  --
        FirebaseViewModelFactory varFirebaseViewModelFactory = FirebaseInjection.provideFirebaseViewModelFactory();
        firebaseViewModel = varFirebaseViewModelFactory.create(FirebaseViewModel.class);
        firebaseViewModel.retrieveAllUsers();
        firebaseViewModel.retrieveAllProperties();
    }

    // 2 -- Get & observe ROOM PROPERTIES & USERS -->
    private void getRoomPropertiesAndUsers() {
        // -- ROOM Properties --
        this.roomViewModel.getAllProperties().observe(this, pPropertyModels -> {
            properties.clear();
            properties.addAll(pPropertyModels);
        });
        // -- ROOM Users --
        this.roomViewModel.getAllUsers().observe(this, users -> {
            this.users.addAll(users);
            initUsersList();
        });

    }

    // 2a -- Get & observe FIREBASE PROPERTIES & USERS  -->
    private void getFirebasePropertiesAndUsers() {
        // -- FIREBASE Properties --
        this.firebaseViewModel.getAllProperties().observe(this, pPropertyModels -> {
            properties.clear();
            properties.addAll(pPropertyModels);
        });
        // -- FIREBASE Users --
        this.firebaseViewModel.getAllUsers().observe(this, users -> {
            this.users.addAll(users);
            initUsersList();
        });
    }

    // 4 -- Add ROOM/FIREBASE Property -->
    private void addProperty(@NonNull PropertyModel pPropertyModel) {
        // -- Add property in ROOM Database --
        this.roomViewModel.addProperty(pPropertyModel);
        // -- Add property in FIREBASE Database & Set propertyId in Firebase --
        this.firebaseViewModel.createProperty(pPropertyModel);
        this.firebaseViewModel.setIdOfProperty(pPropertyModel);
    }

    // 5 -- Update ROOM Property -->
    private void updateProperty(String propertyId, PropertyModel property) {
        // -- Update property in ROOM Database --
        this.roomViewModel.updateProperty(property);
        // -- Update property in FIREBASE Database --
        this.firebaseViewModel.updateProperty(propertyId, property);
        this.firebaseViewModel.setIdOfProperty(property);
    }

    //----------------------
    // BINDING VIEWS & DATA
    //----------------------
    // 1 -- Reference views to graphics data in activity -->
    private void defineViews() {
        recyclerView = findViewById(R.id.form_recyclerView_photo);
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
    private void setConfirmAndCancelBtn() {
        confirmBtn.setOnClickListener(pView -> createNewOrUpdateProperty());
        cancelBtn.setOnClickListener(v -> startActivity(new Intent(this, MainActivity_HomeScreen.class)));
    }

    private void setMoreAndLessBtn() {
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

    //-------------------
    // INIT DATA METHODS
    //-------------------
    // 1 -- Set users' list -->
    private void initUsersList() {
        for (UserModel user : users) {
            String userName = user.getName();
            usersNameList.add(userName);
            //-- Find user position in Drop down list --
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_user_seller, usersNameList);
            atvUserInput.setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();
            atvUserInput.setOnItemClickListener((adapterView, view, position, l) -> sellerName = arrayAdapter.getItem(position));
        }
        //-- Get user for propertyToUpdate if possible --
        if (propertyToUpdate != null) {
            String propertyToUpdateSellerId = propertyToUpdate.getUserId();
            for (UserModel user : users) {
                if (user.getId().equals(propertyToUpdateSellerId)) {
                    sellerName = user.getName();
                    atvUserInput.setText(sellerName);
                }
            }
        }
    }

    // 2 -- Set types' list -->
    private void initTypeList() {
        List<String> typeList = new ArrayList<>(Arrays.asList("House", "Apartment", "Penthouse", "Villa", "Chalet", "Mobil-home", "Building", "Castle", "Loft"));
        //-- Find user position in Drop down list --
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_type, typeList);
        propTypeInput.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
        propTypeInput.setOnItemClickListener((adapterView, view, position, l) -> type = arrayAdapter.getItem(position));
        //-- Get type for propertyToUpdate if possible --
        if (propertyToUpdate != null) {
            String typePropertyToUpdate = propertyToUpdate.getType();
            propTypeInput.setText(typePropertyToUpdate);
        }
    }

    // 3 -- Init & Get Interest List (checked or not) in ChipGroup -->
    @SuppressLint({"UseCompatLoadingForDrawables", "InflateParams"})
    public void initChip() {
        ChipGroup chipGroup = findViewById(R.id.chip_group_form_list_interest);
        String[] tags;
        //-- Split all interest into tags --
        for (int i = 0; i < interestList.size(); i++) {
            tags = interestList.get(i).getName().split(" ");
            //-- Set interest icon --
            int interestIcon = interestList.get(i).getIcon();
            Drawable drawableIcon = getApplicationContext().getDrawable(interestIcon);
            //-- For each tag associate chip to an interest --
            LayoutInflater layoutInflater = LayoutInflater.from(AddPropertyActivity.this);
            for (String text : tags) {
                Chip chip = (Chip) layoutInflater.inflate(R.layout.chip_item, null, true);
                chip.setText(text);
                chipGroup.addView(chip);
                //-- Set chip params --
                chip.setCheckable(true);
                chip.setCloseIconVisible(false);
                chip.setChipIcon(drawableIcon);
                chipGroup.setVisibility(View.VISIBLE);
                //-- Get chip list of property --
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
                //-- Set checked chip logic --
                setCheckedChip(chip, text);
            }
        }
    }

    // 3a -- Set checked Interest List of chip in ChipGroup -->
    public void setCheckedChip(Chip chip, String text) {
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

    // 4 -- Init & get date for sold and onSale date property -->
    private void initDatePicker() {
        Calendar cal = Calendar.getInstance();
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        forSaleSinceInput.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AddPropertyActivity.this, (datePicker, year1, month1, day1) -> {
                month1 += 1;
                if (day1 < 10) {
                    String day2 = "0" + day1;
                    String date = day2 + "/" + month1 + "/" + year1;
                    forSaleSinceInput.setText(date);
                } else {
                    String date = day1 + "/" + month1 + "/" + year1;
                    forSaleSinceInput.setText(date);
                }
            }, year, month, day);
            datePickerDialog.show();
        });
        soldSinceInput.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    AddPropertyActivity.this, (datePicker, year1, month1, day1) -> {
                month1 += 1;
                if (day1 < 10) {
                    String day2 = "0" + day1;
                    String date = day2 + "/" + month1 + "/" + year1;
                    soldSinceInput.setText(date);
                } else {
                    String date = day1 + "/" + month1 + "/" + year1;
                    soldSinceInput.setText(date);
                }
            }, year, month, day);
            datePickerDialog.show();
        });
    }

    //---------------------
    // PHOTOS RECYCLERVIEW
    //---------------------
    // 1 -- Set logic for adding new photos for property in database -->
    public void setAddPhotoLogic() {
        // -- Configure place holder --
        LinearLayout varLinearLayout = findViewById(R.id.form_property_photos_linearLayout);
        LayoutInflater varInflater = (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = varInflater.inflate(R.layout.photo_add_first_item, varLinearLayout, true);
        addPhotoBtn = view.findViewById(R.id.first_item);
        ImageButton takePhotoBtn = findViewById(R.id.takePhoto);
        if (photoProperty.size() == 0) {
            // -- Make add button appear --
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
                takePhotoBtn.setOnClickListener(v-> getCameraToTakePhotos());
            }
        }
    }

    // 2 -- Set recycler view configuration and display photo list -->
    @RequiresApi(api = Build.VERSION_CODES.Q)
    @SuppressLint("NotifyDataSetChanged")
    private void displayPhotoSelectedInRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(fAddPhotoAdapter);
        fAddPhotoAdapter.notifyDataSetChanged();
        //ConnectBtn
        if (photoProperty.size() > 0) {
            addPhotoBtn.setVisibility(View.GONE);
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

    //--------
    // ADD PHOTOS PICKED FROM GALLERY
    //----------------------------------
    // 1 -- Require permission to access data phone & Get access to phone data -->
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void addPhoto() throws IOException {
        if (ContextCompat.checkSelfPermission(AddPropertyActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddPropertyActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, read_permission_code);
        }
        // -- Create intent to get one or many pictures --
        resultPhoto.launch("image/*");
    }

    // 2 -- Get photos & Add it to photos list -->
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void getPhotos(){
        resultPhoto = registerForActivityResult(
                new ActivityResultContracts.GetMultipleContents(),
                result -> {
                    if (photoProperty.isEmpty()) {
                        if (result != null) {
                            int countOfImages = result.size();
                            for (int i = 0; i < countOfImages; i++) {
                                Uri imageUri = result.get(i);
                                String imageString = imageUri.toString();
                                photoProperty.add(imageString);
                            }
                        } else
                            Log.i("Loaded photos", "You haven't picked any images !");
                    } else {
                        int countOfImages = result.size();
                        for (int i = 0; i < countOfImages; i++) {
                            Uri imageUri = result.get(i);
                            String imageString = imageUri.toString();
                            photoProperty.add(imageString);
                        }
                    }
                    addPhotoBtn.setVisibility(View.GONE);
                    displayPhotoSelectedInRecyclerView();
                }
        );
    }

    //--------------------------
    // GET CAMERA & TAKE PHOTOS
    //----------------------------
    // 1 -- Require permission to access data phone & Get access to phone camera -->
    private void getCameraToTakePhotos(){
        if (ContextCompat.checkSelfPermission(AddPropertyActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(AddPropertyActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, read_permission_code);
        }
        // -- Create intent to get take photos --
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        resultTakePhoto.launch(intent);
    }

    // 2 -- Take photo & Add it to photos list -->
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void takePhotos(){
        resultTakePhoto = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                        if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                            Bundle bundle = result.getData().getExtras();
                            Bitmap bitmap = (Bitmap) bundle.get("data");
                            Uri imageUri = getImageUri(this,bitmap);
                            String uriString = imageUri.toString();
                            photoProperty.add(uriString);
                        }
                    displayPhotoSelectedInRecyclerView();
                });
    }

    private Uri getImageUri(Context context, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(context.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    //----------------------
    // VERIFY INPUT METHODS
    //----------------------
    // 1 -- Generic method to verify inputs in form -->
    public void verifyInputData() {
        verifyTotalAreaInput();
        verifyPriceInput();
        verifyInputTitleProperty();
        initDatePicker();
    }

    // 2 -- Verify total area input -->
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
                    propTotalAreaInput.setError("Write only numbers in that section");
                } else {
                    propTotalAreaLayout.setErrorEnabled(false);
                }
            }
        });
    }

    // 3 -- Verify price input -->
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
                } else {
                    propPriceLayout.setErrorEnabled(false);
                }
            }
        });
    }

    // 4 -- Verify title/propertyName input -->
    private void verifyInputTitleProperty() {
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

    //------------------------------
    // IN CASE OF : UPDATE PROPERTY
    //------------------------------
    // 1 -- Verify if Property to update is not null -->
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void verifyIfPropertyToUpdateExist() {
        if (getIntent().hasExtra("propertyToUpdate")) {
            propertyToUpdate = getIntent().getParcelableExtra("propertyToUpdate");
            propertyNameToUpdate = getIntent().getStringExtra("propertyNameToUpdate");
            definePropertyToUpdate();
        }
    }

    // 2 -- Define in views property to update -->
    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void definePropertyToUpdate() {
        if (propertyToUpdate != null) {
            atvUserInput.setText(sellerName);
            propTitleInput.setText(propertyToUpdate.getName());
            propTypeInput.setText(propertyToUpdate.getType());
            propTotalAreaInput.setText(propertyToUpdate.getTotalLeavingArea());
            priceInput.setText(propertyToUpdate.getPrice());
            photoProperty.addAll(propertyToUpdate.getPhotoProperty());
            displayPhotoSelectedInRecyclerView();
            propAddressInput.setText(propertyToUpdate.getAddress());
            forSaleSinceInput.setText(propertyToUpdate.getOnSaleDate());
            soldSinceInput.setText(propertyToUpdate.getSoldDate());
            descInput.setText(propertyToUpdate.getDescription());
            totalRooms.setText(propertyToUpdate.getRooms());
        }
    }

    //--------------------------
    // CREATE / UPDATE PROPERTY
    //--------------------------
    // 1 -- Apply changes or create new Property and return to Main Activity -->
    @SuppressLint("SetTextI18n")
    private void createNewOrUpdateProperty() {
        connectGraphicsToData();
        if (propertyToUpdate == null) {
            setNewProperty();
            addProperty(propertyModel);
        } else {
            setChangeOnPropertyToUpdate();
            updateProperty(propertyNameToUpdate, propertyToUpdate);
        }
        Intent varIntent = new Intent(this, MainActivity_HomeScreen.class);
        startActivity(varIntent);
    }

    // 2 -- Connect graphics data to real data in fragment -->
    private void connectGraphicsToData() {
        // -- Set seller Id --
        for (UserModel user : users) {
            if (sellerName.equals(user.getName())) {
                sellerId = user.getId();
            }
            propertyName = Objects.requireNonNull(propTitleInput.getText()).toString();
            type = Objects.requireNonNull(propTypeInput.getText()).toString();
            totalArea = Objects.requireNonNull(propTotalAreaInput.getText()).toString();
            price = Objects.requireNonNull(priceInput.getText()).toString();
            address = Objects.requireNonNull(propAddressInput.getText()).toString();
            saleSince = Objects.requireNonNull(forSaleSinceInput.getText()).toString();
            soldSince = Objects.requireNonNull(soldSinceInput.getText()).toString();
            description = Objects.requireNonNull(descInput.getText()).toString();
            allRooms = totalRooms.getText().toString();
            // -- Set status of the property according to date --
            if (soldSince.equals("")) {
                status = "Available";
            } else {
                status = "Unavailable";
            }
        }
    }

    // 3 -- Set Property to create in both database -->
    private void setNewProperty() {
        if (propertyToUpdate == null) {
            propertyModel = new PropertyModel(
                    sellerId, propertyName, type, address,
                    description, totalArea, allRooms,
                    price, status, photoProperty,
                    selectedInterest, saleSince, soldSince);
        }
    }

    // 4 -- Set changes on Property to update in both database -->
    public void setChangeOnPropertyToUpdate() {
        propertyToUpdate.setUserId(sellerId);
        propertyToUpdate.setName(propertyName);
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
    }
}