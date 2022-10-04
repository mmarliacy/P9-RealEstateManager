package com.openclassrooms.realestatemanager.firebase.database.helper;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.openclassrooms.realestatemanager.firebase.database.client.FirebaseUserClient;
import com.openclassrooms.realestatemanager.model.UserModel;

public class FirebaseUserHelper implements FirebaseUserClient {

    private static final String USERS = "users";

    //-------------
    // QUERY - GET
    //-------------
    // -- QUERY :: GET USERS COLLECTION IN FIREBASE DATABASE -->
    public CollectionReference getUsersCollection() {
        return FirebaseFirestore.getInstance().collection(USERS);
    }

    // -- QUERY :: GET USER IN FIREBASE DATABASE -->
    public Task<DocumentSnapshot> getUser(String userId){
        return getUsersCollection().document(userId).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot pDocumentSnapshot) {
                
            }
        });
    }

    //--------
    // CREATE
    //--------
    // -- CREATE :: USER IN FIREBASE DATABASE -->
    public Task<Void> createUser(String userId, UserModel user) {
        return getUsersCollection().document(userId).set(user);
    }
}
