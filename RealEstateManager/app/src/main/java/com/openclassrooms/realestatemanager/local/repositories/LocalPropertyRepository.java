package com.openclassrooms.realestatemanager.local.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.local.database.DAO.PropertyDAO;
import com.openclassrooms.realestatemanager.model.PropertyModel;

import java.util.List;

public class LocalPropertyRepository {

    private final PropertyDAO propertyDAO;

    /**
     * Constructor
     */
    public LocalPropertyRepository(PropertyDAO pPropertyDAO) {
        propertyDAO = pPropertyDAO;
    }

    // -- QUERY :: GET ALL PROPERTIES -->
    public LiveData<List<PropertyModel>> getAllProperties() {
        return this.propertyDAO.getAllProperties();
    }

    // -- QUERY :: GET ALL PROPERTIES ACCORDING TO USER -->
    public LiveData<List<PropertyModel>> getAllPropertiesByUser(long userId) {
        return this.propertyDAO.getAllPropertiesByUser(userId);
    }

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
