package com.openclassrooms.realestatemanager.view.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.realestatemanager.MVVM.injection.firebase.FirebaseInjection;
import com.openclassrooms.realestatemanager.MVVM.injection.firebase.FirebaseViewModelFactory;
import com.openclassrooms.realestatemanager.MVVM.injection.room.RoomInjection;
import com.openclassrooms.realestatemanager.MVVM.injection.room.RoomViewModelFactory;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.adapters.PropertyListAdapter;
import com.openclassrooms.realestatemanager.model.PropertyModel;
import com.openclassrooms.realestatemanager.view.activities.AddPropertyActivity;
import com.openclassrooms.realestatemanager.view.viewmodel.FirebaseViewModel;
import com.openclassrooms.realestatemanager.view.viewmodel.RoomViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class PropertyListFragment extends Fragment implements PropertyListAdapter.OnItemClickListener {

    //---------------
    // DATA - FIELDS
    //---------------

    /**
     * Graphics
     */
    RecyclerView propertiesRecyclerView;
    ConstraintLayout containerSheetLayout;
    /**
     * LIVE DATA - VIEW MODELS
     */
    FirebaseViewModel firebaseViewModel;
    RoomViewModel roomViewModel;
    List<PropertyModel> properties = new ArrayList<>();
    /**
     * Tools
     */
    @SuppressLint("StaticFieldLeak")
    static PropertyListAdapter adapter;
    ItemTouchHelper.SimpleCallback itemTouchHelper;
    PropertyModel updatedProperty = null;


    /**
     * CONSTRUCTOR
     */
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
        addButtonClick(view);
        mapButtonClick(view);
        setHasOptionsMenu(true);
        configureToolbar(view);
        //--:: Local ROOM View Model Methods ::--
        configureRecyclerView(view);
        configureViewModels();
        return view;
    }

    // 2 -- ON RESUME -->
    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    //-------------------
    // HANDLE ADD BUTTON
    //-------------------
    public void addButtonClick(View view) {
        FloatingActionButton addBtn = view.findViewById(R.id.create_property_fab_button);
        addBtn.setOnClickListener(v -> startActivity(new Intent(requireActivity(), AddPropertyActivity.class)));
    }

    //-------------------
    // HANDLE MAP BUTTON
    //-------------------
    public void mapButtonClick(View view) {
        FloatingActionButton mapBtn = view.findViewById(R.id.go_on_map);
        mapBtn.setOnClickListener(v -> {
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_list_properties, new MapFragment()).addToBackStack("fragment_list");
            transaction.commit();
        });
    }

    //-----------------
    // TOOLBAR & TITLE
    //-----------------
    private void configureToolbar(View pView) {
        //--:: 2 -- Toolbar ::-->
        Toolbar fToolbar = pView.findViewById(R.id.list_toolbar);
        ((AppCompatActivity) requireActivity()).setSupportActionBar(fToolbar);
        fToolbar.setTitle(R.string.app_name);
    }

    //------------------------
    // MULTIPLE SEARCH FILTER
    //------------------------
    //--:: 1 -- Create search icon by implemented a menu ::-->
    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_filter, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //--:: 2 -- Connect search icon to "get places procedure" ::-->
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int search_id = R.id.search;
        if (item.getItemId() == search_id) {
            openSearchByFilterDialog();
        }
        return super.onOptionsItemSelected(item);
    }

    //-------------------
    // HANDLING FRAGMENT
    //-------------------
    private void openSearchByFilterDialog() {
        MultipleFilterFragment filterFrag = new MultipleFilterFragment();
        filterFrag.show(requireActivity().getSupportFragmentManager(), "My filter dialog");
    }

    //---------------
    // RECYCLER VIEW
    //---------------
    // 1 -- Configure recyclerview -->
    private void configureRecyclerView(View view) {
        //--:: Set layout Manager to set the items in position ::--
        propertiesRecyclerView = view.findViewById(R.id.properties_recyclerView);
        propertiesRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        Log.d("PROPERTIES : ", "properties have been loaded : " + properties.size());
        configureSwiping();
        ItemTouchHelper varItemTouchHelper = new ItemTouchHelper(itemTouchHelper);
        varItemTouchHelper.attachToRecyclerView(propertiesRecyclerView);
    }

    // 2 -- Init list of properties and set it to the adapter -->
    private void initList() {
        if (roomViewModel != null){
            getRoomProperties();
        } else {
            getFirebaseProperties();
        }
        //--:: Configuring adapter according to last list known by the app ::--
        adapter = new PropertyListAdapter(requireActivity(), properties, this);
        //--:: Setting adapter after updated it ::--
        propertiesRecyclerView.setAdapter(adapter);
    }

    //----------------------------
    // 3 - SWIPE : DELETE//UPDATE
    //----------------------------
    private void configureSwiping() {
        itemTouchHelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int lastPosition = viewHolder.getAbsoluteAdapterPosition();
                if (direction == ItemTouchHelper.RIGHT){
                    //--:: Update property ::--
                    updatedProperty = properties.get(lastPosition);
                    Intent intent = new Intent(requireActivity(), AddPropertyActivity.class);
                    intent.putExtra("propertyToUpdate", updatedProperty);
                    intent.putExtra("propertyNameToUpdate", updatedProperty.getName());
                    startActivity(intent);
                    adapter.notifyItemChanged(lastPosition);
                }
            }

            //--:: Design swiping item flow ::--
            @Override
            public void onChildDraw(@NonNull @NotNull Canvas c, @NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.accent))
                        .addSwipeRightActionIcon(R.drawable.ic_baseline_update_24)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            }
        };
    }

    //-------------------
    // HANDLE ITEM CLICK
    //-------------------
    @Override
    public void onItemClick(PropertyModel property) {
        Fragment sheetFragment = PropertySheetFragment.getInstance(property);
        FragmentTransaction varTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
        containerSheetLayout = requireActivity().findViewById(R.id.fragment_real_sheet_layout);
        if (property != null && containerSheetLayout != null) {
            containerSheetLayout.setVisibility(View.VISIBLE);
            FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_real_sheet_layout, sheetFragment);
            transaction.commit();
        } else {
            varTransaction.replace(R.id.fragment_list_properties, sheetFragment, "Replace PropertyList Fragment by SheetProperty Fragment according to item Click");
            varTransaction.addToBackStack(null);
            varTransaction.commit();
        }
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

    // 2a -- Get & observe ROOM PROPERTIES  -->
    private void getRoomProperties() {
        this.roomViewModel.getAllProperties().observe(requireActivity(), pPropertyModels -> {
            properties.clear();
            properties.addAll(pPropertyModels);
            adapter.updateProperties(properties);
        });
    }

    // 2b -- Get & observe FIREBASE PROPERTIES  -->
    private void getFirebaseProperties() {
        firebaseViewModel.getAllProperties().observe(requireActivity(), pPropertyModels -> {
            properties.clear();
            properties.addAll(pPropertyModels);
            adapter.updateProperties(properties);
        });
    }
}

