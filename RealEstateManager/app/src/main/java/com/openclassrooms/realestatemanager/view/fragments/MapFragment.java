package com.openclassrooms.realestatemanager.view.fragments;

import static com.openclassrooms.realestatemanager.R.layout.fragment_map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.openclassrooms.realestatemanager.MVVM.injection.firebase.FirebaseInjection;
import com.openclassrooms.realestatemanager.MVVM.injection.firebase.FirebaseViewModelFactory;
import com.openclassrooms.realestatemanager.MVVM.injection.room.RoomInjection;
import com.openclassrooms.realestatemanager.MVVM.injection.room.RoomViewModelFactory;
import com.openclassrooms.realestatemanager.R;
import com.openclassrooms.realestatemanager.model.PropertyModel;
import com.openclassrooms.realestatemanager.utils.permission.RequestPermissionsHelper;
import com.openclassrooms.realestatemanager.view.viewmodel.FirebaseViewModel;
import com.openclassrooms.realestatemanager.view.viewmodel.RoomViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MapFragment extends Fragment implements OnMapReadyCallback {

    /**
     * Graphics
     */
    FloatingActionButton userLocationBtn;

    /**
     * Data
     */
    //--:: Last-know location retrieved by the Fused Location Provider ::--
    private static Location lastKnownLocation;
    private double lat;
    private double lng;

    /**
     * LIVE DATA - VIEW MODELS
     */
    FirebaseViewModel firebaseViewModel;
    RoomViewModel roomViewModel;
    List<PropertyModel> properties = new ArrayList<>();

    //----------------------------------
    // ON CREATE VIEW
    //----------------------------------


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        askForDeviceLocation();
        return inflater.inflate(fragment_map, container, false);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configureLocateDeviceButton();
        configureViewModels();
        if (roomViewModel != null) {
            getRoomProperties();
        } else {
            getFirebaseProperties();
        }
    }

    //-----------------
    // LOCATION BUTTON
    //-----------------
    private void configureLocateDeviceButton() {
        userLocationBtn = requireView().findViewById(R.id.fab_my_location);
        userLocationBtn.setOnClickListener(pView -> getDeviceLocation());
    }

    //-----------------
    // CONFIGURING MAP
    //-----------------
    //--:: 1 -- On Map ready animate camera, add markers... ::-->
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(@NonNull GoogleMap pGoogleMap) {
        LatLng lastKnownPosition = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
        MarkerOptions marker =
                new MarkerOptions().
                        position(lastKnownPosition).
                        title("You're here");
        pGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastKnownPosition, 14));
        pGoogleMap.addMarker(marker);
        // Enable the zoom controls for the map
        pGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        getPlaces(pGoogleMap);
    }

    //------------------------------------
    // GET ACCESS TO USER DEVICE LOCATION
    //------------------------------------
    //--:: 1 -- Ask & Get current location of the device ::-->
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void askForDeviceLocation() {
        List<String> permissions = new ArrayList<>();
        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        new RequestPermissionsHelper().startPermissionRequest(this, isGranted -> {
            if (isGranted) {
                getDeviceLocation();
            } else {
                Toast.makeText(requireActivity(), "You didn't give access to your location, process cancelled", Toast.LENGTH_LONG).show();
            }
        }, permissions);
    }

    //--:: 2 -- Get current location of the device ::-->
    @SuppressLint("MissingPermission")
    private void getDeviceLocation() {
        //--:: The entry point to the Fused Location Provider::--
        FusedLocationProviderClient varFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        //--:: Launch Task Location ::--
        varFusedLocationProviderClient.getLastLocation().addOnSuccessListener(location -> {
            if (location != null) {
                lastKnownLocation = location;
                //--:: Initializing support Map Fragment & Sync Map ::--
                SupportMapFragment supportMapFragment = (SupportMapFragment) getChildFragmentManager()
                        .findFragmentById(R.id.google_maps_fragment);
                assert supportMapFragment != null;
                supportMapFragment.getMapAsync(MapFragment.this);
            }
        });
    }

    //------------------------------
    // GET PLACES : NEAR BY PLACES
    //------------------------------
    //--:: 1 -- Get addresses' list of properties & set properties position on map ::-->
    private void getPlaces(GoogleMap map) {
        List<String> addressesList = new ArrayList<>();
        if (properties != null) {
            for (PropertyModel property : properties) {
                addressesList.add(property.getAddress());
            }
        }
        if (addressesList.size() != 0) {
            try {
                Geocoder geocoder = new Geocoder(requireContext());
                List<Address> addressComponents;
                for (int i = 0; i < addressesList.size(); i++) {
                    addressComponents = geocoder.getFromLocationName(addressesList.get(i), 1);
                    if (addressComponents != null) {
                        lat = addressComponents.get(0).getLatitude();
                        lng = addressComponents.get(0).getLongitude();
                    }
                    if (addressesList.get(i).equals(properties.get(i).getAddress())) {
                        LatLng varLatLng = new LatLng(lat, lng);
                        MarkerOptions marker =
                                new MarkerOptions().
                                        position(varLatLng).
                                        title(properties.get(i).getName()).
                                        snippet(properties.get(i).getAddress());
                        Objects.requireNonNull(map.addMarker(marker)).setTag(properties.get(i));
                        map.setInfoWindowAdapter(infoWindowAdapter);
                    }
                }
            } catch (IOException pE) {
                pE.printStackTrace();
            }
        } else {
            Toast.makeText(requireActivity(), "There is no property place to display", Toast.LENGTH_LONG).show();
        }
    }


    //------------------------------------
    // MAP MARKER : DISPLAY PROPERTY INFO
    //------------------------------------
    GoogleMap.InfoWindowAdapter infoWindowAdapter = new GoogleMap.InfoWindowAdapter() {
        @NonNull
        @Override
        public View getInfoContents(@NonNull Marker marker) {
            @SuppressLint("InflateParams")
            View view = requireActivity().getLayoutInflater().inflate(R.layout.info_windows_popup, null);
            TextView propertyNamePopUp = view.findViewById(R.id.property_name_popup);
            TextView propertyAddressPopUp = view.findViewById(R.id.property_address_popup);
            propertyNamePopUp.setText(marker.getTitle());
            propertyAddressPopUp.setText(marker.getSnippet());
            return view;
        }

        @Nullable
        @Override
        public View getInfoWindow(@NonNull Marker marker) {
            return null;
        }
    };

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
        });
    }

    // 2b -- Get & observe FIREBASE PROPERTIES  -->
    private void getFirebaseProperties() {
        firebaseViewModel.getAllProperties().observe(requireActivity(), pPropertyModels -> {
            properties.clear();
            properties.addAll(pPropertyModels);
        });
    }
}
