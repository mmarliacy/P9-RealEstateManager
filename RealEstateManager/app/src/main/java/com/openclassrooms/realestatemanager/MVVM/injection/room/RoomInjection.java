package com.openclassrooms.realestatemanager.MVVM.injection.room;

import android.content.Context;
import com.openclassrooms.realestatemanager.MVVM.databases.room.RemDatabase;
import com.openclassrooms.realestatemanager.MVVM.repositories.room.RoomPropertyRepository;
import com.openclassrooms.realestatemanager.MVVM.repositories.room.RoomUserRepository;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RoomInjection {

    //-------------------
    // OBJECTS INJECTION
    //-------------------
    // 1 -- PROPERTY REPOSITORY -->
    public static RoomPropertyRepository providePropertyDataSource(Context context){
        RemDatabase database = RemDatabase.getInstance(context);
        return new RoomPropertyRepository(database.propertyDAO());
    }

    // 2 -- USER REPOSITORY -->
    public static RoomUserRepository provideUserDataSource(Context context){
        RemDatabase database = RemDatabase.getInstance(context);
        return new RoomUserRepository(database.userDAO());
    }

    // 3 -- EXECUTOR FOR CREATE A NEW THREAD -->
    public static Executor provideExecutor(){
        return Executors.newSingleThreadExecutor();
    }

    // 4 -- LOCAL VIEW MODEL FACTORY - INJECT OBJECTS -->
    public static RoomViewModelFactory provideViewModelFactory(Context pContext){
        RoomPropertyRepository propertyRepository = providePropertyDataSource(pContext);
        RoomUserRepository userRepository = provideUserDataSource(pContext);
        Executor executor = provideExecutor();
    return new RoomViewModelFactory(propertyRepository, userRepository, executor);
    }
}
