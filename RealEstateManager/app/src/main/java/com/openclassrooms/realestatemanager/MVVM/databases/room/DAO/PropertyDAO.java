package com.openclassrooms.realestatemanager.MVVM.databases.room.DAO;

import android.database.Cursor;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.openclassrooms.realestatemanager.model.PropertyModel;

import java.util.List;

@Dao
public interface PropertyDAO {

    @Query("SELECT * FROM property_table")
    LiveData<List<PropertyModel>> getAllProperties();

    @Query("SELECT * FROM property_table WHERE userId == :userId")
    Cursor getPropertyWithCursor(String userId);

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    long insertProperty(PropertyModel property);

    @Update (onConflict = OnConflictStrategy.IGNORE)
    int updateProperty(PropertyModel property);

    //--------------------------------------
    // -- QUERY DATABASE : MULTIPLE FILTERS
    //--------------------------------------
    @Query("SELECT * FROM property_table WHERE prop_id "+
            "AND type= :type " +
            "AND room_number >= :minRooms " +
            "AND total_living_area BETWEEN :minArea AND :maxArea "+
            "AND price BETWEEN :minPrice AND :maxPrice "+
            "AND status= :status ")
    LiveData<List<PropertyModel>> getAllPropertiesFiltered(
            String type, int minRooms, int minArea, int maxArea, int minPrice,
            int maxPrice, String status);
}
