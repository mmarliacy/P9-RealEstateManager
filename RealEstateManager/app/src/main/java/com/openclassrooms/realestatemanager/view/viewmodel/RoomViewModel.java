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
    private LiveData<List<UserModel>> allUsers;
    private LiveData<List<PropertyModel>> allProperties;

    /**
     * CONSTRUCTOR
     */
    public RoomViewModel(RoomPropertyRepository pPropertyRepository, RoomUserRepository pUserRepository, Executor pExecutor) {
        fPropertyRepository = pPropertyRepository;
        fUserRepository = pUserRepository;
        fExecutor = pExecutor;
    }

    // 3 -- GET LAST DATA -->
    public void initAllUsers() {
        if (this.allUsers != null) {
            return;
        }
        allUsers = fUserRepository.getAllUsers();
    }

    public void initAllProperties() {
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
    public LiveData<List<PropertyModel>> getAllProperties() {
        return this.allProperties;
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
    public LiveData<List<UserModel>> getAllUsers() {
        return this.allUsers;
    }

    // -- QUERY :: GET USER -->
    public UserModel getUser(String userId) {
        return this.fUserRepository.getUser(userId);
    }

    //---------------------------------------------------------------------------------------
    // -- CREATE - UPDATE - DELETE -->
    //------------------------------------
    // -- INSERT :: USER -->
    public void insertUser(UserModel user) {
        fExecutor.execute(() -> fUserRepository.createUser(user));
    }

    // 6 -- QUERIES : --> MULTIPLE FILTERS
    //---------------------------------------
    public LiveData<List<PropertyModel>> getAllPropertiesFiltered(
            String type, String minRooms, String minArea, String maxArea,
            String minPrice, String maxPrice, String status,
            String onSaleAfter, String onSaleBefore) {
        return this.fPropertyRepository.getAllPropertiesFiltered(
                type, minRooms, minArea, maxArea,
                minPrice, maxPrice,  status, onSaleAfter, onSaleBefore);
    }
}
