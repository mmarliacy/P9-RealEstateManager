package com.openclassrooms.realestatemanager.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.adapters.PropertyListAdapter;
import com.openclassrooms.realestatemanager.MVVM.injection.firebase.FirebaseViewModelFactory;
import com.openclassrooms.realestatemanager.MVVM.injection.firebase.FirebaseInjection;
import com.openclassrooms.realestatemanager.view.activities.AddPropertyActivity;
import com.openclassrooms.realestatemanager.view.viewmodel.FirebaseViewModel;
import com.openclassrooms.realestatemanager.MVVM.injection.room.RoomViewModelFactory;
import com.openclassrooms.realestatemanager.MVVM.injection.room.RoomInjection;
import com.openclassrooms.realestatemanager.view.viewmodel.RoomViewModel;
import com.openclassrooms.realestatemanager.model.PropertyModel;
import org.jetbrains.annotations.NotNull;

public class PropertyListFragment extends Fragment implements PropertyListAdapter.OnItemClickListener{

    //---------------
    // DATA - FIELDS
    //---------------
    /** Graphics */
    RecyclerView propertiesRecyclerView;
    /** LIVE DATA - VIEW MODELS */
    FirebaseViewModel varFirebaseViewModel;
    RoomViewModel roomViewModel;
    /** Tools */
    PropertyListAdapter adapter;

    /** CONSTRUCTOR */
    public static Fragment getInstance() {
        return new PropertyListFragment();
    }

    //-----------
    // LIFECYCLE
    //-----------
    // 1 -- ON CREATE VIEW -->
    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //--:: Set View & RecyclerView with Options (Layout Manager) ::--
        View view = inflater.inflate(R.layout.property_list_home_screen, container, false);
        configureToolbar(view);
        addButtonClick(view);
        this.configureRecyclerView(view);
        //--:: Firebase View Model Methods ::--
         //configureFirebaseViewModel();
         //observeFirebaseProperties();
        //--:: Local ROOM View Model Methods ::--
        configureLocalRoomViewModel();
        getAndObserveProperties();
        return view;
    }

    // 2 -- ON STOP -->
    @Override
    public void onStop() {
        super.onStop();
        //--:: Local ROOM View Model Methods ::--
    }

    //-----------------
    // TOOLBAR & TITLE
    //-----------------
    private void configureToolbar(View pView) {
        Toolbar fToolbar = pView.findViewById(R.id.list_toolbar);
        requireActivity().setActionBar(fToolbar);
    }

    //-------------------
    // HANDLE ADD BUTTON
    //-------------------
    public void addButtonClick(View view){
        FloatingActionButton addBtn = view.findViewById(R.id.create_property_fab_button);
        addBtn.setOnClickListener(v-> startActivity(new Intent(requireActivity(), AddPropertyActivity.class)));
    }

    //---------------
    // RECYCLER VIEW
    //---------------
    // -- Configure recyclerview -->
    private void configureRecyclerView(View view) {
        //--:: Set layout Manager to set the items in position ::--
        this.propertiesRecyclerView = view.findViewById(R.id.properties_recyclerView);
        this.propertiesRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        propertiesRecyclerView.setAdapter(adapter);
    }

    //-------------------
    // HANDLE ITEM CLICK
    //-------------------
    @Override
    public void onItemClick(PropertyModel property) {
        Fragment sheetFragment = PropertySheetFragment.getInstance(property);
        FragmentTransaction varTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        varTransaction.replace(R.id.main_container, sheetFragment, "Replace PropertyList Fragment by SheetProperty Fragment according to item Click");
        varTransaction.addToBackStack(null);
        varTransaction.commit();
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

    // 2 -- Get, update & observe properties changes, by paying attention to the adapter -->
    private void observeFirebaseProperties(){
        varFirebaseViewModel.getAllProperties().observe(getViewLifecycleOwner(), pPropertyModels -> {
            //--:: Configuring adapter according to new list ::--
            adapter = new PropertyListAdapter(requireActivity(), pPropertyModels, this);
            //--:: Attach the adapter to the recycler view to inflate items ::--
            propertiesRecyclerView.setAdapter(adapter);
        });
    }

    //-----------------------
    // LOCAL ROOM VIEW MODEL
    //-----------------------
    // 1 -- Configure ROOM View Model & Retrieve all properties stuck on phone and last connected User -->
    private void configureLocalRoomViewModel(){
        RoomViewModelFactory roomViewModelFactory = RoomInjection.provideViewModelFactory(requireActivity());
        roomViewModel = roomViewModelFactory.create(RoomViewModel.class);
        roomViewModel.initPropertiesList();
    }

    // 2 -- Get, update on phone & keep observing properties changes to get last save of app state -->
    private void getAndObserveProperties() {
        this.roomViewModel.getAllProperties().observe(getViewLifecycleOwner(), pPropertyModels -> {
            //--:: Configuring adapter according to last list known by the app ::--
            adapter = new PropertyListAdapter(requireActivity(), pPropertyModels, this);
            //--:: Keep updating adapter according to list ::--
            adapter.updateProperties(pPropertyModels);
            //--:: Setting adapter after updated it ::--
            propertiesRecyclerView.setAdapter(adapter);
            Log.d("PROPERTIES : ", "properties have been loaded : " + pPropertyModels.size());
        });
    }

}
