package com.openclassrooms.realestatemanager.MVVM.databases.room.DAO;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;
import com.openclassrooms.realestatemanager.model.PropertyModel;
import java.util.List;

@Dao
public interface PropertyDAO {

    @Query("SELECT * FROM property_table WHERE userId == :userId")
    LiveData<List<PropertyModel>> getAllPropertiesByUser (long userId);

    @Query("SELECT * FROM property_table")
    LiveData<List<PropertyModel>> getAllProperties();

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    void insertProperty(PropertyModel property);

    @Update (onConflict = OnConflictStrategy.IGNORE)
    void updateProperty(PropertyModel property);

    @Delete
    void deleteProperty(PropertyModel property);
}
