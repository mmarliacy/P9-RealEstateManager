package com.openclassrooms.realestatemanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.Date;
import java.util.List;

/**
 * This model class implement Parcelable
 */
@Entity(tableName = "property_table", foreignKeys = @ForeignKey(
        entity = UserModel.class,
        parentColumns = "id",
        childColumns = "userId",
        onDelete = ForeignKey.CASCADE)
)
public class PropertyModel implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @ColumnInfo(name = "userId", index = true)
    private long userId;

    private String name, type, address, description;
    private int totalLeavingArea;
    private int rooms;
    private List<String> photoProperty;
    private List<String> propertyInterest;
    private Date onSaleDate;
    private Date soldDate;

    /**
     * Empty Constructor
     */
    public PropertyModel() {
    }

    /**
     * Constructor
     */
    public PropertyModel(long pId, long pUserId, String pName, String pType,
                         String pAddress, String pDescription, int pTotalLeavingArea,
                         int pRooms, List<String> pPhotoProperty,
                         List<String> pPropertyInterest, Date pOnSaleDate, Date pSoldDate) {
        id = pId;
        userId = pUserId;
        name = pName;
        type = pType;
        address = pAddress;
        description = pDescription;
        totalLeavingArea = pTotalLeavingArea;
        rooms = pRooms;
        photoProperty = pPhotoProperty;
        propertyInterest = pPropertyInterest;
        onSaleDate = pOnSaleDate;
        soldDate = pSoldDate;
    }

    /**
     * Getters & Setters
     */
    public long getId() {
        return id;
    }

    public void setId(long pId) {
        id = pId;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long pUserId) {
        userId = pUserId;
    }

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

    public List<String> getPhotoProperty() {
        return photoProperty;
    }

    public void setPhotoProperty(List<String> pPhotoProperty) {
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

    protected PropertyModel(Parcel in) {
        id = in.readLong();
        userId = in.readLong();
        name = in.readString();
        type = in.readString();
        address = in.readString();
        description = in.readString();
        totalLeavingArea = in.readInt();
        rooms = in.readInt();
        photoProperty = in.createStringArrayList();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel pParcel, int pI) {
        pParcel.writeLong(id);
        pParcel.writeLong(userId);
        pParcel.writeString(name);
        pParcel.writeString(type);
        pParcel.writeString(address);
        pParcel.writeString(description);
        pParcel.writeInt(totalLeavingArea);
        pParcel.writeInt(rooms);
        pParcel.writeStringList(photoProperty);
        pParcel.writeStringList(propertyInterest);
    }

}
