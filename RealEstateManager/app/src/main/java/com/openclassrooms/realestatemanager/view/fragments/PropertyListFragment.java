package com.openclassrooms.realestatemanager.view.fragments;

import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.openclassrooms.realestatemanager.MVVM.injection.firebase.FirebaseInjection;
import com.openclassrooms.realestatemanager.MVVM.injection.firebase.FirebaseViewModelFactory;
import com.openclassrooms.realestatemanager.MVVM.injection.room.RoomInjection;
import com.openclassrooms.realestatemanager.MVVM.injection.room.RoomViewModelFactory;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.adapters.PropertyListAdapter;
import com.openclassrooms.realestatemanager.model.PropertyModel;
import com.openclassrooms.realestatemanager.model.UserModel;
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
    /**
     * LIVE DATA - VIEW MODELS
     */
    FirebaseViewModel varFirebaseViewModel;
    RoomViewModel roomViewModel;
    /**
     * Tools
     */
    List<PropertyModel> firebaseProperties = new ArrayList<>();
    List<PropertyModel> roomProperties = new ArrayList<>();
    PropertyListAdapter adapter;
    List<UserModel> allUsers = new ArrayList<>();
    ItemTouchHelper.SimpleCallback itemTouchHelper;
    PropertyModel deletedProperty = null;
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
        configureToolbar(view);
        configureRecyclerView(view);
        addButtonClick(view);
        //--:: Firebase View Model Methods ::--
        //configureFirebaseViewModel();
        if (varFirebaseViewModel != null) {
            observeFirebaseProperties();
        } else {
            //--:: Local ROOM View Model Methods ::--
            configureLocalRoomViewModel();
            getRoomProperties();
            getAndObserveRoomUsers();
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
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

    //---------------
    // RECYCLER VIEW
    //---------------
    // -- Configure recyclerview -->
    private void configureRecyclerView(View view) {
        //--:: Set layout Manager to set the items in position ::--
        propertiesRecyclerView = view.findViewById(R.id.properties_recyclerView);
        propertiesRecyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        //--:: Configuring adapter according to last list known by the app ::--
        adapter = new PropertyListAdapter(requireActivity(), firebaseProperties, this);
        //--:: Setting adapter after updated it ::--
        propertiesRecyclerView.setAdapter(adapter);
        Log.d("PROPERTIES : ", "properties have been loaded : " + firebaseProperties.size());
        configureSwiping();
        ItemTouchHelper varItemTouchHelper = new ItemTouchHelper(itemTouchHelper);
        varItemTouchHelper.attachToRecyclerView(propertiesRecyclerView);
    }

    //-------------------
    // HANDLE ADD BUTTON
    //-------------------
    public void addButtonClick(View view) {
        FloatingActionButton addBtn = view.findViewById(R.id.create_property_fab_button);
        addBtn.setOnClickListener(v -> startActivity(new Intent(requireActivity(), AddPropertyActivity.class)));
    }

    //-----------------------
    // LOCAL ROOM VIEW MODEL
    //-----------------------
    // 1 -- Configure ROOM View Model & Retrieve all properties stuck on phone and last connected User -->
    private void configureLocalRoomViewModel() {
        RoomViewModelFactory roomViewModelFactory = RoomInjection.provideViewModelFactory(requireActivity());
        roomViewModel = roomViewModelFactory.create(RoomViewModel.class);
        roomViewModel.initAllUsers();
        roomViewModel.initAllProperties();
    }

    // 2 -- Get, update on phone & keep observing Room PROPERTIES changes -->
    private void getRoomProperties() {
        this.roomViewModel.getAllProperties().observe(requireActivity(), new Observer<List<PropertyModel>>() {

            @Override
            public void onChanged(List<PropertyModel> pPropertyModels) {
                roomProperties.addAll(pPropertyModels);
                adapter.updateProperties(roomProperties);
            }
        });
    }

    // 3 -- Get, update on phone & keep observing Room USERS changes -->
    private void getAndObserveRoomUsers() {
        this.roomViewModel.getAllUsers().observe(requireActivity(), new Observer<List<UserModel>>() {
            @Override
            public void onChanged(List<UserModel> pUserModels) {
                allUsers.clear();
                allUsers.addAll(pUserModels);
            }
        });
    }


    //---------------------
    // FIREBASE VIEW MODEL
    //---------------------
    // 1 -- Configure Firebase View Model & Retrieve all properties and connected User -->
    private void configureFirebaseViewModel() {
        FirebaseViewModelFactory varFirebaseViewModelFactory = FirebaseInjection.provideFirebaseViewModelFactory();
        varFirebaseViewModel = varFirebaseViewModelFactory.create(FirebaseViewModel.class);
        varFirebaseViewModel.retrieveAllUsers();
        varFirebaseViewModel.retrieveAllProperties();
    }

    // 2 -- Get, update & observe properties changes, by paying attention to the adapter -->
    private void observeFirebaseProperties() {
        varFirebaseViewModel.getAllProperties().observe(getViewLifecycleOwner(), pPropertyModels -> {
            //--:: Attach the adapter to the recycler view to inflate items ::--
            firebaseProperties.addAll(pPropertyModels);
            //--:: Configuring adapter according to new list ::--
            adapter.updateProperties(firebaseProperties);
        });
    }


    //------------------------
    // SWIPE : DELETE//UPDATE
    //------------------------
    private void configureSwiping() {
        itemTouchHelper = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
                final int position = viewHolder.getAdapterPosition();
                switch (direction) {
                    case ItemTouchHelper.LEFT:
                        deletedProperty = firebaseProperties.get(position);
                        firebaseProperties.remove(deletedProperty);
                        //varFirebaseViewModel.deleteProperty(deletedProperty.getName());
                        roomViewModel.deleteProperty(deletedProperty);
                        adapter.notifyItemRemoved(position);
                        Snackbar.make(propertiesRecyclerView, deletedProperty.getName(), Snackbar.LENGTH_LONG)
                                .setAction("Undo", pView -> {
                                    firebaseProperties.add(position, deletedProperty);
                                    roomViewModel.addProperty(deletedProperty);
                                    adapter.notifyItemInserted(position);
                                }).show();
                        break;
                    case ItemTouchHelper.RIGHT:
                        updatedProperty = firebaseProperties.get(position);
                        Intent intent = new Intent(requireActivity(), AddPropertyActivity.class);
                        intent.putExtra("propertyToUpdate",updatedProperty);
                        startActivity(intent);
                        adapter.notifyItemChanged(position);
                        break;
                }
            }

            @Override
            public void onChildDraw(@NonNull @NotNull Canvas c, @NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.primary_dark))
                        .addSwipeLeftActionIcon(R.drawable.ic_baseline_delete_sweep_24 )
                        .addSwipeRightBackgroundColor(ContextCompat.getColor(requireActivity(), R.color.accent))
                        .addSwipeRightActionIcon(R.drawable.ic_baseline_update_24)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

            }
        };
    }

}
