package com.openclassrooms.realestatemanager.local.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.local.database.DAO.PropertyDAO;
import com.openclassrooms.realestatemanager.model.PropertyModel;

import java.util.List;

public class LocalPropertyRepository {

    //-----------
    // VARIABLES
    //-----------
    private final PropertyDAO propertyDAO;

    /** CONSTRUCTOR */
    public LocalPropertyRepository(PropertyDAO pPropertyDAO) {
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
