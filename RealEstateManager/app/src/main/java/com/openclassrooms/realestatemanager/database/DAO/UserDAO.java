package com.openclassrooms.realestatemanager.database.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.openclassrooms.realestatemanager.model.UserModel;

import java.util.List;

public interface UserDAO {

    @Insert
    void insertUser(UserModel user);

    @Delete
    void deleteUser(UserModel user);

    @Query("SELECT * FROM user_table")
    LiveData<List<UserModel>> getUsers();
}
