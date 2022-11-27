package com.openclassrooms.realestatemanager.MVVM.repositories.room;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Query;

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

    // 3 -- QUERIES : --> MULTIPLE FILTERS
    //--------------------------------------
    // -- By type -->
    public LiveData<List<PropertyModel>> getAllPropertiesByType(
            String type, String minRooms,String minArea,String maxArea,String address,
            String minPrice,String maxPrice, String status) {
        return this.propertyDAO.getAllPropertiesByType(
                type, minRooms, minArea, maxArea, address,
                minPrice, maxPrice, status);
    }

    public LiveData<List<PropertyModel>> getAllPropertiesOneByOne (
            String type, String minRooms , String minArea, String maxArea,
            String minPrice, String maxPrice, String status, String onSaleAfter, String onSaleBefore){
        return this.propertyDAO.getAllPropertiesOneByOne(type, minRooms,minArea, maxArea,  minPrice,  maxPrice, status, onSaleAfter, onSaleBefore);
    }


}
