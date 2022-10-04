package com.openclassrooms.realestatemanager.firebase.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.openclassrooms.realestatemanager.firebase.repositories.FirebasePropertyRepository;
import com.openclassrooms.realestatemanager.firebase.repositories.FirebaseUserRepository;
import com.openclassrooms.realestatemanager.model.PropertyModel;
import com.openclassrooms.realestatemanager.model.UserModel;

import java.util.List;
import java.util.concurrent.Executor;

public class FirebaseViewModel extends ViewModel {

    //-- REPOSITORIES & EXECUTOR -->
    private final FirebasePropertyRepository fFirebasePropertyRepository;
    private final FirebaseUserRepository fFirebaseUserRepository;

    /**
     * Constructor
     */
    public FirebaseViewModel(FirebasePropertyRepository pFirebasePropertyRepository,
                                    FirebaseUserRepository pFirebaseUserRepository) {
        fFirebasePropertyRepository = pFirebasePropertyRepository;
        fFirebaseUserRepository = pFirebaseUserRepository;
    }

    // 2 -- DATA -->
    Task<QuerySnapshot> allProperties;
    List<UserModel> allUsers;

    // 3 -- GET FIRST DATA -->
    public void initPropertiesList() {
        if (allProperties == null) {
            allProperties= fFirebasePropertyRepository.getAllProperties();
        }
    }

    //-------------------------------------
    // 4 -- METHODS TO MANAGE USERS LIST --
    //-------------------------------------
    // -- QUERY :: GET ALL USERS -->
    public CollectionReference getUsersCollection() {
        return this.fFirebaseUserRepository.getUsersCollection();
    }

    // -- QUERY :: GET USER IN FIREBASE DATABASE -->
    public Task<DocumentSnapshot> getUser(String userId){
        return this.fFirebaseUserRepository.getUser(userId);
    }

    // -- INSERT :: USER -->
    public Task<Void> createUser(String userId, UserModel user) {
        return this.fFirebaseUserRepository.createUser(userId, user);
    }

    //------------------------------------------
    // 5 -- METHODS TO MANAGE PROPERTIES LIST --
    //------------------------------------------

    // -- QUERY :: GET PROPERTIES COLLECTION INTO USER COLLECTION -->
    public CollectionReference getPropertiesCollection(String userId) {
        return this.fFirebasePropertyRepository.getPropertiesCollection(userId);
    }

    // -- QUERY :: GET ALL PROPERTIES IN FIREBASE DATABASE (A TESTER) -->
    public Task<QuerySnapshot> getAllProperties() {
        return this.fFirebasePropertyRepository.getAllProperties();
    }

    // -- QUERY :: GET PROPERTY IN FIREBASE DATABASE -->
    public Task<DocumentSnapshot> getProperty(String userId, String propertyId) {
        return this.fFirebasePropertyRepository.getProperty(userId, propertyId);
    }

    // -- QUERY :: GET ALL PROPERTIES ACCORDING TO USER IN FIREBASE DATABASE -->
    public Task<QuerySnapshot> getPropertyDocuments(String userId) {
        return this.fFirebasePropertyRepository.getPropertyDocuments(userId);
    }

    // -- INSERT :: PROPERTY -->
    public Task<Void> createProperty(String propertyId, PropertyModel property, String userId) {
        return this.fFirebasePropertyRepository.createProperty(propertyId,property,userId);
    }

    // -- UPDATE :: PROPERTY -->
    public Task<Void> updateProperty(String propertyId, String userId, PropertyModel property) {
        return this.fFirebasePropertyRepository.updateProperty(propertyId,userId,property);
    }

    // -- DELETE :: PROPERTY -->
    public Task<Void> deleteProperty(String propertyId, PropertyModel property, String userId) {
        return this.fFirebasePropertyRepository.deleteProperty(propertyId,property,userId);
    }
}
