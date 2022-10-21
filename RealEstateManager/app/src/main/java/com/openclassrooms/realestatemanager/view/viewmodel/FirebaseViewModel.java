package com.openclassrooms.realestatemanager.view.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.openclassrooms.realestatemanager.MVVM.repositories.firebase.FirebasePropertyHelper;
import com.openclassrooms.realestatemanager.MVVM.repositories.firebase.FirebaseUserHelper;
import com.openclassrooms.realestatemanager.model.PropertyModel;
import java.util.List;

public class FirebaseViewModel extends ViewModel {

    // 1 -- REPOSITORIES -->
    private final FirebasePropertyHelper fFirebasePropertyRepository;
    private final FirebaseUserHelper fFirebaseUserRepository;


    // 2 -- DATA -->
    LiveData<List<PropertyModel>> propertiesList;
    FirebaseUser user;


    /** CONSTRUCTOR */
    public FirebaseViewModel(FirebasePropertyHelper pFirebasePropertyRepository, FirebaseUserHelper pFirebaseUserRepository) {
        fFirebasePropertyRepository = pFirebasePropertyRepository;
        fFirebaseUserRepository = pFirebaseUserRepository;
    }

    // 3 -- GET LAST DATA -->
    // -- PROPERTIES -->
    public void retrieveAllProperties(){
            propertiesList = fFirebasePropertyRepository.getPropertiesData();
    }
    // -- CONNECTED USER-->
    public void retrieveUserConnected(){
        if(user == null){
            user = fFirebaseUserRepository.getUser();
        }
    }

    //-----------------------------------
    // 4 -- QUERY DATABASE :: PROPERTIES
    //-----------------------------------
    // -- QUERIES : -->
    //-----------------------
    // -- GET ALL PROPERTIES IN FIREBASE DATABASE -->
    public LiveData<List<PropertyModel>> getAllProperties() {
        return this.propertiesList;
    }

    //---------------------------------------------------------------------------------------
    // -- CREATE - UPDATE - DELETE -->
    //------------------------------------
    // -- CREATE :: PROPERTY IN FIREBASE DATABASE -->
    public void createProperty(String propertyId, PropertyModel property) {
        fFirebasePropertyRepository.createProperty(propertyId, property);
    }
    // -- UPDATE :: PROPERTY IN FIREBASE DATABASE -->
    public Task<Void> updateProperty(String propertyId, PropertyModel property) {
        return fFirebasePropertyRepository.updateProperty(propertyId, property);
    }
    // -- DELETE :: PROPERTY FROM FIREBASE DATABASE -->
    public Task<Void> deleteProperty(String propertyId) {
        return fFirebasePropertyRepository.deleteProperty(propertyId);
    }

    //------------------------------
    // 5 -- QUERY DATABASE :: USERS
    //------------------------------
    // -- QUERIES : -->
    //-----------------------


    //---------------------------------------------------------------------------------------
    // -- CREATE - UPDATE - DELETE -->
    //----------------------------------------------------
}
