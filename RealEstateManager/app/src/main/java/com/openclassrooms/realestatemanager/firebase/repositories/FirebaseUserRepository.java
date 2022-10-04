package com.openclassrooms.realestatemanager.firebase.repositories;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.openclassrooms.realestatemanager.firebase.database.client.FirebaseUserClient;
import com.openclassrooms.realestatemanager.model.UserModel;

public class FirebaseUserRepository {

    private final FirebaseUserClient fFirebaseUserClient;

    public FirebaseUserRepository(FirebaseUserClient pFirebaseUserClient) {
        fFirebaseUserClient = pFirebaseUserClient;
    }

    //-------------
    // QUERY - GET
    //-------------
    // -- QUERY :: GET USERS COLLECTION IN FIREBASE DATABASE -->
    public CollectionReference getUsersCollection() {
        return this.fFirebaseUserClient.getUsersCollection();
    }

    // -- QUERY :: GET USER IN FIREBASE DATABASE -->
    public Task<DocumentSnapshot> getUser(String userId){
        return this.fFirebaseUserClient.getUser(userId);
    }

    //--------
    // CREATE
    //--------
    // -- CREATE :: USER IN FIREBASE DATABASE -->
    public Task<Void> createUser(String userId, UserModel user) {
        return this.fFirebaseUserClient.createUser(userId, user);
    }
}
