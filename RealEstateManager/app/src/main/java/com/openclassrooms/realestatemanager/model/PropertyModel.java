package com.openclassrooms.realestatemanager.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import java.util.List;


@Entity(tableName = "property_table", foreignKeys =
        @ForeignKey(
        entity = UserModel.class,
        parentColumns = "id",
        childColumns = "userId",
        onDelete = ForeignKey.CASCADE)
)
public class PropertyModel implements Parcelable {

    //-----------
    // VARIABLES
    //-----------
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "prop_id", index = true)
    private long id;

    @ColumnInfo(name = "userId")
    private int userId;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "type")
    private String type;

    @ColumnInfo(name = "address")
    private String address;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "total_living_area")
    private String totalLeavingArea;

    @ColumnInfo(name = "room_number")
    private String rooms;

    @ColumnInfo(name = "price")
    private String price;

    @ColumnInfo(name = "status")
    private String status;

    @ColumnInfo(name = "photos_list")
    private List<String> photoProperty;

    @ColumnInfo(name = "property_interest")
    private List<String> propertyInterest;

    @ColumnInfo(name = "on_sale_date")
    private String onSaleDate;


    @ColumnInfo(name = "sold_date")
    private String soldDate;

    /** EMPTY CONSTRUCTOR */
    public PropertyModel() {
    }

    /** CONSTRUCTOR */
    public PropertyModel(int pUserId, String pName, String pType, String pAddress,
                         String pDescription, String pTotalLeavingArea, String pRooms, String pPrice,
                         String pStatus, List<String> pPhotoProperty, List<String> pPropertyInterest,
                         String pOnSaleDate, String pSoldDate) {
        this.setUserId(pUserId);
        this.setName(pName);
        this.setType(pType);
        this.setAddress(pAddress);
        this.setPrice(pPrice);
        this.setDescription(pDescription);
        this.setTotalLeavingArea(pTotalLeavingArea);
        this.setRooms(pRooms);
        this.setStatus(pStatus);
        this.setPhotoProperty(pPhotoProperty);
        this.setPropertyInterest(pPropertyInterest);
        this.setOnSaleDate(pOnSaleDate);
        this.setSoldDate(pSoldDate);
    }



    /** Getters */
    public long getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    /** Returns the user associated to the property */
    public UserModel getUser() {
        return UserModel.getUserById(userId);
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public String getAddress() {
        return address;
    }

    public String getDescription() {
        return description;
    }

    public String getTotalLeavingArea() {
        return totalLeavingArea;
    }

    public String getRooms() {
        return rooms;
    }

    public String getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getPhotoProperty() {
        return photoProperty;
    }

    public List<String> getPropertyInterest() {
        return propertyInterest;
    }

    public String getOnSaleDate() {
        return onSaleDate;
    }

    public String getSoldDate() {
        return soldDate;
    }

    /** Setters */
    public void setId(long pId) {
        id = pId;
    }

    public void setUserId(int pUserId) {
        userId = pUserId;
    }

    public void setName(String pName) {
        name = pName;
    }

    public void setType(String pType) {
        type = pType;
    }

    public void setAddress(String pAddress) {
        address = pAddress;
    }

    public void setDescription(String pDescription) {
        description = pDescription;
    }

    public void setRooms(String pRooms) {
        rooms = pRooms;
    }

    public void setTotalLeavingArea(String pTotalLeavingArea) {
        totalLeavingArea = pTotalLeavingArea;
    }

    public void setPrice(String pPrice) {
        price = pPrice;
    }

    public void setStatus(String pStatus) {
        status = pStatus;
    }

    public void setPhotoProperty(List<String> pPhotoProperty) {
        photoProperty = pPhotoProperty;
    }

    public void setPropertyInterest(List<String> pPropertyInterest) {
        propertyInterest = pPropertyInterest;
    }

    public void setOnSaleDate(String pOnSaleDate) {
        onSaleDate = pOnSaleDate;
    }

    public void setSoldDate(String pSoldDate) {
        soldDate = pSoldDate;
    }


    /**
     * PARCELABLE IMPLEMENTATION
     */
    protected PropertyModel(Parcel in) {
        id = in.readLong();
        userId = in.readInt();
        name = in.readString();
        type = in.readString();
        address = in.readString();
        description = in.readString();
        totalLeavingArea = in.readString();
        rooms = in.readString();
        price = in.readString();
        status = in.readString();
        photoProperty = in.createStringArrayList();
        propertyInterest = in.createStringArrayList();
        onSaleDate = in.readString();
        soldDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeInt(userId);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(address);
        dest.writeString(description);
        dest.writeString(totalLeavingArea);
        dest.writeString(rooms);
        dest.writeString(price);
        dest.writeString(status);
        dest.writeStringList(photoProperty);
        dest.writeStringList(propertyInterest);
        dest.writeString(onSaleDate);
        dest.writeString(soldDate);
    }

    @Override
    public int describeContents() {
        return 0;
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
