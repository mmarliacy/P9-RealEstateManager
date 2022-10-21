package com.openclassrooms.realestatemanager.view.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.openclassrooms.realestatemanager.MVVM.repositories.room.RoomPropertyRepository;
import com.openclassrooms.realestatemanager.MVVM.repositories.room.RoomUserRepository;
import com.openclassrooms.realestatemanager.model.PropertyModel;
import com.openclassrooms.realestatemanager.model.UserModel;
import java.util.List;
import java.util.concurrent.Executor;

public class RoomViewModel extends ViewModel {

    // 1 -- REPOSITORIES & EXECUTOR -->
    private final RoomPropertyRepository fPropertyRepository;
    private final RoomUserRepository fUserRepository;
    private final Executor fExecutor;

    // 2 -- DATA -->
    private LiveData<List<PropertyModel>> allProperties;
    private List<UserModel> allUsers;

    /** CONSTRUCTOR */
    public RoomViewModel(RoomPropertyRepository pPropertyRepository, RoomUserRepository pUserRepository, Executor pExecutor) {
        fPropertyRepository = pPropertyRepository;
        fUserRepository = pUserRepository;
        fExecutor = pExecutor;
    }

    // 3 -- GET LAST DATA -->
    // -- PROPERTIES -->
    public void initPropertiesList() {
        if (this.allProperties != null) {
            return;
        }
            allProperties = fPropertyRepository.getAllProperties();
    }

    //-----------------------------------
    // 4 -- QUERY DATABASE :: PROPERTIES
    //-----------------------------------
    // -- QUERIES : -->
    //-----------------------

    // -- QUERY :: GET ALL PROPERTIES -->
    public LiveData<List<PropertyModel>> getAllProperties(){
        return this.allProperties;
    }

    // -- QUERY :: GET ALL PROPERTIES ACCORDING TO USER -->
    public LiveData<List<PropertyModel>> getAllPropertiesByUser(long userId){
        return this.fPropertyRepository.getAllPropertiesByUser(userId);
    }
    //---------------------------------------------------------------------------------------
    // -- CREATE - UPDATE - DELETE -->
    //------------------------------------
    // -- INSERT :: PROPERTY -->
    public void addProperty(PropertyModel property) {
        fExecutor.execute(() -> this.fPropertyRepository.addProperty(property));
    }

    // -- UPDATE :: PROPERTY -->
    public void updateProperty(PropertyModel property) {
        fExecutor.execute(() -> this.fPropertyRepository.updateProperty(property));
    }

    // -- DELETE :: PROPERTY -->
    public void deleteProperty(PropertyModel property) {
        fExecutor.execute(() -> this.fPropertyRepository.deleteProperty(property));
    }

    //------------------------------
    // 5 -- QUERY DATABASE :: USERS
    //------------------------------
    // -- QUERIES : -->
    //-----------------------
    // -- QUERY :: GET ALL USERS -->
    public List<UserModel> getUsers() {
        return this.allUsers;
    }
    //---------------------------------------------------------------------------------------
    // -- CREATE - UPDATE - DELETE -->
    //------------------------------------
    // -- INSERT :: USER -->
    public void insertUser(UserModel user) {
        fExecutor.execute(() -> fUserRepository.createUser(user));
    }

    // -- DELETE :: USER -->
    public void deleteUser(UserModel user) {
        fExecutor.execute(() -> this.fUserRepository.deleteUser(user));
    }
}
