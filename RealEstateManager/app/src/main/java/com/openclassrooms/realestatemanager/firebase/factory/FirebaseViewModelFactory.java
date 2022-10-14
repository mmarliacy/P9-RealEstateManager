package com.openclassrooms.realestatemanager.firebase.factory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.openclassrooms.realestatemanager.firebase.helperRepositories.FirebasePropertyHelper;
import com.openclassrooms.realestatemanager.firebase.helperRepositories.FirebaseUserHelper;
import com.openclassrooms.realestatemanager.firebase.viewmodel.FirebaseViewModel;
import org.jetbrains.annotations.NotNull;

public class FirebaseViewModelFactory implements ViewModelProvider.Factory {

    // 1 -- REPOSITORIES & EXECUTOR -->
    private final FirebasePropertyHelper fFirebasePropertyRepository;
    private final FirebaseUserHelper fFirebaseUserRepository;

    /** CONSTRUCTOR */
    public FirebaseViewModelFactory(FirebasePropertyHelper pFirebasePropertyRepository,
                                    FirebaseUserHelper pFirebaseUserRepository) {
        fFirebasePropertyRepository = pFirebasePropertyRepository;
        fFirebaseUserRepository = pFirebaseUserRepository;
    }

    // 2 -- UNIQUE INSTANCE OF FIREBASE VIEW MODEL -->
    @NotNull
    public <T extends ViewModel> T create(Class<T>  modelClass){
        if(modelClass.isAssignableFrom(FirebaseViewModel.class)){
            return (T) new FirebaseViewModel(fFirebasePropertyRepository, fFirebaseUserRepository);
        }
        throw new IllegalArgumentException("Unknown ViewModel Class");
    }
}
