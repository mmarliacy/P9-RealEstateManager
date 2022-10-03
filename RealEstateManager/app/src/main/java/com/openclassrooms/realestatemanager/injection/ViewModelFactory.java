package com.openclassrooms.realestatemanager.injection;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.repositories.PropertyRepository;
import com.openclassrooms.realestatemanager.repositories.UserRepository;
import com.openclassrooms.realestatemanager.viewmodel.PropertyViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

public class ViewModelFactory implements ViewModelProvider.Factory {

    //-- REPOSITORIES & EXECUTOR -->
    private final PropertyRepository fPropertyRepository;
    private final UserRepository fUserRepository;
    private final Executor fExecutor;

    /**
     * Constructor
     */
    public ViewModelFactory(PropertyRepository pPropertyRepository, UserRepository pUserRepository, Executor pExecutor) {
        this.fPropertyRepository = pPropertyRepository;
        this.fUserRepository = pUserRepository;
        this.fExecutor = pExecutor;
    }

    //-- UNIQUE INSTANCE OF VIEW MODEL -->
    @NotNull
    public <T extends ViewModel> T create(Class<T>  modelClass){
        if(modelClass.isAssignableFrom(PropertyViewModel.class)){
            return (T) new PropertyViewModel(fPropertyRepository, fUserRepository, fExecutor);
        }
        throw new IllegalArgumentException("Unknown ViewModel Class");
    }

}
