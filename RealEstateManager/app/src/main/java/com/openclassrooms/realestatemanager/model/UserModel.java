package com.openclassrooms.realestatemanager.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Arrays;
import java.util.List;

@Entity(tableName = "user_table")
public class UserModel {

    //-----------
    // VARIABLES
    //-----------
    @PrimaryKey
    private int id;
    private String name;
    private String mail;
    private String photo;
    private List<String> propertyId;

    /** EMPTY CONSTRUCTOR */
    public UserModel() {
    }

    /** ROOM CONSTRUCTOR */
    public UserModel(int pId, String pName, String pMail, String pPhoto) {
        id = pId;
        name = pName;
        mail = pMail;
        photo = pPhoto;
    }

    /** FIREBASE CONSTRUCTOR */
    public UserModel(int pId, String pName, String pMail, String pPhoto, List<String> pPropertyId) {
        id = pId;
        name = pName;
        mail = pMail;
        photo = pPhoto;
        propertyId = pPropertyId;
    }

    //-------------
    // DUMMY_USERS
    //-------------
    public static List<UserModel> FAKE_USERS = Arrays.asList(
            new UserModel(1, "Patrick Markov", "patrick.marcov@gmail.com", "https://images.app.goo.gl/kz16xd9BkwMC7MnUA"),
            new UserModel(2, "Charlotte Claire", "cha.claire@orange.fr", "https://images.app.goo.gl/ubgV5t9YCvxGAc7x5"),
            new UserModel(3, "Klaus Michelson", "mick_klaus@yahoo.fr", "https://images.app.goo.gl/q61vHLYZLwgJMX886"));

    /**
     * Getters
     */
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public String getPhoto() {
        return photo;
    }

    public List<String> getPropertyId() {
        return propertyId;
    }

    /**
     * Setters
     */
    public void setId(int pId) {
        id = pId;
    }

    public void setName(String pName) {
        name = pName;
    }

    public void setMail(String pMail) {
        mail = pMail;
    }

    public void setPhoto(String pPhoto) {
        photo = pPhoto;
    }

    public void setPropertyId(List<String> pPropertyId) {
        propertyId = pPropertyId;
    }
}
