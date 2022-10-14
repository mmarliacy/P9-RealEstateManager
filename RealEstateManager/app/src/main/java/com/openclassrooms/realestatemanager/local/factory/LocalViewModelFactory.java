package com.openclassrooms.realestatemanager.local.factory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.openclassrooms.realestatemanager.local.repositories.LocalPropertyRepository;
import com.openclassrooms.realestatemanager.local.repositories.LocalUserRepository;
import com.openclassrooms.realestatemanager.local.viewmodel.LocalPropertyViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.Executor;

public class LocalViewModelFactory implements ViewModelProvider.Factory {

    // 1 -- REPOSITORIES & EXECUTOR -->
    private final LocalPropertyRepository fPropertyRepository;
    private final LocalUserRepository fUserRepository;
    private final Executor fExecutor;

    /** CONSTRUCTOR */
    public LocalViewModelFactory(LocalPropertyRepository pPropertyRepository, LocalUserRepository pUserRepository, Executor pExecutor) {
        this.fPropertyRepository = pPropertyRepository;
        this.fUserRepository = pUserRepository;
        this.fExecutor = pExecutor;
    }

    // 2 -- UNIQUE INSTANCE OF LOCAL VIEW MODEL -->
    @NotNull
    public <T extends ViewModel> T create(Class<T>  modelClass){
        if(modelClass.isAssignableFrom(LocalPropertyViewModel.class)){
            return (T) new LocalPropertyViewModel(fPropertyRepository, fUserRepository, fExecutor);
        }
        throw new IllegalArgumentException("Unknown ViewModel Class");
    }

}
