package com.openclassrooms.realestatemanager.model;

import static com.openclassrooms.realestatemanager.model.DummyListCallback.getDummyUsers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import java.util.List;
import java.util.Objects;

@Entity(tableName = "user_table")
public class UserModel {

    //-----------
    // VARIABLES
    //-----------
    @PrimaryKey
    @NonNull
    private String id;
    private String name;
    private String mail;
    private List<String> propertyId;


    public UserModel() {
    }

    /** ROOM CONSTRUCTOR */
    public UserModel(String id, String name, String mail) {
        this.id = id;
        this.name = name;
        this.mail = mail;
    }


    /** Getters */
    public String getId() {
        return id;
    }



    public String getName() {
        return name;
    }

    public String getMail() {
        return mail;
    }

    public List<String> getPropertyId() {
        return propertyId;
    }

    /** Setters */
    public void setId(String pId) {
        id = pId;
    }

    public void setName(String pName) {
        name = pName;
    }

    public void setMail(String pMail) {
        mail = pMail;
    }

    public void setPropertyId(List<String> pPropertyId) {
        propertyId = pPropertyId;
    }
}
