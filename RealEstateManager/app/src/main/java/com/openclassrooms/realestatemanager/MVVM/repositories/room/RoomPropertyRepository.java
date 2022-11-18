package com.openclassrooms.realestatemanager.MVVM.repositories.room;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.openclassrooms.realestatemanager.MVVM.databases.room.DAO.PropertyDAO;
import com.openclassrooms.realestatemanager.model.PropertyModel;
import java.util.List;

public class RoomPropertyRepository {

    //-----------
    // VARIABLES
    //-----------
    private final PropertyDAO propertyDAO;

    /** CONSTRUCTOR */
    public RoomPropertyRepository(PropertyDAO pPropertyDAO) {
        this.propertyDAO = pPropertyDAO;
    }

    // 1 -- QUERIES : -->
    //-----------------------

    // -- QUERY :: GET ALL PROPERTIES -->
    public LiveData<List<PropertyModel>> getAllProperties() {
        return this.propertyDAO.getAllProperties();
    }

    // -- QUERY :: GET ALL PROPERTIES ACCORDING TO USER -->
    public LiveData<List<PropertyModel>> getAllPropertiesByUser(long userId) {
        return this.propertyDAO.getAllPropertiesByUser(userId);
    }

    // 2 -- CREATE - UPDATE - DELETE -->
    //------------------------------------
    // -- INSERT :: PROPERTY -->
    public void addProperty(PropertyModel property) {
        this.propertyDAO.insertProperty(property);
    }

    // -- UPDATE :: PROPERTY -->
    public void updateProperty(PropertyModel property) {
        this.propertyDAO.updateProperty(property);
    }

    // -- DELETE :: PROPERTY -->
    public void deleteProperty(PropertyModel property) {
        this.propertyDAO.deleteProperty(property);
    }
}
