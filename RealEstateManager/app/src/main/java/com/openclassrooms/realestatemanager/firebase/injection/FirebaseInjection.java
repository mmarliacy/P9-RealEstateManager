package com.openclassrooms.realestatemanager.firebase.injection;

import com.openclassrooms.realestatemanager.firebase.database.helper.FirebasePropertyHelper;
import com.openclassrooms.realestatemanager.firebase.database.helper.FirebaseUserHelper;
import com.openclassrooms.realestatemanager.firebase.factory.FirebaseViewModelFactory;
import com.openclassrooms.realestatemanager.firebase.repositories.FirebasePropertyRepository;
import com.openclassrooms.realestatemanager.firebase.repositories.FirebaseUserRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class FirebaseInjection {

    //----------------------------------------
    //-- Inject Objects for unique instance --
    //----------------------------------------
    // -- PROPERTY REPOSITORY -->
    public static FirebasePropertyRepository providePropertyDataSource(){
        FirebasePropertyHelper propertyInstance = new FirebasePropertyHelper();
        return new FirebasePropertyRepository(propertyInstance);
    }

    // -- USER REPOSITORY -->
    public static FirebaseUserRepository provideUserDataSource(){
        FirebaseUserHelper userInstance = new FirebaseUserHelper();
        return new FirebaseUserRepository(userInstance);
    }

    // -- VIEW MODEL FACTORY OBJECTS -->
    public static FirebaseViewModelFactory provideFirebaseViewModelFactory(){
        FirebasePropertyRepository firebasePropertyRepository = providePropertyDataSource();
        FirebaseUserRepository firebaseUserRepository = provideUserDataSource();
        return new FirebaseViewModelFactory(firebasePropertyRepository, firebaseUserRepository);
    }
}
