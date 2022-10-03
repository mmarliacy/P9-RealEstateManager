package com.openclassrooms.realestatemanager.repositories;


import com.openclassrooms.realestatemanager.database.DAO.UserDAO;
import com.openclassrooms.realestatemanager.model.UserModel;

import java.util.List;

public class UserRepository {

    private final UserDAO userDAO;

    /**
     * Constructor
     */
    public UserRepository(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    // -- QUERY :: GET ALL USERS -->
    public List<UserModel> getAllUsers(){
        return this.userDAO.getUsers();
    }

    // -- INSERT :: USER -->
    public void createUser(UserModel user){
        this.userDAO.insertUser(user);
    }

    // -- DELETE :: USER -->
    public void deleteUser(UserModel user){
        this.userDAO.deleteUser(user);
    }
}
