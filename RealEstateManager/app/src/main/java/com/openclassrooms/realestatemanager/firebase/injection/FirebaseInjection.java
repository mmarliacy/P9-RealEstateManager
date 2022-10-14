package com.openclassrooms.realestatemanager.firebase.injection;

import com.openclassrooms.realestatemanager.firebase.helperRepositories.FirebasePropertyHelper;
import com.openclassrooms.realestatemanager.firebase.helperRepositories.FirebaseUserHelper;
import com.openclassrooms.realestatemanager.firebase.factory.FirebaseViewModelFactory;

public class FirebaseInjection {

    //-------------------
    // OBJECTS INJECTION
    //-------------------
    // 1 -- PROPERTY HELPER REPOSITORY -->
    public static FirebasePropertyHelper providePropertyDataSource() {
        return new FirebasePropertyHelper();
    }

    // 2 -- USER HELPER REPOSITORY -->
    public static FirebaseUserHelper provideUserDataSource() {
        return new FirebaseUserHelper();
    }

    // 3 -- FIREBASE VIEW MODEL FACTORY - INJECT OBJECTS -->
    public static FirebaseViewModelFactory provideFirebaseViewModelFactory() {
        FirebasePropertyHelper propertyFirebaseSource = providePropertyDataSource();
        FirebaseUserHelper userFirebaseSource = provideUserDataSource();
        return new FirebaseViewModelFactory(propertyFirebaseSource, userFirebaseSource);
    }
}
