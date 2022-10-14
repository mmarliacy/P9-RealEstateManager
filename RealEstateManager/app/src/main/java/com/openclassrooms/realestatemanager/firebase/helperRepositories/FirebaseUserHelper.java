package com.openclassrooms.realestatemanager.firebase.helperRepositories;


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
