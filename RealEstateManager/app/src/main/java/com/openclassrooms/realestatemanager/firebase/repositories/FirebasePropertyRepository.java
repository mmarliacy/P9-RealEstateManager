package com.openclassrooms.realestatemanager.firebase.repositories;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.openclassrooms.realestatemanager.firebase.database.client.FirebasePropertyClient;
import com.openclassrooms.realestatemanager.model.PropertyModel;

public class FirebasePropertyRepository {

    private final FirebasePropertyClient fFirebasePropertyClient;

    public FirebasePropertyRepository(FirebasePropertyClient pFirebasePropertyClient) {
        fFirebasePropertyClient = pFirebasePropertyClient;
    }

    //-------------
    // QUERY - GET
    //-------------
    // -- QUERY :: GET PROPERTIES COLLECTION INTO USER COLLECTION -->
    public CollectionReference getPropertiesCollection(String userId) {
        return this.fFirebasePropertyClient.getPropertiesCollection(userId);
    }
    // -- QUERY :: GET ALL PROPERTIES IN FIREBASE DATABASE (A TESTER) -->
    public Task<QuerySnapshot> getAllProperties() {
        return this.fFirebasePropertyClient.getAllProperties();
    }

    // -- QUERY :: GET PROPERTY IN FIREBASE DATABASE -->
    public Task<DocumentSnapshot> getProperty(String userId, String propertyId) {
        return this.fFirebasePropertyClient.getProperty(userId, propertyId);
    }

    // -- QUERY :: GET ALL PROPERTIES ACCORDING TO USER IN FIREBASE DATABASE -->
    public Task<QuerySnapshot> getPropertyDocuments(String userId) {
        return this.fFirebasePropertyClient.getPropertyDocuments(userId);
    }

    //---------------------------
    // CREATE - UPDATE - DELETE
    //---------------------------
    public Task<Void> createProperty(String propertyId, PropertyModel property, String userId) {
        return this.fFirebasePropertyClient.createProperty(propertyId,property,userId);
    }

    public Task<Void> updateProperty(String propertyId, String userId, PropertyModel property) {
        return this.fFirebasePropertyClient.updateProperty(propertyId,userId,property);
    }

    public Task<Void> deleteProperty(String propertyId, PropertyModel property, String userId) {
        return this.fFirebasePropertyClient.deleteProperty(propertyId,property,userId);
    }
}
