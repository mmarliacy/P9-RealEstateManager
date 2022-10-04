package com.openclassrooms.realestatemanager.firebase.database.client;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.openclassrooms.realestatemanager.model.UserModel;

public interface FirebaseUserClient {

    //-------------
    // QUERY - GET
    //-------------
    // -- QUERY :: GET USERS COLLECTION IN FIREBASE DATABASE -->
    CollectionReference getUsersCollection();

    // -- QUERY :: GET USER IN FIREBASE DATABASE -->
    Task<DocumentSnapshot> getUser(String userId);

    //--------
    // CREATE
    //--------
    // -- CREATE :: USER IN FIREBASE DATABASE -->
    Task<Void> createUser(String documentId, UserModel user);

}
