package com.openclassrooms.realestatemanager.view.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.openclassrooms.realestatemanager.MVVM.repositories.firebase.FirebasePropertyHelper;
import com.openclassrooms.realestatemanager.MVVM.repositories.firebase.FirebaseUserHelper;
import com.openclassrooms.realestatemanager.model.PropertyModel;
import com.openclassrooms.realestatemanager.model.UserModel;

import java.util.List;

public class FirebaseViewModel extends ViewModel {

    // 1 -- REPOSITORIES -->
    private final FirebasePropertyHelper fFirebasePropertyRepository;
    private final FirebaseUserHelper fFirebaseUserRepository;


    // 2 -- DATA -->
    LiveData<List<PropertyModel>> propertiesList;
    LiveData<List<UserModel>> usersList;


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
    pub le cas où aurait prit le relais comment ajouter à firebase
    // les derlic void retrieveAllUsers(){
        usersList = fFirebaseUserRepository.getAllUsers();
    }
    // Dansnières propriétés ajoutées à Room.

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
    public void createProperty(String propertyName, PropertyModel property) {
        fFirebasePropertyRepository.createProperty(propertyName, property);
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
    public FirebaseUser getUser() {
        return fFirebaseUserRepository.getUser();
    }

    public LiveData<List<UserModel>> getAllUsers(){
        return this.usersList;
    }
    //---------------------------------------------------------------------------------------
    // -- CREATE - UPDATE - DELETE -->
    //----------------------------------------------------
    // -- CREATE :: USER IN FIREBASE DATABASE -->
    public Task<Void> createUser(String uid, UserModel user) {
        return fFirebaseUserRepository.createUser(uid, user);
    }
    // -- UPDATE :: USER IN FIREBASE DATABASE -->
    public Task<Void> updateUser(String userId, String propertyId) {
        return fFirebaseUserRepository.updateUser(userId, propertyId);
    }
}
