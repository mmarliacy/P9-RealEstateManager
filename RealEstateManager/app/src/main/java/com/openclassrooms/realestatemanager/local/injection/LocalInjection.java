package com.openclassrooms.realestatemanager.local.injection;

import android.content.Context;

import com.openclassrooms.realestatemanager.local.factory.LocalViewModelFactory;
import com.openclassrooms.realestatemanager.local.database.RemDatabase;
import com.openclassrooms.realestatemanager.local.repositories.LocalPropertyRepository;
import com.openclassrooms.realestatemanager.local.repositories.LocalUserRepository;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class LocalInjection {

    //----------------------------------------
    //-- Inject Objects for unique instance --
    //----------------------------------------
    // -- PROPERTY REPOSITORY -->
    public static LocalPropertyRepository providePropertyDataSource(Context context){
        RemDatabase database = RemDatabase.getInstance(context);
        return new LocalPropertyRepository(database.propertyDAO());
    }

    // -- USER REPOSITORY -->
    public static LocalUserRepository provideUserDataSource(Context context){
        RemDatabase database = RemDatabase.getInstance(context);
        return new LocalUserRepository(database.userDAO());
    }

    // -- EXECUTOR FOR CREATE A NEW THREAD -->
    public static Executor provideExecutor(){
        return Executors.newSingleThreadExecutor();
    }

    // -- VIEW MODEL FACTORY OBJECTS -->
    public static LocalViewModelFactory provideViewModelFactory(Context pContext){
        LocalPropertyRepository propertyRepository = providePropertyDataSource(pContext);
        LocalUserRepository userRepository = provideUserDataSource(pContext);
        Executor executor = provideExecutor();
    return new LocalViewModelFactory(propertyRepository, userRepository, executor);
    }
}
