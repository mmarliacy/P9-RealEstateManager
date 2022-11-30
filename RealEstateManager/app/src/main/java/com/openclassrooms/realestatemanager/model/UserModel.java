package com.openclassrooms.realestatemanager.model;

import static com.openclassrooms.realestatemanager.model.DummyListCallback.getDummyUsers;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
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
    private String id = "";
    private String name;
    private String mail;


    public UserModel() {
    }

    /** ROOM CONSTRUCTOR */
    public UserModel(@NotNull String id, String name, String mail) {
        this.id = id;
        this.name = name;
        this.mail = mail;
    }


    /** Getters */
    @NotNull
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
    public void setId(@NotNull String pId) {
        id = pId;
    }

    public void setName(String pName) {
        name = pName;
    }

    public void setMail(String pMail) {
        mail = pMail;
    }
}
