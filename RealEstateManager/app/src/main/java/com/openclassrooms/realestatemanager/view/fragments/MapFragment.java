package com.openclassrooms.realestatemanager.view.fragments;

import static androidx.core.content.ContextCompat.checkSelfPermission;
import static com.openclassrooms.realestatemanager.R.layout.fragment_map;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
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
import com.openclassrooms.realestatemanager.view.viewmodel.FirebaseViewModel;
import com.openclassrooms.realestatemanager.view.viewmodel.RoomViewModel;

import org.jetbrains.annotations.NotNull;

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
    //--:: Request location permission ::--
    private final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    List<String> addresses;
    double lat;
    double lng;
    GoogleMap map;
    public boolean locationPermissionGranted;

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
        View view = inflater.inflate(fragment_map, container, false);
        //--:: Launch Task Location ::--
        getDeviceLocation();
        return view;
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userLocationBtn = requireView().findViewById(R.id.fab_my_location);
        userLocationBtn.setOnClickListener(pView -> getDeviceLocation());
        configureViewModels();
        if (roomViewModel !=null){
            getRoomProperties();
        } else {
            getFirebaseProperties();
        }
        getDeviceLocation();
    }

    //-----------------
    // CONFIGURING MAP
    //-----------------
    //--:: 1 -- On Map ready animate camera, add markers... ::-->
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onMapReady(@NonNull GoogleMap pGoogleMap) {
        map = pGoogleMap;
        LatLng lastKnownPosition = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
        MarkerOptions marker =
                new MarkerOptions().
                        position(lastKnownPosition).
                        title("You're here");
        pGoogleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastKnownPosition, 14));
        pGoogleMap.addMarker(marker);
        // Enable the zoom controls for the map
        pGoogleMap.getUiSettings().setZoomControlsEnabled(true);
        getPlaces(map);
    }

    //------------------------------------
    // GET ACCESS TO USER DEVICE LOCATION
    //------------------------------------
    //--:: 1 -- Gets the current location of the device ::-->
    @SuppressLint("MissingPermission")
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getDeviceLocation() {
        //--:: The entry point to the Fused Location Provider::--
        FusedLocationProviderClient varFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());
        //--:: Request location permission, so that we can get the location of the device ::--
        if (checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
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

    //--:: 2 -- Handling result of getting permission and relaunch getDeviceLocation ::-->
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull @NotNull int[] grantResults) {
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getDeviceLocation();
                locationPermissionGranted = true;
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    //------------------------------
    // GET PLACES : NEAR BY PLACES
    //------------------------------
    //--:: 1 -- Make API request to get all restaurants, add markers ::-->
    private void getPlaces(GoogleMap map) {
        addresses = new ArrayList<>();
        if (properties != null) {
            for (PropertyModel property : properties) {
                addresses.add(property.getAddress());
            }
        }
            if (addresses.size() != 0) {
                try {
                    Geocoder geocoder = new Geocoder(requireContext());
                    List<Address> addressList;
                    for (int i = 0; i < addresses.size(); i++) {
                        addressList = geocoder.getFromLocationName(addresses.get(i), 1);
                        if (addressList != null) {
                            lat = addressList.get(0).getLatitude();
                            lng = addressList.get(0).getLongitude();
                        }
                        if (addresses.get(i).equals(properties.get(i).getAddress())) {
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


    //---------------------------
    // MAP MARKER : DISPLAY INFO
    //---------------------------
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
