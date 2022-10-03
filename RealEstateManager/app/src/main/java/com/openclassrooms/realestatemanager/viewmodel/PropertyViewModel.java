package com.openclassrooms.realestatemanager.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.openclassrooms.realestatemanager.model.PropertyModel;
import com.openclassrooms.realestatemanager.model.UserModel;
import com.openclassrooms.realestatemanager.repositories.PropertyRepository;
import com.openclassrooms.realestatemanager.repositories.UserRepository;

import java.util.List;
import java.util.concurrent.Executor;

public class PropertyViewModel extends ViewModel {

    // 1 -- REPOSITORIES & EXECUTOR -->
    private final PropertyRepository fPropertyRepository;
    private final UserRepository fUserRepository;
    private final Executor fExecutor;

    // 2 -- DATA -->
    LiveData<List<PropertyModel>> allProperties;
    List<UserModel> allUsers;

    /**
     * Constructor
     */
    public PropertyViewModel(PropertyRepository pPropertyRepository, UserRepository pUserRepository, Executor pExecutor) {
        fPropertyRepository = pPropertyRepository;
        fUserRepository = pUserRepository;
        fExecutor = pExecutor;
    }

    // 3 -- GET FIRST DATA -->
    public void initUsersList() {
        if (allUsers == null) {
            allUsers = fUserRepository.getAllUsers();
        }
    }

    //-------------------------------------
    // 4 -- METHODS TO MANAGE USERS LIST --
    //-------------------------------------
    // -- QUERY :: GET ALL USERS -->
    public List<UserModel> getUsers() {
        return this.allUsers;
    }

    // -- INSERT :: USER -->
    public void insertUser(UserModel user) {
        fExecutor.execute(() -> fUserRepository.createUser(user));
    }

    // -- DELETE :: USER -->
    public void deleteUser(UserModel user) {
        fExecutor.execute(() -> this.fUserRepository.deleteUser(user));
    }

    //------------------------------------------
    // 5 -- METHODS TO MANAGE PROPERTIES LIST --
    //------------------------------------------

    // -- QUERY :: GET ALL PROPERTIES -->
    public LiveData<List<PropertyModel>> getAllProperties(){
        return this.fPropertyRepository.getAllProperties();
    }

    // -- QUERY :: GET ALL PROPERTIES ACCORDING TO USER -->
    public LiveData<List<PropertyModel>> getAllPropertiesByUser(long userId){
        return this.fPropertyRepository.getAllPropertiesByUser(userId);
    }

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
}
