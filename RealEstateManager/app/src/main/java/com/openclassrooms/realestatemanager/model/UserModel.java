package com.openclassrooms.realestatemanager.model;

import static com.openclassrooms.realestatemanager.model.DummyListCallback.getDummyUsers;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
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

    /** Getters */
    public int getId() {
        return id;
    }

    /**
     * Returns the user with the given unique identifier, or null if no user with that
     * identifier can be found.
     */
    @Nullable
    public static UserModel getUserById(long id) {
        for (UserModel user : getDummyUsers()) {
            if (user.id == id)
                return user;
        }
        return null;
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

    /** Setters */
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
