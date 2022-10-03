package com.openclassrooms.realestatemanager.database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import com.openclassrooms.realestatemanager.model.PropertyModel;
import java.util.List;

@Dao
public interface PropertyDAO {

    @Insert
    void insertProperty(PropertyModel property);

    @Update
    void updateProperty(PropertyModel property);

    @Delete
    void deleteProperty(PropertyModel property);

    @Query("SELECT * FROM property_table WHERE id == :userId")
    LiveData<List<PropertyModel>> getAllPropertiesByUser (long userId);

    @Query("SELECT * FROM property_table")
    LiveData<List<PropertyModel>> getAllProperties();
}
