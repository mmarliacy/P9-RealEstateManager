package com.openclassrooms.realestatemanager.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

@Entity(tableName = "user_table")
public class UserModel {

    //-----------
    // VARIABLES
    //-----------
    @PrimaryKey
    @ColumnInfo(name = "id", index = true)
    @NonNull
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
