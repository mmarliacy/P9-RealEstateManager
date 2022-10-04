package com.openclassrooms.realestatemanager.firebase.database.client;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.openclassrooms.realestatemanager.model.PropertyModel;

public interface FirebasePropertyClient {

    //-------------
    // QUERY - GET
    //-------------

    // -- QUERY :: GET PROPERTIES COLLECTION INTO USER COLLECTION -->
    CollectionReference getPropertiesCollection(String userId);

    // -- QUERY :: GET ALL PROPERTIES IN FIREBASE DATABASE -->
    Task<QuerySnapshot> getAllProperties();

    // -- QUERY :: GET PROPERTY IN FIREBASE DATABASE -->
    Task<DocumentSnapshot> getProperty(String userId, String propertyId);

    // -- QUERY :: GET ALL PROPERTIES ACCORDING TO USER IN FIREBASE DATABASE -->
    Task<QuerySnapshot> getPropertyDocuments(String userId);

    //---------------------------
    // CREATE - UPDATE - DELETE
    //---------------------------
    // -- CREATE :: PROPERTY IN FIREBASE DATABASE -->
    Task<Void> createProperty(String propertyId, PropertyModel property, String userId) ;

    // -- UPDATE :: PROPERTY IN FIREBASE DATABASE -->
    Task<Void> updateProperty(String propertyId, String userId, PropertyModel property);

    // -- DELETE :: PROPERTY IN FIREBASE DATABASE -->
    Task<Void> deleteProperty(String propertyId, PropertyModel property, String userId) ;
}
