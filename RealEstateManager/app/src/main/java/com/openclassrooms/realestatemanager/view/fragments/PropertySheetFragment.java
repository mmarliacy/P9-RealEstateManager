package com.openclassrooms.realestatemanager.view.fragments;

import static com.openclassrooms.realestatemanager.view.activities.MainActivity_HomeScreen.api_key;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.openclassrooms.realestatemanager.MVVM.injection.firebase.FirebaseInjection;
import com.openclassrooms.realestatemanager.MVVM.injection.firebase.FirebaseViewModelFactory;
import com.openclassrooms.realestatemanager.MVVM.injection.room.RoomInjection;
import com.openclassrooms.realestatemanager.MVVM.injection.room.RoomViewModelFactory;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.adapters.InterestListAdapter;
import com.openclassrooms.realestatemanager.adapters.PhotoListAdapter;
import com.openclassrooms.realestatemanager.model.PropertyModel;
import com.openclassrooms.realestatemanager.model.UserModel;
import com.openclassrooms.realestatemanager.view.viewmodel.FirebaseViewModel;
import com.openclassrooms.realestatemanager.view.viewmodel.RoomViewModel;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PropertySheetFragment extends Fragment {

    //---------------
    // DATA - FIELDS
    //---------------
    /**
     * Data
     */
    public static PropertyModel property;
    static List<UserModel> allUsers ;

    /**
     * LIVE DATA - VIEW MODELS
     */
    RoomViewModel roomViewModel;
    FirebaseViewModel firebaseViewModel;

    /**
     * Graphics
     */
    TextView sellerName;
    TextView propertyCost;
    TextView propertyStatus;
    TextView propertyFirstProperties;
    TextView propertyAddress;
    TextView propertyDescription;
    TextView propertyForSale;
    TextView propertySold;
    ImageView mapContainer;

    //-------------------
    // FRAGMENT INSTANCE
    //-------------------
    // -- Create static method getInstance() & get Parcelable property data by Bundle -->
    public static PropertySheetFragment getInstance(PropertyModel pProperty) {
        property = pProperty;
        PropertySheetFragment varPropertySheetFragment = new PropertySheetFragment();
        Bundle propertyData = new Bundle();
        propertyData.putParcelable("property", pProperty);
        varPropertySheetFragment.setArguments(propertyData);
        return varPropertySheetFragment;
    }

    //-----------
    // LIFECYCLE
    //-----------
    // 1 -- ON CREATE VIEW -->
    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.real_estate_sheet_layout, container, false);
        bindView(view);
        configureViewModels();
        if (roomViewModel != null) {
            getRoomPropertiesAndUsers();
        } else {
            getFirebasePropertiesAndUsers();
        }
        configureToolbar(view, property);
        configureViewPager(view, property);
        this.configureInterestRecyclerView(view, property);
        connectViewToData(property);
        return view;
    }


    //----------------------
    // BINDING VIEWS & DATA
    //----------------------
    // 1 -- Reference views to graphics data in fragment -->
    private void bindView(View pView) {
        sellerName = pView.findViewById(R.id.seller_name);
        propertyCost = pView.findViewById(R.id.sheet_prop_cost);
        propertyStatus = pView.findViewById(R.id.sheet_prop_status);
        propertyFirstProperties = pView.findViewById(R.id.sheet_prop_properties);
        propertyAddress = pView.findViewById(R.id.sheet_prop_address);
        propertyDescription = pView.findViewById(R.id.sheet_prop_description);
        propertyForSale = pView.findViewById(R.id.property_for_sale_since);
        propertySold = pView.findViewById(R.id.property_sold_since);
        mapContainer = pView.findViewById(R.id.container_static_maps);
    }

    // 2 -- Connect graphics data to real data in fragment -->
    @SuppressLint("SetTextI18n")
    private void connectViewToData(PropertyModel pPropertyModel) {
        definePropertyStatus(pPropertyModel);
        defineMapPicture(pPropertyModel);
        propertyCost.setText("" + pPropertyModel.getPrice() + " $");
        propertyFirstProperties.setText(
                pPropertyModel.getType()
                        + " - "
                        + pPropertyModel.getRooms() + " P"
                        + " - "
                        + pPropertyModel.getTotalLeavingArea() + " m²");
        propertyAddress.setText(pPropertyModel.getAddress());
        propertyDescription.setText(pPropertyModel.getDescription());
        propertyForSale.setText(pPropertyModel.getOnSaleDate());
        propertySold.setText(pPropertyModel.getSoldDate());

    }

    // 3a -- Define seller name according to id -->
    @SuppressLint("SetTextI18n")
    private void defineUserName(PropertyModel pPropertyModel) {
        for (UserModel user : allUsers) {
            if (Objects.equals(user.getId(), pPropertyModel.getUserId())) {
                sellerName.setText("By " + user.getName());
                return;
            } else {
                sellerName.setText("By Unknown Seller");
            }
        }
    }

    // 3b -- Define status behaviour -->
    private void definePropertyStatus(PropertyModel pPropertyModel) {
        if (pPropertyModel.getStatus().matches(getString(R.string.available_status))) {
            propertyStatus.setTextColor(requireActivity().getResources().getColor(R.color.grass_green));
        } else {
            propertyStatus.setTextColor(requireActivity().getResources().getColor(R.color.chain_grey));
        }
        propertyStatus.setText(pPropertyModel.getStatus());
    }

    private void defineMapPicture(PropertyModel pPropertyModel) {
        try {
            String BASE_URL = "https://maps.googleapis.com/maps/";
            String url = BASE_URL + "api/staticmap?center=" + pPropertyModel.getAddress() + "&zoom=" + 14 + "&size=400x400" + "&key=" + api_key;
            Picasso.get().load(url).into(mapContainer);
        } catch (Exception pException) {
            pException.printStackTrace();
        }
    }

    //-----------------
    // TOOLBAR & TITLE
    //-----------------
    private void configureToolbar(View pView, PropertyModel pPropertyModel) {
        Toolbar fToolbar = pView.findViewById(R.id.sheet_toolbar);
        requireActivity().setActionBar(fToolbar);
        fToolbar.setTitle(pPropertyModel.getName());
    }

    //------------
    // VIEW PAGER
    //------------
    private void configureViewPager(View pView, PropertyModel property) {
        ViewPager2 fViewPager = pView.findViewById(R.id.sheet_view_pager_photo);
        PhotoListAdapter photoListAdapter = new PhotoListAdapter(property.getPhotoProperty(), fViewPager);
        fViewPager.setAdapter(photoListAdapter);
    }

    //-----------------------
    // INTEREST RECYCLERVIEW
    //-----------------------
    // -- Configure recyclerview -->
    private void configureInterestRecyclerView(View view, PropertyModel pProperty) {
        //--:: Set layout Manager to set the items in position ::--
        RecyclerView propertyInterestRecyclerView = view.findViewById(R.id.sheet_prop_interest_recyclerView);
        propertyInterestRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false));
        InterestListAdapter adapter = new InterestListAdapter(pProperty);
        propertyInterestRecyclerView.setAdapter(adapter);
    }
    //----------------------------------
    // LOCAL ROOM & FIREBASE VIEW MODEL
    //----------------------------------
    // 1 -- Configure ROOM & FIREBASE View Model  -->
    private void configureViewModels() {
        // -- ROOM View Model  --
        RoomViewModelFactory roomViewModelFactory = RoomInjection.provideViewModelFactory(requireActivity());
        roomViewModel = roomViewModelFactory.create(RoomViewModel.class);
        roomViewModel.initAllUsers();
        roomViewModel.initAllProperties();
        // -- FIREBASE View Model  --
        FirebaseViewModelFactory varFirebaseViewModelFactory = FirebaseInjection.provideFirebaseViewModelFactory();
        firebaseViewModel = varFirebaseViewModelFactory.create(FirebaseViewModel.class);
        firebaseViewModel.retrieveAllUsers();
        firebaseViewModel.retrieveAllProperties();
    }

    // 2a -- Get & observe ROOM USERS -->
    private void getRoomPropertiesAndUsers() {
        // -- ROOM Users --
        this.roomViewModel.getAllUsers().observe(requireActivity(), users -> {
            allUsers = new ArrayList<>();
            allUsers.addAll(users);
            defineUserName(property);
        });

    }

    // 2b -- Get & observe FIREBASE USERS  -->
    private void getFirebasePropertiesAndUsers() {
        // -- FIREBASE Users --
        this.firebaseViewModel.getAllUsers().observe(requireActivity(), users -> {
            allUsers = new ArrayList<>();
            allUsers.addAll(users);
            defineUserName(property);
        });
    }

}
