package com.openclassrooms.realestatemanager.MVVM.repositories.room;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.MVVM.databases.room.DAO.PropertyDAO;
import com.openclassrooms.realestatemanager.model.PropertyModel;

import java.util.List;

public class RoomPropertyRepository {

    //-----------
    // VARIABLES
    //-----------
    private final PropertyDAO propertyDAO;

    /**
     * CONSTRUCTOR
     */
    public RoomPropertyRepository(PropertyDAO pPropertyDAO) {
        this.propertyDAO = pPropertyDAO;
    }

    // 1 -- QUERIES : -->
    //-----------------------
    // -- QUERY :: GET ALL PROPERTIES -->
    public LiveData<List<PropertyModel>> getAllProperties() {
        return this.propertyDAO.getAllProperties();
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

    // 3 -- QUERIES : --> MULTIPLE FILTERS
    //--------------------------------------
    public LiveData<List<PropertyModel>> getAllPropertiesFiltered(
            String type, int minRooms, int minArea, int maxArea, int minPrice,
            int maxPrice, String status){
        return this.propertyDAO.getAllPropertiesFiltered(type, minRooms,minArea, maxArea,  minPrice,  maxPrice, status);
    }
}
