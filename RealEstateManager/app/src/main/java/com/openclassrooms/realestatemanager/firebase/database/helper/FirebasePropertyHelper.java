package com.openclassrooms.realestatemanager.firebase.database.helper;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.openclassrooms.realestatemanager.firebase.database.client.FirebasePropertyClient;
import com.openclassrooms.realestatemanager.model.PropertyModel;

public class FirebasePropertyHelper implements FirebasePropertyClient {

    private final String PROPERTIES = "properties";

    //-------------
    // QUERY - GET
    //-------------
    // -- QUERY :: GET PROPERTIES COLLECTION INTO USER COLLECTION -->
    @Override
    public CollectionReference getPropertiesCollection(String userId) {
        String USERS_COLLECTION = "users";
        return FirebaseFirestore.getInstance().collection(USERS_COLLECTION).document(userId).
                collection(PROPERTIES);
    }
    // -- QUERY :: GET ALL PROPERTIES IN FIREBASE DATABASE (A TESTER) -->
    @Override
    public Task<QuerySnapshot> getAllProperties() {
        return FirebaseFirestore.getInstance().collection(PROPERTIES).get();
    }

    // -- QUERY :: GET PROPERTY IN FIREBASE DATABASE -->
    @Override
    public Task<DocumentSnapshot> getProperty(String userId, String propertyId) {
        return getPropertiesCollection(userId).document(propertyId).get();
    }

    // -- QUERY :: GET ALL PROPERTIES ACCORDING TO USER IN FIREBASE DATABASE -->
    @Override
    public Task<QuerySnapshot> getPropertyDocuments(String userId) {
        return getPropertiesCollection(userId).get();
    }

    //---------------------------
    // CREATE - UPDATE - DELETE
    //---------------------------
    @Override
    public Task<Void> createProperty(String propertyId, PropertyModel property, String userId) {
        return getPropertiesCollection(userId).document(propertyId).set(property);
    }

    @Override
    public Task<Void> updateProperty(String propertyId, String userId, PropertyModel property) {
        return getPropertiesCollection(userId).document(propertyId).set(property, SetOptions.merge());
    }

    @Override
    public Task<Void> deleteProperty(String propertyId, PropertyModel property, String userId) {
        return getPropertiesCollection(userId).document(propertyId).delete();
    }
}
