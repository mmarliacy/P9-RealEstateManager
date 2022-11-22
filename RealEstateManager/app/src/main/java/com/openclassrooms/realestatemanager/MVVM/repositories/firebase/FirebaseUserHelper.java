package com.openclassrooms.realestatemanager.MVVM.repositories.firebase;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.openclassrooms.realestatemanager.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class FirebaseUserHelper {

    private final String USERS_COLLECTION = "Sellers";

    //-------------
    // QUERY - GET
    //-------------
    // -- QUERY :: GET CONNECTED USER OR CREATE NEW USER -->
    public FirebaseUser getUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }

    // -- QUERY :: GET ALL USERS IN FIREBASE DATABASE -->
    public LiveData<List<UserModel>> getAllUsers() {
        MutableLiveData<List<UserModel>> mutableUsersList = new MutableLiveData<>();
        FirebaseFirestore.getInstance().collection(USERS_COLLECTION).get().addOnSuccessListener(pQueryDocumentSnapshots -> {
            Log.d("FIRESTORE DATA", "FirebaseFirestore.getInstance().collection('Sellers').get() called with SUCCESS!");
            if (!pQueryDocumentSnapshots.isEmpty()) {
                List<UserModel> usersList = new ArrayList<>();
                for (QueryDocumentSnapshot userDocument : pQueryDocumentSnapshots) {
                    UserModel user = userDocument.toObject(UserModel.class);
                    Log.d("FIRESTORE DATA", "user has been collected " + user);
                    usersList.add(user);
                }
                mutableUsersList.setValue(usersList);
                Log.d("FIRESTORE DATA", "Mutable List Data contains : " + mutableUsersList.getValue());
            }
        });
        Log.d("FIRESTORE DATA", "MUTABLE LIVE DATA : has been called  " + mutableUsersList.getValue());
        return mutableUsersList;
    }

    //---------------------------
    // CREATE - UPDATE - DELETE
    //---------------------------
    // -- CREATE :: USER IN FIREBASE DATABASE -->
    public Task<Void> createUser(String uid, UserModel user) {
        return FirebaseFirestore.getInstance().collection(USERS_COLLECTION).document(uid).set(user);
    }
    // -- UPDATE :: USER IN FIREBASE DATABASE -->
    public Task<Void> updateUser(String userId, String propertyId) {
        return FirebaseFirestore.getInstance().collection(USERS_COLLECTION).document(userId)
                .update("propertyId", FieldValue.arrayUnion(propertyId));
    }
}
