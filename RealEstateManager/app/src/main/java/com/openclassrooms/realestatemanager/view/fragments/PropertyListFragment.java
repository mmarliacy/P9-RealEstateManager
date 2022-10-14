package com.openclassrooms.realestatemanager.view.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.adapters.PropertyListAdapter;
import com.openclassrooms.realestatemanager.firebase.factory.FirebaseViewModelFactory;
import com.openclassrooms.realestatemanager.firebase.injection.FirebaseInjection;
import com.openclassrooms.realestatemanager.firebase.viewmodel.FirebaseViewModel;
import com.openclassrooms.realestatemanager.local.factory.LocalViewModelFactory;
import com.openclassrooms.realestatemanager.local.injection.LocalInjection;
import com.openclassrooms.realestatemanager.local.viewmodel.LocalPropertyViewModel;

import org.jetbrains.annotations.NotNull;

public class PropertyListFragment extends Fragment {

    //---------------
    // DATA - FIELDS
    //---------------
    /** Graphics */
    RecyclerView propertiesRecyclerView;
    /** LIVE DATA - VIEW MODELS */
    FirebaseViewModel varFirebaseViewModel;
    LocalPropertyViewModel varLocalViewModel;
    /** Tools */
    PropertyListAdapter adapter;

    /** CONSTRUCTOR */
    public static Fragment getInstance() {
        return new PropertyListFragment();
    }

    //----------------
    // ON CREATE VIEW
    //----------------
    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //--:: Set View & RecyclerView with Options (Layout Manager) ::--
        View view = inflater.inflate(R.layout.property_list_home_screen, container, false);
        this.configureRecyclerView(view);

        //--:: Local ROOM View Model Methods ::--
        configureLocalRoomViewModel();
        getAndObserveProperties();

        //--:: Firebase View Model Methods ::--
         configureFirebaseViewModel();
         observeFirebaseProperties();
        return view;
    }

    //---------------
    // RECYCLER VIEW
    //---------------
    // -- Configure recyclerview -->
    private void configureRecyclerView(View view) {
        //--:: Set layout Manager to set the items in position ::--
        this.propertiesRecyclerView = view.findViewById(R.id.properties_recyclerView);
        this.propertiesRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        this.propertiesRecyclerView.addItemDecoration(new DividerItemDecoration(requireActivity(),
                DividerItemDecoration.VERTICAL));
        propertiesRecyclerView.setAdapter(adapter);
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
            adapter = new PropertyListAdapter(requireActivity(), pPropertyModels);
            //--:: Attach the adapter to the recycler view to inflate items ::--
            propertiesRecyclerView.setAdapter(adapter);
        });
    }

    //-----------------------
    // LOCAL ROOM VIEW MODEL
    //-----------------------
    // 1 -- Configure ROOM View Model & Retrieve all properties stuck on phone and last connected User -->
    private void configureLocalRoomViewModel(){
        LocalViewModelFactory varLocalViewModelFactory = LocalInjection.provideViewModelFactory(requireActivity());
        varLocalViewModel = varLocalViewModelFactory.create(LocalPropertyViewModel.class);
        varLocalViewModel.initPropertiesList();
    }

    // 2 -- Get, update on phone & keep observing properties changes to get last save of app state -->
    private void getAndObserveProperties() {
        this.varLocalViewModel.getAllProperties().observe(getViewLifecycleOwner(), pPropertyModels -> {
            //--:: Configuring adapter according to last list known by the app ::--
            adapter = new PropertyListAdapter(requireContext(), pPropertyModels);
            //--:: Keep updating adapter according to list ::--
            adapter.updateProperties(pPropertyModels);
            //--:: Setting adapter after updated it ::--
            propertiesRecyclerView.setAdapter(adapter);
            Log.d("PROPERTIES : ", "properties have been loaded : " + pPropertyModels.size());
        });
    }
}
