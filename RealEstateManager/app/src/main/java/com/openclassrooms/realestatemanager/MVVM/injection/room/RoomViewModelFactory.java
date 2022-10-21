package com.openclassrooms.realestatemanager.MVVM.injection.room;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.openclassrooms.realestatemanager.MVVM.repositories.room.RoomPropertyRepository;
import com.openclassrooms.realestatemanager.MVVM.repositories.room.RoomUserRepository;
import com.openclassrooms.realestatemanager.view.viewmodel.RoomViewModel;

import org.jetbrains.annotations.NotNull;
import java.util.concurrent.Executor;

public class RoomViewModelFactory implements ViewModelProvider.Factory {

    // 1 -- REPOSITORIES & EXECUTOR -->
    private final RoomPropertyRepository fPropertyRepository;
    private final RoomUserRepository fUserRepository;
    private final Executor fExecutor;

    /** CONSTRUCTOR */
    public RoomViewModelFactory(RoomPropertyRepository pPropertyRepository, RoomUserRepository pUserRepository, Executor pExecutor) {
        this.fPropertyRepository = pPropertyRepository;
        this.fUserRepository = pUserRepository;
        this.fExecutor = pExecutor;
    }

    // 2 -- UNIQUE INSTANCE OF LOCAL VIEW MODEL -->
    @NotNull
    public <T extends ViewModel> T create(Class<T>  modelClass){
        if(modelClass.isAssignableFrom(RoomViewModel.class)){
            //noinspection unchecked
            return (T) new RoomViewModel(fPropertyRepository, fUserRepository, fExecutor);
        }
        throw new IllegalArgumentException("Unknown ViewModel Class");
    }

}
