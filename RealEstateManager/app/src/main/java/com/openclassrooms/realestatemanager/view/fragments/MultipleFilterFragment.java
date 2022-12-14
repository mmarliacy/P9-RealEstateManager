package com.openclassrooms.realestatemanager.view.fragments;

import static com.openclassrooms.realestatemanager.view.fragments.PropertyListFragment.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.openclassrooms.realestatemanager.MVVM.injection.room.RoomInjection;
import com.openclassrooms.realestatemanager.MVVM.injection.room.RoomViewModelFactory;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.PropertyModel;
import com.openclassrooms.realestatemanager.view.viewmodel.RoomViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class MultipleFilterFragment extends BottomSheetDialogFragment {

    //---------------
    // DATA - FIELDS
    //---------------
    /**
     * Graphics
     */
    private AutoCompleteTextView typeInput;
    private TextInputEditText roomsMin;
    private TextInputEditText areaMinInput;
    private TextInputEditText areaMaxInput;
    private TextInputEditText priceMinInput;
    private TextInputEditText priceMaxInput;
    private CheckBox sold;
    private Button search;

    /**
     * DATA
     */
    // -- List -->
    List<PropertyModel> properties = new ArrayList<>();

    // -- Variables -->
    private String type;
    private String minRooms;
    private String minArea;
    private String maxArea;
    private String minPrice;
    private String maxPrice;
    private String status;

    /**
     * VIEW MODELS
     */
    RoomViewModel roomViewModel;

    /**
     * CONSTRUCTOR
     */
    public static Fragment getInstance() {
        return new MultipleFilterFragment();
    }

    //-----------
    // LIFECYCLE
    //-----------
    // 1 -- ON CREATE VIEW -->
    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        //--:: Set View & RecyclerView with Options (Layout Manager) ::--
        View view = inflater.inflate(R.layout.filter_search_fragment, container, false);
        defineViews(view);
        configureViewModel();
        getRoomProperties();
        initTypeList();
        configureSearchBtn();
        return view;
    }

    //----------------------
    // BINDING VIEWS & DATA
    //----------------------
    // 1 -- Reference views to graphics data in fragment -->
    private void defineViews(View view) {
        typeInput = view.findViewById(R.id.filter_type_input);
        roomsMin = view.findViewById(R.id.filter_rooms_min_input);
        areaMinInput = view.findViewById(R.id.filter_area_min_input);
        areaMaxInput = view.findViewById(R.id.filter_area_max_input);
        priceMinInput = view.findViewById(R.id.filter_price_min_input);
        priceMaxInput = view.findViewById(R.id.filter_price_max_input);
        sold = view.findViewById(R.id.filter_sold_input);
        search = view.findViewById(R.id.button_search);
    }

    // 2 -- Set type'sSantana house list -->
    private void initTypeList() {
        List<String> typeList = new ArrayList<>(Arrays.asList("House", "Apartment", "Penthouse", "Villa", "Chalet", "Mobil-home", "Building", "Castle", "Loft"));
        //-- Find user position in Drop down list --
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(requireActivity(), R.layout.dropdown_menu_type, typeList);
        typeInput.setAdapter(arrayAdapter);
        arrayAdapter.notifyDataSetChanged();
        typeInput.setOnItemClickListener((adapterView, view, position, l) -> type = arrayAdapter.getItem(position));
    }

    //------------
    // LOCAL ROOM
    //------------
    // 1 -- Configure ROOM View Model  -->
    private void configureViewModel() {
        // -- ROOM View Model  --
        RoomViewModelFactory roomViewModelFactory = RoomInjection.provideViewModelFactory(requireActivity());
        roomViewModel = roomViewModelFactory.create(RoomViewModel.class);
        roomViewModel.initAllUsers();
        roomViewModel.initAllProperties();
    }

    // 2 -- Get & observe ROOM PROPERTIES  -->
    private void getRoomProperties() {
        this.roomViewModel.getAllProperties().observe(requireActivity(), pPropertyModels -> {
            properties.clear();
            properties.addAll(pPropertyModels);
        });
    }

    //---------------
    // SEARCH FILTER
    //---------------
    // 1 -- Get filtered elements on click  -->
    private void configureSearchBtn() {
        search.setOnClickListener(pView -> connectGraphicsToData());
    }

    // 2 -- Connect graphics data to real data in fragment -->
    private void connectGraphicsToData() {
        minRooms = Objects.requireNonNull(roomsMin.getText()).toString();
        minArea = Objects.requireNonNull(areaMinInput.getText()).toString();
        maxArea = Objects.requireNonNull(areaMaxInput.getText()).toString();
        minPrice = Objects.requireNonNull(priceMinInput.getText()).toString();
        maxPrice = Objects.requireNonNull(priceMaxInput.getText()).toString();
        if (sold.isChecked()){
            status = "Unavailable";
        } else {
            status = "Available";
        }
        verifyData();
    }

    // 3 -- Verify data parameters -->
    private void verifyData() {
        if (minRooms.equals("")) {
            minRooms = "0";
        }
        if (minArea.equals("")) {
            minArea = "0";
        }
        if (maxArea.equals("")) {
            areaMaxInput.setError("Missing");
            return;
        }
        if (minPrice.equals("")) {
            minPrice = "0";
        }
        if (maxPrice.equals("")) {
            typeInput.setError("Missing");
            return;
        }

        if (typeInput.getText().toString().equals("")) {
            typeInput.setError("Missing");
            return;
        }
        applyFilter();
    }

    // 4 -- See the result on list according to parameters -->
    private void applyFilter(){
        roomViewModel.getAllPropertiesFiltered(type, Integer.parseInt(minRooms), Integer.parseInt(minArea), Integer.parseInt(maxArea), Integer.parseInt(minPrice),
                Integer.parseInt(maxPrice), status).observe(requireActivity(), pPropertyModels -> {
            properties.clear();
            properties.addAll(pPropertyModels);
            adapter.updateProperties(properties);
        });
    }
}
