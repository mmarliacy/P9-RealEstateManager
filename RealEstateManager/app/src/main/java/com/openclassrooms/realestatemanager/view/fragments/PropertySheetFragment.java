package com.openclassrooms.realestatemanager.view.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.adapters.InterestListAdapter;
import com.openclassrooms.realestatemanager.adapters.PhotoListAdapter;
import com.openclassrooms.realestatemanager.model.PropertyModel;
import com.openclassrooms.realestatemanager.model.UserModel;

import org.jetbrains.annotations.NotNull;

public class PropertySheetFragment extends Fragment {

    //---------------
    // DATA - FIELDS
    //---------------
    /**
     * Data
     */
    private static PropertyModel property;

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
        connectViewToData(property);
        configureToolbar(view, property);
        configureViewPager(view, property);
        this.configureInterestRecyclerView(view, property);
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
    }

    // 2 -- Connect graphics data to real data in fragment -->
    @SuppressLint("SetTextI18n")
    private void connectViewToData(PropertyModel pPropertyModel) {
        Log.d("Property photo is ", pPropertyModel.getPhotoProperty().get(0));
        defineUserName(pPropertyModel);
        definePropertyStatus(pPropertyModel);
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

    // 3 -- Logics methods for other graphic data -->

    // 3a -- Define seller name according to id -->
    private void defineUserName(PropertyModel pPropertyModel) {
        UserModel user = pPropertyModel.getUser();
        if (user != null) {
            sellerName.setText(user.getName());
        } else {
            sellerName.setText(String.valueOf(pPropertyModel.getUserId()));
        }
    }

    // 3b -- Define status behaviour -->
    private void definePropertyStatus(PropertyModel pPropertyModel) {
        if (pPropertyModel.getStatus().matches(getString(R.string.available_status))) {
            propertyStatus.setTextColor(requireActivity().getResources().getColor(R.color.grass_green));
        } else {
            propertyStatus.setTextColor(requireActivity().getResources().getColor(R.color.red));
        }
        propertyStatus.setText(pPropertyModel.getStatus());
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
        Log.d("Photo is ", property.getPhotoProperty().get(0));
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
}
