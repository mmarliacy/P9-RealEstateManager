package com.openclassrooms.realestatemanager.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Arrays;
import java.util.List;

@Entity(tableName = "user_table")
public class UserModel {

    @PrimaryKey
    private int id;
    private String name;
    private String mail;
    private String photo;

    /**
     * Empty Constructor
     */
    public UserModel() {
    }

    /**
     * Constructor
     */
    public UserModel(int pId, String pName, String pMail, String pPhoto) {
        id = pId;
        name = pName;
        mail = pMail;
        photo = pPhoto;
    }

    public static List<UserModel> DUMMY_USERS = Arrays.asList(
            new UserModel(1, "Patrick Markov", "patrick.marcov@gmail.com", "https://images.app.goo.gl/kz16xd9BkwMC7MnUA"),
            new UserModel(2, "Charlotte Claire", "cha.claire@orange.fr", "https://images.app.goo.gl/ubgV5t9YCvxGAc7x5"),
            new UserModel(3, "Klaus Michelson", "mick_klaus@yahoo.fr", "https://images.app.goo.gl/q61vHLYZLwgJMX886")
            );

    public int getId() {
        return id;
    }

    public void setId(int pId) {
        id = pId;
    }

    public String getName() {
        return name;
    }

    public void setName(String pName) {
        name = pName;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String pMail) {
        mail = pMail;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String pPhoto) {
        photo = pPhoto;
    }
}
