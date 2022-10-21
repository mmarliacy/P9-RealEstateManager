package com.openclassrooms.realestatemanager.MVVM.repositories.firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class FirebaseUserHelper {

    //-------------
    // QUERY - GET
    //-------------
    // -- QUERY :: GET CONNECTED USER OR CREATE NEW USER -->
    public FirebaseUser getUser() {
        return FirebaseAuth.getInstance().getCurrentUser();
    }
}
