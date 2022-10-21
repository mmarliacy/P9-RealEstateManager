package com.openclassrooms.realestatemanager.model;

import androidx.room.ColumnInfo;
import androidx.room.PrimaryKey;
import java.io.Serializable;

/** This model class is built for interest list */

public class InterestModel implements Serializable {

    //-----------
    // VARIABLES
    //-----------
    @PrimaryKey
    @ColumnInfo(name = "id")
    private int id;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "icon")
    private int icon;

    /** CONSTRUCTOR */
    public InterestModel(int pId, String pName, int pIcon) {
        id = pId;
        name = pName;
        icon = pIcon;
    }

    /** Getters */
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getIcon() {
        return icon;
    }


    /** Setters */
    public void setId(int pId) {
        id = pId;
    }

    public void setName(String pName) {
        name = pName;
    }

    public void setIcon(int pIcon) {
        icon = pIcon;
    }
}
