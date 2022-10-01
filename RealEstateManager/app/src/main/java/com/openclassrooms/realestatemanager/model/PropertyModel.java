package com.openclassrooms.realestatemanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.List;

/** This model class implement Parcelable */
public class PropertyModel implements Parcelable {

    private String name,type,address,description;
    private int totalLeavingArea;
    private int rooms;
    private int[] photoProperty;
    private List<String> propertyInterest;
    private Date onSaleDate;
    private Date soldDate;

    /** Empty Constructor */
    public PropertyModel() {
    }

    /**
     * Constructor
     */
    public PropertyModel(String pName, String pType, int pTotalLeavingArea, int pRooms,
                         int[] pPhotoProperty, String pAddress, List<String> pPropertyInterest,
                         Date pOnSaleDate, Date pSoldDate, String pDescription) {
        name = pName;
        type = pType;
        totalLeavingArea = pTotalLeavingArea;
        rooms = pRooms;
        photoProperty = pPhotoProperty;
        address = pAddress;
        propertyInterest = pPropertyInterest;
        onSaleDate = pOnSaleDate;
        soldDate = pSoldDate;
        description = pDescription;}

    /**
     * Getters & Setters
     */
    public String getName() {
        return name;
    }

    public void setName(String pName) {
        name = pName;
    }

    public String getType() {
        return type;
    }

    public void setType(String pType) {
        type = pType;
    }

    public int getTotalLeavingArea() {
        return totalLeavingArea;
    }

    public void setTotalLeavingArea(int pTotalLeavingArea) {
        totalLeavingArea = pTotalLeavingArea;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int pRooms) {
        rooms = pRooms;
    }

    public int[] getPhotoProperty() {
        return photoProperty;
    }

    public void setPhotoProperty(int[] pPhotoProperty) {
        photoProperty = pPhotoProperty;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String pAddress) {
        address = pAddress;
    }

    public List<String> getPropertyInterest() {
        return propertyInterest;
    }

    public void setPropertyInterest(List<String> pPropertyInterest) {
        propertyInterest = pPropertyInterest;
    }

    public Date getOnSaleDate() {
        return onSaleDate;
    }

    public void setOnSaleDate(Date pOnSaleDate) {
        onSaleDate = pOnSaleDate;
    }

    public Date getSoldDate() {
        return soldDate;
    }

    public void setSoldDate(Date pSoldDate) {
        soldDate = pSoldDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String pDescription) {
        description = pDescription;
    }

    /**
     * PARCELABLE IMPLEMENTATION
     */

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel pParcel, int pI) {
        pParcel.writeString(name);
        pParcel.writeString(type);
        pParcel.writeString(address);
        pParcel.writeString(description);
        pParcel.writeInt(totalLeavingArea);
        pParcel.writeInt(rooms);
        pParcel.writeIntArray(photoProperty);
        pParcel.writeStringList(propertyInterest);
    }

    protected PropertyModel(Parcel in) {
        name = in.readString();
        type = in.readString();
        address = in.readString();
        description = in.readString();
        totalLeavingArea = in.readInt();
        rooms = in.readInt();
        photoProperty = in.createIntArray();
        propertyInterest = in.createStringArrayList();
    }

    public static final Creator<PropertyModel> CREATOR = new Creator<>() {
        @Override
        public PropertyModel createFromParcel(Parcel in) {
            return new PropertyModel(in);
        }

        @Override
        public PropertyModel[] newArray(int size) {
            return new PropertyModel[size];
        }
    };
}
