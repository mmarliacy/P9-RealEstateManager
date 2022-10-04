package com.openclassrooms.realestatemanager.firebase.factory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.firebase.repositories.FirebasePropertyRepository;
import com.openclassrooms.realestatemanager.firebase.repositories.FirebaseUserRepository;
import com.openclassrooms.realestatemanager.firebase.viewmodel.FirebaseViewModel;
import com.openclassrooms.realestatemanager.local.viewmodel.LocalPropertyViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

public class FirebaseViewModelFactory implements ViewModelProvider.Factory {

    //-- REPOSITORIES & EXECUTOR -->
    private final FirebasePropertyRepository fFirebasePropertyRepository;
    private final FirebaseUserRepository fFirebaseUserRepository;

    public FirebaseViewModelFactory(FirebasePropertyRepository pFirebasePropertyRepository,
                                    FirebaseUserRepository pFirebaseUserRepository) {
        fFirebasePropertyRepository = pFirebasePropertyRepository;
        fFirebaseUserRepository = pFirebaseUserRepository;
    }

    //-- UNIQUE INSTANCE OF VIEW MODEL -->
    @NotNull
    public <T extends ViewModel> T create(Class<T>  modelClass){
        if(modelClass.isAssignableFrom(FirebaseViewModel.class)){
            return (T) new FirebaseViewModel(fFirebasePropertyRepository, fFirebaseUserRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel Class");
    }


}
