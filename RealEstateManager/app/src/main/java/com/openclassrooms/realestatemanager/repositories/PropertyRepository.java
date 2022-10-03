package com.openclassrooms.realestatemanager.repositories;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.database.DAO.PropertyDAO;
import com.openclassrooms.realestatemanager.model.PropertyModel;
import com.openclassrooms.realestatemanager.model.UserModel;

import java.util.List;

public class PropertyRepository {

    private final PropertyDAO propertyDAO;

    /**
     * Constructor
     */
    public PropertyRepository(PropertyDAO pPropertyDAO) {
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
