package com.openclassrooms.realestatemanager.local.repositories;


import com.openclassrooms.realestatemanager.local.database.DAO.UserDAO;
import com.openclassrooms.realestatemanager.model.UserModel;

import java.util.List;

public class LocalUserRepository {

    //-----------
    // VARIABLES
    //-----------
    private final UserDAO userDAO;

    /** CONSTRUCTOR */
    public LocalUserRepository(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // 1 -- QUERIES : -->
    //-----------------------
    // -- QUERY :: GET ALL USERS -->
    public List<UserModel> getAllUsers(){
        return this.userDAO.getUsers();
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
