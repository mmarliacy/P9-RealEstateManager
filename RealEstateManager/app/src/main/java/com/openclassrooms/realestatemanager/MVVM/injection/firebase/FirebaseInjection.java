package com.openclassrooms.realestatemanager.MVVM.injection.firebase;

import com.openclassrooms.realestatemanager.MVVM.repositories.firebase.FirebasePropertyHelper;
import com.openclassrooms.realestatemanager.MVVM.repositories.firebase.FirebaseUserHelper;

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
