package com.openclassrooms.realestatemanager.MVVM.databases.room.DAO;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import com.openclassrooms.realestatemanager.model.UserModel;
import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    void insertUser(UserModel user);

    @Delete
    void deleteUser(UserModel user);

    @Query("SELECT * FROM user_table")
    LiveData<List<UserModel>> getUsers();

    @Query("SELECT * FROM user_table WHERE id == :userId")
    UserModel getUser(String userId);
}
