package com.openclassrooms.realestatemanager.MVVM.repositories.room;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.openclassrooms.realestatemanager.MVVM.databases.room.DAO.UserDAO;
import com.openclassrooms.realestatemanager.model.UserModel;
import java.util.List;

public class RoomUserRepository {

    //-----------
    // VARIABLES
    //-----------
    private final UserDAO userDAO;
    private UserModel user;

    /** CONSTRUCTOR */
    public RoomUserRepository(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // 1 -- QUERIES : -->
    //-----------------------
    // -- QUERY :: GET ALL USERS -->
    public LiveData<List<UserModel>> getAllUsers(){
        return this.userDAO.getUsers();
    }

    // -- QUERY :: GET USER -->
    public UserModel getUser(String userId){
    AsyncTask.execute(() -> user = userDAO.getUser(userId));
        return user;
    }

    // 2 -- CREATE - UPDATE - DELETE -->
    //------------------------------------
    // -- INSERT :: USER -->
    public void createUser(UserModel user){
        this.userDAO.insertUser(user);
    }

    // -- DELETE :: USER -->
    public void deleteUser(UserModel user){
        this.userDAO.deleteUser(user);
    }
}
