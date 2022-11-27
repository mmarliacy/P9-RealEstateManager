package com.openclassrooms.realestatemanager.model;

import static com.openclassrooms.realestatemanager.model.DummyListCallback.getDummyUsers;

import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class UserModel {

    //-----------
    // VARIABLES
    //-----------
    @PrimaryKey
    @ColumnInfo(name = "id", index = true)
    private String id;
    private String name;
    private String mail;


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

    @Nullable
    public static UserModel getUserById(String id) {
        for (UserModel user :  getDummyUsers()) {
            if (user.id.equals(id))
                return user;
        }
        return null;
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
}
