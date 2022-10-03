package com.openclassrooms.realestatemanager.injection;

import android.content.Context;

import com.openclassrooms.realestatemanager.database.RemDatabase;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;
import com.openclassrooms.realestatemanager.repositories.UserRepository;
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModel;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Injection {

    //----------------------------------------
    //-- Inject Objects for unique instance --
    //----------------------------------------
    // -- PROPERTY REPOSITORY -->
    public static PropertyRepository providePropertyDataSource(Context context){
        RemDatabase database = RemDatabase.getInstance(context);
        return new PropertyRepository(database.propertyDAO());
    }

    // -- USER REPOSITORY -->
    public static UserRepository provideUserDataSource(Context context){
        RemDatabase database = RemDatabase.getInstance(context);
        return new UserRepository(database.userDAO());
    }

    // -- EXECUTOR FOR CREATE A NEW THREAD -->
    public static Executor provideExecutor(){
        return Executors.newSingleThreadExecutor();
    }

    // -- VIEW MODEL FACTORY OBJECTS -->
    public static ViewModelFactory provideViewModelFactory(Context pContext){
        PropertyRepository propertyRepository = providePropertyDataSource(pContext);
        UserRepository userRepository = provideUserDataSource(pContext);
        Executor executor = provideExecutor();
    return new ViewModelFactory(propertyRepository, userRepository, executor);
    }
}
