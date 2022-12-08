package com.openclassrooms.realestatemanager.view.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.firebase.auth.FirebaseUser;
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
        if(this.propertiesList != null) {
            return;
        }
            propertiesList = fFirebasePropertyRepository.getPropertiesData();
    }
    // -- CONNECTED USER-->
    public void retrieveAllUsers(){
        if(this.usersList != null){
            return;
        }
        usersList = fFirebaseUserRepository.getAllUsers();
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
    public void createProperty(PropertyModel property) {
        fFirebasePropertyRepository.createProperty(property);
    }

    // -- UPDATE :: PROPERTY IN FIREBASE DATABASE -->
    public void updateProperty(String propertyName, PropertyModel property) {
        fFirebasePropertyRepository.updateProperty(propertyName, property);
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
    public void createUser(String uid, UserModel user) {
        fFirebaseUserRepository.createUser(uid, user);
    }
}
